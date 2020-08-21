package com.chat.elit.bot.tarkovbot.discord.bots;

import com.chat.elit.bot.tarkovbot.discord.DiscordService;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Collection;

public abstract class DiscordBot extends ListenerAdapter {
    private static Logger log = LoggerFactory.getLogger(DiscordBot.class);

    protected Collection<GatewayIntent> events;
    protected String token;
    protected String activity;
    protected JDA jda;

    protected DiscordBot() {
        this.token = DiscordService.getCurrentToken();
    }

    protected void setEvents(Collection<GatewayIntent> events) {
        this.events = events;
    }

    protected void setActivity(String activity) {
        this.activity = activity;
    }

    protected Collection<GatewayIntent> getDefaultEvents(){
        return new ArrayList<>();
    }

    private Collection<GatewayIntent> getEvents(){
        return (events != null && !events.isEmpty() ? events : getDefaultEvents());
    }

    private void setActivity(JDABuilder builder, String activity){
        if (!StringUtils.isEmpty(activity))
            builder.setActivity(Activity.playing(activity));
    }

    public boolean startBot(){
        try {
            JDABuilder builder = JDABuilder.create(token, getEvents());
            setActivity(builder, activity);

            this.jda = builder.setMemberCachePolicy(MemberCachePolicy.ALL)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .addEventListeners(this)
                    .build();
        } catch (LoginException e) {
            log.error("Login fail :(");
            return false;
        }
        log.debug(this + " bot is started!");
        return true;
    }

    public JDA getJda() {
        return jda;
    }
}
