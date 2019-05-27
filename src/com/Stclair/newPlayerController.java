package com.Stclair;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class newPlayerController {

    @FXML
    private TextField playerNameField;
    @FXML
    private Button createPlayerButton;
    @FXML
    private Label instructionLabel;
    @FXML
    private Button rollStatsButton;

    private int strength;
    private int constitution;
    private int dexterity;
    private int intelligence;
    private int wisdom;
    private int charisma;

    //public ObservableList<Integer> statRolls;
    private ObservableList<Integer> statRolls;
    @FXML
    private ListView<Integer> statRollList;

    @FXML
    private Label strLabel;
    @FXML
    private Label constLabel;
    @FXML
    private Label dexLabel;
    @FXML
    private Label intelLabel;
    @FXML
    private Label wisLabel;
    @FXML
    private Label charismaLabel;

    private Integer selectedRoll;

    public void initialize() {
        strength = 0;
        constitution = 0;
        dexterity = 0;
        intelligence = 0;
        wisdom = 0;
        charisma = 0;
        createPlayerButton.setDisable(true);
        rollStatsButton.setDisable(true);



        statRollList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //listener for rolled stats
        statRollList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                if (newValue != null) {
                    selectedRoll = statRollList.getSelectionModel().getSelectedItem();
                }
                if (statRollList.getItems().isEmpty()){
                    createPlayerButton.setDisable(false);
//                    if (!playerNameField.getText().isEmpty()){
//                        createPlayerButton.setDisable(true);
//                    }
                }
            }
        });
        //listener for if name text field is empty
        playerNameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.trim().equals("")){
                    rollStatsButton.setDisable(true);
                    instructionLabel.setText("Enter your name before rolling stats");
                    createPlayerButton.setDisable(true);
                } else {
                    rollStatsButton.setDisable(false);
                    instructionLabel.setText("Click to button to add selected roll to that stat");
//                    if (!statRollList.getItems().isEmpty()){
//                        createPlayerButton.setDisable(false);
//                        instructionLabel.setText("Create your player!");
//                    }
                }
            }
        });
    }

    public void createNewPlayer() {
        if (!playerNameField.getText().isEmpty() || strength == 0 || dexterity == 0 || constitution == 0 || intelligence == 0 || wisdom == 0 || charisma == 0) {
            Main.player = new Character(playerNameField.getText().trim(), strength, dexterity, constitution, intelligence, wisdom, charisma, 1);
            System.out.println("new player created");
        }
    }

    @FXML
    private void rollDice() {
        statRolls = FXCollections.observableArrayList();
        strLabel.setText(null);
        dexLabel.setText(null);
        constLabel.setText(null);
        intelLabel.setText(null);
        wisLabel.setText(null);
        charismaLabel.setText(null);

        for (int i = 0; i <= 5; i++) {
            statRolls.add(Dice.statRoll());
        }
        statRollList.setItems(statRolls);
    }

    @FXML
    private void handleClickListRollView() {
        Integer stat = (Integer) statRollList.getSelectionModel().getSelectedItem();
        System.out.println(stat + " selected"); //todo temprint
        selectedRoll = stat;
    }

    @FXML
    private void handleStrengthButton() {
        if (this.strength == 0 && selectedRoll != null) {
            Integer str = (Integer) statRollList.getSelectionModel().getSelectedItem();
            strLabel.setText(str.toString());
            this.strength = str;
            statRollList.getItems().remove(selectedRoll);
            statRollList.getSelectionModel().selectFirst();
        } else if (this.strength > 0) {
            statRollList.getItems().add(strength);
            this.strength = 0;
            strLabel.setText("");
            selectedRoll = null;
            createPlayerButton.setDisable(true);
        }
    }

    @FXML
    private void handleConstitutionButton() {
        if (this.constitution == 0 && selectedRoll != null) {
            Integer con = statRollList.getSelectionModel().getSelectedItem();
            constLabel.setText(con.toString());
            this.constitution = con;
            statRollList.getItems().remove(selectedRoll);
            statRollList.getSelectionModel().selectFirst();
        } else if (this.constitution > 0) {
            statRollList.getItems().add(this.constitution);
            this.constitution = 0;
            constLabel.setText("");
            selectedRoll = null;
            createPlayerButton.setDisable(true);
        }

    }

    @FXML
    private void handleDexButton() {
        if (this.dexterity == 0 && selectedRoll != null) {
            Integer dex = statRollList.getSelectionModel().getSelectedItem();
            dexLabel.setText(dex.toString());
            this.dexterity = dex;
            statRollList.getItems().remove(selectedRoll);
            statRollList.getSelectionModel().selectFirst();
        } else if (this.dexterity > 0) {
            statRollList.getItems().add(this.dexterity);
            this.dexterity = 0;
            dexLabel.setText("");
            selectedRoll = null;
            createPlayerButton.setDisable(true);
        }
    }

    @FXML
    private void handleIntelligenceButton() {
        if (this.intelligence == 0 && selectedRoll != null) {
            Integer intel = statRollList.getSelectionModel().getSelectedItem();
            intelLabel.setText(intel.toString());
            this.intelligence = intel;
            statRollList.getItems().remove(selectedRoll);
            statRollList.getSelectionModel().selectFirst();
        } else if (this.intelligence > 0) {
            statRollList.getItems().add(this.intelligence);
            this.intelligence = 0;
            intelLabel.setText("");
            selectedRoll = null;
            createPlayerButton.setDisable(true);
        }
    }

    @FXML
    private void handleWisdomButton() {
        if (this.wisdom == 0 && selectedRoll != null) {
            Integer wis = selectedRoll;
            wisLabel.setText(wis.toString());
            this.wisdom = wis;
            statRollList.getItems().remove(selectedRoll);
            statRollList.getSelectionModel().selectFirst();
        } else if (this.wisdom > 0) {
            statRollList.getItems().add(this.wisdom);
            this.wisdom = 0;
            wisLabel.setText("");
            selectedRoll = null;
            createPlayerButton.setDisable(true);
        }
    }

    @FXML
    private void handleCharismaButton() {
        if (this.charisma == 0 && selectedRoll != null) {
            Integer charisma = selectedRoll;
            charismaLabel.setText(charisma.toString());
            this.charisma = charisma;
            statRollList.getItems().remove(selectedRoll);
            statRollList.getSelectionModel().selectFirst();
        } else if (this.charisma > 0) {
            statRollList.getItems().add(this.charisma);
            this.charisma = 0;
            charismaLabel.setText("");
            selectedRoll = null;
            createPlayerButton.setDisable(true);
        }
    }
}

