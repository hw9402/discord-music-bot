package discordMusicBot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Help extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("노래봇 도움말");
        embedBuilder.setColor(new Color(0x8ac6ff));
        embedBuilder.addField("당부하는 글", "1. 봇의 기본 음량이 큰 편이므로, 처음 사용할 때 음량을 줄일 것을 권장합니다.\n" +
                "2. 피드백은 언제나 환영입니다. 문의는 봇의 상태메세지를 확인해주세요.\n" +
                "3. 노래 재생이 가끔 안 될 때가 있습니다. 그럴 땐 '/stop' 을 통해 봇을 내보낸 후 다시 시도해보시기 바랍니다." +
                "4. 노래가 재생되는 데에 딜레이가 생길 수 있습니다.", false);
        embedBuilder.addField("/help", "도움말을 볼 수 있는 명령어입니다.", false);
        embedBuilder.addField("/play", "노래의 제목(과 아티스트명) 또는 유튜브 링크를 입력하면 노래를 재생할 수 있습니다.\n두 옵션 모두 입력한다면, 제목을 기준으로 가져옵니다.", false);
        embedBuilder.addField("/skip", "현재 재생중인 노래를 넘길 수 있습니다.", false);
        embedBuilder.addField("/stop", "통화방에 있는 봇을 내보낼 수 있습니다. 현재 플레이리스트를 초기화하는 효과도 있습니다.", false);

        if (event.getName().equals("help")) {
            event.replyEmbeds(embedBuilder.build()).queue();
        }
    }
}
