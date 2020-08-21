package com.chat.elit.bot.tarkovbot.memory;

import com.chat.elit.bot.tarkovbot.model.Crime;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemoryService {
    private MemoryCache memoryCache = new MemoryCache();

    public List<Crime> findSimilarCrimes(Crime crime){
        return memoryCache.getCrimes().values().stream().filter(crime1 -> crime1.getUniqueKey().equals(crime.getUniqueKey())).sorted()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public synchronized void registerCrime(Crime newCrime){
        memoryCache.addCrime(newCrime);
    }

    public <T> String getJson(T objToSerialize){
        StringBuilder json = new StringBuilder();
        ObjectMapper mapper = new ObjectMapper();
        try {
            json.append(mapper.writeValueAsString(objToSerialize));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    public <T> T getObj(String json, Class<T> objClass){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, objClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveMemoryCache(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("discord_bot_memory_cache.json"), memoryCache);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMemoryCache(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            memoryCache = mapper.readValue(new File("discord_bot_memory_cache.json"), MemoryCache.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMemory(){
        return getJson(memoryCache);
    }
}
