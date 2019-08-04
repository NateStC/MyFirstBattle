package com.Stclair.battlePane;

import com.Stclair.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class battlePaneController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private GridPane actionsGrid;

    @FXML
    private Label playerNameLabel, playerLvlLabel, strLabel, conLabel, dexLabel, intLabel, wisLabel, chaLabel,
            enemyNameLabel, enemyLvlLabel, enemyStrLabel, enemyConLabel, enemyDexLabel, enemyIntLabel, enemyWisLabel,
            enemyChaLabel, playerHpLabel, playerMpLabel, playerXpLabel, playerMaxHpLabel, playerMaxMpLabel,
            enemyHpLabel, enemyMpLabel, enemyMaxMpLabel, enemyMaxHpLabel, strBonusLabel, dexBonusLabel, conBonusLabel,
            intBonusLabel, wisBonusLabel, chaBonusLabel;

    @FXML
    private ProgressBar expBar, playerHpBar, manaBar, enemyHpBar, enemyManaBar;

//    @FXML
//    private Button stabButton, healButton, spellButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button strPlus, dexPlus, conPlus, intPlus, wisPlus, chaPlus;

    static myCharacter player;

    Enemy enemy;

    private List<Enemy> enemies;

    private int enemiesDefeated, roundsCompleted, deaths, totalKills, skillPoints;

    private ObservableList<String> actionList;

    public void initialize() {
        actionList = FXCollections.observableArrayList();
        skillPoints = 0;
        showLvlUpButtons(false);
    }

    private void showActionList() {
        ListView<String> actionLV = new ListView<>();
        mainBorderPane.setCenter(actionLV);
        actionLV.setItems(actionList);

        //set actions list view to wrap text  from Ryotsu on StackOverflow
        actionLV.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setMinWidth(param.getWidth());
                    setMaxWidth(param.getWidth());
                    setPrefWidth(param.getWidth());
                    setWrapText(true);
                    setText(item);
                }
            }
        });
    }


//    @FXML
//    private void startNewGame() {
//        FXMLLoader Loader = new FXMLLoader();
//        try {
//            Loader.setLocation(FXMLLoader.load(getClass().getResource("newPlayerDialog.fxml")));
//        } catch (IOException e){
//            System.out.println("Could not set the loader");
//            e.printStackTrace();
//        }
//        Parent p = Loader.getRoot();
//        mainBorderPane.setCenter(p);

