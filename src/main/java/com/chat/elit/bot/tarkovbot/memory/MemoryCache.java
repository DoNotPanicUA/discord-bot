package com.chat.elit.bot.tarkovbot.memory;

import com.chat.elit.bot.tarkovbot.model.Crime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class MemoryCache {
    @JsonIgnore
    private Map<Integer, Crime> crimes = new HashMap<>();
    @JsonProperty
    private Crime crime;

    public void addCrime(Crime crime){
        this.crime = crime;
//        if (memoryCache.getCrimes().containsKey(newCrime.getId()))
//            memoryCache.getCrimes().get(newCrime.getId()).addReporters(newCrime.getReporters());
//        else
//            memoryCache.getCrimes().put(newCrime.getId(), newCrime);

        //crimes.put(crime.getId(), crime);
    }

    public Map<Integer, Crime> getCrimes(){
        return crimes;
    }
}
