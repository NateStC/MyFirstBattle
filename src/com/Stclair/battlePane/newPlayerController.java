package com.Stclair.battlePane;

import com.Stclair.*;
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
    private Button createPlayerButton, rollStatsButton;
    @FXML
    private Label instructionLabel;

    private int newStrength, newConstitution, newDexterity, newIntelligence, newWisdom, newCharisma;

    @FXML
    private ListView<Integer> statRollList;

    @FXML
    private ListView<Weapon> weaponListView;

    @FXML
    private Label newStrLabel, newConLabel, newDexLabel,newIntelLabel, newWisLabel, newCharismaLabel;
    @FXML
    private Label newStrBonusLabel, newConBonusLabel, newDexBonusLabel, newIntBonusLabel, newWisBonusLabel, newChaBonusLabel;

    private Integer selectedRoll;

    public void initialize() {
        newStrength = 0;
        newConstitution = 0;
        newDexterity = 0;
        newIntelligence = 0;
        newWisdom = 0;
        newCharisma = 0;
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

        ObservableList<Weapon> weapons = FXCollections.observableArrayList();
        weapons.addAll(Weapons.swordShort(), Weapons.longSword(), Weapons.daggers(), Weapons.staff(), Weapons.mace());
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
                newStrBonusLabel.setText("(+" + newWeapon.getStrBonus() + ")");
            } else {
                newStrBonusLabel.setText("(" + newWeapon.getStrBonus() + ")");
            }
            if (newWeapon.getConBonus() >= 0) {
                newConBonusLabel.setText("(+" + newWeapon.getConBonus() + ")");
            } else {
                newConBonusLabel.setText("(" + newWeapon.getConBonus() + ")");
            }
            if (newWeapon.getDexBonus() >= 0) {
                newDexBonusLabel.setText("(+" + newWeapon.getDexBonus() + ")");
            } else {
                newDexBonusLabel.setText("(" + newWeapon.getDexBonus() + ")");
            }
            if (newWeapon.getIntBonus() >= 0) {
                newIntBonusLabel.setText("(+" + newWeapon.getIntBonus() + ")");
            } else {
                newIntBonusLabel.setText("(" + newWeapon.getIntBonus() + ")");
            }
            if (newWeapon.getWisBonus() >= 0) {
                newWisBonusLabel.setText("(+" + newWeapon.getWisBonus() + ")");
            } else {
                newWisBonusLabel.setText("(" + newWeapon.getWisBonus() + ")");
            }
            if (newWeapon.getCharBonus() >= 0) {
                newChaBonusLabel.setText("(+" + newWeapon.getCharBonus() + ")");
            } else {
                newChaBonusLabel.setText("(" + newWeapon.getCharBonus() + ")");
            }
        });
        weaponListView.getSelectionModel().selectFirst();

        createPlayerButton.setOnAction(e -> {

        });
    }

    public void createNewPlayer() {
        if (!playerNameField.getText().isEmpty() || newStrength == 0 || newDexterity == 0 || newConstitution == 0 ||
                newIntelligence == 0 || newWisdom == 0 || newCharisma == 0) {

            //todo add way to choose armor for player
            battlePaneController.player = new Player(playerNameField.getText().trim(), newStrength, newDexterity,
                    newConstitution, newIntelligence, newWisdom, newCharisma, weaponListView.getSelectionModel().getSelectedItem(), Armors.scrapLeathers());
            System.out.println("new player created");

            //todo how to pass new character to battlePaneController from create character button and not close button
        }
    }

    @FXML
    private void rollDice() {
        ObservableList<Integer> statRolls = FXCollections.observableArrayList();
        newStrLabel.setText(null);
        newDexLabel.setText(null);
        newConLabel.setText(null);
        newIntelLabel.setText(null);
        newWisLabel.setText(null);
        newCharismaLabel.setText(null);

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
        if (this.newStrength == 0 && selectedRoll != null) {
            Integer str = statRollList.getSelectionModel().getSelectedItem();
            newStrLabel.setText(str.toString());
            this.newStrength = str;
            statRollList.getItems().remove(selectedRoll);
            statRollList.getSelectionModel().selectFirst();
        } else if (this.newStrength > 0) {
            statRollList.getItems().add(newStrength);
            this.newStrength = 0;
            newStrLabel.setText("");
            selectedRoll = null;
            createPlayerButton.setDisable(true);
        }
    }

    @FXML
    private void handleConstitutionButton() {
        if (this.newConstitution == 0 && selectedRoll != null) {
            Integer con = statRollList.getSelectionModel().getSelectedItem();
            newConLabel.setText(con.toString());
            this.newConstitution = con;
            statRollList.getItems().remove(selectedRoll);
            statRollList.getSelectionModel().selectFirst();
        } else if (this.newConstitution > 0) {
            statRollList.getItems().add(this.newConstitution);
            this.newConstitution = 0;
            newConLabel.setText("");
            selectedRoll = null;
            createPlayerButton.setDisable(true);
        }

    }

    @FXML
    private void handleDexButton() {
        if (this.newDexterity == 0 && selectedRoll != null) {
            Integer dex = statRollList.getSelectionModel().getSelectedItem();
            newDexLabel.setText(dex.toString());
            this.newDexterity = dex;
            statRollList.getItems().remove(selectedRoll);
            statRollList.getSelectionModel().selectFirst();
        } else if (this.newDexterity > 0) {
            statRollList.getItems().add(this.newDexterity);
            this.newDexterity = 0;
            newDexLabel.setText("");
            selectedRoll = null;
            createPlayerButton.setDisable(true);
        }
    }

    @FXML
    private void handleIntelligenceButton() {
        if (this.newIntelligence == 0 && selectedRoll != null) {
            Integer intel = statRollList.getSelectionModel().getSelectedItem();
            newIntelLabel.setText(intel.toString());
            this.newIntelligence = intel;
            statRollList.getItems().remove(selectedRoll);
            statRollList.getSelectionModel().selectFirst();
        } else if (this.newIntelligence > 0) {
            statRollList.getItems().add(this.newIntelligence);
            this.newIntelligence = 0;
            newIntelLabel.setText("");
            selectedRoll = null;
            createPlayerButton.setDisable(true);
        }
    }

    @FXML
    private void handleWisdomButton() {
        if (this.newWisdom == 0 && selectedRoll != null) {
            Integer wis = selectedRoll;
            newWisLabel.setText(wis.toString());
            this.newWisdom = wis;
            statRollList.getItems().remove(selectedRoll);
            statRollList.getSelectionModel().selectFirst();
        } else if (this.newWisdom > 0) {
            statRollList.getItems().add(this.newWisdom);
            this.newWisdom = 0;
            newWisLabel.setText("");
            selectedRoll = null;
            createPlayerButton.setDisable(true);
        }
    }

    @FXML
    private void handleCharismaButton() {
        if (this.newCharisma == 0 && selectedRoll != null) {
            Integer charisma = selectedRoll;
            newCharismaLabel.setText(charisma.toString());
            this.newCharisma = charisma;
            statRollList.getItems().remove(selectedRoll);
            statRollList.getSelectionModel().selectFirst();
        } else if (this.newCharisma > 0) {
            statRollList.getItems().add(this.newCharisma);
            this.newCharisma = 0;
            newCharismaLabel.setText("");
            selectedRoll = null;
            createPlayerButton.setDisable(true);
        }
    }

    public void processResults() {

    }
}