//        try {
//            mainBorderPane.setCenter(FXMLLoader.load(getClass().getResource("newPlayerDialog.fxml")));
//        } catch (IOException e) {
//            System.out.println("Unable to load roof dialog");
//            e.printStackTrace();
//        }
//    }

    //     dialog popup window to create new player
    @FXML
    private void rollNewCharacter() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("newPlayerDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e) {
            System.out.println("Unable to load dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.CLOSE && player != null) {
            startGame();
            System.out.println("Game Starting");
        }
    }

    @FXML
    private void newSpellSword() {
        player = new myCharacter("Trofasthet the Spell-Sword", 11, 9, 13, 12,
                10, 8, Weapons.swordShort(), Armors.tinChainmail());
        startGame();

    }

    @FXML
    private void newRogue() {
        player = new myCharacter("John the Silent-but-Deadly", 7, 14, 10, 8,
                9, 11, Weapons.daggers(), Armors.scrapLeathers());
        startGame();
    }

    @FXML
    private void newWarrior() {
        player = new myCharacter("Volstagg the Burly", 14, 8, 13, 8, 9,
                7, Weapons.mace(), Armors.tinPlatemail());
        startGame();
    }

    @FXML
    private void newWizard() {
        player = new myCharacter("Dinklebot the Astute", 6, 8, 9, 15, 13,
                11, Weapons.staff(), Armors.initiateRobes());
        startGame();
    }

    @FXML
    private void newRanger() {
        player = new myCharacter("Colton the Ranger", 8, 14, 10, 10, 10,
                8, Weapons.bow(), Armors.scrapLeathers());
    }

    @FXML
    private void loadGame() {
        //todo load character and game
    }

    private void makeNextEnemyButton() {
        nextButton.setText("Next Enemy");
        nextButton.setDisable(enemy.getHealth() > 0);
        nextButton.setOnAction(event -> nextEnemy());
    }

    private void nextEnemy() {
        if (enemy.getHealth() < 1) {
            enemiesDefeated += 1;
            totalKills += 1;
            System.out.println("enemies defeated: " + enemiesDefeated);
            actionList.clear();
            if (enemiesDefeated < enemies.size()) {
                if (!player.manaIsFull()) {
                    int mp = (int) ((player.getIntelligence() + player.getWisdom()) / 1.5);
                    playerRestoreMana(mp);
                    actionList.add("You rest between enemies and restore " + mp + " mana.");
                }
                if (!player.healthIsFull()) {
                    int hp = player.getConStat() + player.getLevel();
                    playerHeal(hp);
                    actionList.add("In the respite between battles you restore " + hp + " health.");
                }
                setEnemy(enemies.get(enemiesDefeated));
//                nextButton.setDisable(true);
            } else {
                actionList.add("You have defeated all of the enemies!");
                roundsCompleted += 1;
                nextButton.setText("New Round");
                nextButton.setOnAction(event -> newRound());
//                nextButton.setDisable(false);
//                spellButton.setDisable(true);
//                healButton.setDisable(true);
//                stabButton.setDisable(true);
            }
        } else {
            actionList.add(enemy.getName() + " stands in your way, refusing to let you continue.");
        }
    }

    public void startGame() {
        setPlayer();
        enemiesDefeated = 0;
        restorePlayer();
        showActionList();
        setUpEnemies(Enemies.goblinGang(1));
        actionList.add("Your journey begins!");
        setEnemy(enemies.get(0));
    }

    public void newRound() {
        enemiesDefeated = 0;
        restorePlayer();

        //todo find a way to choose different gauntlets
        setUpEnemies(Enemies.goblinGang(player.getLevel()));
        makeNextEnemyButton();
        setEnemy(enemies.get(0));
    }

    private void setActions() {
        ArrayList<Button> attacks = new ArrayList<>();

        //work your way through the weapon's attacks, creating a button for each
        for (Attack a : player.getWeapon().getAttackList()) {
            Button button = new Button(a.getName());
            button.setPrefWidth(100);
            button.setOnAction(e -> {
                List<Damage> result = a.doAttack(player);
                int atks = 0;
                //build a button for each attack
                for (Damage d : result) {
                    if (enemy.isDead() && d.getPhysDamage() > 0) {
                        actionList.add("Enemy is already dead!");
                        break;
                    }
                    if (d.getPhysDamage() == 0 && d.getHeal()>0 && player.healthIsFull()){
                        actionList.add(player.getName() + " is already at full health");
                        break;
                    }
                    if (atks == 0) {
                        actionList.add(player.getName() + " uses " + d.getAttackName());
                    }
                    playerDrainMana(d.getManaCost());
                    atks++;

                    if (!d.isHit()) {
                        if (result.size() == 1) {
                            ALPause(500, d.getAttackName() + " missed.");
                            enemyTurn();
                            break;
                        } else {
                            ALPause(500, firstOrSecond(atks) + " attack missed!");
                        }
                    }
                    if (d.isCrit()) {
                        ALPause(500, "Critical hit!");
                    }
                    if (d.getPhysDamage() > 0) {
                        if (result.size() == 1){
                            ALPause(50,enemy.getName() + " takes " + d.getPhysDamage() + " damage");
                        } else {
                            ALPause(50, firstOrSecond(atks) + " strike deals " + d.getPhysDamage());
                        }
                        enemyTakeDamage(d.getPhysDamage());
                    }
                    if (d.getHeal() > 0 && !player.healthIsFull()) {
                        playerHeal(d.getHeal());
                    }
                    if (enemy.isDead()){
                        break;
                    }
                    if (atks == result.size()){
                        enemyTurn();
                    }
                }
            });
            attacks.add(button);
        }

        nextButton = new Button("Next Enemy");
        nextButton.setPrefWidth(100);
        nextButton.setOnAction(e -> nextEnemy());
        attacks.add(nextButton);

        int row = 0;
        int column = 0;

        //populate gridPane with buttons for attacks player is able to cast with that weapon
        for (Button b : attacks) {
            actionsGrid.add(b, column, row);
            column += 1;
            if (column > 1) {
                column = 0;
                row++;
            }
        }

        for (int col = 0; col < actionsGrid.getColumnCount(); col++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setFillWidth(true);
        }

    }

    private String firstOrSecond(int num) {
        switch (num) {
            case 1:
                return "First";
            case 2:
                return "Second";
            case 3:
                return "Third";
            case 4:
                return "Fourth";
            case 5:
                return "Fifth";
            case 6:
                return "Sixth";
            default:
                return "";
        }
    }

    private void playerAttack(Damage damage) {
        if (player.isDead()) {
            actionList.add(player.getName() + " is dead!");
            return;
        }
        ALPause(500, player.getName() + " uses " + damage.getAttackName());
        playerDrainMana(damage.getManaCost());
        if (!damage.isHit()) {
            actionList.add(player.getName() + " missed!");
            enemyTurn();
            return;
        }
        if (damage.isCrit()) {
            ALPause(500, "Critical Hit!");
        }
        if (damage.getPhysDamage() > 0) {
            enemyTakeDamage(damage.getPhysDamage());
        }
        if (damage.getHeal() > 0) {
            playerHeal(damage.getHeal());
        }
    }


    //fixme I want to be able to pause between messages, but it pauses displaying them but continues to generate them, and dumps them all at the same time
