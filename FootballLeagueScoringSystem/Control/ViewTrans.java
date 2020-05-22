package FootballLeagueScoringSystem.Control;

import FootballLeagueScoringSystem.Module.League;
import FootballLeagueScoringSystem.Module.Player;
import FootballLeagueScoringSystem.Module.Team;
import FootballLeagueScoringSystem.View.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewTrans {
    public void toPlayerView(League theLeague, Stage stage, String teamName, String playerName) {
        Player player = theLeague.getPlayer(teamName, playerName);
        PlayerView playerView = new PlayerView(player, stage, theLeague);
        Scene scene = new Scene(playerView);
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        stage.setScene(scene);;
    }

    public void toTeamView(League theLeague, Stage stage, String teamName) {
        Team team = theLeague.getTeam(teamName);
        TeamView teamView = new TeamView(team, stage, theLeague);
        Scene scene = new Scene(teamView);
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        stage.setScene(scene);;
    }

    public void toAddDataView(League theLeague, Stage stage) {
        AddDataView registerTeamView = new AddDataView(stage, theLeague);
        Scene scene = new Scene(registerTeamView);
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        stage.setScene(scene);;
    }

    public void toMainView(League theLeague, Stage stage) throws IOException {
        MainView mainView = new MainView(theLeague, stage);
        Scene scene = new Scene(mainView);
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        stage.setScene(scene);
    }

    public void toMainView(League theLeague, Stage stage, String tab) throws IOException {
        /**
         * tab:
         * 球队排名->球队排名
         * 射手排名->射手排名
         * 今日赛程->今日赛程
         * 其他（""）->其他功能
         * */
        MainView mainView = new MainView(theLeague, stage);
        mainView.selectTab(tab);
        Scene scene = new Scene(mainView);
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        stage.setScene(scene);
        
    }

    public void toUpdateDataView(League theLeague, Stage stage) {
        UpdateDataView updateDataView = new UpdateDataView(stage, theLeague);
        Scene scene = new Scene(updateDataView);
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        stage.setScene(scene);;
    }

    public void toUpdateSchedule(League theLeague, Stage stage) {
        UpdateSchedule updateSchedule = new UpdateSchedule(theLeague, stage);
        Scene scene = new Scene(updateSchedule);
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        
        stage.setScene(scene);;
    }

}
