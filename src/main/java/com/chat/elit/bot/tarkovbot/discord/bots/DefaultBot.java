package com.chat.elit.bot.tarkovbot.discord.bots;

import com.chat.elit.bot.tarkovbot.discord.DiscordService;
import com.chat.elit.bot.tarkovbot.memory.MemoryService;
import com.chat.elit.bot.tarkovbot.model.Crime;
import com.chat.elit.bot.tarkovbot.model.CrimeFactory;
import com.chat.elit.bot.tarkovbot.model.DiscordUser;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.channel.voice.GenericVoiceChannelEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class DefaultBot extends DiscordBot {
    private static Logger log = LoggerFactory.getLogger(DefaultBot.class);

    @Autowired
    private MemoryService memoryService;

    @Autowired
    private CrimeFactory crimeFactory;

    @Autowired
    private DiscordService discordService;

    @Override
    protected Collection<GatewayIntent> getDefaultEvents() {
        Collection<GatewayIntent> events = new ArrayList<>();
        events.add(GatewayIntent.GUILD_VOICE_STATES);
        events.add(GatewayIntent.DIRECT_MESSAGES);
        events.add(GatewayIntent.GUILD_MESSAGES);
        events.add(GatewayIntent.GUILD_PRESENCES);
        return events;
    }

    public DefaultBot() {
        super();
        this.setActivity("Under development!");
    }

    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().matches("!crime .+")) {
            String[] values = event.getMessage().getContentRaw().split("\\s");
            int length = values.length;

            if (length == 4){
                try{
                    DiscordUser killer = discordService.getDiscordUser(values[1]);
                    DiscordUser victim = discordService.getDiscordUser(values[2]);
                    int seriousnessOfCrime = Integer.parseInt(values[3]);
                    memoryService.registerCrime(crimeFactory.getSimpleCrime(killer, victim, seriousnessOfCrime));
                    discordService.sendPrivateMessage(event.getAuthor(), "Crime is registered :)");
                } catch (Exception e) {
                    System.out.println("Unable to register a crime by input: " + event.getMessage().getContentRaw());
                    discordService.sendPrivateMessage(event.getAuthor(), "Unable to register a crime by input: " + event.getMessage().getContentRaw());
                }
            }
        }

        if (event.getMessage().getContentRaw().equals("!getMemory")){
            discordService.sendPrivateMessage(event.getAuthor(), memoryService.getJson(memoryService.getMemory()));
        }

        if (event.getMessage().getContentRaw().equals("!saveMemory")){
            memoryService.saveMemoryCache();
            discordService.sendPrivateMessage(event.getAuthor(), "Memory successfully saved!");
        }

        if (event.getMessage().getContentRaw().equals("!loadMemory")){
            try {
                memoryService.loadMemoryCache();
            } catch (IOException e) {
                log.error("Unable to load cache :(. Error: " + e.getMessage());
            }
            discordService.sendPrivateMessage(event.getAuthor(), "Memory successfully saved!");
        }

        if (event.getMessage().getContentRaw().equals("!testCrime")){
            Crime crime = crimeFactory.getSimpleCrime(discordService.getDiscordUser("monkeydig#3407"), discordService.getDiscordUser("DoNotPanic#6993"), 5);
            String json = memoryService.getJson(crime);
            discordService.sendPrivateMessage(event.getAuthor(), json);
            Crime newCrime = memoryService.getObj(json, Crime.class);
            discordService.sendPrivateMessage(event.getAuthor(), newCrime.toString());
        }

        if (event.getMessage().getContentRaw().equals("!testDiscordUser")){
            DiscordUser user = discordService.getDiscordUser("monkeydig#3407");
            String json = memoryService.getJson(user);
            discordService.sendPrivateMessage(event.getAuthor(), json);
            DiscordUser newUser = memoryService.getObj(json, DiscordUser.class);
            discordService.sendPrivateMessage(event.getAuthor(), newUser.toString());
        }
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        System.out.println(event.getMessage().getContentRaw());
        if (event.getMessage().getContentRaw().equals("!ping"))
            event.getChannel().sendMessage("Fuck off " + event.getAuthor().getName()).queue();

        if (event.getMessage().getContentRaw().equals("!checklists")){
            JDA jda = event.getJDA();
            System.out.println("Guilds: " + jda.getGuilds().size());
            jda.getGuilds().forEach(guild -> System.out.println(guild.getName()));
            System.out.println("Privates: " + jda.getPrivateChannels().size());
            jda.getPrivateChannels().forEach(guild -> System.out.println(guild.getName()));
            System.out.println("Voices: " + jda.getVoiceChannels().size());
            jda.getVoiceChannels().forEach(voiceChannel -> System.out.println(voiceChannel.getName()));
            System.out.println("Users: ");
            jda.getUsers().forEach(user -> System.out.println(user.getName() + ", " + user.getAsTag() + ", " + user.getId()));

            System.out.println("Members: ");
            jda.getGuilds().forEach(guild -> guild.getMembers().forEach(member -> System.out.println(member.getNickname())));

            User user = event.getJDA().getUserByTag("DoNotPanic#6993");
            if (user != null) {
                RestAction<PrivateChannel> restAction = event.getJDA().openPrivateChannelById(user.getId());
                PrivateChannel privateChannel = restAction.complete();
                privateChannel.sendMessage("it works! check logs!").queue();
            }
        }
    }

    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
        User user = event.getJDA().getUserByTag("DoNotPanic#6993");
        if (user != null) {
            RestAction<PrivateChannel> restAction = event.getJDA().openPrivateChannelById(user.getId());
            PrivateChannel privateChannel = restAction.complete();
            privateChannel.sendMessage(event.getMember().getNickname() + " has joined to voice channel " + event.getChannelJoined().getName()).queue();
        }
        //event.getJDA().openPrivateChannelById();
        System.out.println(event.getMember().getNickname() + " has joined to voice channel " + event.getChannelJoined().getName());
    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        User user = event.getJDA().getUserByTag("DoNotPanic#6993");
        if (user != null) {
            RestAction<PrivateChannel> restAction = event.getJDA().openPrivateChannelById(user.getId());
            PrivateChannel privateChannel = restAction.complete();
            privateChannel.sendMessage(event.getMember().getNickname() + " has left the voice channel " + event.getChannelLeft().getName()).queue();
        }
        //event.getJDA().openPrivateChannelById();
        System.out.println(event.getMember().getNickname() + " has left the voice channel " + event.getChannelLeft().getName());
    }

    @Override
    public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {
        User user = event.getJDA().getUserByTag("DoNotPanic#6993");
        if (user != null) {
            RestAction<PrivateChannel> restAction = event.getJDA().openPrivateChannelById(user.getId());
            PrivateChannel privateChannel = restAction.complete();
            privateChannel.sendMessage(event.getEntity().getNickname() + " has left the voice channel " + event.getChannelLeft().getName() + " and joined to " + event.getChannelJoined().getName()).queue();
        }
        //event.getJDA().openPrivateChannelById();
        System.out.println(event.getEntity().getNickname() + " has left the voice channel " + event.getChannelLeft().getName() + " and joined to " + event.getChannelJoined().getName());
    }

    @Override
    public void onGenericVoiceChannel(@NotNull GenericVoiceChannelEvent event) {
        User user = event.getJDA().getUserByTag("DoNotPanic#6993");
        if (user != null) {
            RestAction<PrivateChannel> restAction = event.getJDA().openPrivateChannelById(user.getId());
            PrivateChannel privateChannel = restAction.complete();
            privateChannel.sendMessage(event.getChannel().getName() + " is triggered!").queue();
        }
        //event.getJDA().openPrivateChannelById();
        System.out.println(event.getChannel().getName() + " is triggered!");
    }
}
