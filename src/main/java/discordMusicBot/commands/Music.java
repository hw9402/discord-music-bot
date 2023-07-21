package discordMusicBot.commands;

import discordMusicBot.lavaPlayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Music extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "play" -> {
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

                PlayerManager playerManager = PlayerManager.get();
                playerManager.play(event.getGuild(), event.getOption("title").getAsString());
            }
        }
    }
}
