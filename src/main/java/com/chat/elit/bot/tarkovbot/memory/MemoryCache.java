package com.chat.elit.bot.tarkovbot.memory;

import com.chat.elit.bot.tarkovbot.model.Crime;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class MemoryCache {
    @JsonProperty
    private Map<Integer, Crime> crimes = new HashMap<>();

    @JsonAnySetter
    public void addCrime(Crime crime){
        if (crimes.containsKey(crime.getId()))
            crimes.get(crime.getId()).addReporters(crime.getReporters());
        else
            crimes.put(crime.getId(), crime);
    }

    public Map<Integer, Crime> getCrimes(){
        return crimes;
    }
}
