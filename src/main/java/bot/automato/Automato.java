package bot.automato;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Automato {
	
	public static final String prefix = ";;";
	
	public static void main(String[] args) throws LoginException, FileNotFoundException {
			File txt = new File("token.txt"); 
			Scanner input = new Scanner(txt);
			String token = input.nextLine();
			input.close();
		
			JDABuilder builder = JDABuilder.createDefault(token).enableIntents(GatewayIntent.GUILD_MEMBERS);
			builder.addEventListeners(new MessageListener());
			builder.build();
	}

}
