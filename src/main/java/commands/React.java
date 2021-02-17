package commands;

import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;

import bot.automato.Command;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class React extends Command {

	public React() {
		super("react");
	}

	@Override
	public void execute(GuildMessageReceivedEvent event, String[] args) {
		
		// if id is provided
		if (args.length == 3 && args[1].matches("\\d+")) {
			long id = Long.parseLong(args[1]);
			
			// if emoji is unicode emoji
			String emoji = args[2];
			if (EmojiManager.isEmoji(emoji)) {
				emoji = EmojiParser.extractEmojis(emoji).get(0);
				event.getChannel().addReactionById(id, emoji).queue();
			}
			
			// if custom server emote
			else if (!event.getMessage().getEmotes().isEmpty()) {
				Emote emote = event.getMessage().getEmotes().get(0);
				event.getChannel().addReactionById(id, emote).queue();
			}
		}
		
		else if (args.length == 2) { // if no id is provided, react to previous message by default
			
			// get previous message
			Message targetMessage = event.getChannel().getHistoryBefore(event.getMessageIdLong(), 1).complete().getRetrievedHistory().get(0);
			
			// if emoji is unicode emoji
			if (EmojiManager.isEmoji(args[1])) {
				String emoji = EmojiParser.extractEmojis(args[1]).get(0);
				event.getChannel().addReactionById(targetMessage.getIdLong(), emoji).queue();
			}
			
			// if custom server emote
			else if (!event.getMessage().getEmotes().isEmpty()) {
				Emote emote = event.getMessage().getEmotes().get(0);
				targetMessage.addReaction(emote).queue();
			}
		}
	}
}
