package FootballLeagueScoringSystem.View;

import FootballLeagueScoringSystem.Control.ViewTrans;
import FootballLeagueScoringSystem.Module.League;
import FootballLeagueScoringSystem.Module.Team;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


/**
 * 球队视图模型
 * 仅显示单个球队
 * 显示队名，球队logo，进球数，失球数，胜场数，负场数，积分，排名
 */
public class TeamView extends Pane {
    public Stage stage;
    private ScrollPane players;//显示球员信息的滚动面板
    private ScrollPane schedule;//显示赛程的滚动面板
    private Button rank;//显示积分的按钮
    private League theLeague;
    public TeamView(Team thisTeam, Stage stage, League theLeague) {
        this.stage = stage;
        this.theLeague=theLeague;
        this.players = new ScrollPane();
        this.schedule = new ScrollPane();
        this.rank = new Button();
        generate(thisTeam,theLeague);
        this.setMinSize(1200, 600);
        this.setMaxSize(1920, 1080);
    }
    public void generate(Team thisTeam,League theLeague) {
        TopButton topButton = new TopButton(this.stage,this.theLeague);//顶部的四个按钮
        //中左，标签：球队名
        Button teamName = new Button();
        teamName.setFont(new Font("Microsoft YaHei", 28));
        teamName.setText(thisTeam.getTeamName());
        teamName.setLayoutX(topButton.getNewBaseX());
        teamName.setLayoutY(topButton.getNewBaseY() + 10);
        teamName.setMinSize(topButton.getButtonWidth() * 2 + topButton.getSpace(),
                topButton.getButtonHeight() * 2);
        teamName.setBackground(new Background(new BackgroundFill(Color.LAVENDER, null, null)));
        //中右，按钮：当前排名，当前球队积分
        this.rank = new Button();
        rank.setFont(new Font("Microsoft YaHei", 28));
        rank.setText("当前排名：" + thisTeam.getTeamRank() + ";当前积分：" + thisTeam.getTeamScore());
        rank.setLayoutX(teamName.getLayoutX() + teamName.getMinWidth() + topButton.getSpace());
        rank.setLayoutY(teamName.getLayoutY());
        rank.setMinSize(teamName.getMinWidth(), teamName.getMinHeight());
        rank.setBackground(new Background(new BackgroundFill(Color.LAVENDER, null, null)));
        //下左，标签：球队LOGO
        Label teamLOGO = new Label();
        Image image = new Image(getPic("LOGO"));
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        teamLOGO.setGraphic(imageView);
        teamLOGO.setLayoutX(teamName.getLayoutX());
        teamLOGO.setLayoutY(teamName.getLayoutY() + teamName.getMinHeight() + 10);
        teamLOGO.setMinSize(topButton.getButtonWidth() * 1.5 + topButton.getSpace(),
                (topButton.getButtonHeight() * 2) * 5);
        //下中，滚动面板：球员列表
        FlowPane playerC = new FlowPane();
        this.players.setLayoutX(teamLOGO.getLayoutX() + teamLOGO.getMinWidth() + 10);
        this.players.setLayoutY(teamLOGO.getLayoutY());
        this.players.setMaxSize(topButton.getButtonWidth(), teamLOGO.getMinHeight());
        this.players.setMinHeight(topButton.getButtonHeight() * 10);
        this.players.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        playerC.setMaxWidth(topButton.getButtonWidth());
        for (int i = 0; i<theLeague.getPlayers(thisTeam.getTeamName()).size(); i++) {
            Button player = new Button();
            player.setFont(new Font("Microsoft YaHei", 24));
            player.setText(theLeague.getPlayers(thisTeam.getTeamName()).get(i).getName());
            player.setTextAlignment(TextAlignment.CENTER);
            player.setMinSize(topButton.getButtonWidth(), topButton.getButtonHeight());
            player.setBackground(new Background(new BackgroundFill(Color.LAVENDER, null, null)));
            player.setOnMouseClicked(new EventHandler< MouseEvent>(){
                @Override
                public void handle(MouseEvent event) {
                    ViewTrans vt = new ViewTrans();
                    vt.toPlayerView(theLeague, stage,thisTeam.getTeamName(),player.getText());
                }
            });
            playerC.getChildren().add(player);
        }
        this.players.setContent(playerC);
        //下右，滚动面板：赛事列表
        FlowPane gameC = new FlowPane();
        this.schedule.setLayoutX(this.players.getLayoutX() + this.players.getMaxWidth() + 10);
        this.schedule.setLayoutY(this.players.getLayoutY());
        this.schedule.setMaxSize(rank.getLayoutX() + rank.getMinWidth() - this.schedule.getLayoutX(),
                teamLOGO.getMinHeight());
        this.schedule.setMinHeight(topButton.getButtonHeight() * 15 / 2);
        this.schedule.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        gameC.setMaxWidth(rank.getLayoutX() + rank.getMinWidth() - this.schedule.getLayoutX());
        String[] gameInfo = theLeague.getGameInfo(thisTeam);
        for (int i = 0; gameInfo[i]!=null; i++) {
            Button game = new Button();
            game.setFont(new Font("Microsoft YaHei", 24));
            game.setText(gameInfo[i]);
            game.setMinSize(rank.getLayoutX() + rank.getMinWidth() - this.schedule.getLayoutX(),
                    topButton.getButtonHeight() * 1.5);
            game.setBackground(new Background(new BackgroundFill(Color.LAVENDER, null, null)));
            gameC.getChildren().add(game);
        }
        this.schedule.setContent(gameC);
        //将所有控件添加到面板
        this.getChildren().addAll(
                topButton.teamRank, topButton.playerRank,
                topButton.todaySchedule, topButton.schedule,
                teamName, rank, teamLOGO,
                this.players, this.schedule);
    }

    public String getPic(String name) {
        return "file:./GameData/Pic/" + name + ".png";
    }
}

