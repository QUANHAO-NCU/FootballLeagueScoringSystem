package FootballLeagueScoringSystem.View;

import FootballLeagueScoringSystem.Control.ViewTrans;
import FootballLeagueScoringSystem.Module.League;
import FootballLeagueScoringSystem.Module.Player;
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
 * @author QuanHao
 * 球员射手视图模型
 * 仅显示单个射手
 * 显示姓名，所属球队，进球数，排名
 * 部分应该使用Label控件的使用了Button控件仅仅是为了方便让文字居中
 */
public class PlayerView extends Pane {
    public Stage stage;
    private Player player;
    private ScrollPane scrollPane;
    private Button teamName;
    private Button rank;
    private League theLeague;
    public PlayerView(Player player, Stage stage,League theLeague) {
        this.stage = stage;
        this.scrollPane = new ScrollPane();
        this.player = player;
        this.theLeague= theLeague;
        Generate(player,theLeague);
        this.setMinSize(1200, 600);
        this.setMaxSize(1920, 1080);
    }
    private void getInfo(String playerName){
        /**
         * 传入一个球员的名字，然后从数据库中读取到这个球员的信息
         * 再以此实例化一个球员的个人信息面板
         * */
        
    }
    private void Generate(Player player,League theLeague) {
        /**
         * 各个控件之间的坐标采用相近控件的相对坐标
         * */
        TopButton topButton = new TopButton(this.stage,this.theLeague);//顶部的四个按钮
        //左上一，队名按钮
        this.teamName = new Button();
        teamName.setFont(new Font("Microsoft YaHei", 28));
        teamName.setText(player.getTeamName());
        teamName.setLayoutX(topButton.getNewBaseX());
        teamName.setLayoutY(topButton.getNewBaseY() + 10);
        teamName.setMinSize(topButton.getButtonWidth() * 2 + topButton.getSpace(), topButton.getButtonHeight() * 2);
        teamName.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ViewTrans vt = new ViewTrans();
                vt.toTeamView(theLeague, stage,teamName.getText());
                System.out.println("to player View");
            }
        });
        //右上一，排名按钮
        this.rank = new Button();
        rank.setFont(new Font("Microsoft YaHei", 28));
        rank.setText("个人排名：" + player.getRank());
        rank.setLayoutX(teamName.getLayoutX() + teamName.getMinWidth() + topButton.getSpace());
        rank.setLayoutY(teamName.getLayoutY());
        rank.setMinSize(topButton.getButtonWidth() * 2 + topButton.getSpace(), topButton.getButtonHeight() * 2);
        //左下一，头像标签
        Label playerLogo = new Label();
        Image image = new Image(getPic(player.getName()));
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        playerLogo.setGraphic(imageView);
        playerLogo.setLayoutX(teamName.getLayoutX());
        playerLogo.setLayoutY(teamName.getLayoutY() + teamName.getMinHeight() + 10);
        playerLogo.setMinSize(topButton.getButtonWidth() * 1.5 + topButton.getSpace(), (topButton.getButtonHeight() * 2) * 5);
        playerLogo.setBackground(new Background(new BackgroundFill(Color.LAVENDER, null, null)));
        //中一，球员名字标签
        Button playerName = new Button();
        playerName.setTextAlignment(TextAlignment.CENTER);
        playerName.setBackground(new Background(new BackgroundFill(Color.LAVENDER, null, null)));
        playerName.setFont(new Font("Microsoft YaHei", 28));
        playerName.setText(player.getName());
        playerName.setLayoutX(playerLogo.getLayoutX() + playerLogo.getMinWidth() + 10);
        playerName.setLayoutY(playerLogo.getLayoutY());
        playerName.setMinSize(topButton.getButtonWidth() * 3 / 4, (playerLogo.getMinHeight() - 10) / 2);
        //中二，进球数标签
        Button goalNum = new Button();
        goalNum.setTextAlignment(TextAlignment.CENTER);
        goalNum.setBackground(new Background(new BackgroundFill(Color.LAVENDER, null, null)));
        goalNum.setFont(new Font("Microsoft YaHei", 28));
        goalNum.setText("进球数：\n" + player.getScore());
        goalNum.setLayoutX(playerName.getLayoutX());
        goalNum.setLayoutY(playerName.getLayoutY() + playerName.getMinHeight() + 10);
        goalNum.setMinSize(playerName.getMinWidth(), playerName.getMinHeight());
        //右下，进球详细信息
        FlowPane container = new FlowPane();
        String[] goalInfo = theLeague.getGoalInfo(player);
        if(goalInfo[0]==null){
            Button info = new Button();
            info.setFont(new Font("LiShu", 28));
            info.setBackground(new Background(new BackgroundFill(Color.LAVENDER, null, null)));
            info.setText("无进球数据");
            info.setTextAlignment(TextAlignment.CENTER);
            info.setMinSize(500, 120);
            container.getChildren().add(info);
        }
        else {
            for (int i = 0; goalInfo[i] != null; i++) {
                Button info = new Button();
                info.setFont(new Font("Microsoft YaHei", 28));
                info.setBackground(new Background(new BackgroundFill(Color.LAVENDER, null, null)));
                info.setText(goalInfo[i]);
                info.setTextAlignment(TextAlignment.CENTER);
                info.setMinSize(500, 120);
                container.getChildren().add(info);
            }
        }
        scrollPane.setContent(container);
        scrollPane.setLayoutX(playerName.getLayoutX() + playerName.getMinWidth() + 10);
        scrollPane.setLayoutY(rank.getLayoutY() + rank.getMinHeight() + 10);
        scrollPane.setMaxSize(rank.getLayoutX() + rank.getMinWidth() - scrollPane.getLayoutX(),
                playerLogo.getMinHeight());
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        //将所有控件添加到面板
        this.getChildren().addAll(
                topButton.teamRank, topButton.playerRank,
                topButton.todaySchedule, topButton.schedule,
                this.teamName, this.rank, playerLogo, playerName, goalNum, scrollPane);
    }
    public String getPic(String name) {
        return "file:./GameData/Pic/" + name + ".png";
    }
}
