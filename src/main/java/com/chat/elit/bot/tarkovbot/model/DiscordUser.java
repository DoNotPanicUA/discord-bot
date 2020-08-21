package com.chat.elit.bot.tarkovbot.model;

import com.chat.elit.bot.tarkovbot.interfaces.Identifiable;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.dv8tion.jda.api.entities.User;

public class DiscordUser implements Identifiable {

    private final String asTag;
    private final String nickName;
    private final String id;

    public DiscordUser(User discordUser) {
        this.asTag = discordUser.getAsTag();
        this.nickName = discordUser.getName();
        this.id = discordUser.getId();
    }

    @JsonCreator
    public DiscordUser(@JsonProperty("asTag") String asTag,
                       @JsonProperty("nickName") String nickName,
                       @JsonProperty("id") String id) {
        this.asTag = asTag;
        this.nickName = nickName;
        this.id = id;
    }

    @Override
    public String getUniqueKey() {
        return asTag;
    }

    public String getAsTag() {
        return asTag;
    }

    public String getNickName() {
        return nickName;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "DiscordUser{" +
                "asTag='" + asTag + '\'' +
                ", nickName='" + nickName + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
