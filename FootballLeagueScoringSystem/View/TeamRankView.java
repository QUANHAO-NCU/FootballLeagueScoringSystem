package FootballLeagueScoringSystem.View;

import FootballLeagueScoringSystem.Control.ViewTrans;
import FootballLeagueScoringSystem.Module.League;
import FootballLeagueScoringSystem.Module.Team;
import javafx.event.EventHandler;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

/***
 * @author QuanHao ,
 * @author Long ,
 * 球队积分排名界面
 * */
public class TeamRankView extends Accordion{
    public TeamRankView(Stage stage, League theLeague){
        String titleStyle = "-fx-pref-width:1200px;-fx-pref-height:60px;-fx-text-align:center;-fx-font-size:18px;";
        //男子甲组
        TitledPane groupMale1 = new TitledPane();
        groupMale1.setText("男子甲组");
        groupMale1.setStyle(titleStyle);

        AnchorPane gm1GP0 = new AnchorPane();
        gm1GP0.setPrefHeight(500);
        ScrollPane gm1SP = new ScrollPane();
        gm1SP.setPrefHeight(500);
        gm1SP.setLayoutX(0);
        gm1SP.setLayoutY(36);
        AnchorPane gm1AP = new AnchorPane();
        gm1SP.setMinHeight(500);
        gm1AP.setLayoutX(0);
        gm1AP.setLayoutY(0);
        //
        theLeague.setTeamRank("男子甲组");
        List<Team> gm1Teams = theLeague.getTeams("男子甲组");
        RankData(gm1Teams, gm1AP,stage,theLeague);
        //
        gm1SP.setContent(gm1AP);
        TitleLabels(gm1GP0);
        gm1GP0.getChildren().add(gm1SP);
        groupMale1.setContent(gm1GP0);
        //男子乙组
        TitledPane groupMale2 = new TitledPane();
        groupMale2.setText("男子乙组");
        groupMale2.setStyle(titleStyle);
        AnchorPane gm2AP0 = new AnchorPane();
        gm2AP0.setMinSize(1200, 435);
        ScrollPane gm2SP = new ScrollPane();
        gm2SP.setMinSize(1200, 435);
        gm2SP.setLayoutX(0);
        gm2SP.setLayoutY(36);
        AnchorPane gm2AP = new AnchorPane();
        gm2AP.setMinSize(1200, 435);
        //
        List<Team> gm2Teams = theLeague.getTeams("男子乙组");
        RankData(gm2Teams, gm2AP,stage,theLeague);
        //
        gm2SP.setContent(gm2AP);
        TitleLabels(gm2AP0);
        gm2AP0.getChildren().add(gm2SP);
        groupMale2.setContent(gm2AP0);
        //女子组
        TitledPane groupFemale = new TitledPane();
        groupFemale.setText("女子组");
        groupFemale.setStyle(titleStyle);
        AnchorPane gfAP0 = new AnchorPane();
        gfAP0.setMinSize(1200, 435);
        ScrollPane gfSP = new ScrollPane();
        gfSP.setMinSize(1200, 435);
        gfSP.setLayoutX(0);
        gfSP.setLayoutY(36);
        AnchorPane gfAP = new AnchorPane();
        gfAP.setMinSize(1200, 435);
        //
        List<Team> gfTeams = theLeague.getTeams("女子组");
        RankData(gfTeams, gfAP,stage,theLeague);
        //
        gfSP.setContent(gfAP);
        TitleLabels(gfAP0);
        gfAP0.getChildren().add(gfSP);
        groupFemale.setContent(gfAP0);
        //成年组
        TitledPane groupAdult = new TitledPane();
        groupAdult.setText("成年组");
        groupAdult.setStyle(titleStyle);
        AnchorPane gaAP0 = new AnchorPane();
        gaAP0.setMinSize(1200, 435);
        ScrollPane gaSP = new ScrollPane();
        gaSP.setMinSize(1200, 400);
        gaSP.setLayoutX(0);
        gaSP.setLayoutY(36);
        AnchorPane gaAP = new AnchorPane();
        gaAP.setMinSize(1200, 400);
        //
        List<Team> gaTeams = theLeague.getTeams("成年组");
        RankData(gaTeams, gaAP,stage,theLeague);
        //
        gaSP.setContent(gaAP);
        TitleLabels(gaAP0);
        gaAP0.getChildren().add(gaSP);
        groupAdult.setContent(gaAP0);
        this.getPanes().addAll(groupMale1, groupMale2, groupFemale, groupAdult);
    }
    private void RankData(List<Team> teams, AnchorPane AP,Stage stage,League theLeague) {
        if (teams.isEmpty()) {
            //数据库中没有查到对应数据
            Button NoData = new Button();
            NoData.getStyleClass().addAll("btn","btn-warning");
            NoData.setText("没有找到对应数据！");
            NoData.setLayoutX(0);
            NoData.setLayoutY(0);
            NoData.setPrefSize(1200, 30);
            AP.getChildren().add(NoData);
            AP.setBackground(new Background(new BackgroundFill(Color.LAVENDERBLUSH,null,null)));
        } else {
            for (int i = 0; i<teams.size(); i++) {
                Button Rank = new Button();
                Rank.setBackground(new Background(new BackgroundFill(Color.LAVENDERBLUSH,null,null)));
                Rank.setText("" + teams.get(i).getTeamRank());
                Rank.setLayoutX(0);
                Rank.setLayoutY(i * 30);
                Rank.setMinSize(150, 30);

                Button name = new Button();
                name.setBackground(new Background(new BackgroundFill(Color.LAVENDERBLUSH,null,null)));
                name.setText(teams.get(i).getTeamName());
                name.setLayoutX(Rank.getLayoutX() + Rank.getMinWidth());
                name.setLayoutY(i * 30);
                name.setMinSize(150, 30);
                name.setFont(new Font("LiShu",24));
                name.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        ViewTrans vt = new ViewTrans();
                        vt.toTeamView(theLeague, stage,name.getText());
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
                        name.setBackground(new Background(new BackgroundFill(Color.LAVENDERBLUSH,null,null)));
                    }
                });

