package com.Stclair.battlePane;

import com.Stclair.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class battlePaneController {

    //todo make a window to view Weapons

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private GridPane actionsGrid, playerGrid, enemyGrid;

    @FXML
    private AnchorPane centerPane;

    private Label nameFieldLabel = new Label();
    private TextField nameField = new TextField();
    private Label attackDescription = new Label();

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

    private int enemiesDefeated, skillPoints;

    private ObservableList<String> actionList;

    public void initialize() {
        actionList = FXCollections.observableArrayList();
        skillPoints = 0;
        showLvlUpButtons(false);
        enemyGrid.setVisible(false);
        attackDescription.setWrapText(true);
        GridPane.setColumnSpan(attackDescription, 2);
        GridPane.setRowSpan(attackDescription,3);

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
        startNewGame();
    }

    private void showActionList() {
        ListView<String> actionLV = new ListView<>();
        mainBorderPane.setCenter(actionLV);
        actionLV.setItems(actionList);

    }

    @FXML
    private void startNewGame() {
        actionListView.setVisible(false);
        Button newCharacter = new Button("Roll New Character");
        newCharacter.setOnAction(e -> rollNewCharacter());
        newCharacter.setPrefWidth(120);

        Button loadCharacter = new Button("Load Game");
        loadCharacter.setPrefWidth(120);
        //todo finish load character button

        Button spellSword = new Button("Spell Sword");
        spellSword.setOnAction(e -> newSpellSword());
        spellSword.setPrefWidth(120);

        Button rogue = new Button("Rogue");
        rogue.setPrefWidth(120);
        rogue.setOnAction(e -> newRogue());

        Button warrior = new Button("Warrior");
        warrior.setOnAction(e -> newWarrior());
        warrior.setPrefWidth(120);

        Button wizard = new Button("Wizard");
        wizard.setOnAction(e -> newWizard());
        wizard.setPrefWidth(120);

        Button ranger = new Button("Ranger");
        ranger.setOnAction(e -> newRanger());
        ranger.setPrefWidth(120);

        GridPane grid = new GridPane();
        grid.getColumnConstraints().setAll(new ColumnConstraints(120));
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.alignmentProperty().setValue(Pos.CENTER);

        GridPane.setColumnSpan(nameFieldLabel, 2);
        GridPane.setHalignment(nameFieldLabel, HPos.CENTER);
        nameFieldLabel.setText("Or select a pre-rolled character");

        //todo find a way to limit number of characters entered to textfield;
        GridPane.setColumnSpan(nameField, 2);
        nameField.setPromptText("Player Name");

        grid.setVgap(10);
        grid.add(newCharacter, 0, 0);
        grid.add(loadCharacter, 1, 0);
        grid.add(nameFieldLabel, 0, 1);
        grid.add(nameField, 0, 2);
        grid.add(spellSword, 0, 3);
        grid.add(rogue, 1, 3);
        grid.add(warrior, 0, 4);
        grid.add(wizard, 1, 4);
        grid.add(ranger, 0, 5);

        AnchorPane.setLeftAnchor(grid, 80.0);
        AnchorPane.setTopAnchor(grid, 60.0);

        mainBorderPane.setCenter(grid);
    }

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

        //todo find a way to make button that closes, validates dialog data, creates player, and closes
//        ButtonType createButton = new ButtonType("Create Player", ButtonBar.ButtonData.FINISH);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.CLOSE && player != null) {
            startGame();
            System.out.println("Game Starting");
        }
    }

    private boolean nameCheck() {
        if (nameField.getText().length() > 30) {
            nameFieldLabel.setText("Name must be 30 or fewer characters");
            nameFieldLabel.setStyle("-fx-color: red");
            nameField.clear();
            return false;
        }
        return true;
    }

    //TODO make each button part of a buttonGroup and return a new player that can be used by one method
    @FXML
    private void newSpellSword() {
        if (nameCheck()) {
            String name = "Trofasthet the Spell-Sword";
            if (!nameField.getText().isBlank()) {
                name = nameField.getText().trim();
            }
            player = new Player(name, 10, 9, 13, 12,
                    10, 8, Weapons.swordShort(), Armors.tinChainmail());
            startGame();
        }
    }

    @FXML
    private void newRogue() {
        if (nameCheck()) {
            String name = "John the Silent-but-Deadly";
            if (!nameField.getText().isBlank()) {
                name = nameField.getText().trim();
            }
            player = new Player(name, 7, 14, 10, 8,
                    9, 11, Weapons.daggers(), Armors.scrapLeathers());
            startGame();
        }
    }

    @FXML
    private void newWarrior() {
        if (nameCheck()) {
            String name = "Volstagg the Burly";
            if (!nameField.getText().isBlank()) {
                name = nameField.getText().trim();
            }
            player = new Player(name, 14, 8, 13, 8, 9,
                    7, Weapons.mace(), Armors.tinPlatemail());
            startGame();
        }
    }

    @FXML
    private void newWizard() {
        if (nameCheck()) {
            String name = "Dinklebot the Astute";
            if (!nameField.getText().isBlank()) {
                name = nameField.getText().trim();
            }
            player = new Player(name, 6, 8, 9, 15, 13,
                    11, Weapons.staff(), Armors.initiateRobes());
            startGame();
        }
    }

    @FXML
    private void newRanger() {
        if (nameCheck()) {
            String name = "Colton the Ranger";
            if (!nameField.getText().isBlank()) {
                name = nameField.getText().trim();
            }
            player = new Player(name, 8, 14, 10, 10, 10,
                    8, Weapons.bow(), Armors.scrapLeathers());
            startGame();
        }
    }

    @FXML
    private void test() {
        player = new Player("Test God", 20, 20, 20, 20, 20,
                20, Weapons.godSword(), Armors.chain(5));
        startGame();
    }

    @FXML
    private void saveGame() {
        //todo save character
    }

    @FXML
    private void loadGame() {
        //todo load character and game
    }

    private void makeNextEnemyButton() {
        nextButton = new Button("Next Enemy");
        nextButton.setPrefWidth(120);
        nextButton.setOnAction(event -> nextEnemy());
    }

    private void nextEnemy() {
        if (enemy.isDead()) {
            enemiesDefeated += 1;
            player.addKill();
            System.out.println("enemies defeated: " + enemiesDefeated);
            actionList.clear();
            if (enemiesDefeated < enemies.size()) {
                playerRest();
                setEnemy(enemies.get(enemiesDefeated));
//                nextButton.setDisable(true);
            } else {
                actionList.add("You have defeated all of the enemies!");
                player.roundCompleted();
                nextButton.setText("New Round");
                nextButton.setOnAction(event -> newRound());
            }
        } else {
            actionList.add(enemy.getName() + " stands in your way, refusing to let you continue.");
        }
    }

    public void playerRest() {
        if (!player.manaIsFull()) {
            int mp = player.getIntStat() * 2 + player.getWisStat() * 2;
            playerRestoreMana(mp);
            actionList.add("You rest between enemies and restore " + mp + " mana.");
        }
        if (!player.healthIsFull()) {
            int hp = player.getConStat() * 2 + player.getLevel() * 2;
            playerHeal(hp);
            actionList.add("In the respite between battles you restore " + hp + " health.");
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

    private int setActionsGrid(List<Button> buttons) {
        actionsGrid.getChildren().clear();
        int column = 0, row = 0;

        for (Button b : buttons) {
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
        return row;
    }

    private int setActionsGrid(List<Button> buttons, Button lastButton) {
        int lastRow = setActionsGrid(buttons) + 1;
        actionsGrid.add(lastButton, 1, lastRow);
        return lastRow+1;
    }

    public void newRound() {
        enemiesDefeated = 0;
        restorePlayer();
        actionsGrid.getChildren().clear();
        setActionsGrid(getEnemyGroupButtons());
    }

    public void startNewRound() {
        setAttacks();
        setUpEnemies(enemies);
        setEnemy(enemies.get(0));
    }

    private void setAttacks() {
        makeNextEnemyButton();
        int row = setActionsGrid(getAttackButtons(), nextButton);
        actionsGrid.add(attackDescription,0,row);
    }

    private List<Button> getEnemyGroupButtons() {
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
        return buttonList;
    }

    private List<Button> getAttackButtons() {
        ArrayList<Button> attacks = new ArrayList<>();

        //Todo figure out how to use Optional<Button>

        //TODO make spell buttons grayed out if not enough mana  ?BooleanBinding??

        //work your way through the weapon's attacks, creating a button for each

        //todo implement stream for weapon attackList in getAttackButtons()
        for (Attack action : player.getWeapon().getAttackList()) {
            Button button = new Button(action.getName());
            button.setPrefWidth(130);

//            button.setOnMouseDragOver(e-> attackDescription.setText(action.getDescription()));
            button.setOnMouseEntered(event -> attackDescription.setText(action.getDescription()));
            button.setOnMouseExited(e -> attackDescription.setText(""));

            button.setOnAction(e -> {
                if (player.isDead()) {
                    actionList.add("You can't use " + action.getName() + ", you are dead!");
                } else {
                    ActionResultList result = action.action(player, enemy);
                    int atks = 0;
                    //build a button for each attack
                    for (ActionResult r : result) {
                        if (enemy.isDead() && r.getPhysDamage() > 0 && r.isHit()) {
                            actionList.add("Enemy is already dead!");
                            break;
                        }
                        if (r.getPhysDamage() == 0 && r.getHeal() > 0 && player.healthIsFull()) {
                            actionList.add(player.getName() + " is already at full health");
                            break;
                        }
                        if (r.isOOM()) {
                            actionList.add("Not enough mana to cast " + r.getAttackName());
                            break;
                        }
                        if (atks == 0) {
                            actionList.add(player.getName() + " uses " + r.getAttackName());
                        }
                        playerDrainMana(r.getManaCost());
                        atks++;

                        if (!r.isHit()) {
                            if (result.size() == 1) {
                                ALPause(500, r.getAttackName() + " missed.");
                                enemyTurn();
                                break;
                            } else {
                                ALPause(500, firstOrSecond(atks) + " attack missed!");
                            }
                        }
                        if (r.isCrit()) {
                            ALPause(500, "Critical hit!");
                        }
                        if (r.getDamage() > 0) {
                            if (result.size() == 1) {
                                actionList.add(enemy.getName() + " takes " + r.getDamage() + " damage");
                            } else {
                                ALPause(50, firstOrSecond(atks) + " strike deals " + r.getDamage() + " damage");
                            }
                            enemyTakeDamage(r.getDamage());
                        } else if (!(action instanceof HealingSpell)) {
                            actionList.add(enemy.getName() + " absorbed the blow");
                        }
                        if (atks == result.size() && result.getTotalHeals() > 0) {
                            playerHeal(result.getTotalHeals());
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
        return attacks;
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
        setAttacks();

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
        if (!player.takeDamage(damage)) {
            playerDied();
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
        if (!enemy.takeDamage(damage)) {
            setEnemyHpBar();
            ALPause(500, enemy.getName() + " has died!");
            actionList.add(player.getName() + " gains " + enemy.getExperienceGained() + " experience points");
            if (player.gainExp(enemy.getExperienceGained())) {
                levelUp();
            }
            setExpBar();
            nextButton.setDisable(false);
        }
        setEnemyHpBar();
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
    }

    private void enemyTurn() {
        if (enemy.isDead()) {
            return;
        }
        Attack a = enemy.defaultAttack(player);
        List<ActionResult> results = new ArrayList<>(a.action(enemy, player));
        int atks = 0;
        //todo implement stream for ActionResults ArrayList in enemyTurn()
        for (ActionResult r : results) {
            atks++;
            if (r.isOOM()) { // rolls new attack if enemy doesn't have enough mana to cast // should have already been caught by defaultAttack()
                enemyTurn();
                return;
            }
            ALPause(500, enemy.getName() + " uses " + r.getAttackName());
            if (atks == 1) {
                enemyDrainMana(r.getManaCost());
            }
            if (!r.isHit()) {
                actionList.add(enemy.getName() + " missed!");
                return;
            }
            int dmg = r.getDamage();
            int heal = r.getHeal();
            if (r.isCrit()) {
                ALPause(250, "Critical Hit!");
            }
            if (dmg > 0) {
                if (results.size() == 1) {
                    actionList.add(enemy.getName() + " deals " + dmg + " damage" + " to " + player.getName());
                } else {
                    actionList.add(firstOrSecond(atks) + " attack deals " + dmg + " damage");
                }
                playerTakeDamage(dmg);
            } else if (!(a instanceof HealingSpell)) {
                actionList.add(player.getName() + " absorbed the blow!");
            }
            if (heal > 0) {
                ALPause(500, enemy.getName() + " heals for " + heal + " damage");
                enemyHeal(heal);
            }
        }
    }

    private void playerDied() {
        player.addDeath();
        actionList.add("Oh no! You died!\nGame over.");
        actionList.add("Rounds Completed: " + player.getRoundsCompleted() +
                "\nDeaths: " + player.getTotalDeaths() +
                "\nTotal Kills: " + player.getTotalKills() +
                "\nRecord Kill Streak: " + player.getHighestKillStreak() +
                "\nRecord Rounds Completed Streak: " + player.getRoundStreak());

        nextButton.setText("Restart Round");
        nextButton.setOnAction(event -> restartRound());
        int gold = player.getGold();
        if (gold > 0) {
            player.loseGold((int) (gold / 10));
        }
    }

    private void restartRound() {
        actionList.clear();
        makeNextEnemyButton();
        setUpEnemies(enemies);
        enemiesDefeated = 0;
        setEnemy(enemies.get(0));
        restorePlayer();
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
