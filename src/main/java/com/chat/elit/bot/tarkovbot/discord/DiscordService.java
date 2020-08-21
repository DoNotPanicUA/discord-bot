package com.chat.elit.bot.tarkovbot.discord;

import com.chat.elit.bot.tarkovbot.discord.bots.DefaultBot;
import com.chat.elit.bot.tarkovbot.model.DiscordUser;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DiscordService extends ListenerAdapter {
    private static Logger log = LoggerFactory.getLogger(DiscordService.class);
    private static String TOKEN = System.getProperty("com.chat.elit.bot.tarkovbot.token");

    public static String getCurrentToken(){
        return TOKEN;
    }

    @Autowired
    private DefaultBot bot;

    public DiscordService() {
    }

    @PostConstruct
    public void postConstruct(){
        if (bot.startBot())
            log.debug("Bot is up and running!");
        else
            log.error("Bot start is failed!");
    }

    public DiscordUser getDiscordUser(String userTag){
        User discordUser = bot.getJda().getUserByTag(userTag);
        if (discordUser != null)
            return new DiscordUser(discordUser);
        else{
            log.error("Unable to find user " + userTag);
            return null;
        }
    }

    public void sendPrivateMessage(User user, String message){
        if (user != null) {
            RestAction<PrivateChannel> restAction = user.getJDA().openPrivateChannelById(user.getId());
            PrivateChannel privateChannel = restAction.complete();
            privateChannel.sendMessage(message).queue();
        }
    }
}