                Button WinNum = new Button();
                WinNum.setBackground(new Background(new BackgroundFill(Color.LAVENDERBLUSH,null,null)));
                WinNum.setText("" + teams.get(i).getWinNum());
                WinNum.setLayoutX(name.getLayoutX() + name.getMinWidth());
                WinNum.setLayoutY(i * 30);
                WinNum.setMinSize(150, 30);

                Button LoseNum = new Button();
                LoseNum.setBackground(new Background(new BackgroundFill(Color.LAVENDERBLUSH,null,null)));
                LoseNum.setText("" + teams.get(i).getLoseNum());
                LoseNum.setLayoutX(WinNum.getLayoutX() + WinNum.getMinWidth());
                LoseNum.setLayoutY(i * 30);
                LoseNum.setMinSize(150, 30);

                Button DrawNum = new Button();
                DrawNum.setBackground(new Background(new BackgroundFill(Color.LAVENDERBLUSH,null,null)));
                DrawNum.setText("" + teams.get(i).getDrawNum());
                DrawNum.setLayoutX(LoseNum.getLayoutX() + LoseNum.getMinWidth());
                DrawNum.setLayoutY(i * 30);
                DrawNum.setMinSize(150, 30);

                Button GoalNum = new Button();
                GoalNum.setBackground(new Background(new BackgroundFill(Color.LAVENDERBLUSH,null,null)));
                GoalNum.setText("" + teams.get(i).getGoalNum());
                GoalNum.setLayoutX(DrawNum.getLayoutX() + DrawNum.getMinWidth());
                GoalNum.setLayoutY(i * 30);
                GoalNum.setMinSize(150, 30);

