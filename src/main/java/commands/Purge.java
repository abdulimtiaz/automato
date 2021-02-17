package commands;


import java.util.concurrent.TimeUnit;

import bot.automato.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Purge extends Command {

	public Purge() {
		super("purge");
	}
	
	@Override
	public boolean canExecute(GuildMessageReceivedEvent event, Member member) {
		if (member.hasPermission(event.getChannel(), Permission.MESSAGE_MANAGE))
			return true;
		else
			return false;
	}

	@Override
	public void execute(GuildMessageReceivedEvent event, String[] args) {
		
		int purgeAmt = 0;
		TextChannel channel = event.getChannel();
		
		if (args[1].matches("\\d+")) {
			purgeAmt = Integer.parseInt(args[1]);
			
			if (purgeAmt > 0 && purgeAmt < 100) {
				channel.getHistory().retrievePast(purgeAmt + 1).queue(msgs -> channel.deleteMessages(msgs).queue());
				channel.sendMessage(String.format("Deleted %d messages.", purgeAmt)).queue(msg -> msg.delete().queueAfter(3, TimeUnit.SECONDS));
			}
			
			else {
				channel.sendMessage("Number of messages must be between 1 and 99.").queue(msg -> msg.delete().queueAfter(3, TimeUnit.SECONDS));
			}
		}
		
	}
	
}
