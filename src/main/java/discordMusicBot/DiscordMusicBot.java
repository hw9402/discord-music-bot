package discordMusicBot;

import discordMusicBot.commands.Music;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;

public class DiscordMusicBot {

    public static void main(String[] args) {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream("config.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String token = properties.getProperty("discord.api.key");

        JDA jda = JDABuilder.createLight(token, Collections.emptyList())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new Music())
                .build();

        jda.updateCommands().addCommands(
                Commands.slash("ping", "reply pong"),
                Commands.slash("집게사장", "그래 이 새끼야")
        ).queue();

        jda.getPresence().setStatus(OnlineStatus.ONLINE);
    }
}
