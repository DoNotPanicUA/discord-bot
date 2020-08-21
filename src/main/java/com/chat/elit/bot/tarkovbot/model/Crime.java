package com.chat.elit.bot.tarkovbot.model;

import com.chat.elit.bot.tarkovbot.interfaces.Identifiable;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Crime implements Identifiable, Comparable<Crime> {
    @JsonProperty
    private int id;
    @JsonIgnore
    private Set<DiscordUser> reporters = new HashSet<>();
    @JsonProperty
    private DiscordUser killer;
    @JsonProperty
    private DiscordUser victim;
    @JsonIgnore
    private TarkovMap crimeScene;
    @JsonIgnore
    private TarkovCharacter character;
    @JsonIgnore
    private LocalDate crimeDate;
    @JsonIgnore
    private LocalDateTime registrationDateTime;
    @JsonProperty
    private int seriousnessOfCrime;

    @JsonCreator
    public Crime(@JsonProperty("id") int id) {
        this.id = id;
    }

    @Override
    public String getUniqueKey() {
        String separator = ";";
        return killer.getUniqueKey() + separator +
               victim + separator +
               crimeDate.toString() + separator +
               crimeScene.getUniqueKey() + separator +
               character.getUniqueKey() + separator;
    }

    public Crime setKiller(DiscordUser killer) {
        this.killer = killer;
        return this;
    }

    public Crime setVictim(DiscordUser victim) {
        this.victim = victim;
        return this;
    }

    public Crime setCrimeDate(LocalDate crimeDate) {
        this.crimeDate = crimeDate;
        return this;
    }

    public Crime setRegistrationDateTime(LocalDateTime registrationDateTime) {
        this.registrationDateTime = registrationDateTime;
        return this;
    }

    public Crime setSeriousnessOfCrime(int seriousnessOfCrime) {
        this.seriousnessOfCrime = seriousnessOfCrime;
        return this;
    }

    public Crime setCrimeScene(TarkovMap crimeScene) {
        this.crimeScene = crimeScene;
        return this;
    }

    public Crime setCharacter(TarkovCharacter character) {
        this.character = character;
        return this;
    }

    public void addReporters(Set<DiscordUser> reporters) {
        this.reporters.addAll(reporters);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setReporters(Set<DiscordUser> reporters) {
        this.reporters = reporters;
    }

    public int getId() {
        return id;
    }

    public DiscordUser getKiller() {
        return killer;
    }

    public DiscordUser getVictim() {
        return victim;
    }

    public TarkovMap getCrimeScene() {
        return crimeScene;
    }

    public TarkovCharacter getCharacter() {
        return character;
    }

    public LocalDate getCrimeDate() {
        return crimeDate;
    }

    public LocalDateTime getRegistrationDateTime() {
        return registrationDateTime;
    }

    public int getSeriousnessOfCrime() {
        return seriousnessOfCrime;
    }

    public Set<DiscordUser> getReporters() {
        return reporters;
    }

    public void changeIdTo(Crime actualCrime){
        this.id = actualCrime.getId();
    }

    @Override
    public int compareTo(@NotNull Crime o) {
        return Integer.compare(id, o.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Crime crime = (Crime) o;
        return id == crime.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Crime{" +
                "id=" + id +
                ", reporters=" + reporters +
                ", killer=" + killer.getAsTag() +
                ", victim=" + victim.getAsTag() +
                ", crimeScene=" + (crimeScene != null ? crimeScene.getName() : "n/a") +
                ", character=" + (character != null ? character.getName() : "n/a") +
                ", crimeDate=" + crimeDate +
                ", registrationDateTime=" + registrationDateTime +
                ", seriousnessOfCrime=" + seriousnessOfCrime +
                '}';
    }
}
