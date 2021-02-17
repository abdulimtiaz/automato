package commands;

import java.util.Arrays;

import bot.automato.Command;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Say extends Command {

	public Say() {
		super("say");
	}
	
	@Override
	public void execute(GuildMessageReceivedEvent event, String[] args) {
		String[] msgArr = Arrays.copyOfRange(args, 1, args.length);
		String message = String.join(" ", msgArr);
		event.getChannel().sendMessage(message).queue();
	}

}
