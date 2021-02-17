package bot.automato;

import java.util.HashMap;
import java.util.Map;

import commands.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {
	Map<String, Command> commands;
	
	public void addCommand(Command command) {
		commands.put(command.getName(), command);
	}
	
	public MessageListener() {
		commands = new HashMap<String, Command>();
		addCommand(new Say());
		addCommand(new Purge());
		addCommand(new Kick());
		addCommand(new Ban());
		addCommand(new React());
		addCommand(new UserInfo());
	}
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		Command command = commands.get(args[0]);
		if (command != null && command.canExecute(event, event.getMember())) {
			command.execute(event, args);
		}
	}

}
