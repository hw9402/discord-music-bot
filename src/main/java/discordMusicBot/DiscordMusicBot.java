package discordMusicBot;

import discordMusicBot.commands.Help;
import discordMusicBot.commands.Play;
import discordMusicBot.commands.Skip;
import discordMusicBot.commands.Stop;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;

public class DiscordMusicBot {

    public static void main(String[] args) {
        Properties properties = new Properties();
        try (InputStream inputStream = DiscordMusicBot.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String token = properties.getProperty("discord.api.key");

        JDA jda = JDABuilder.createLight(token, Collections.emptyList())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_VOICE_STATES)
                .setMemberCachePolicy(MemberCachePolicy.VOICE)
                .enableCache(CacheFlag.VOICE_STATE)
                .addEventListeners(new Help())
                .addEventListeners(new Play())
                .addEventListeners(new Skip())
                .addEventListeners(new Stop())
                .build();

        jda.updateCommands().addCommands(
                Commands.slash("help", "도움말을 보고 싶다면 사용하세요!"),
                Commands.slash("play", "재생하고 싶은 노래 제목과 아티스트명 또는 유튜브 링크를 입력해주세요!")
                        .addOption(OptionType.STRING, "title", "제목 + 아티스트명")
                        .addOption(OptionType.STRING, "link", "유튜브 링크 (재생목록 등등 안 됩니다. only 유튜브 영상)"),
                Commands.slash("skip", "다음 노래를 재생하고 싶다면 사용하세요!"),
                Commands.slash("stop", "노래를 멈추고 봇을 내보내고 싶다면 사용하세요!")
        ).queue();

        jda.getPresence().setStatus(OnlineStatus.ONLINE);
    }
}
