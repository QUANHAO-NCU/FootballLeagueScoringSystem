package FootballLeagueScoringSystem.View;

import FootballLeagueScoringSystem.Control.ViewTrans;
import FootballLeagueScoringSystem.Module.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * @author QuanHao
 * 新建球队，新建球员，增加裁判界面
 */
public class AddDataView extends Pane {
    public AddDataView(Stage stage, League theLeague) {
        this.setMinSize(1200, 600);
        this.setMaxSize(1920, 1080);
        addTeam(theLeague);
        addPlayer(theLeague);
        addJudger(theLeague);
        Button back = new Button("返回");
        back.setLayoutX(900);
        back.setLayoutY(465);
        back.setMinSize(180, 60);
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ViewTrans vt = new ViewTrans();
                try {
                    vt.toMainView(theLeague, stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        this.getChildren().add(back);
    }

    private void addTeam(League theLeague) {
        /**
         * 添加队伍
         * */
        String style = "-fx-background-color: #6293ff;";
        //这里设置颜色是便于开发的时候区分，具体样式给美化来做
        //顶部，提示栏，欢迎栏，建议设置为窗口Title
        Label tips = new Label("添加信息");
        tips.setStyle(style);
        tips.setLayoutX(0);
        tips.setLayoutY(0);
        tips.setMinSize(1200, 100);
        //提示输入球队名字的标签
        Label teamNameL = new Label("输入球队名字(必填)：");
        teamNameL.setLayoutX(tips.getLayoutX());
        teamNameL.setLayoutY(tips.getLayoutY() + tips.getMinHeight());
        teamNameL.setMinSize(185, 40);
        teamNameL.setStyle(style);
        //输入队名的文本框
        TextField teamNameInput = new TextField();
        teamNameInput.setPromptText("输入新队伍名字:");//提示信息
        teamNameInput.setEditable(true);//可编辑
        teamNameInput.setPrefSize(110, teamNameL.getMinHeight());//预设大小
        teamNameInput.setAlignment(Pos.CENTER_LEFT);//文字对齐方式
        teamNameInput.setLayoutX(teamNameL.getLayoutX() + teamNameL.getMinWidth());
        teamNameInput.setLayoutY(teamNameL.getLayoutY());
        //提示输入所属小组的标签
        Label groupNameL = new Label("输入所属的小组(必填)：");
        groupNameL.setLayoutX(teamNameL.getLayoutX());
        groupNameL.setLayoutY(teamNameL.getLayoutY() + teamNameL.getMinHeight());
        groupNameL.setMinSize(185, 40);
        groupNameL.setStyle(style);
        //选择所属的小组
        ComboBox<String> groupNameInput = new ComboBox<>(FXCollections.observableArrayList(
                "男子甲组", "男子乙组", "女子组", "成年组"));
        groupNameInput.getStyleClass().addAll("split-menu-btn-info");

        /**
         * 请设置默认值
         * */
        groupNameInput.setPrefSize(110, 40);//预设大小
        groupNameInput.setLayoutX(groupNameL.getLayoutX() + groupNameL.getMinWidth());
        groupNameInput.setLayoutY(groupNameL.getLayoutY());
        //
        /***
         * 其他信息，用折叠面板整合
         */
        Accordion container = new Accordion();
        container.setMinSize(310, 260);
        container.setBorder(Border.EMPTY);
        container.setLayoutX(groupNameL.getLayoutX());
        container.setLayoutY(groupNameL.getLayoutY() + groupNameL.getMinHeight());
        TitledPane titledPane = new TitledPane();
        titledPane.setText("其他信息(可以使用默认值)");
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setMaxSize(310, 260);
        titledPane.setBorder(Border.EMPTY);
        FlowPane flowPane = new FlowPane();
        flowPane.setMinWidth(310);
        flowPane.setBorder(Border.EMPTY);
        //提示输入排名标签
        Label teamRankL = new Label("输入排名(选填)：");
        teamRankL.setLayoutX(0);
        teamRankL.setLayoutY(0);
        teamRankL.setMinSize(185, 40);
        teamRankL.setStyle(style);
        //输入排名
        TextField teamRankInput = new TextField();
        teamRankInput.setPromptText("输入排名:");//提示信息
        teamRankInput.setEditable(true);//可编辑
        teamRankInput.setPrefSize(110, 40);//预设大小
        teamRankInput.setAlignment(Pos.CENTER_LEFT);//文字对齐方式
        teamRankInput.setLayoutX(teamRankL.getLayoutX() + teamRankL.getMinWidth());
        teamRankInput.setLayoutY(teamRankL.getLayoutY());
        //提示输入胜场数的标签
        Label winNumL = new Label("输入胜场数(选填)：");
        winNumL.setLayoutX(teamRankL.getLayoutX());
        winNumL.setLayoutY(teamRankL.getLayoutY() + teamRankL.getMinHeight());
        winNumL.setMinSize(teamRankL.getMinWidth(), teamRankL.getMinHeight());
        winNumL.setStyle(style);
        //输入胜场数
        TextField winNumInput = new TextField();
        winNumInput.setPromptText("输入胜场数:");//提示信息
        winNumInput.setEditable(true);//可编辑
        winNumInput.setPrefSize(teamRankInput.getPrefWidth(), teamRankInput.getPrefHeight());//预设大小
        winNumInput.setAlignment(Pos.CENTER_LEFT);//文字对齐方式
        winNumInput.setLayoutX(winNumL.getLayoutX() + winNumL.getMinWidth());
        winNumInput.setLayoutY(winNumL.getLayoutY());
        //提示输入负场数的标签
        Label loseNumL = new Label("输入负场数(选填)不填默认为0：");
        loseNumL.setLayoutX(winNumL.getLayoutX());
        loseNumL.setLayoutY(winNumL.getLayoutY() + winNumL.getMinHeight());
        loseNumL.setMinSize(winNumL.getMinWidth(), winNumL.getMinHeight());
        loseNumL.setStyle(style);
        //输负场数
        TextField loseNumInput = new TextField();
        loseNumInput.setPromptText("输入负场数:");//提示信息
        loseNumInput.setEditable(true);//可编辑
        loseNumInput.setPrefSize(winNumInput.getPrefWidth(), winNumInput.getPrefHeight());//预设大小
        loseNumInput.setAlignment(Pos.CENTER_LEFT);//文字对齐方式
        loseNumInput.setLayoutX(loseNumL.getLayoutX() + loseNumL.getMinWidth());
        loseNumInput.setLayoutY(loseNumL.getLayoutY());
        //提示输入平局数的标签
        Label drawNumL = new Label("输入平局场数(选填)不填默认为0：");
        drawNumL.setLayoutX(loseNumL.getLayoutX());
        drawNumL.setLayoutY(loseNumL.getLayoutY() + loseNumL.getMinHeight());
        drawNumL.setMinSize(winNumL.getMinWidth(), winNumL.getMinHeight());
        drawNumL.setStyle(style);
        //输入平局场数
        TextField drawNumInput = new TextField();
        drawNumInput.setPromptText("输入平局数:");//提示信息
        drawNumInput.setEditable(true);//可编辑
        drawNumInput.setPrefSize(winNumInput.getPrefWidth(), winNumInput.getPrefHeight());//预设大小
        drawNumInput.setAlignment(Pos.CENTER_LEFT);//文字对齐方式
        drawNumInput.setLayoutX(drawNumL.getLayoutX() + drawNumL.getMinWidth());
        drawNumInput.setLayoutY(drawNumL.getLayoutY());
        //提示输入进球数的标签
        Label goalNumL = new Label("输入进球数(选填)不填默认为0：");
        goalNumL.setLayoutX(winNumL.getLayoutX());
        goalNumL.setLayoutY(drawNumInput.getLayoutY() + drawNumInput.getMinHeight());
        goalNumL.setMinSize(winNumL.getMinWidth(), winNumL.getMinHeight());
        goalNumL.setStyle(style);
        //输入进球数
        TextField goalNumInput = new TextField();
        goalNumInput.setPromptText("输入进球数:");//提示信息
        goalNumInput.setEditable(true);//可编辑
        goalNumInput.setPrefSize(winNumInput.getPrefWidth(), winNumInput.getPrefHeight());//预设大小
        goalNumInput.setAlignment(Pos.CENTER_LEFT);//文字对齐方式
        goalNumInput.setLayoutX(goalNumL.getLayoutX() + goalNumL.getMinWidth());
        goalNumInput.setLayoutY(goalNumL.getLayoutY());
        //提示输入失球数的标签
        Label goalLostNumL = new Label("输入失球数(选填)不填默认为0：");
        goalLostNumL.setLayoutX(winNumL.getLayoutX());
        goalLostNumL.setLayoutY(goalNumL.getLayoutY() + goalNumL.getMinHeight());
        goalLostNumL.setMinSize(winNumL.getMinWidth(), winNumL.getMinHeight());
        goalLostNumL.setStyle(style);
        //输入失球数
        TextField goalLostNumInput = new TextField();
        goalLostNumInput.setPromptText("输入失球数:");//提示信息
        goalLostNumInput.setEditable(true);//可编辑
        goalLostNumInput.setPrefSize(winNumInput.getPrefWidth(), winNumInput.getPrefHeight());//预设大小
        goalLostNumInput.setAlignment(Pos.CENTER_LEFT);//文字对齐方式
        goalLostNumInput.setLayoutX(goalLostNumL.getLayoutX() + goalLostNumL.getMinWidth());
        goalLostNumInput.setLayoutY(goalLostNumL.getLayoutY());
        //提示输入积分的标签
        Label scoreNumL = new Label("输入积分(选填不填默认为0)：");
        scoreNumL.setLayoutX(winNumL.getLayoutX());
        scoreNumL.setLayoutY(goalLostNumL.getLayoutY() + goalLostNumL.getMinHeight());
        scoreNumL.setMinSize(winNumL.getMinWidth(), winNumL.getMinHeight());
        scoreNumL.setStyle(style);
        //输入积分
        TextField scoreInput = new TextField();
        scoreInput.setPromptText("输入积分:");//提示信息
        scoreInput.setEditable(true);//可编辑
        scoreInput.setPrefSize(winNumInput.getPrefWidth(), winNumInput.getPrefHeight());//预设大小
        scoreInput.setAlignment(Pos.CENTER_LEFT);//文字对齐方式
        scoreInput.setLayoutX(scoreNumL.getLayoutX() + scoreNumL.getMinWidth());
        scoreInput.setLayoutY(scoreNumL.getLayoutY());
        flowPane.getChildren().addAll(teamRankL, teamRankInput, winNumL, winNumInput, loseNumL, loseNumInput, drawNumL, drawNumInput,
                goalNumL, goalNumInput, goalLostNumL, goalLostNumInput, scoreNumL, scoreInput);
        scrollPane.setContent(flowPane);
        titledPane.setContent(scrollPane);
        container.getPanes().addAll(titledPane);
        //添加球队按钮
        Button registerTeam = new Button("添加球队");
        registerTeam.setLayoutX(40);
        registerTeam.setLayoutY(465);
        registerTeam.setMinSize(180, 60);
        registerTeam.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String teamName = teamNameInput.getText();
                String groupName = groupNameInput.getValue();
                int teamRank, winNum, loseNum, drawNum, goalNum, goalLostNum, score;
                try {
                    teamRank = Integer.valueOf(teamRankInput.getText());
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("参数错误");
                    alert.setContentText("输入了非法参数，将设置这个值为默认值");
                    teamRank = -1;
                    alert.showAndWait();
                }
                try {
                    winNum = Integer.valueOf(winNumInput.getText());
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("参数错误");
                    alert.setContentText("输入了非法参数，将设置这个值为默认值");
                    winNum = 0;
                    alert.showAndWait();
                }
                try {
                    loseNum = Integer.valueOf(loseNumInput.getText());
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("参数错误");
                    alert.setContentText("输入了非法参数，将设置这个值为默认值");
                    loseNum = 0;
                    alert.showAndWait();
                }
                try {
                    drawNum = Integer.valueOf(drawNumInput.getText());
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("参数错误");
                    alert.setContentText("输入了非法参数，将设置这个值为默认值");
                    drawNum = 0;
                    alert.showAndWait();
                }
                try {
                    goalNum = Integer.valueOf(goalNumInput.getText());
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("参数错误");
                    alert.setContentText("输入了非法参数，将设置这个值为默认值");
                    goalNum = 0;
                    alert.showAndWait();
                }
                try {
                    goalLostNum = Integer.valueOf(goalLostNumInput.getText());
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("参数错误");
                    alert.setContentText("输入了非法参数，将设置这个值为默认值");
                    goalLostNum = 0;
                    alert.showAndWait();
                }
                try {
                    score = Integer.valueOf(scoreInput.getText());
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("参数错误");
                    alert.setContentText("输入了非法参数，将设置这个值为默认值");
                    score = 0;
                    alert.showAndWait();
                }
                Team team = new Team(teamName, teamRank, winNum, loseNum, drawNum, goalNum, goalLostNum, groupName, score);
                if(theLeague.addTeam(team)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("球队添加成功");
                    alert.showAndWait();
                }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("球队添加失败，请检查数据库设置");
                    alert.showAndWait();
                }
            }
        });
        this.getChildren().addAll(tips, teamNameL, teamNameInput, groupNameL, groupNameInput,
                container, registerTeam
        );
    }

    private void addPlayer(League theLeague) {
        /**
         * 添加球员
         * */
        String style = "-fx-background-color: #6293ff;";
        //输入球员名字
        Label playerNameL = new Label("输入球员名字：");
        playerNameL.setLayoutX(320);
        playerNameL.setLayoutY(100);
        playerNameL.setMinSize(185, 40);
        playerNameL.setStyle(style);
        TextField playerNameInput = new TextField();
        playerNameInput.setLayoutX(playerNameL.getLayoutX() + playerNameL.getMinWidth());
        playerNameInput.setLayoutY(playerNameL.getLayoutY());
        playerNameInput.setPrefSize(playerNameL.getMinWidth(), playerNameL.getMinHeight());
        playerNameInput.setEditable(true);
        playerNameInput.setPromptText("输入球员的名字");
        playerNameInput.setAlignment(Pos.CENTER_LEFT);
        //输入球队的名字
        Label teamNameL = new Label("输入所属球队的名字：");
        teamNameL.setLayoutX(playerNameL.getLayoutX());
        teamNameL.setLayoutY(playerNameL.getLayoutY() + playerNameL.getMinHeight());
        teamNameL.setMinSize(playerNameL.getMinWidth(), playerNameL.getMinHeight());
        teamNameL.setStyle(style);
        ComboBox<String> teamNameInput = new ComboBox<>(FXCollections.observableArrayList(theLeague.getTeams()));
        teamNameInput.setLayoutX(teamNameL.getLayoutX() + teamNameL.getMinWidth());
        teamNameInput.setLayoutY(teamNameL.getLayoutY());
        teamNameInput.setPrefSize(teamNameL.getMinWidth(), teamNameL.getMinHeight());
        teamNameInput.setEditable(true);
        teamNameInput.setPromptText("选择所属球队");

        //其他信息
        Accordion container = new Accordion();
        container.setMinSize(310, 260);
        container.setBorder(Border.EMPTY);
        container.setLayoutX(teamNameL.getLayoutX());
        container.setLayoutY(teamNameL.getLayoutY() + teamNameL.getMinHeight());
        TitledPane titledPane = new TitledPane();
        titledPane.setText("其他信息(可以使用默认值)");
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setMaxSize(310, 260);
        titledPane.setBorder(Border.EMPTY);
        FlowPane flowPane = new FlowPane();
        flowPane.setMinWidth(310);
        flowPane.setBorder(Border.EMPTY);
        //输入球员排名
        Label playerRankL = new Label("输入排名(选填)：");
        playerRankL.setLayoutX(0);
        playerRankL.setLayoutY(0);
        playerRankL.setMinSize(185, 40);
        playerRankL.setStyle(style);
        //输入排名
        TextField playerRankInput = new TextField();
        playerRankInput.setPromptText("输入排名（不输入会根据积分自动计算）:");//提示信息
        playerRankInput.setEditable(true);//可编辑
        playerRankInput.setPrefSize(110, 40);//预设大小
        playerRankInput.setAlignment(Pos.CENTER_LEFT);//文字对齐方式
        playerRankInput.setLayoutX(playerRankL.getLayoutX() + playerRankL.getMinWidth());
        playerRankInput.setLayoutY(playerRankL.getLayoutY());
        //输入球员进球数
        Label playerGoalL = new Label("输入进球数(选填)：");
        playerGoalL.setLayoutX(playerRankL.getLayoutX());
        playerGoalL.setLayoutY(playerRankL.getLayoutY() + playerRankL.getMinHeight());
        playerGoalL.setMinSize(playerRankL.getMinWidth(), playerRankL.getMinHeight());
        playerGoalL.setStyle(style);
        //输入进球数
        TextField playerGoalInput = new TextField();
        playerGoalInput.setPromptText("输入进球数:");//提示信息
        playerGoalInput.setEditable(true);//可编辑
        playerGoalInput.setPrefSize(110, 40);//预设大小
        playerGoalInput.setAlignment(Pos.CENTER_LEFT);//文字对齐方式
        playerGoalInput.setLayoutX(playerGoalL.getLayoutX() + playerGoalL.getMinWidth());
        playerGoalInput.setLayoutY(playerGoalL.getLayoutY());
        //
        flowPane.getChildren().addAll(playerRankL, playerRankInput, playerGoalL, playerGoalInput);
        scrollPane.setContent(flowPane);
        titledPane.setContent(scrollPane);
        container.getPanes().addAll(titledPane);
        //添加球员按钮
        Button registerPlayer = new Button("添加球员");
        registerPlayer.setLayoutX(400);
        registerPlayer.setLayoutY(465);
        registerPlayer.setMinSize(180, 60);
        registerPlayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String playerName = playerNameInput.getText();
                String teamName = teamNameInput.getValue();
                int rank, score;
                try {
                    rank = Integer.valueOf(playerRankInput.getText());
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("参数错误");
                    alert.setContentText("输入了非法参数，将设置这个值为默认值");
                    rank = 0;
                    alert.showAndWait();
                }
                try {
                    score = Integer.valueOf(playerGoalInput.getText());
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("参数错误");
                    alert.setContentText("输入了非法参数，将设置这个值为默认值");
                    score = 0;
                    alert.showAndWait();
                }
                Player player = new Player(playerName, teamName, "", score, rank, "");
                theLeague.addPlayer(player);
            }
        });
        this.getChildren().addAll(playerNameL, playerNameInput, teamNameL, teamNameInput, container, registerPlayer);
    }

    private void addJudger(League theLeague) {
        /**
         * 添加管理员/裁判
         * */
        String style = "-fx-background-color: #6293ff;";
        //输入管理员/裁判名字
        Label userNameL = new Label("输入新增管理员/裁判名字：");
        userNameL.setLayoutX(670);
        userNameL.setLayoutY(100);
        userNameL.setMinSize(185, 40);
        userNameL.setStyle(style);
        TextField userNameInput = new TextField();
        userNameInput.setLayoutX(userNameL.getLayoutX() + userNameL.getMinWidth());
        userNameInput.setLayoutY(userNameL.getLayoutY());
        userNameInput.setPrefSize(userNameL.getMinWidth(), userNameL.getMinHeight());
        userNameInput.setEditable(true);
        userNameInput.setPromptText("输入新增管理员/裁判名字");
        userNameInput.setAlignment(Pos.CENTER_LEFT);
        //输入管理员/裁判账号
        Label userAccountL = new Label("输入管理员/裁判账号：");
        userAccountL.setLayoutX(userNameL.getLayoutX());
        userAccountL.setLayoutY(userNameL.getLayoutY() + userNameL.getMinHeight());
        userAccountL.setMinSize(userNameL.getMinWidth(), userNameL.getMinHeight());
        userAccountL.setStyle(style);
        TextField userAccountInput = new TextField();
        userAccountInput.setLayoutX(userAccountL.getLayoutX() + userAccountL.getMinWidth());
        userAccountInput.setLayoutY(userAccountL.getLayoutY());
        userAccountInput.setPrefSize(userAccountL.getMinWidth(), userAccountL.getMinHeight());
        userAccountInput.setEditable(true);
        userAccountInput.setPromptText("输入管理员/裁判账号");
        userAccountInput.setAlignment(Pos.CENTER_LEFT);
        //输入管理员/裁判密码
        Label userPasswordL = new Label("输入管理员/裁判密码：");
        userPasswordL.setLayoutX(userAccountL.getLayoutX());
        userPasswordL.setLayoutY(userAccountL.getLayoutY() + userAccountL.getMinHeight());
        userPasswordL.setMinSize(185, 40);
        userPasswordL.setStyle(style);
        //输入管理员/裁判密码
        TextField userPasswordInput = new TextField();
        userPasswordInput.setPromptText("输入管理员/裁判密码:");//提示信息
        userPasswordInput.setEditable(true);//可编辑
        userPasswordInput.setPrefSize(110, 40);//预设大小
        userPasswordInput.setAlignment(Pos.CENTER_LEFT);//文字对齐方式
        userPasswordInput.setLayoutX(userPasswordL.getLayoutX() + userPasswordL.getMinWidth());
        userPasswordInput.setLayoutY(userPasswordL.getLayoutY());
        //输入用户职位
        Label positionL = new Label("输入管理员/裁判职位：");
        positionL.setLayoutX(userPasswordL.getLayoutX());
        positionL.setLayoutY(userPasswordL.getLayoutY() + userPasswordL.getMinHeight());
        positionL.setMinSize(userPasswordL.getMinWidth(), userPasswordL.getMinHeight());
        positionL.setStyle(style);
        //输入职位
        ComboBox<String> positionInput = new ComboBox<String>(FXCollections.observableArrayList("其他管理员","主裁判","副裁判"));
        positionInput.setPromptText("选择职位:");//提示信息
        positionInput.setPrefSize(110, 40);//预设大小
        positionInput.setLayoutX(positionL.getLayoutX() + positionL.getMinWidth());
        positionInput.setLayoutY(positionL.getLayoutY());
        //
        //添加用户按钮
        Button registerUser = new Button("添加用户/裁判");
        registerUser.setLayoutX(660);
        registerUser.setLayoutY(465);
        registerUser.setMinSize(180, 60);
        registerUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String userName, userAccount, userPassword, position;
                userName = userNameInput.getText();
                userAccount = userAccountInput.getText();
                userPassword = userPasswordInput.getText();position = positionInput.getValue();
                if((userAccount==null&&userAccount.length()==0)||(userPassword==null&&userPassword.length()==0)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("参数错误");
                    alert.setContentText("输入了非法参数，账号或密码不应为空");
                    alert.showAndWait();
                }else{
                    theLeague.addSystemUser(userName, userAccount, userPassword, position);
                }
            }
        });
        this.getChildren().addAll(userNameL, userNameInput, userAccountL, userAccountInput,
                userPasswordL, userPasswordInput, positionL, positionInput, registerUser);
    }
}
