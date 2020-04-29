package FootballLeagueScoringSystem.Control;

import FootballLeagueScoringSystem.Module.Player;
import FootballLeagueScoringSystem.Module.Team;
import FootballLeagueScoringSystem.View.PlayerView;
import FootballLeagueScoringSystem.View.TeamView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewTrans {
    public void toPlayerView(Stage stage,String teamName,String playerName){
        Player player = new Player(teamName,playerName);
        PlayerView playerView = new PlayerView(player,stage);
        Scene scene = new Scene(playerView);
        stage.setScene(scene);
        stage.show();
    }
    public void toTeamView(Stage stage,String teamName){
        Team team = new Team(teamName);
        TeamView teamView = new TeamView(team,stage);
        Scene scene = new Scene(teamView);
        stage.setScene(scene);
        stage.show();
    }
}