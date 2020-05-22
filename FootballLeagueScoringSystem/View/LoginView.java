package FootballLeagueScoringSystem.View;

import FootballLeagueScoringSystem.Control.ViewTrans;
import FootballLeagueScoringSystem.Module.League;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginView extends Pane {
    private League theLeague;

    public LoginView(Stage stage) {
        theLeague = new League();
        this.setMinSize(1200, 600);
        this.setMaxSize(1920, 1080);
        generate(stage,theLeague);
    }

    private void generate(Stage stage, League theLeague) {
        Label welcome = new Label("欢迎使用足球联赛评分系统！");
        welcome.setLayoutX(300);
        welcome.setLayoutY(100);
        welcome.setMinSize(600,80);
        welcome.setMaxSize(600,80);
        welcome.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(Color.LAVENDERBLUSH, (CornerRadii)null, (Insets)null)}));
        welcome.setFont(new Font("Microsoft YaHei", 42.0D));
        Label accountL = new Label("输入账号：");
        accountL.setLayoutX(450);
        accountL.setLayoutY(180);
        accountL.setMinSize(100,50);
        TextField accountInput = new TextField();
        accountInput.setPromptText("输入账号");
        accountInput.setLayoutX(accountL.getLayoutX()+accountL.getMinWidth());
        accountInput.setLayoutY(accountL.getLayoutY());
        accountInput.setPrefSize(200,50);
        Label passwordL = new Label("输入密码：");
        passwordL.setLayoutX(accountL.getLayoutX());
        passwordL.setLayoutY(accountL.getLayoutY()+accountL.getMinHeight());
        passwordL.setMinSize(accountL.getMinWidth(),accountL.getMinHeight());
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("输入密码");
        passwordInput.setLayoutX(passwordL.getLayoutX()+passwordL.getMinWidth());
        passwordInput.setLayoutY(passwordL.getLayoutY());
        passwordInput.setPrefSize(accountInput.getPrefWidth(),accountInput.getPrefHeight());
        Button confirm = new Button("确认登录");
        confirm.setLayoutX(passwordL.getLayoutX());
        confirm.setLayoutY(passwordL.getLayoutY()+passwordL.getMinHeight());
        confirm.setMinSize(80,50);
        confirm.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("正在加载信息，请耐心等待...");
                alert.showAndWait();
                confirm.setText("信息加载中");
                String account = accountInput.getText();
                String password = passwordInput.getText();
                if(theLeague.checkUser(account,password)!=null){
                    ViewTrans vt = new ViewTrans();
                    theLeague.setUserStatus(theLeague.checkUser(account,password));
                    try {
                        vt.toMainView(theLeague,stage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Button visitor = new Button("访客登录");
        visitor.setLayoutX(confirm.getLayoutX()+confirm.getMinWidth());
        visitor.setLayoutY(confirm.getLayoutY());
        visitor.setMinSize(80,50);
        visitor.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("正在加载信息，请耐心等待...");
                alert.showAndWait();
                visitor.setText("信息加载中...");
                ViewTrans vt = new ViewTrans();
                theLeague.setUserStatus("游客登录");
                try {
                    vt.toMainView(theLeague, stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        this.getChildren().addAll(welcome,accountL,accountInput,passwordL,passwordInput,confirm,visitor);
    }
}
