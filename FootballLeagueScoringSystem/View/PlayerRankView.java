package FootballLeagueScoringSystem.View;

import FootballLeagueScoringSystem.Control.ViewTrans;
import FootballLeagueScoringSystem.Module.League;
import FootballLeagueScoringSystem.Module.Player;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

/**
 * @author QuanHao ,Long
 * 射手榜，显示球员排名
 */
public class PlayerRankView extends AnchorPane {
    PlayerRankView(Stage stage, League theLeague) {
        AnchorPane AP0 = new AnchorPane();
        TitleLabels(AP0);
        ScrollPane SP = new ScrollPane();
        AnchorPane AP = new AnchorPane();
        theLeague.setPlayerRank();
        RankData(theLeague.getPlayers(), AP, stage, theLeague);
        SP.setLayoutX(0);
        SP.setLayoutY(36);
        SP.setMinSize(1200, 450);
        SP.setMaxSize(1920, 1080);
        SP.setContent(AP);
        SP.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        AP0.getChildren().add(SP);
        this.getChildren().add(AP0);
    }

    private void RankData(List<Player> players, AnchorPane AP, Stage stage, League theLeague) {
        if (players.isEmpty()) {
            //数据库中没有查到对应数据
            Button NoData = new Button();
            NoData.setText("没有找到对应数据！");
            NoData.setLayoutX(0);
            NoData.setLayoutY(0);
            NoData.setMinSize(1200, 30);
            AP.getChildren().add(NoData);
        } else {
            for (int i = 0; i<players.size(); i++) {
                Button Rank = new Button();
                Rank.setBackground(new Background(new BackgroundFill(Color.LAVENDER,null,null)));
                Rank.setText("" + players.get(i).getRank());
                Rank.setLayoutX(0);
                Rank.setLayoutY(i * 30);
                Rank.setMinSize(240, 36);

                Button name = new Button();
                name.setBackground(new Background(new BackgroundFill(Color.LAVENDER,null,null)));
                name.setText(players.get(i).getName());
                name.setLayoutX(Rank.getLayoutX() + Rank.getMinWidth());
                name.setLayoutY(i * 30);
                name.setMinSize(Rank.getMinWidth(), Rank.getMinHeight());

                Button teamName = new Button();
                teamName.setBackground(new Background(new BackgroundFill(Color.LAVENDER,null,null)));
                teamName.setText("" + players.get(i).getTeamName());
                teamName.setLayoutX(name.getLayoutX() + name.getMinWidth());
                teamName.setLayoutY(i * 30);
                teamName.setMinSize(Rank.getMinWidth(), Rank.getMinHeight());
                teamName.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        ViewTrans vt = new ViewTrans();
                        vt.toTeamView(theLeague, stage, teamName.getText());
                    }
                });
                teamName.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        teamName.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, null, null)));
                    }
                });
                teamName.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        teamName.setBackground(new Background(new BackgroundFill(Color.LAVENDER,null,null)));
                    }
                });

                name.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        ViewTrans vt = new ViewTrans();
                        vt.toPlayerView(theLeague, stage, teamName.getText(), name.getText());
                    }
                });
                name.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        name.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, null, null)));
                    }
                });

                name.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        name.setBackground(new Background(new BackgroundFill(Color.LAVENDER,null,null)));
                    }
                });

                Button GoalNum = new Button();
                GoalNum.setBackground(new Background(new BackgroundFill(Color.LAVENDER,null,null)));
                GoalNum.setText("" + players.get(i).getScore());
                GoalNum.setLayoutX(teamName.getLayoutX() + teamName.getMinWidth());
                GoalNum.setLayoutY(i * 30);
                GoalNum.setMinSize(Rank.getMinWidth(), Rank.getMinHeight());

                Button Score = new Button();
                Score.setBackground(new Background(new BackgroundFill(Color.LAVENDER,null,null)));
                Score.setText("" + players.get(i).getScore());
                Score.setLayoutX(GoalNum.getLayoutX() + GoalNum.getMinWidth());
                Score.setLayoutY(i * 30);
                Score.setMinSize(Rank.getMinWidth(), Rank.getMinHeight());
                AP.getChildren().addAll(Rank, name, teamName,
                        GoalNum, Score);
            }
        }
    }

    private void TitleLabels(AnchorPane Ap) {
        Button rank = new Button();
        rank.getStyleClass().addAll("btn","btn-success");
        rank.setText("当前名次");
        rank.setLayoutX(80);
        rank.setLayoutY(0);
        rank.setMinSize(240, 30);
        rank.setMaxSize(240,30);

        Button name = new Button();
        name.getStyleClass().addAll("btn","btn-warning");
        name.setText("姓名");
        name.setLayoutX(rank.getLayoutX() + rank.getMinWidth());
        name.setLayoutY(rank.getLayoutY());
        name.setMinSize(rank.getMinWidth(), rank.getMinHeight());

        Button teamName = new Button();
        teamName.getStyleClass().addAll("btn","btn-info");
        teamName.setText("球队");
        teamName.setLayoutX(name.getLayoutX() + name.getMinWidth());
        teamName.setLayoutY(name.getLayoutY());
        teamName.setMinSize(rank.getMinWidth(), rank.getMinHeight());

        Button goalNum = new Button();
        goalNum.getStyleClass().addAll("btn","btn-primary");
        goalNum.setText("进球数");
        goalNum.setLayoutX(teamName.getLayoutX() + teamName.getMinWidth());
        goalNum.setLayoutY(teamName.getLayoutY());
        goalNum.setMinSize(rank.getMinWidth(), rank.getMinHeight());

        Button score = new Button();
        score.getStyleClass().addAll("btn","btn-danger");
        score.setText("积分");
        score.setLayoutX(goalNum.getLayoutX() + goalNum.getMinWidth());
        score.setLayoutY(goalNum.getLayoutY());
        score.setMinSize(rank.getMinWidth(), rank.getMinHeight());
        Ap.getChildren().addAll(rank, name, teamName, goalNum, score);
        Ap.setBackground(new Background(new BackgroundFill(Color.LAVENDER,null,null)));
    }
}
