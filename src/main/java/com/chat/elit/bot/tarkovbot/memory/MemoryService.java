package com.chat.elit.bot.tarkovbot.memory;

import com.chat.elit.bot.tarkovbot.model.Crime;
import com.chat.elit.bot.tarkovbot.model.CrimeFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemoryService {
    private static Logger log = LoggerFactory.getLogger(MemoryService.class);
    private MemoryCache memoryCache = new MemoryCache();

    @Autowired
    private CrimeFactory crimeFactory;

    public List<Crime> findSimilarCrimes(Crime crime){
        return memoryCache.getCrimes().values().stream().filter(crime1 -> crime1.getUniqueKey().equals(crime.getUniqueKey())).sorted()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public synchronized void registerCrime(Crime newCrime){
        memoryCache.addCrime(newCrime);
        saveMemoryCache();
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

    public void loadMemoryCache() throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        memoryCache = mapper.readValue(new File("discord_bot_memory_cache.json"), MemoryCache.class);
        crimeFactory.setCrimeId(memoryCache.getCrimes().keySet().stream().max(Integer::compareTo).get());
    }

    public String getMemory(){
        return getJson(memoryCache);
    }

    @PostConstruct
    public void postConstruct(){
        try {
            loadMemoryCache();
        } catch (Exception e) {
            log.warn("Unable to load initial memory! Cache is empty! Error: " + e.getMessage());
        }
    }
}
