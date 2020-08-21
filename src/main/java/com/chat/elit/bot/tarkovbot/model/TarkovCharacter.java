package com.chat.elit.bot.tarkovbot.model;

import com.chat.elit.bot.tarkovbot.interfaces.Identifiable;

public enum TarkovCharacter implements Identifiable {
    PMC("ЧВК"), SCUV("Дикий"), UNKNOWN("Неизвестно");

    private final String name;

    TarkovCharacter(String name) {
        this.name = name;
    }

    @Override
    public String getUniqueKey() {
        return name;
    }

    public String getName() {
        return name;
    }
}
