package com.Stclair;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class battlePaneController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Label mainLabel;

    @FXML
    private Label playerName;

    @FXML
    private Button displayStats;

    @FXML
    private Button attack;

    @FXML
    private Button openInventory;

    @FXML
    private ListView<Integer> stats;

    public void initialize(){

    }

    public void showNewPlayerDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("newPlayerDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e){
            System.out.println("Unable to load dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            System.out.println("OK pressed"); //todo temprint
        } else if (result.isPresent() && result.get() == ButtonType.CANCEL){
            System.out.println("Cancel pressed"); //todo temprint
        }

    }

//    @FXML
//    private List<Array> getStatsList(){
//        return com.Stclair.Main.getPlayerStats();
//    }

}