//    private void actionPause(String message){
//        actionList.add(message);
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e){
//            e.printStackTrace();
//        }
//    }
//
//    private void actionPause(String message, int miliPause){
//        actionList.add(message);
//        try{
//            Thread.sleep(miliPause);
//        } catch (InterruptedException e){
//            e.printStackTrace();
//        }
//    }

    private void setPlayer() {
        //todo find a way to populate buttons for attacks the weapon has
        playerNameLabel.setText(player.getName());
//        playerHp = new SimpleIntegerProperty(player.getHealth());
//        playerMana = new SimpleIntegerProperty(player.getMana());
//        experience = new SimpleIntegerProperty(player.getExperience());

        setPlayerStats();
        setActions();

        //broken listeners in an attempt to bind labels to stats
//        experience.addListener((observable, oldValue, newValue) -> {
//            setExpBar();
//            playerXpLabel.setText(newValue.toString());
//        });
//        playerHp.addListener((observable, oldValue, newValue) -> {
//            playerHpLabel.setText(newValue.toString());
//            setHpBar();
//            if (oldValue.intValue() < 1) {
//                gameOver();
//            }
//        });
//        playerMana.addListener((observable, oldValue, newValue) -> {
//            setManaBar();
//            healButton.setDisable(newValue.intValue() < 10);
//            spellButton.setDisable(newValue.intValue() < 10);
//            playerMpLabel.setText(newValue.toString());
//        });

        //broken label binding
//        playerXpLabel.textProperty().bind(new SimpleIntegerProperty(player.experience).asString());
//        playerHpLabel.textProperty().bind(new SimpleIntegerProperty(player.health).asString());
//        playerMpLabel.textProperty().bind(new SimpleIntegerProperty(player.mana).asString());
//        playerMaxMpLabel.textProperty().bind(new SimpleIntegerProperty(player.getMaxMana()).asString());
//        playerMaxHpLabel.textProperty().bind(new SimpleIntegerProperty(player.getMaxHealth()).asString());
//        playerLvlLabel.textProperty().bind(new SimpleIntegerProperty(player.getLevel()).asString());

//        strLabel.textProperty().bind(new SimpleIntegerProperty(player.getStrength()).asString());
//        conLabel.textProperty().bind(new SimpleIntegerProperty(player.getConstitution()).asString());
//        dexLabel.textProperty().bind(new SimpleIntegerProperty(player.getDexterity()).asString());
//        intLabel.textProperty().bind(new SimpleIntegerProperty(player.getIntelligence()).asString());
//        wisLabel.textProperty().bind(new SimpleIntegerProperty(player.getWisdom()).asString());
//        chaLabel.textProperty().bind(new SimpleIntegerProperty(player.getCharisma()).asString());

        //broken progress bar binding
//        expBar.progressProperty().bind(player.getExpObsv());
//        playerHpBar.progressProperty().bind(new SimpleDoubleProperty(player.getHealth()).divide(new SimpleDoubleProperty(player.getMaxHealth())));
//        manaBar.progressProperty().bind(new SimpleDoubleProperty(player.getMana()).divide(new SimpleDoubleProperty(player.getMaxMana())));


    }

    private void setPlayerStats() {
        playerLvlLabel.setText("Level " + player.getLevel());
        playerMaxHpLabel.setText(player.getMaxHealth() + "");
        playerMaxMpLabel.setText(player.getMaxMana() + "");
        strLabel.setText("" + player.getStrength());
        conLabel.setText("" + player.getConstitution());
        dexLabel.setText("" + player.getDexterity());
        intLabel.setText("" + player.getIntelligence());
        wisLabel.setText("" + player.getWisdom());
        chaLabel.setText("" + player.getCharisma());
        if (player.getWeapon().getStrBonus() >= 0) {
            strBonusLabel.setText("(+" + player.getWeapon().getStrBonus() + ")");
        } else {
            strBonusLabel.setText("(" + player.getWeapon().getStrBonus() + ")");
        }
        if (player.getWeapon().getConBonus() >= 0) {
            conBonusLabel.setText("(+" + player.getWeapon().getConBonus() + ")");
        } else {
            conBonusLabel.setText("(" + player.getWeapon().getConBonus() + ")");
        }
        if (player.getWeapon().getDexBonus() >= 0) {
            dexBonusLabel.setText("(+" + player.getWeapon().getDexBonus() + ")");
        } else {
            dexBonusLabel.setText("(" + player.getWeapon().getDexBonus() + ")");
        }
        if (player.getWeapon().getIntBonus() >= 0) {
            intBonusLabel.setText("(+" + player.getWeapon().getIntBonus() + ")");
        } else {
            intBonusLabel.setText("(" + player.getWeapon().getIntBonus() + ")");
        }
        if (player.getWeapon().getWisBonus() >= 0) {
            wisBonusLabel.setText("(+" + player.getWeapon().getWisBonus() + ")");
        } else {
            wisBonusLabel.setText("(" + player.getWeapon().getWisBonus() + ")");
        }
        if (player.getWeapon().getCharBonus() >= 0) {
            chaBonusLabel.setText("(+" + player.getWeapon().getCharBonus() + ")");
        } else {
            chaBonusLabel.setText("(" + player.getWeapon().getCharBonus() + ")");
        }
        setHpBar();
        setManaBar();
        setExpBar();
    }

    private void setUpEnemies(List<Enemy> enemies) {
        System.out.println("Setting up enemies");
        this.enemies = enemies;
    }

    private void setEnemy(Enemy enemy) {
        this.enemy = enemy;

        enemyNameLabel.setText(enemy.getName());
        enemyHpLabel.setText(enemy.getHealth() + "");
        enemyMpLabel.setText(enemy.getMana() + "");
        enemyMaxHpLabel.setText(enemy.getMaxHealth() + "");
        enemyMaxMpLabel.setText(enemy.getMaxMana() + "");
        enemyLvlLabel.setText("Level " + enemy.getLevel());
        enemyStrLabel.setText("" + enemy.getStrength());
        enemyConLabel.setText("" + enemy.getConstitution());
        enemyDexLabel.setText("" + enemy.getDexterity());
        enemyIntLabel.setText("" + enemy.getIntelligence());
        enemyWisLabel.setText("" + enemy.getWisdom());
        enemyChaLabel.setText("" + enemy.getCharisma());
        setEnemyHpBar();
        setEnemyManaBar();
        actionList.add("A " + enemy.getName() + " " + enemy.getEntrance());
    }


    private void setEnemyHpBar() {
        double progress = (double) enemy.getHealth() / (double) enemy.getMaxHealth();
        System.out.println("health % = " + progress);
        enemyHpBar.setProgress(progress);
        System.out.println("Enemy has " + enemy.getHealth() + " health left");
        enemyHpLabel.setText(enemy.getHealth() + "");

        //broken bindings
//        enemyHpLabel.textProperty().bind(new SimpleIntegerProperty(enemy.getHealth()).asString());
//        enemyHpBar.progressProperty().bind(new SimpleDoubleProperty(enemy.getHealth()).divide(enemy.getMaxHealth()));
    }

    private void setEnemyManaBar() {
        enemyManaBar.progressProperty().bind(new SimpleDoubleProperty(enemy.getMana()).divide(enemy.getMaxMana()));
//        enemyMpLabel.textProperty().bind(new SimpleIntegerProperty(enemy.getMana()).asString());
    }

    private void setHpBar() {
        playerHpBar.setProgress((double) player.getHealth() / (double) player.getMaxHealth());
        playerHpLabel.setText(player.getHealth() + "");
    }

    private void setManaBar() {
        manaBar.setProgress((double) player.getMana() / (double) player.getMaxMana());
        playerMpLabel.setText(player.getMana() + "");
    }

    private void setExpBar() {
        double progress = player.getExpProgPct();
        expBar.setProgress(progress);
        playerXpLabel.setText(player.getExperience() + " / " + player.getNextLvlExp() + " XP");
    }

    private void playerTakeDamage(int damage) {
        if (player.takeDamage(damage)) {
            actionList.add(player.getName() + " has " + player.getHealth() + "hp left");
        } else {
            gameOver();
        }
        setHpBar();
    }

    private void playerHeal(int health) {
        ALPause(250, player.getName() + " restores " + health + " health.");
        player.heal(health);
        setHpBar();

    }

    private void playerRestoreMana(int mana) {
        player.restoreMana(mana);
        setManaBar();
    }

    private void playerDrainMana(int mana) {
        if (mana > 0) {
            player.drainMana(mana);
            setManaBar();
        }
    }

    private void ALPause(long ms, String message) {
        actionList.add(message);

        //todo figure out how to get messages to pause between each display (Thread pools?)
//        try {
//            Thread.sleep(ms);
//        } catch (InterruptedException e){
//            e.printStackTrace();
//        }
    }

    private void enemyTakeDamage(int damage) {
//        ALPause(500, enemy.getName() + " takes " + damage + " damage");
        if (enemy.takeDamage(damage)) {
            ALPause(500, enemy.getName() + " has " + enemy.getHealth() + "hp left");
            setEnemyHpBar();
        } else {
            setEnemyHpBar();
            ALPause(500, enemy.getName() + " has died!");
            actionList.add(player.getName() + " gains " + enemy.getExperienceGained() + " experience points");
            if (player.gainExp(enemy.getExperienceGained())) {
                levelUp();
            }
            setExpBar();
            nextButton.setDisable(false);
        }
    }

    private void enemyHeal(int hp) {
        enemy.heal(hp);
        setEnemyHpBar();
        actionList.add(enemy.getName() + " restored " + hp + " health\n" +
                "Enemy now has " + enemy.getHealth() + " hp");
    }

    private void enemyDrainMana(int mana) {
        if (mana > 0) {
            enemy.drainMana(mana);
            setEnemyManaBar();
        }
//        System.out.println("enemy has " + enemy.getMana() + " mana left");
    }

    private void enemyTurn() {
        enemyAttack(enemy.defaultAttack());
    }

    private void enemyAttack(List<Damage> damage) {
        for (Damage d : damage) {
            if (d.isOOM()) {
                enemyAttack(enemy.defaultAttack());
                return;
            }
            ALPause(500, enemy.getName() + " uses " + d.getAttackName());
            enemyDrainMana(d.getManaCost());
            int dmg = d.getPhysDamage();
            int heal = d.getHeal();
            if (!d.isHit() && d.getPhysDamage()>0) {
                actionList.add(enemy.getName() + " missed!");
                break;
            }
            if (d.isCrit()) {
                ALPause(250, "Critical Hit!");
            }
            if (dmg > 0) {
                actionList.add(enemy.getName() + " deals " + dmg + " damage" + " to " + player.getName());
                playerTakeDamage(dmg);
            }
            if (heal > 0) {
                ALPause(500, enemy.getName() + " heals for " + heal + " damage");
                enemyHeal(heal);
            }
        }
    }

    private void gameOver() {
        deaths += 1;
        actionList.add("Oh no! You died!\nGame over.");
        actionList.add("Rounds Completed: " + roundsCompleted +
                "\nDeaths: " + deaths +
                "\nTotal Kills: " + totalKills);
//        stabButton.setDisable(true);
//        spellButton.setDisable(true);
//        healButton.setDisable(true);

        nextButton.setText("Restart Round");
        nextButton.setOnAction(event -> restartRound());
//        nextButton.setDisable(false);
    }

    private void restartRound() {
        actionList.clear();
        makeNextEnemyButton();
        for (Enemy e : enemies) {
            e.fullHealth();
            e.fullMana();
        }
        enemiesDefeated = 0;
        setEnemy(enemies.get(0));
        restorePlayer();
//        stabButton.setDisable(false);
//        spellButton.setDisable(false);
//        healButton.setDisable(false);
    }

    private void levelUp() {
        skillPoints += 4 + (player.getLevel() / 4);
        actionList.add("LEVEL UP! You are now level " + player.getLevel() + "\nYou have " + skillPoints + " points to spend on attributes");
        nextButton.setDisable(true);
        showLvlUpButtons(true);
    }

    //todo try to implement spendPoint(Button button) to consolidate button actions
    private void spendPoint() {
        skillPoints -= 1;
        actionList.add(skillPoints + " points left to spend");
        if (skillPoints < 1) {
            makeNextEnemyButton();
            nextButton.setDisable(false);
            showLvlUpButtons(false);
            restorePlayer();
            setPlayerStats();
        }
    }

    @FXML
    private void strPlus() {
        if (skillPoints > 0) {
            player.setStrength(player.getStrength() + 1);
            strLabel.setText(player.getStrength() + "");
            spendPoint();
        }
    }

    @FXML
    private void conPlus() {
        if (skillPoints > 0) {
            player.setConstitution(player.getConstitution() + 1);
            conLabel.setText(player.getConstitution() + "");
            spendPoint();
        }
    }

    @FXML
    private void dexPlus() {
        if (skillPoints > 0) {
            player.setDexterity(player.getDexterity() + 1);
            dexLabel.setText("" + player.getDexterity());
            spendPoint();
        }
    }

    @FXML
    private void intPlus() {
        if (skillPoints > 0) {
            player.setIntelligence(player.getIntelligence() + 1);
            intLabel.setText("" + player.getIntelligence());
            spendPoint();
        }
    }

    @FXML
    private void wisPlus() {
        if (skillPoints > 0) {
            player.setWisdom(player.getWisdom() + 1);
            wisLabel.setText("" + player.getWisdom());
            spendPoint();
        }
    }

    @FXML
    private void chaPlus() {
        if (skillPoints > 0) {
            player.setCharisma(player.getCharisma() + 1);
            chaLabel.setText("" + player.getCharisma());
            spendPoint();
        }
    }

    private void showLvlUpButtons(boolean show) {
        strPlus.setDisable(!show);
        strPlus.setVisible(show);
        conPlus.setDisable(!show);
        conPlus.setVisible(show);
        dexPlus.setDisable(!show);
        dexPlus.setVisible(show);
        intPlus.setDisable(!show);
        intPlus.setVisible(show);
        wisPlus.setDisable(!show);
        wisPlus.setVisible(show);
        chaPlus.setDisable(!show);
        chaPlus.setVisible(show);
        System.out.println("LvlUp Buttons visible = " + show);
    }

    private void restorePlayer() {
        player.fullHealth();
        player.fullMana();
        setManaBar();
        setHpBar();
    }
}
