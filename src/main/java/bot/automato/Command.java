package bot.automato;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public abstract class Command {
	
	String name;
	
	public Command(String name) {
		this.name = Automato.prefix + name;
	}
	
	public abstract void execute(GuildMessageReceivedEvent event, String[] args);
	
	public boolean canExecute(GuildMessageReceivedEvent event, Member member) {
		return true;
	};
	
	public String getName() {
		return name;
	}
}
