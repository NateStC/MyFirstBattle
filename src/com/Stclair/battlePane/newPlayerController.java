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
    private Button rollStatsButton;

    @FXML
    private Button createPlayerButton;

//    @FXML
//    private ButtonType createPlayerButton;

    @FXML
    private Label instructionLabel;

    private int str, con, dex, intl, wis, cha;

    @FXML
    private ListView<Integer> statRollList;

    @FXML
    private ListView<Weapon> weaponListView;

    @FXML
    private Label newStrLabel, newConLabel, newDexLabel, newIntelLabel, newWisLabel, newCharismaLabel;
    @FXML
    private Label newStrBonusLabel, newConBonusLabel, newDexBonusLabel, newIntBonusLabel, newWisBonusLabel, newChaBonusLabel;

    private Integer selectedRoll;

    public void initialize() {
        str = 0;
        con = 0;
        dex = 0;
        intl = 0;
        wis = 0;
        cha = 0;
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
        weaponListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            public void updateItem(Weapon weapon, boolean empty) {
                super.updateItem(weapon, empty);
                if (empty) {
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

//        createPlayerButton.setOnAction(e -> {
//
//        });
    }

    @FXML
    public void createNewPlayer() {
        if (playerNameField.getText().isEmpty()) {
            System.out.println("Enter player name");
            return;
        }
        if (str == 0 || dex == 0 || con == 0 || intl == 0 || wis == 0 || cha == 0) {
            System.out.println("Assign all stats");
            return;
        }
        if (weaponListView.getSelectionModel().getSelectedItem() == null) {
            System.out.println("Choose a weapon");
            return;
        }

        //todo add way to choose armor for player

        battlePaneController.player = new Player(playerNameField.getText().trim(), str, dex,
                con, intl, wis, cha, weaponListView.getSelectionModel().getSelectedItem(), Armors.scrapLeathers());
        System.out.println("new player created");

        //todo how to pass new character to battlePaneController from create character button and not close button
//        }
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
        if (this.str == 0 && selectedRoll != null) {
            Integer str = statRollList.getSelectionModel().getSelectedItem();
            newStrLabel.setText(str.toString());
            this.str = str;
            statRollList.getItems().remove(selectedRoll);
            statRollList.getSelectionModel().selectFirst();
        } else if (this.str > 0) {
            statRollList.getItems().add(str);
            this.str = 0;
            newStrLabel.setText("");
            createPlayerButton.setDisable(true);
            statRollList.getSelectionModel().selectLast();
        }
    }

    @FXML
    private void handleConstitutionButton() {
        if (this.con == 0 && selectedRoll != null) {
            Integer con = statRollList.getSelectionModel().getSelectedItem();
            newConLabel.setText(con.toString());
            this.con = con;
            statRollList.getItems().remove(selectedRoll);
            statRollList.getSelectionModel().selectFirst();
        } else if (this.con > 0) {
            statRollList.getItems().add(this.con);
            this.con = 0;
            newConLabel.setText("");
            createPlayerButton.setDisable(true);
            statRollList.getSelectionModel().selectLast();
        }
    }

    @FXML
    private void handleDexButton() {
        if (this.dex == 0 && selectedRoll != null) {
            Integer dex = statRollList.getSelectionModel().getSelectedItem();
            newDexLabel.setText(dex.toString());
            this.dex = dex;
            statRollList.getItems().remove(selectedRoll);
            statRollList.getSelectionModel().selectFirst();
        } else if (this.dex > 0) {
            statRollList.getItems().add(this.dex);
            this.dex = 0;
            newDexLabel.setText("");
            createPlayerButton.setDisable(true);
            statRollList.getSelectionModel().selectLast();
        }
    }

    @FXML
    private void handleIntelligenceButton() {
        if (this.intl == 0 && selectedRoll != null) {
            Integer intel = statRollList.getSelectionModel().getSelectedItem();
            newIntelLabel.setText(intel.toString());
            this.intl = intel;
            statRollList.getItems().remove(selectedRoll);
            statRollList.getSelectionModel().selectFirst();
        } else if (this.intl > 0) {
            statRollList.getItems().add(this.intl);
            this.intl = 0;
            newIntelLabel.setText("");
            createPlayerButton.setDisable(true);
            statRollList.getSelectionModel().selectLast();
        }
    }

    @FXML
    private void handleWisdomButton() {
        if (this.wis == 0 && selectedRoll != null) {
            Integer wis = selectedRoll;
            newWisLabel.setText(wis.toString());
            this.wis = wis;
            statRollList.getItems().remove(selectedRoll);
            statRollList.getSelectionModel().selectFirst();
        } else if (this.wis > 0) {
            statRollList.getItems().add(this.wis);
            this.wis = 0;
            newWisLabel.setText("");
            createPlayerButton.setDisable(true);
            statRollList.getSelectionModel().selectLast();
        }
    }

    @FXML
    private void handleCharismaButton() {
        if (this.cha == 0 && selectedRoll != null) {
            Integer charisma = selectedRoll;
            newCharismaLabel.setText(charisma.toString());
            this.cha = charisma;
            statRollList.getItems().remove(selectedRoll);
            statRollList.getSelectionModel().selectFirst();
        } else if (this.cha > 0) {
            statRollList.getItems().add(this.cha);
            this.cha = 0;
            newCharismaLabel.setText("");
            createPlayerButton.setDisable(true);
            statRollList.getSelectionModel().selectLast();
        }
    }

    public void processResults() {

    }
}

