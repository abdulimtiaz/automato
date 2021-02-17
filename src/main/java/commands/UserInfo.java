package commands;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import bot.automato.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class UserInfo extends Command {
	
	public UserInfo() {
		super("userinfo");
	}

	@Override
	public void execute(GuildMessageReceivedEvent event, String[] args) {
		List<Member> mentionedMembers = event.getMessage().getMentionedMembers();
		
		Member targetMember;
		User targetUser;
		if (mentionedMembers.size() > 1) {
			event.getChannel().sendMessage("Please mention a single user.").queue();
			return;
		}
		
		else if (mentionedMembers.isEmpty()) {
			targetMember = event.getMember();
		}
		
		else {
			targetMember = mentionedMembers.get(0);
		}
		
		targetUser = targetMember.getUser();
		
		MessageEmbed embed = new EmbedBuilder()
				.setTitle(targetUser.getAsTag())
				.addField("Nickname", targetMember.getNickname(), false)
				.addField("ID", targetUser.getId(), false)
				.addField("Account Created", targetUser.getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME), false)
				.addField("Date Joined", targetMember.getTimeJoined().format(DateTimeFormatter.RFC_1123_DATE_TIME), false)
				.addField("Roles", String.join(", ", 
						targetMember
						.getRoles()
						.stream()
						.map(role -> role.getName())
						.collect(Collectors.toList())),
						false)
				.setThumbnail(targetUser.getAvatarUrl())
				.build();
		event.getChannel().sendMessage(embed).queue();
	}

}
