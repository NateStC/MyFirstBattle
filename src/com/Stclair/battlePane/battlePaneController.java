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
import java.util.Random;

public class battlePaneController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private GridPane actionsGrid, playerGrid, enemyGrid;

    @FXML
    private Label playerNameLabel, playerLvlLabel, strLabel, conLabel, dexLabel, intLabel, wisLabel, chaLabel,
            enemyNameLabel, enemyLvlLabel, enemyStrLabel, enemyConLabel, enemyDexLabel, enemyIntLabel, enemyWisLabel,
            enemyChaLabel, playerHpLabel, playerMpLabel, playerXpLabel, playerMaxHpLabel, playerMaxMpLabel,
            enemyHpLabel, enemyMpLabel, enemyMaxMpLabel, enemyMaxHpLabel, strBonusLabel, dexBonusLabel, conBonusLabel,
            intBonusLabel, wisBonusLabel, chaBonusLabel;

    @FXML
    private ProgressBar expBar, playerHpBar, manaBar, enemyHpBar, enemyManaBar;

    @FXML
    private Button nextButton;

    @FXML
    private Button strPlus, dexPlus, conPlus, intPlus, wisPlus, chaPlus;

    @FXML
    private ListView<String> actionListView;

    static Player player;

    Enemy enemy;

    private Enemies.EnemyGroup enemies;

    private int enemiesDefeated, roundsCompleted, deaths, totalKills, skillPoints;

    private ObservableList<String> actionList;

    public void initialize() {
        actionList = FXCollections.observableArrayList();
        skillPoints = 0;
        showLvlUpButtons(false);
        enemyGrid.setVisible(false);
        //set actions list view to wrap text  from Ryotsu on StackOverflow
        actionListView.setCellFactory(param -> new ListCell<>() {
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

    private void showActionList() {
        ListView<String> actionLV = new ListView<>();
        mainBorderPane.setCenter(actionLV);
        actionLV.setItems(actionList);

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
        player = new Player("Trofasthet the Spell-Sword", 10, 9, 13, 12,
                10, 8, Weapons.swordShort(), Armors.tinChainmail());
        startGame();

    }

    @FXML
    private void newRogue() {
        player = new Player("John the Silent-but-Deadly", 7, 14, 10, 8,
                9, 11, Weapons.daggers(), Armors.scrapLeathers());
        startGame();
    }

    @FXML
    private void newWarrior() {
        player = new Player("Volstagg the Burly", 14, 8, 13, 8, 9,
                7, Weapons.mace(), Armors.tinPlatemail());
        startGame();
    }

    @FXML
    private void newWizard() {
        player = new Player("Dinklebot the Astute", 6, 8, 9, 15, 13,
                11, Weapons.staff(), Armors.initiateRobes());
        startGame();
    }

    @FXML
    private void newRanger() {
        player = new Player("Colton the Ranger", 8, 14, 10, 10, 10,
                8, Weapons.bow(), Armors.scrapLeathers());
        startGame();
    }

    @FXML
    private void test() {
        player = new Player("Test God", 20, 20, 20, 20, 20,
                20, Weapons.godSword(), Armors.chain(5));
        startGame();
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
                    int mp = (player.getIntStat() + player.getWisStat());
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
        actionsGrid.getChildren().clear();

        List<Enemies.EnemyGroup> groups = Enemies.enemyGroups(player.getLevel());
        List<Button> buttonList = new ArrayList<>();

        for (Enemies.EnemyGroup eg : groups) {
            //Todo figure out how to use Optional<Button>
            Button button = new Button(eg.name);
            button.setPrefWidth(100);
            button.setOnAction(e -> {
                this.enemies = eg;
                startNewRound();
            });
            buttonList.add(button);
        }
        int column = 0, row = 0;

        //populate gridPane with buttons for attacks player is able to cast with that weapon
        for (Button b : buttonList) {
            actionsGrid.add(b, column, row);
            column += 1;
            if (column > 1) {
                column = 0;
                row++;
            }
        }

        Button button = new Button("Test Dummy");
        button.setPrefWidth(100);
        button.setOnAction(e -> {
            this.enemies = Enemies.dummyTest(player.getLevel());
            startNewRound();
        });
        actionsGrid.add(button, column, row);

        for (int col = 0; col < actionsGrid.getColumnCount(); col++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setFillWidth(true);
        }
    }

    public void startNewRound() {
        setActions();
        setUpEnemies(enemies);
        setEnemy(enemies.get(0));
    }

    private void setActions() {
        ArrayList<Button> attacks = new ArrayList<>();

        //Todo figure out how to use Optional<Button>

        //TODO make spell buttons grayed out if not enough mana  ?BooleanBinding??

        //work your way through the weapon's attacks, creating a button for each
        for (Attack a : player.getWeapon().getAttackList()) {
            Button button = new Button(a.getName());
            button.setPrefWidth(130);

            button.setOnAction(e -> {
                if (player.isDead()) {
                    actionList.add("You can't use " + a.getName() + ", you are dead!");
                } else {
                    List<Damage> result = a.doAttack(player, enemy);
                    int atks = 0;
                    //build a button for each attack
                    for (Damage d : result) {
                        if (enemy.isDead() && d.getPhysDamage() > 0) {
                            actionList.add("Enemy is already dead!");
                            break;
                        }
                        if (d.getPhysDamage() == 0 && d.getHeal() > 0 && player.healthIsFull()) {
                            actionList.add(player.getName() + " is already at full health");
                            break;
                        }
                        if (d.isOOM()) {
                            actionList.add("Not enough mana to cast " + d.getAttackName());
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
                        if (d.getDamage() > 0) {
                            if (result.size() == 1) {
                                ALPause(50, enemy.getName() + " takes " + d.getDamage() + " damage");
                            } else {
                                ALPause(50, firstOrSecond(atks) + " strike deals " + d.getDamage());
                            }
                            enemyTakeDamage(d.getDamage());
                        }
                        if (d.getHeal() > 0 && !player.healthIsFull()) {
                            playerHeal(d.getHeal());
                        }
                        if (enemy.isDead()) {
                            break;
                        }
                        //TODO add turnIsOver boolean to Damage class to allow certain actions to not use up turn
                        if (atks == result.size()) {
                            enemyTurn();
                        }
                    }
                }
            });
            attacks.add(button);
        }


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

        //adds Next Enemy Button at bottom of actionGrid
        nextButton = new Button("Next Enemy");
        nextButton.setPrefWidth(100);
        nextButton.setOnAction(e -> nextEnemy());
        if (column == 1) {
            row++;
        }
        actionsGrid.add(nextButton, 1, row);

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
            strBonusLabel.setText("(+" + player.getStrBonus() + ")");
        } else {
            strBonusLabel.setText("(" + player.getStrBonus() + ")");
        }
        if (player.getConBonus() > 0) {
            conBonusLabel.setText("(+" + player.getConBonus() + ")");
        } else {
            conBonusLabel.setText("(" + player.getConBonus() + ")");
        }
        if (player.getWeapon().getDexBonus() >= 0) {
            dexBonusLabel.setText("(+" + player.getDexBonus() + ")");
        } else {
            dexBonusLabel.setText("(" + player.getDexBonus() + ")");
        }
        if (player.getWeapon().getIntBonus() >= 0) {
            intBonusLabel.setText("(+" + player.getIntBonus() + ")");
        } else {
            intBonusLabel.setText("(" + player.getIntBonus() + ")");
        }
        if (player.getWeapon().getWisBonus() >= 0) {
            wisBonusLabel.setText("(+" + player.getWisBonus() + ")");
        } else {
            wisBonusLabel.setText("(" + player.getWisBonus() + ")");
        }
        if (player.getWeapon().getCharBonus() >= 0) {
            chaBonusLabel.setText("(+" + player.getChaBonus() + ")");
        } else {
            chaBonusLabel.setText("(" + player.getChaBonus() + ")");
        }
        setHpBar();
        setManaBar();
        setExpBar();
    }

    private void setUpEnemies(Enemies.EnemyGroup enemies) {
        System.out.println("Setting up enemies");
        this.enemies = enemies;
        restoreEnemies();
        setEnemy(enemies.get(0));
    }

    private void setEnemy(Enemy enemy) {
        this.enemy = enemy;
        enemyGrid.setVisible(true);

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
        enemyHpBar.setProgress(enemy.getHealthPct());
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
        enemyAttack(enemyDefaultAttack());
    }

    private void enemyAttack(List<Damage> damage) {
        int atks = 0;
        for (Damage d : damage) {
            atks++;
            if (d.isOOM()) {
                enemyAttack(enemyDefaultAttack());
                return;
            }
            if (atks == 1) {
                ALPause(500, enemy.getName() + " uses " + d.getAttackName());
                enemyDrainMana(d.getManaCost());
            }
            int dmg = d.getDamage();
            int heal = d.getHeal();
//            if (!d.isHit() && d.getPhysDamage() > 0) {
            if (!d.isHit()) {
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

    public List<Damage> enemyDefaultAttack() {
        //todo finish change for ArrayList<Attack>
        // look for healing spells if health is low enough and if enemy is smart enough
        Random rand = new Random();
        ArrayList<Attack> attacks = new ArrayList<>(enemy.getWeapon().getAttackList());
        ArrayList<Integer> indicesToRemove = new ArrayList<>();
        for (Attack a: attacks){
            if (a.getTotalManaCost(enemy)>enemy.getMana()){
                indicesToRemove.add(attacks.indexOf(a));
            }
        }
        for (Integer i : indicesToRemove){
            attacks.remove((int)i);
        }

        if (enemy.getHealthPct()<.20 && enemy.isSmart()){
            for (Attack a : attacks){
                if (a.isHealingSpell()){
                    return a.doAttack(enemy,player);
                }
            }
        }

        return attacks.get(rand.nextInt(attacks.size())).doAttack(enemy,player);
    }

    private void gameOver() {
        deaths += 1;
        actionList.add("Oh no! You died!\nGame over.");
        actionList.add("Rounds Completed: " + player.getRoundsCompleted() +
                "\nDeaths: " + player.getDeaths() +
                "\nTotal Kills: " + player.getTotalKills());
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
        setUpEnemies(enemies);
        enemiesDefeated = 0;
        setEnemy(enemies.get(0));
        restorePlayer();
//        stabButton.setDisable(false);
//        spellButton.setDisable(false);
//        healButton.setDisable(false);
    }

    private void restoreEnemies() {
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).fullRestore();
        }
    }

    private void levelUp() {
        skillPoints += 4 + (player.getLevel() / 4);
        actionList.add("LEVEL UP! You are now level " + player.getLevel() + "\nYou have " + skillPoints + " points to spend on attributes");
        nextButton.setDisable(true);
        showLvlUpButtons(true);
        setActionButtonsDisabled(true);
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
            setActionButtonsDisabled(false);
        }
    }

    private void setActionButtonsDisabled(boolean bool) {
        for (int i = 0; i < actionsGrid.getChildren().size(); i++) {
            Button b = (Button) actionsGrid.getChildren().get(i);
            b.setDisable(bool);
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
