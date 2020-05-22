package FootballLeagueScoringSystem.View;

import FootballLeagueScoringSystem.Module.*;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AllBattleView extends Pane {
    public Stage stage;
    private League battleSql;
    private ScrollPane scrollPane = new ScrollPane();
    public ScrollPane getScrollPane() {
        return scrollPane;
    }
    AllBattleView(Stage stage, League theLeague){
        this.battleSql = theLeague;
        generate();
    }
    public void generate(){
        /**
         * @author :Long
         * 生成所有赛程
         */
        List<Battle> battles =  battleSql.getAllBattles();
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);        //修改为battles.length/3
        gridPane.setVgap(4);
        gridPane.setPadding(new Insets(20,20,20,20));
        String [] colors = new String[]{"panel-success","panel-info","panel-warning"};      //panel顶部颜色选择
        FlowPane flowPane =  new FlowPane();    //添加label，datePicker,button
        //日期选择器
        DatePicker datePicker = new DatePicker();
        StringConverter converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd");
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        datePicker.setConverter(converter);
        datePicker.setPromptText("yyyy-MM-dd".toLowerCase());
        Label label = new Label("Choose one day:  ");
        Button button = new Button("确定");
        flowPane.getChildren().addAll(label,datePicker,button);
        gridPane.add(flowPane,3,0);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GridPane gridPane2 =new GridPane();
                ScrollPane  scrollPane2 = new ScrollPane();
                gridPane2.setHgap(20);
                gridPane2.setVgap(3);
                gridPane2.setPadding(new Insets(20,20,20,20));
                String date = datePicker.getValue().toString();
                List<Battle> oneDayBattles = battleSql.getOneDayBattles(date);
                if(oneDayBattles.isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(date+" is no battle");
                    //alert.text
                    alert.show();
                }
                else {
                    for(int i=0;i<oneDayBattles.size();i++){
                        Timestamp battleTime = oneDayBattles.get(i).getBattleTime();  //对战时间
                        String teamA = oneDayBattles.get(i).getTeamA();
                        String teamB = oneDayBattles.get(i).getTeamB();
                        String battleSide = oneDayBattles.get(i).getBattleSide();      //比赛场地
                        int battleResult = oneDayBattles.get(i).getBattleResult();    //比赛结果，1表示A胜，0表示平局，-1表示A负
                        String battleScore = oneDayBattles.get(i).getBattleScore();
                        org.kordamp.bootstrapfx.scene.layout.Panel panel = new org.kordamp.bootstrapfx.scene.layout.Panel();
                        panel.getStyleClass().addAll("panel-primary",colors[i%3],"panel-default");
                        panel.setPadding(new Insets(20,20,20,20));
                        Label headLable = new Label(battleTime.toString().split("\\.")[0]+"\t"+battleSide);
                        Label bodyLable  = new Label(teamA+"\t\t"+battleScore+"\t\t"+teamB);
                        Label footLable = new Label();
                        switch (battleResult){
                            case -2:footLable.setText("\t\t未开始");break;
                            case -1:footLable.setText("\t\t"+teamB+"获胜");break;
                            case 0:footLable.setText("\t\t平局");break;
                            case 1:footLable.setText("\t\t"+teamA+"获胜");break;
                        }
                        panel.setHeading(headLable);
                        panel.setBody(bodyLable);
                        panel.setFooter(footLable);
                        gridPane2.add(panel,i%3,i/3);
                    }
                    scrollPane2.setContent(gridPane2);
                    Stage secondStage = new Stage();
                    Scene secondScene = new Scene(scrollPane2, 900, 600);
                    secondScene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
                    secondStage.setScene(secondScene);
                    secondStage.show();
                }

            }
        });

        for(int i=0;i<battles.size();i++){
            Timestamp battleTime = battles.get(i).getBattleTime();  //对战时间
            String teamA = battles.get(i).getTeamA();
            String teamB = battles.get(i).getTeamB();
            String battleSide = battles.get(i).getBattleSide();      //比赛场地
            int battleResult = battles.get(i).getBattleResult();    //比赛结果，1表示A胜，0表示平局，-1表示A负
            String battleScore = battles.get(i).getBattleScore();
            org.kordamp.bootstrapfx.scene.layout.Panel panel = new org.kordamp.bootstrapfx.scene.layout.Panel();
            panel.getStyleClass().addAll("panel-primary",colors[i%3],"panel-default");
            panel.setPadding(new Insets(20,20,20,20));
            Label headLable = new Label(battleTime.toString().split("\\.")[0]+"\t"+battleSide);
            Label bodyLable  = new Label(teamA+"\t\t"+battleScore+"\t\t"+teamB);
            Label footLable = new Label();
            switch (battleResult){
                case -2:footLable.setText("\t\t未开始");break;
                case -1:footLable.setText("\t\t"+teamB+"获胜");break;
                case 0:footLable.setText("\t\t平局");break;
                case 1:footLable.setText("\t\t"+teamA+"获胜");break;
            }
            panel.setHeading(headLable);
            panel.setBody(bodyLable);
            panel.setFooter(footLable);
            gridPane.add(panel,i%3,i/3);
        }
        this.scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);    //设置水平滚动条用不出现
        this.scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    //设置垂直滚动条需要时出现
        this.scrollPane.setContent(gridPane);
    }
}
