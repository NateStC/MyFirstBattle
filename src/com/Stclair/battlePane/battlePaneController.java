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

    @FXML
    private void showNewPlayerDialog() {
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
            setPlayer();
            startGame();
            System.out.println("Game Starting");
        }
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
            System.out.println("enemies defeated: " + enemiesDefeated);
            actionList.clear();
            if (enemiesDefeated < enemies.size()) {
                if (!player.manaIsFull()) {
                    int mp = (int) ((player.getIntelligence() + player.getWisdom()) / 1.5);
                    playerRestoreMana(mp);
                    actionList.add("You rest between enemies and restore " + mp + " mana.");
                }
                if (!player.healthIsFull()) {
                    int hp = player.getConstitution() + player.getLevel();
                    playerHeal(hp);
                    actionList.add("In the respite between battles you restore " + hp + " health.");
                }
                setEnemy(enemies.get(enemiesDefeated));
                nextButton.setDisable(true);
            } else {
                actionList.add("You have defeated all of the enemies!");
                roundsCompleted += 1;
                nextButton.setText("New Round");
                nextButton.setOnAction(event -> startGame());
                nextButton.setDisable(false);
//                spellButton.setDisable(true);
//                healButton.setDisable(true);
//                stabButton.setDisable(true);
            }
        } else {
            actionList.add(enemy.getName() + " stands in your way, refusing to let you continue.");
        }
    }

    private void startGame() {
        enemiesDefeated = 0;
        player.fullHealth();
        player.fullMana();
        setHpBar();
        setManaBar();
        showActionList();
        setUpEnemies();
        actionList.add("Your journey begins!");
        setEnemy(enemies.get(0));
//        spellButton.setDisable(false);
//        healButton.setDisable(false);
//        stabButton.setDisable(false);
//        stabButton.setDefaultButton(true);
//        makeNextEnemyButton();

//        try {
//            mainBorderPane.setCenter(FXMLLoader.load(getClass().getResource("battleUI.fxml")));
//        } catch (IOException e){
//            System.out.println("Unable to start game");
//            e.printStackTrace();
//        }
    }

    private void setActions() {
        //get weapon attacks (wa) from equipped weapon
        ArrayList<String> wa = player.getWeapon().getAttacks();

        ArrayList<Button> attacks = new ArrayList<>();

        if (wa.contains("stab")) {
            Button stab = new Button("Stab");
            stab.setPrefWidth(100);
            stab.setOnAction(e -> {
                if (enemy.getHealth() > 0) {
                    playerAttack(Attack.stab(player));
                } else {
                    enemyDead();
                }
            });
            attacks.add(stab);
        }

        if (wa.contains("bash")) {
            Button bash = new Button("Bash");
            bash.setPrefWidth(100);
            bash.setOnAction(e -> {
                if (enemy.getHealth() > 0) {
                    playerAttack(Attack.bash(player));
                } else {
                    enemyDead();
                }
            });
            attacks.add(bash);
        }

        if (wa.contains("daggerSlice")) {
            Button dSlice = new Button("Dagger Slice");
            dSlice.setPrefWidth(100);
            dSlice.setOnAction(e -> {
                if (enemy.getHealth() > 0) {
                    playerAttack(Attack.daggerSlice(player));
                } else {
                    enemyDead();
                }
            });
            attacks.add(dSlice);
        }

        if (wa.contains("swordSlice")) {
            Button sSlice = new Button("Sword Slice");
            sSlice.setPrefWidth(100);
            sSlice.setOnAction(e -> {
                if (enemy.getHealth() > 0) {
                    playerAttack(Attack.swordSlice(player));
                } else {
                    enemyDead();
                }
            });
            attacks.add(sSlice);
        }

        if (wa.contains("smash")) {
            Button smash = new Button("Smash");
            smash.setPrefWidth(100);
            smash.setOnAction(e -> {
                if (enemy.getHealth() > 0) {
                    playerAttack(Attack.smash(player));
                } else {
                    enemyDead();
                }
            });
            attacks.add(smash);
        }

        if (wa.contains("arrowStrike")) {
            Button arrow = new Button("Arrow Strike");
            arrow.setPrefWidth(100);
            arrow.setOnAction(e -> {
                if (enemy.getHealth() > 0) {
                    playerAttack(Attack.arrowStrike(player));
                } else {
                    enemyDead();
                }
            });
            attacks.add(arrow);
        }

        if (wa.contains("headShot")) {
            Button headShot = new Button("Head Shot");
            headShot.setPrefWidth(100);
            headShot.setOnAction(e -> {
                if (enemy.getHealth() > 0) {
                    playerAttack(Attack.headShot(player));
                } else {
                    enemyDead();
                }
            });
            attacks.add(headShot);
        }

        if (wa.contains("fireball")) {
            Button fireball = new Button("Fireball");
            fireball.setPrefWidth(100);
            fireball.setOnAction(e -> {
                if (enemy.getHealth() > 0) {
                    playerCast(Spell.fireball(player));
                } else {
                    enemyDead();
                }
            });
            attacks.add(fireball);
        }

        if (wa.contains("staticShock")) {
            Button shock = new Button("Static Shock");
            shock.setPrefWidth(100);
            shock.setOnAction(e -> {
                if (enemy.getHealth() > 0) {
                    playerCast(Spell.staticShock(player));
                } else {
                    enemyDead();
                }
            });
            attacks.add(shock);
        }

        if (wa.contains("healingHands")) {
            Button heal = new Button("Healing Hands");
            heal.setPrefWidth(100);
            heal.setOnAction(event -> {
                if (!player.healthIsFull()) {
                    playerCast(Spell.healingHands(player));
                } else {
                    actionList.add(player.getName() + "'s health is already full");
                }
            });
            attacks.add(heal);
        }

        if (wa.contains("drainLife")) {
            Button drain = new Button("Drain Life");
            drain.setPrefWidth(100);
            drain.setOnAction(e -> {
                if (enemy.getHealth() > 0) {
                    playerCast(Spell.drainLife(player));
                } else {
                    enemyDead();
                }
            });
            attacks.add(drain);
        }

        if (wa.contains("fireArrow")) {
            Button fire = new Button("Fire Arrow");
            fire.setPrefWidth(100);
            fire.setOnAction(event -> {
                if (!enemy.isDead()) {
                    playerCast(Spell.fireArrow(player));
                } else {
                    enemyDead();
                }
            });
            attacks.add(fire);
        }

        nextButton = new Button("Next Enemy");
        nextButton.setPrefWidth(100);
        nextButton.setOnAction(e -> nextEnemy());
        attacks.add(nextButton);
//        actionsGrid.getChildren().add(nextButton);

        int row = 0;
        int column = 0;

        //populate gridPane with buttons for attacks player is able to cast with that weapon
        for (Button b : attacks) {
            actionsGrid.add(b,column,row);
            column +=1;
            if (column >1){
                column = 0;
                row ++;
            }
        }

        for (int col = 0; col < actionsGrid.getColumnCount(); col++){
            ColumnConstraints cc = new ColumnConstraints();
            cc.setFillWidth(true);
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

    private void enemyDead() {
        actionList.add("Enemy " + enemy.getName() + " is already dead!");
    }

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

    private void setUpEnemies() {
        System.out.println("Setting up enemies");
        enemies = new ArrayList<>();
        int lvl = player.getLevel();
        enemies.add(new Enemy(player.getLevel()));
        enemies.add(new Enemy("Goblin Grunt", 5 + (lvl * 2), 5 + (lvl * 2), 6 + lvl, 3 + lvl, 2 + lvl, 2 + lvl, lvl, Weapon.dagger()));
        enemies.add(new Enemy("Goblin Shaman", 3 + lvl, 7 + (lvl * 2), 3 + lvl, 7 + (lvl * 2), 6 + (lvl * 2), 6 + lvl, lvl, Weapon.staff()));
        enemies.add(new Enemy("Goblin Archer", 3 + (int) (lvl * 1.5), 6 + (int) (lvl * 1.5), 9 + (lvl * 3), 5 + lvl, 4 + lvl, 4 + lvl, lvl, Weapon.bow()));
        enemies.add(new Enemy("Goblin Brute", 7 + (lvl * 3), 8 + (int) (lvl * 1.5), 5 + lvl, 2 + lvl, 2 + lvl, 2 + lvl, lvl, Weapon.mace()));
        enemies.add(new Enemy("Goblin King", 8 + (lvl * 2), 9 + (lvl * 3), 8 + (lvl * 2), 7 + (lvl), 7 + lvl, 7 + lvl, 1 + lvl, Weapon.longSword()));
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
//        System.out.println("Experience progress to next lvl: " + progress);
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
        player.heal(health);
        setHpBar();
//        System.out.println("Player heals " + health + " points.");
//        System.out.println("Player now has " + player.getHealth() + " hp");

    }

    private void playerRestoreMana(int mana) {
        player.restoreMana(mana);
        setManaBar();
//        System.out.println("Player restored " + mana + " mana\n" +
//                "Player now has + " + player.getMana() + " mp");
    }

    private void playerDrainMana(int mana) {
        player.drainMana(mana);
        setManaBar();
    }

    private void enemyTakeDamage(int damage) {
        if (enemy.takeDamage(damage)) {
            actionList.add(enemy.getName() + " has " + enemy.getHealth() + "hp left");
            enemyTurn();
            setEnemyHpBar();
        } else {
            setEnemyHpBar();
            actionList.add(enemy.getName() + " has died!");
            actionList.add(player.getName() + " gains " + enemy.getExperienceGained() + " experience points");
            if (player.gainExp(enemy.getExperienceGained())) {
                levelUp();
                setExpBar();
                return;
            }
            setExpBar();
            nextButton.setDisable(false);
        }
    }

    private void enemyHeal(int hp) {
        enemy.heal(hp);
        setEnemyHpBar();
//        System.out.println(enemy.getName() + " restored " + hp + " health\n" +
//                "Enemy now has " + enemy.getHealth() + " hp");
    }

    private void enemyDrainMana(int mana) {
        enemy.drainMana(mana);
        setEnemyManaBar();
//        System.out.println("enemy has " + enemy.getMana() + " mana left");
    }

    private void enemyTurn() {
        enemyAttack(enemy.defaultAttack());
    }

    private void enemyAttack(Attack attack) {
        if (attack instanceof Spell) {
            enemySpell((Spell) attack);
            return;
        }
        actionList.add(enemy.getName() + " uses " + attack.getName());
        if (attack.getAccuracy() == 1) {
            actionList.add(enemy.getName() + " missed!");
            return;
        }
        if (attack.isCrit()) {
            actionList.add("Critical Hit!");
        }
        if (attack.getDamage() > 0) {
            actionList.add(enemy.getName() + " deals " + attack.getDamage() + " damage" + " to " + player.getName());
            playerTakeDamage(attack.getDamage());
        }
    }

    private void enemySpell(Spell spell) {
        if (spell.getManaCost() > enemy.getMana()) {
            enemyAttack(Attack.bash(enemy));
            return;
        }
        actionList.add(enemy.getName() + " casts " + spell.getName());
        enemyDrainMana(spell.getManaCost());
        if (spell.getAccuracy() == 1) {
            actionList.add(enemy.getName() + " missed!");
            return;
        }
        if (spell.isCrit()) {
            actionList.add("Critical hit!");
        }
        if (spell.getDamage() > 0) {
            actionList.add(enemy.getName() + " deals " + spell.getDamage() + " damage to " + player.getName() + ".");
            playerTakeDamage(spell.getDamage());
        }
        if (spell.getHeal() > 0) {
            actionList.add(enemy.getName() + " restores " + spell.getHeal() + " health");
            enemyHeal(spell.getHeal());
        }
    }

    private void gameOver() {
        deaths += 1;
        actionList.add("Oh no! You died!\nGame over.");
        actionList.add("Rounds Completed: " + roundsCompleted +
                "\nDeaths: " + deaths);
//        stabButton.setDisable(true);
//        spellButton.setDisable(true);
//        healButton.setDisable(true);

        nextButton.setText("Restart Round");
        nextButton.setOnAction(event -> restartRound());
        nextButton.setDisable(false);
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

    private void playerCast(Spell spell) {
        if (player.isDead()){
            actionList.add(player.getName() + " is dead!");
            return;
        }
        if (spell.getDamage() == -1) {
            actionList.add("Not enough mana to cast " + spell.getName());
            return;
        }
        playerDrainMana(spell.getManaCost());
        if (spell.getAccuracy() != 1) {
            if (spell.isCrit()) {
                actionList.add("Critical Hit!");
            }
            if (spell.getHeal() > 0) {
                actionList.add("You heal yourself for " + spell.getHeal());
                playerHeal(spell.getHeal());
            }
            if (spell.getDamage() == 0 && spell.getHeal() == 0) {
                actionList.add("Spell does no damage");
                enemyTurn();
            } else {
                actionList.add(enemy.getName() + " takes " + spell.getDamage() + " damage from your " + spell.getName());
                enemyTakeDamage(spell.getDamage());
            }
        } else {
            actionList.add("Your " + spell.getName() + " missed!");
            enemyTurn();
        }
    }

    private void playerAttack(Attack attack) {
        if (player.isDead()){
            actionList.add(player.getName() + " is dead!");
            return;
        }
        if (attack instanceof Spell) {
            playerCast((Spell) attack);
            return;
        }
        if (attack.getAccuracy() != 1) {
            if (attack.isCrit()) {
                System.out.println("Critical hit!");
            }
            actionList.add(player.getName() + "'s " + attack.getName() + " hits the enemy for " + attack.getDamage());
            enemyTakeDamage(attack.getDamage());
        } else {
            actionList.add("Your attack missed!");
            enemyTurn();
        }
    }

    @FXML
    private void fireBall() {
        if (enemy.getHealth() > 0) {
            playerCast(Spell.fireball(player));
        } else {
            actionList.add(enemy.getName() + " is already dead.");
        }
    }

    @FXML
    private void stab() {
        if (enemy.getHealth() > 0) {
            playerAttack(Attack.stab(player));
        } else {
            actionList.add(enemy.getName() + " is already dead");
        }
    }

    @FXML
    private void healingSpell() {
        if (!player.healthIsFull()) {
            playerCast(Spell.healingHands(player));
        } else {
            actionList.add("Your health is already full");
        }
    }
}
