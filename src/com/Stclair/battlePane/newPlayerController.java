package com.Stclair.battlePane;

import com.Stclair.Item;
import com.Stclair.Weapon;
import com.Stclair.myCharacter;
import com.Stclair.Dice;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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

    @FXML
    private ListView<Integer> statRollList;

    @FXML
    private ListView<Weapon> weaponListView;

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
    @FXML
    private Label strBonusLabel, conBonusLabel, dexBonusLabel, intBonusLabel, wisBonusLabel, chaBonusLabel;

    private
    ObservableList<Weapon> weapons;

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
        statRollList.setDisable(true);

        statRollList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //listener for rolled stats
        statRollList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                if (newValue != null) {
                    selectedRoll = statRollList.getSelectionModel().getSelectedItem();
                }
                if (statRollList.getItems().isEmpty()) {
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
                if (newValue.trim().equals("")) {
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

        weapons = FXCollections.observableArrayList();
        weapons.addAll(Weapon.swordShort(), Weapon.longSword(), Weapon.daggers(), Weapon.staff(), Weapon.mace());
        weaponListView.setItems(weapons);
        weaponListView.setCellFactory(lv -> new ListCell<>(){
            @Override
            public void updateItem(Weapon weapon, boolean empty){
                super.updateItem(weapon,empty);
                if (empty){
                    setText(null);
                } else {
                    setText(weapon.getName());
                }
            }
        });
        weaponListView.getSelectionModel().selectedItemProperty().addListener((weapon, oldWeapon, newWeapon) -> {
            if (newWeapon.getStrBonus() >= 0) {
                strBonusLabel.setText("(+" + newWeapon.getStrBonus() + ")");
            } else {
                strBonusLabel.setText("(" + newWeapon.getStrBonus() + ")");
            }
            if (newWeapon.getConBonus() >= 0) {
                conBonusLabel.setText("(+" + newWeapon.getConBonus() + ")");
            } else {
                conBonusLabel.setText("(" + newWeapon.getConBonus() + ")");
            }
            if (newWeapon.getDexBonus() >= 0) {
                dexBonusLabel.setText("(+" + newWeapon.getDexBonus() + ")");
            } else {
                dexBonusLabel.setText("(" + newWeapon.getDexBonus() + ")");
            }
            if (newWeapon.getIntBonus() >= 0) {
                intBonusLabel.setText("(+" + newWeapon.getIntBonus() + ")");
            } else {
                intBonusLabel.setText("(" + newWeapon.getIntBonus() + ")");
            }
            if (newWeapon.getWisBonus() >= 0) {
                wisBonusLabel.setText("(+" + newWeapon.getWisBonus() + ")");
            } else {
                wisBonusLabel.setText("(" + newWeapon.getWisBonus() + ")");
            }
            if (newWeapon.getCharBonus() >= 0) {
                chaBonusLabel.setText("(+" + newWeapon.getCharBonus() + ")");
            } else {
                chaBonusLabel.setText("(" + newWeapon.getCharBonus() + ")");
            }
        });
        weaponListView.getSelectionModel().selectFirst();
    }

    public void createNewPlayer() {
        if (!playerNameField.getText().isEmpty() || strength == 0 || dexterity == 0 || constitution == 0 ||
                intelligence == 0 || wisdom == 0 || charisma == 0) {

            battlePaneController.player = new myCharacter(playerNameField.getText().trim(), strength, dexterity,
                    constitution, intelligence, wisdom, charisma, weaponListView.getSelectionModel().getSelectedItem());
            System.out.println("new player created");

            //todo how to pass new character to battlePaneController from create character button and not close button
        }
    }

    @FXML
    private void rollDice() {
        ObservableList<Integer> statRolls = FXCollections.observableArrayList();
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
        statRollList.getSelectionModel().select(0);

        statRollList.setDisable(false);
    }

    @FXML
    private void handleClickListRollView() {
        selectedRoll = statRollList.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void handleStrengthButton() {
        if (this.strength == 0 && selectedRoll != null) {
            Integer str = statRollList.getSelectionModel().getSelectedItem();
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

    public void processResults() {

    }
}

