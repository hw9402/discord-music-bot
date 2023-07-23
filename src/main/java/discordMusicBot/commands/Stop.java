package discordMusicBot.commands;

import discordMusicBot.lavaPlayer.GuildMusicManager;
import discordMusicBot.lavaPlayer.PlayerManager;
import discordMusicBot.lavaPlayer.TrackScheduler;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class Stop extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("stop")) {
            Member member = event.getMember();
            GuildVoiceState memberVoiceState = member.getVoiceState();

            if (memberVoiceState == null) {
                event.reply("현재 음성 채널에 참가하고 있지 않습니다.").queue();
                return;
            }

            Member self = event.getGuild().getSelfMember();
            GuildVoiceState selfVoiceState = self.getVoiceState();

            if (!selfVoiceState.inAudioChannel()) {
                event.reply("봇이 음성 채널에 존재하지 않습니다.").queue();
                return;
            }

            if (selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
                event.reply("당신과 봇이 같은 채널에 있지 않습니다.").queue();
                return;
            }

            GuildMusicManager guildMusicManager;
            try {
                guildMusicManager = PlayerManager.get().getGuildMusicManager(event.getGuild());
            } catch (GeneralSecurityException | IOException e) {
                throw new RuntimeException(e);
            }
            TrackScheduler trackScheduler = guildMusicManager.getTrackScheduler();
            trackScheduler.getQueue().clear();
            trackScheduler.getPlayer().stopTrack();
            event.getGuild().kickVoiceMember(self).queue();
            event.reply("봇이 꺼집니다.").queue();
        }
    }
}
