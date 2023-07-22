package discordMusicBot.commands;

import discordMusicBot.lavaPlayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class Play extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("play")) {
            Member member = event.getMember();
            GuildVoiceState memberVoiceState = member.getVoiceState();

            if (memberVoiceState == null) {
                event.reply("현재 음성 채널에 참가하고 있지 않습니다.").queue();
                return;
            }

            Member self = event.getGuild().getSelfMember();
            GuildVoiceState selfVoiceState = self.getVoiceState();

            if (!selfVoiceState.inAudioChannel()) {
                event.getGuild().getAudioManager().openAudioConnection(memberVoiceState.getChannel());
            } else {
                if (selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
                    event.reply("당신과 봇이 같은 채널에 있지 않습니다.").queue();
                    return;
                }
            }

            String title = event.getOption("title") != null ? event.getOption("title").getAsString() : "";
            String link = event.getOption("link") != null ? event.getOption("link").getAsString() : "";
            PlayerManager playerManager;

            if (!title.equals("")) {
                try {
                    playerManager = PlayerManager.get();
                } catch (GeneralSecurityException | IOException e) {
                    throw new RuntimeException(e);
                }
                playerManager.playTitle(event.getGuild(), title);
                event.reply("노래가 재생됩니다.\n" + "제목 : " + title).queue();
            } else if (!link.equals("")) {
                try {
                    playerManager = PlayerManager.get();
                } catch (GeneralSecurityException | IOException e) {
                    throw new RuntimeException(e);
                }
                playerManager.playURL(event.getGuild(), link);
                event.reply("노래가 재생됩니다.\n" + link).queue();
            }
        }
    }
}