                Button GoalLostNum = new Button();
                GoalLostNum.setBackground(new Background(new BackgroundFill(Color.LAVENDERBLUSH,null,null)));
                GoalLostNum.setText("" + teams.get(i).getGoalLostNum());
                GoalLostNum.setLayoutX(GoalNum.getLayoutX() + GoalNum.getMinWidth());
                GoalLostNum.setLayoutY(i * 30);
                GoalLostNum.setMinSize(150, 30);

                Button Score = new Button();
                Score.setBackground(new Background(new BackgroundFill(Color.LAVENDERBLUSH,null,null)));
                Score.setText("" + teams.get(i).getTeamScore());
                Score.setLayoutX(GoalLostNum.getLayoutX() + GoalLostNum.getMinWidth());
                Score.setLayoutY(i * 30);
                Score.setMinSize(150, 30);
                AP.getChildren().addAll(Rank, name, WinNum, LoseNum, DrawNum,
                        GoalNum, GoalLostNum, Score);
                AP.setBackground(new Background(new BackgroundFill(Color.LAVENDERBLUSH,null,null)));
            }
        }
    }

    private void TitleLabels(AnchorPane Ap) {
        Button rank = new Button();
        rank.getStyleClass().addAll("btn-success","btn");
        rank.setText("当前名次");
        rank.setLayoutX(30);
        rank.setLayoutY(0);
        rank.setMinSize(150, 30);

        Button name = new Button();
        name.getStyleClass().addAll("btn","btn-primary");
        name.setText("队名");
        name.setLayoutX(rank.getLayoutX() + rank.getMinWidth());
        rank.setLayoutY(rank.getLayoutY());
        name.setMinSize(rank.getMinWidth(), rank.getMinHeight());

        Button winNum = new Button();
        winNum.getStyleClass().addAll("btn","btn-info");
        winNum.setText("胜场数");
        winNum.setLayoutX(name.getLayoutX() + name.getMinWidth());
        winNum.setLayoutY(name.getLayoutY());
        winNum.setMinSize(rank.getMinWidth(), rank.getMinHeight());

        Button loseNum = new Button();
        loseNum.getStyleClass().addAll("btn","btn-info");
        loseNum.setText("负场数");
        loseNum.setLayoutX(winNum.getLayoutX() + winNum.getMinWidth());
        loseNum.setLayoutY(winNum.getLayoutY());
        loseNum.setMinSize(rank.getMinWidth(), rank.getMinHeight());

        Button drawNum = new Button();
        drawNum.getStyleClass().addAll("btn","btn-info");
        drawNum.setText("平局数");
        drawNum.setLayoutX(loseNum.getLayoutX() + loseNum.getMinWidth());
        drawNum.setLayoutY(loseNum.getLayoutY());
        drawNum.setMinSize(rank.getMinWidth(), rank.getMinHeight());

        Button goalNum = new Button();
        goalNum.getStyleClass().addAll("btn","btn-warning");
        goalNum.setText("进球数");
        goalNum.setLayoutX(drawNum.getLayoutX() + drawNum.getMinWidth());
        goalNum.setLayoutY(drawNum.getLayoutY());
        goalNum.setMinSize(rank.getMinWidth(), rank.getMinHeight());

        Button goalLostNum = new Button();
        goalLostNum.getStyleClass().addAll("btn","btn-warning");
        goalLostNum.setText("失球数");
        goalLostNum.setLayoutX(goalNum.getLayoutX() + goalNum.getMinWidth());
        goalLostNum.setLayoutY(goalNum.getLayoutY());
        goalLostNum.setMinSize(rank.getMinWidth(), rank.getMinHeight());

        Button score = new Button();
        score.getStyleClass().addAll("btn","btn-danger");
        score.setText("积分");
        score.setLayoutX(goalLostNum.getLayoutX() + goalLostNum.getMinWidth());
        score.setLayoutY(goalLostNum.getLayoutY());
        score.setMinSize(rank.getMinWidth(), rank.getMinHeight());
        Ap.getChildren().addAll(rank, name, winNum, loseNum, drawNum, goalNum, goalLostNum, score);
        Ap.setBackground(new Background(new BackgroundFill(Color.LAVENDERBLUSH,null,null)));
    }
}
