package commands;

import java.util.List;

import bot.automato.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Ban extends Command {

	public Ban() {
		super("ban");
	}

	@Override
	public boolean canExecute(GuildMessageReceivedEvent event, Member member) {
		if (member.hasPermission(event.getChannel(), Permission.BAN_MEMBERS))
			return true;
		else
			return false;
	}

	@Override
	public void execute(GuildMessageReceivedEvent event, String[] args) {
		List<Member> membersToBan = event.getMessage().getMentionedMembers();
		if (membersToBan.isEmpty()) {
			event.getChannel().sendMessage("Please mention the user(s) you want to ban.").queue();
		}
		
		else {
			for (Member member : membersToBan) {
				if (event.getGuild().getSelfMember().canInteract(member) && event.getMember().canInteract(member)) {
					event.getChannel().sendMessage(String.format("**%s** was banned from the server.", member.getUser().getAsTag())).queue();
					member.ban(0).queue();
				}
				
				else {
					event.getChannel().sendMessage(String.format("Either you or the bot does not have a high enough role to ban **%s**.", member.getUser().getAsTag())).queue();
				}
			}
		}
		
	}

}
