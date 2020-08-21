package com.chat.elit.bot.tarkovbot.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Identifiable {
    @JsonIgnore
    String getUniqueKey();
}
