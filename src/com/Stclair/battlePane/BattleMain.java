package com.Stclair.battlePane;

import com.Stclair.myCharacter;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BattleMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("battlePane.fxml"));
        primaryStage.setTitle("My First Battle");
        primaryStage.setScene(new Scene(root, 900,700));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
