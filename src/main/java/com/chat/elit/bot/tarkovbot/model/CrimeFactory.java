package com.chat.elit.bot.tarkovbot.model;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CrimeFactory {

    private AtomicInteger crimeId = new AtomicInteger(1);

    public Crime getSimpleCrime(DiscordUser killer, DiscordUser victim, int seriousnessOfCrime){
        return new Crime(crimeId.incrementAndGet())
                .setKiller(killer)
                .setVictim(victim)
                .setSeriousnessOfCrime(seriousnessOfCrime)
                .setCrimeDate(LocalDate.now())
                .setRegistrationDateTime(LocalDateTime.now())
                .setCrimeScene(TarkovMap.UNKNOWN)
                .setCharacter(TarkovCharacter.UNKNOWN);
    }

    public Crime getOldCrime(DiscordUser killer, DiscordUser victim, int seriousnessOfCrime, LocalDate crimeDate){
        return getSimpleCrime(killer, victim, seriousnessOfCrime).setCrimeDate(crimeDate);
    }
}
