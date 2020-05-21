package FootballLeagueScoringSystem.View;

import FootballLeagueScoringSystem.Control.ViewTrans;
import FootballLeagueScoringSystem.Module.Battle;
import FootballLeagueScoringSystem.Module.League;
import com.browniebytes.javafx.control.DateTimePicker;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author QuanHao
 * 修改比赛信息，通过选定某一个未开始的赛程（即未录入信息的赛程）
 * 修改此次比赛的获胜方，进球数据（进球球员，进球时间），违规信息
 */
public class UpdateDataView extends Pane {
    private GridPane container;
    private String[] teamSelect = new String[2];
    private String goalDetail;
    private String[] foulInfo;

    private String[] getTeamSelect() {
        return teamSelect;
    }

    public UpdateDataView(Stage stage, League theLeague) {
        container = new GridPane();
        container.setGridLinesVisible(true);//显示网格线，开发用
        container.setPadding(new Insets(20, 20, 20, 20));
        container.add(battleSelect(theLeague), 0, 0, 2, 12);
        container.add(goalInput(theLeague, teamSelect), 2, 0, 5, 2);
        container.add(goalInfoInput(theLeague, "", "", 0, 0, -1), 2, 2, 5, 6);
        container.add(foulInfoInput(theLeague), 2, 8, 5, 4);
        container.add(update(theLeague), 7, 0, 1, 6);
        container.add(back(theLeague, stage), 7, 6, 1, 6);

        this.setMinSize(1200, 600);
        this.setMaxSize(1920, 1080);
        this.getChildren().add(container);
    }

