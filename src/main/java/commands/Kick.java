package commands;


import java.util.List;

import bot.automato.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Kick extends Command {

	public Kick() {
		super("kick");
	}
	
	@Override
	public boolean canExecute(GuildMessageReceivedEvent event, Member member) {
		if (member.hasPermission(event.getChannel(), Permission.KICK_MEMBERS))
			return true;
		else
			return false;
	}

	@Override
	public void execute(GuildMessageReceivedEvent event, String[] args) {
		List<Member> membersToKick = event.getMessage().getMentionedMembers();
		if (membersToKick.isEmpty()) {
			event.getChannel().sendMessage("Please mention the user(s) you want to kick.").queue();
		}
		
		else {
			for (Member member : membersToKick) {
				if (event.getGuild().getSelfMember().canInteract(member) && event.getMember().canInteract(member)) {
					event.getChannel().sendMessage(String.format("**%s** was kicked from the server.", member.getUser().getAsTag())).queue();
					member.kick().queue();
				}
				
				else {
					event.getChannel().sendMessage(String.format("Either you or the bot does not have a high enough role to kick **%s**.", member.getUser().getAsTag())).queue();
				}
			}
		}
		
	}

}
