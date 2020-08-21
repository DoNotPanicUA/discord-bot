package com.chat.elit.bot.tarkovbot.model;

import com.chat.elit.bot.tarkovbot.interfaces.Identifiable;

public enum TarkovMap implements Identifiable {
    FACTORY("Завод"), WOODS("Лес"), CUSTOMS("Таможня"), INTERCHANGE("Развязка"), RESERVE("Резерв"), SHORELINE("Берег"), LAB("Лаборатория"), UNKNOWN("Неизвестно");

    private final String name;

    TarkovMap(String name) {
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
