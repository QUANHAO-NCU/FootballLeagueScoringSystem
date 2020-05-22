package FootballLeagueScoringSystem.View;

import FootballLeagueScoringSystem.Module.League;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * @author QuanHao
 * 这个是主界面
 */
public class MainView extends TabPane {
    private League theLeague;
    public Stage stage;

    public MainView(League theLeague, Stage stage){
        this.theLeague = theLeague;
        this.stage = stage;
        generate();
        this.setPrefSize(1200, 600);
        this.setMaxSize(1200, 600);
    }

    public void generate(){
        /**
         * 标签换成按钮仅仅是为了让文本居中，设置背景填充是为了让按钮显示出标签的效果
         * setStyle("-fx-CSS代码")
         * */
        //设置标签属性
        String tabStyle = "-fx-pref-width:240px;-fx-pref-height:40px;-fx-text-align:center;-fx-font-size:18px;";
        //球队排行榜
        TeamRankView teamRankView = new TeamRankView(stage, theLeague);
        Tab teamRank = new Tab("球队排名", teamRankView);
        teamRank.setStyle(tabStyle);
        teamRank.setClosable(false);//不允许关闭这个标签页面
        //射手排行榜
        PlayerRankView playerRankView = new PlayerRankView(stage, theLeague);
        Tab playerRank = new Tab("射手排名", playerRankView);
        playerRank.setStyle(tabStyle);
        playerRank.setClosable(false);
        //今日赛程
        TodayBattleView todayBattleView = new TodayBattleView(stage, theLeague);
        Tab todayBattle = new Tab("今日赛程", todayBattleView.getScrollPane());
        todayBattle.setStyle(tabStyle);
        todayBattle.setClosable(false);
        //全部赛程
        AllBattleView allBattleView = new AllBattleView(stage, theLeague);
        Tab allBattle = new Tab("全部赛程", allBattleView.getScrollPane());
        allBattle.setStyle(tabStyle);
        allBattle.setClosable(false);
        //菜单功能界面
        MenuView menuView = new MenuView(stage, theLeague);
        Tab menu = new Tab("更多功能", menuView);
        menu.setStyle(tabStyle);
        menu.setClosable(false);
        //
        this.getTabs().addAll(teamRank, playerRank, todayBattle, allBattle, menu);
    }

    public void selectTab(String tab) {
        if(tab.equals("球队排名")){
            this.getSelectionModel().select(0);
        }
        if(tab.equals("射手排名")){
            this.getSelectionModel().select(1);
        }if(tab.equals("今日赛程")){
            this.getSelectionModel().select(2);
        }if(tab.equals("全部赛程")){
            this.getSelectionModel().select(3);
        }
    }

}