    private GridPane battleSelect(League theLeague) {
        /**
         * 选择赛程
         * */
        String[] colors = new String[]{"panel-success", "panel-info", "panel-warning"};      //panel顶部颜色选择
        GridPane battleSelector = new GridPane();
        battleSelector.setPrefSize(300, 600);
        battleSelector.setMaxSize(300, 600);
        Label tips = new Label("选择要修改的比赛信息↓");
        tips.setMinSize(300, 50);
        tips.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        tips.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
        battleSelector.add(tips, 0, 0, 2, 2);
        ScrollPane scrollPane = new ScrollPane();
        GridPane battles = new GridPane();
        Battle[] noStartBattles = theLeague.getNoStartBattle();
        battles.setVgap(1);
        for (int i = 0; noStartBattles[i] != null; i++) {
            Timestamp battleTime = noStartBattles[i].getBattleTime();  //对战时间
            String teamA = noStartBattles[i].getTeamA();
            String teamB = noStartBattles[i].getTeamB();
            String battleSide = noStartBattles[i].getBattleSide();
            org.kordamp.bootstrapfx.scene.layout.Panel panel = new org.kordamp.bootstrapfx.scene.layout.Panel();
            panel.getStyleClass().addAll("panel-primary", colors[i % 3], "panel-default");
            panel.setPadding(new Insets(8, 8, 8, 8));
            Label headLable = new Label(battleTime.toString().split("\\.")[0] + "\t" + battleSide);
            Label bodyLable = new Label(teamA + "\t\t" + "VS" + "\t\t" + teamB);
            panel.setHeading(headLable);
            panel.setBody(bodyLable);
            panel.setPrefWidth(300);
            panel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    teamSelect[0] = teamA;
                    teamSelect[1] = teamB;
                    goalDetail = null;
                    container.clearConstraints(goalInput(theLeague, teamSelect));
                    container.clearConstraints(goalInfoInput(theLeague, "", "", 0, 0, -1));
                    container.add(goalInput(theLeague, teamSelect), 2, 0, 5, 2);
                }
            });
            battles.add(panel, 0, i);
        }
        scrollPane.setPrefSize(300, 550);
        scrollPane.setMaxHeight(550);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setContent(battles);
        battleSelector.add(scrollPane, 0, 2, 2, 10);
        return battleSelector;
    }

    private GridPane goalInput(League theLeague, String[] battleSelect) {
        /**
         * 输入进球数
         * */
        GridPane goalInput = new GridPane();
        goalInput.setPrefSize(750, 100);
        goalInput.setMaxSize(750, 100);
        Label teamAL = new Label();
        Label teamBL = new Label();
        if (battleSelect[0] == null) {
            teamAL.setText("选择一个赛程");
            teamBL.setText("选择一个赛程");
        } else {
            teamAL.setText(battleSelect[0]);
            teamBL.setText(battleSelect[1]);
        }
        teamAL.setPrefSize(300, 50);
        teamBL.setPrefSize(300, 50);
        teamAL.getStyleClass().addAll("btn", "btn-primary");
        teamBL.getStyleClass().addAll("btn", "btn-primary");

        goalInput.add(teamAL, 0, 0, 2, 1);
        goalInput.add(teamBL, 3, 0, 2, 1);
        TextField teamAGoal = new TextField();
        teamAGoal.setPrefSize(150, 50);
        TextField teamBGoal = new TextField();
        teamBGoal.setPrefSize(150, 50);
        if (battleSelect[0] == null) {
            teamAGoal.setPromptText("选择一个赛程");
            teamBGoal.setPromptText("选择一个赛程");
        } else {
            teamAGoal.setPromptText("输入" + battleSelect[0] + "的进球数");
            teamBGoal.setPromptText("输入" + battleSelect[1] + "的进球数");
        }
        goalInput.add(teamAGoal, 0, 1, 2, 1);
        goalInput.add(teamBGoal, 4, 1, 2, 1);

        Label VS = new Label("VS");
        VS.setPrefSize(150, 50);
        VS.getStyleClass().addAll("btn", "btn-primary");
        goalInput.add(VS, 2, 0, 1, 1);
        Button commit = new Button("确定");
        commit.setPrefSize(150, 50);
        commit.getStyleClass().addAll("btn", "btn-primary");
        commit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //container.clearConstraints(goalInfoInput(theLeague, "","",0, 0, -1));
                if (teamAGoal.getText() != null && teamBGoal.getText() != null) {
                    container.clearConstraints(goalInfoInput(theLeague, "", "", 0, 0, -1));
                    container.add(goalInfoInput(theLeague, battleSelect[0], battleSelect[1], Integer.valueOf(teamAGoal.getText()), Integer.valueOf(teamBGoal.getText()), 0), 2, 2, 5, 6);
                    container.clearConstraints(foulInfoInput(theLeague));
                    container.add(foulInfoInput(theLeague), 2, 8, 5, 4);
                } else {
                    goalInfoInput(theLeague, "", "", 0, 0, -1);
                }
            }
        });
        goalInput.add(commit, 2, 1, 1, 1);
        return goalInput;
    }

    private GridPane goalInfoInput(League theLeague, String teamA, String teamB, int teamAGoal, int teamBGoal, int confirm) {
        final String[] goalDetail = new String[1];
        goalDetail[0] = "";
        /**
         * 处理进球信息
         * */
        GridPane goalInfoInput = new GridPane();
        goalInfoInput.setPrefSize(750, 300);
        goalInfoInput.setMaxSize(750, 300);
        /*******************************控件分割线（专用）**************************************/
        Label title = new Label("输入进球信息");
        title.setPrefSize(600, 50);
        title.setMaxSize(600, 50);
        title.getStyleClass().addAll("btn", "btn-primary");
        goalInfoInput.add(title, 0, 0, 4, 1);
        /*******************************控件分割线（专用）**************************************/
        if (teamAGoal == 0 && teamBGoal == 0 && confirm == -1) {
            //这种情况下表示没有输入有效的进球信息
            Label tips = new Label("选择一个赛程并输入对应的进球信息\n然后点击确定按钮");
            tips.setPrefSize(600, 250);
            tips.getStyleClass().addAll("btn", "btn-primary");
            goalInfoInput.add(tips, 0, 1, 4, 5);
        } else {
            Label playerName = new Label("球员名");
            playerName.setPrefSize(140, 50);
            playerName.getStyleClass().addAll("btn", "btn-primary");

            Label goalTeam = new Label("进球方");
            goalTeam.setPrefSize(140, 50);
            goalTeam.getStyleClass().addAll("btn", "btn-primary");

            Label goalTeam1 = new Label("被进球方");
            goalTeam1.setPrefSize(140, 50);
            goalTeam1.getStyleClass().addAll("btn", "btn-primary");

            Label time = new Label("进球时间");
            time.setPrefSize(180, 50);
            time.getStyleClass().addAll("btn", "btn-primary");

            HBox titleBar = new HBox();
            titleBar.setPadding(new Insets(0, 0, 0, 0));
            titleBar.setPrefSize(600, 50);
            titleBar.getChildren().addAll(playerName, goalTeam, goalTeam1, time);

            goalInfoInput.add(titleBar, 0, 1, 4, 1);
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setPadding(new Insets(0, 0, 0, 0));
            scrollPane.setPrefSize(600, 200);
            scrollPane.setMaxSize(600, 200);
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            GridPane goalInfo = new GridPane();
            goalInfo.setVgap(4);
            for (int i = 0; i < teamAGoal + teamBGoal; i++) {
                List<String> players = new ArrayList<>();
                players.addAll(theLeague.getPlayersName(teamA));
                players.addAll(theLeague.getPlayersName(teamB));
                ComboBox<String> t1 = new ComboBox<String>(FXCollections.observableArrayList(players));
                t1.setPrefSize(140, 50);
                t1.setPromptText("选择球员名字");

                ComboBox<String> t2 = new ComboBox(FXCollections.observableArrayList(
                        teamA, teamB));
                t2.setPrefSize(140, 50);
                t2.getSelectionModel().select(0);
                t2.setPromptText("选择进球方队伍");

                ComboBox<String> t3 = new ComboBox(FXCollections.observableArrayList(
                        teamA, teamB));
                t3.setPrefSize(140, 50);
                t3.getSelectionModel().select(1);
                t3.setPromptText("选择被进球方队伍");
                //时间选择器
                VBox vBox = new VBox();
                DateTimePicker datePicker = new DateTimePicker();

                Button button = new Button("提交本条信息");
                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        String playerName = t1.getValue();
                        String teamAName = t2.getValue();
                        String teamBName = t3.getValue();
                        if (teamAName.equals(teamBName)) {
                            /**
                             * 提示错误
                             * */
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("进球方和被进球方不应相同");
                            alert.showAndWait();
                        } else {
                            LocalDateTime time = datePicker.dateTimeProperty().getValue();
                            String formatTime = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                            /**
                             * 检查时间是否在比赛的时间范围内
                             * */
                            goalDetail[0] += (playerName + " " + teamAName + " " + teamBName + " " + formatTime + " ");
                        }
                        button.setText("本条信息已提交");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("本条信息提交成功！请勿重复提交 ");
                        alert.showAndWait();

                    }
                });
                vBox.getChildren().addAll(datePicker, button);
                goalInfo.addRow(i, t1, t2, t3, vBox);
            }
            scrollPane.setContent(goalInfo);
            goalInfoInput.add(scrollPane, 0, 2, 4, 4);
        }

        Button commit = new Button("提交信息");
        commit.setPrefSize(150, 300);
        commit.setMaxSize(150, 300);
        commit.getStyleClass().addAll("btn", "btn-primary");
        commit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String[] goalData = goalDetail[0].split(" ");
                for (int i = 0; i < (teamAGoal + teamBGoal) * 5; i += 5) {
                    int result;
                    if (teamAGoal > teamBGoal) {
                        result = 1;
                    } else if (teamAGoal < teamBGoal) {
                        result = -1;
                    } else result = 0;
                    theLeague.addGoalDetail(goalData[i + 0], goalData[i + 1], goalData[i + 2], goalData[i + 3] + " " + goalData[i + 4], result, "" + teamAGoal + ":" + teamBGoal);
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("信息提交成功！");
                alert.showAndWait();
            }
        });
        goalInfoInput.add(commit, 4, 0, 1, 6);

        return goalInfoInput;
    }

    private GridPane foulInfoInput(League theLeague) {
        String teamA = getTeamSelect()[0];
        String teamB = getTeamSelect()[1];
        List<String> players = new ArrayList<>();
        players.addAll(theLeague.getPlayersName(teamA));
        players.addAll(theLeague.getPlayersName(teamB));
        GridPane foulInfoPane = new GridPane();
        foulInfoPane.setPrefSize(750, 200);
        foulInfoPane.setMaxSize(750, 200);
        /*******************************控件分割线（专用）**************************************/
        Label title = new Label("录入违规信息");
        title.setPrefSize(750, 50);
        title.getStyleClass().addAll("btn", "btn-primary");
        foulInfoPane.add(title, 0, 0, 5, 1);
        /*******************************控件分割线（专用）**************************************/
        HBox head = new HBox();
        Label playerName = new Label("球员名字");
        playerName.setPrefSize(150, 50);
        playerName.getStyleClass().addAll("btn", "btn-primary");
        Label teamName = new Label("所属球队");
        teamName.setPrefSize(150, 50);
        teamName.getStyleClass().addAll("btn", "btn-primary");
        Label fouInfo = new Label("违规信息");
        fouInfo.setPrefSize(150, 50);
        fouInfo.getStyleClass().addAll("btn", "btn-primary");
        Label punishment = new Label("惩罚信息");
        punishment.setPrefSize(150, 50);
        punishment.getStyleClass().addAll("btn", "btn-primary");
        Label summit = new Label("提交按钮");
        summit.setPrefSize(150, 50);
        summit.getStyleClass().addAll("btn", "btn-primary");
        head.getChildren().addAll(playerName, teamName, fouInfo, punishment, summit);
        foulInfoPane.add(head, 0, 1, 5, 1);
        /*******************************控件分割线（专用）**************************************/
        ScrollPane dataInputPane = new ScrollPane();
        dataInputPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        dataInputPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        dataInputPane.setPrefSize(750, 100);
        dataInputPane.setMaxSize(750, 100);
        dataInputPane.setPadding(new Insets(0, 0, 0, 0));
        GridPane container = new GridPane();
        container.add(cell(players, teamA, teamB, theLeague), 0, 0, 5, 1);
        Button plus = new Button("+");
        plus.setPrefSize(150, 50);
        container.add(plus, 2, 1);
        plus.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int indexRow = container.getRowIndex(plus);
                int indexColumn = container.getColumnIndex(plus);
                container.setRowIndex(plus, indexRow + 1);
                container.setColumnIndex(plus, indexColumn);//重新设置plus按钮的位置
                container.add(cell(players, teamA, teamB, theLeague), 0, indexRow, 5, 1);
            }
        });
        dataInputPane.setContent(container);
        foulInfoPane.add(dataInputPane, 0, 2, 5, 2);
        return foulInfoPane;
    }

    private HBox cell(List<String> players, String teamA, String teamB, League theLeague) {
        String[] foulInfo = new String[4];
        HBox cell = new HBox();
        ComboBox<String> choicePlayerName = new ComboBox<>(FXCollections.observableArrayList(players));
        choicePlayerName.setPrefSize(150, 50);
        ComboBox<String> team = new ComboBox<>(FXCollections.observableArrayList(teamA, teamB));
        team.setPrefSize(150, 50);
        TextField inputFoul = new TextField();
        inputFoul.setPrefSize(150, 50);
        TextField inputPunishment = new TextField();
        inputPunishment.setPrefSize(150, 50);
        Button summit = new Button("确定");
        summit.setPrefSize(150, 50);
        summit.getStyleClass().addAll("btn", "btn-primary");
        summit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                foulInfo[0] = choicePlayerName.getValue();
                foulInfo[1] = team.getValue();
                foulInfo[2] = inputFoul.getText();
                foulInfo[3] = inputPunishment.getText();
                if ((inputFoul.getText() == null && inputFoul.getText().length() == 0) || (inputPunishment.getText() == null && inputPunishment.getText().length() == 0)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("犯规信息或和惩罚信息不应为空");
                    alert.showAndWait();
                } else {
                    if (theLeague.addFoul(foulInfo)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("信息添加成功！");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("检查球员是否属于所属球队！");
                        alert.showAndWait();
                    }
                }
            }
        });
        cell.getChildren().addAll(choicePlayerName, team, inputFoul, inputPunishment, summit);
        return cell;
    }

    private Button update(League theLeague) {
        Button update = new Button("更新信息到数据库");
        update.getStyleClass().addAll("btn", "btn-primary");
        update.setPrefSize(150, 300);
        update.setMaxSize(150, 300);
        update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("信息更新成功！你可以继续更新信息或返回上级");
                alert.show();
            }
        });
        return update;
    }

    private Button back(League theLeague, Stage stage) {
        Button back = new Button("返回上级");
        back.getStyleClass().addAll("btn", "btn-primary");
        back.setPrefSize(150, 300);
        back.setMaxSize(150, 300);
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ViewTrans vt = new ViewTrans();
                try {
                    vt.toMainView(theLeague, stage, "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return back;
    }

}
