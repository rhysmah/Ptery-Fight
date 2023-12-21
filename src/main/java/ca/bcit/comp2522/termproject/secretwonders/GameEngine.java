package ca.bcit.comp2522.termproject.secretwonders;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * GameEngine Class.
 *
 * @author Olafson and Mahannah
 * @version 2022
 */
public class GameEngine {

    /**
     * Keyboard control directions.
     */
    public enum Direction { UP, DOWN, LEFT, RIGHT }

    private static final int ENEMY_WIDTH_IN_PIXELS = 50;
    private static final int ENEMY_HEIGHT_IN_PIXELS = 50;
    private static final int ENEMY_CREATION_TIMER_IN_MS = 500;
    private static final int PLAYER_PROJECTILE_COOLDOWN_IN_MS = 500;
    private static final int ONE_SECOND_REPRESENTED_AS_MS = 1000;

    /**
     * Arraylist of entities to be removed from play at the end of the current game loop.
     */
    ArrayList<Entity> entitiesToAdd = new ArrayList<>();

    /**
     * Arraylist of projectiles to be removed from play at the end of the current game loop.
     */
    ArrayList<Entity> entitiesToRemove = new ArrayList<>();

    /*
     * Visible contents of Objects for the screen.
     */
    private GamePane pane;

    /*
     * the scene for the game.
     */
    private final Scene scene;

    /*
     * the loop in which all game events happen.
     */
    private Timeline gameLoop;

    /*
     * Player one is the bee character who is controlled with the arrow keys and CTRL button.
     */
    private Player1 player1 = new Player1();

    /*
     * Player two is the dragonfly character who is controlled with the WASD keys and SPACE bar.
     */
    private Player2 player2 = new Player2();

    /*
    /*
     *Arraylist of Entities, not including projectiles. This keeps track of Entities are currently part of the game.
     */
    private final ArrayList<Entity> entities = new ArrayList<>();

    /*
     * Arraylist of Projectiles, keeps track of what projectiles are currently part of the game.
     */
    private final ArrayList<Projectile> projectiles = new ArrayList<>();

    /*
     * Arraylist of Enemies, keeps track of what projectiles are currently part of the game.
     */
    private final ArrayList<Enemy> enemies = new ArrayList<>();

    /*
     * The last timestamp of player 2 attack (fireball).
     */
    private long lastPlayerTwoShot = System.currentTimeMillis();
    private long lastPlayerOneHit = System.currentTimeMillis();

    private final long gameStartTime = System.currentTimeMillis();
    private long lastEnemyAddedToGame = System.currentTimeMillis();

    private int playerOneScore = 0;
    private int playerTwoScore = 0;

    private final Media media;

    {
        try {
            media = new Media(getClass().getResource("/mainTheme.mp3").toURI().toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private MediaPlayer themeSong;
    /**
     * Constructor for GameEngine.
     */
    public GameEngine() {
        pane = new GamePane();
        scene = new Scene(pane, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        setupScene(pane);
        setupKeybindings();
        try {
            music();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        // Add players to the game.
        add(player1);
        add(player2);

        // Main Game Loop
        setupTimelines();
    }

    /**
     * sets up the attribute of pane to the passed pane, sets the root of the scene to watch the pane.
     * @param gamePane a GamePane pane.
     */
    private void setupScene(final GamePane gamePane) {
        this.pane = gamePane;
        gamePane.setEngine(this);
        scene.setRoot(gamePane);
    }

    /**
     * watches key press, and if it is a valid move, then either starts moving a character or creates projectiles.
     * Keeps going as long as the keypress is held down.
     */
    private void setupKeybindings() {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    player1.startMovement(Direction.LEFT); break;
                case A:
                    player2.startMovement(Direction.LEFT); break;
                case RIGHT:
                    player1.startMovement(Direction.RIGHT); break;
                case D:
                    player2.startMovement(Direction.RIGHT); break;
                case UP:
                    player1.startMovement(Direction.UP); break;
                case W:
                    player2.startMovement(Direction.UP); break;
                case DOWN:
                    player1.startMovement(Direction.DOWN); break;
                case S:
                    player2.startMovement(Direction.DOWN); break;
                case CONTROL:
                    player1.fireProjectile(); break;
                case SPACE:
                    if (System.currentTimeMillis() - lastPlayerTwoShot >= PLAYER_PROJECTILE_COOLDOWN_IN_MS) {
                        player2.fireProjectile();
                        System.out.println(System.currentTimeMillis());
                        lastPlayerTwoShot = System.currentTimeMillis();
                        break;
                    }
            }
        });
        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT -> player1.stopMovement(Direction.LEFT);
                case A -> player2.stopMovement(Direction.LEFT);
                case RIGHT -> player1.stopMovement(Direction.RIGHT);
                case D -> player2.stopMovement(Direction.RIGHT);
                case UP -> player1.stopMovement(Direction.UP);
                case W -> player2.stopMovement(Direction.UP);
                case DOWN -> player1.stopMovement(Direction.DOWN);
                case S -> player2.stopMovement(Direction.DOWN);
            }
        });
    }
    /**
     * adds an entity to the game pane.
     * If the entity is a player, attaches a health bar to the entity.
     * @param entity an object that extends Entity.
     */
    private void add(final Entity entity) {
        entities.add(entity);
        if (entity instanceof Player1) {
            player1 = (Player1) entity;
            pane.bindHealthOne(player1.healthPropertyUnmodifiable());
            pane.getChildren().add(entity);
        }
        if (entity instanceof Player2) {
            player2 = (Player2) entity;
            pane.bindHealthTwo(player2.healthPropertyUnmodifiable());
            pane.getChildren().add(entity);
        }
        if (entity instanceof Projectile) {
            projectiles.add((Projectile) entity);
            pane.getChildren().add(entity);
        }
    }
    /**
     * removes the entity from the ArrayLists.
     * @param entity an Entity of entities or projectile.
     */
    private void remove(final Entity entity) {
        entities.remove(entity);
        projectiles.remove(entity);
        enemies.remove(entity);
        pane.getChildren().remove(entity);
    }
    /**
     * Adds Entity to queue to add on next game loop.
     * @param entity to be added.
     */
    public void queueAddition(final Entity entity) {
        entitiesToAdd.add(entity);
    }
    /**
     * Adds entity to be removed on next game loop.
     * @param entity to be removed.
     */
    public void queueRemoval(final Entity entity) {
        entitiesToRemove.add(entity);
    }
    /**
     * returns this scene.
     * @return Scene.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * sets background music.
     * @throws URISyntaxException if media not found.
     */
    public void music() throws URISyntaxException {
        themeSong = new MediaPlayer(media);
        themeSong.setAutoPlay(true);
        themeSong.setCycleCount(MediaPlayer.INDEFINITE);
    }

    ////////////////////
    // MAIN GAME LOOP //
    ////////////////////
    /**
     * Sets up Timelines, and loops through gameLoop Timeline, watching for events. Handles game logic.
     */
    private void setupTimelines() {

        // Game Start //
        gameLoop = new Timeline(new KeyFrame(Duration.millis(Constants.TICK_LENGTH), e -> {

            // Check if both players are dead;
            // if so, show score screen and stop game.
            if (player1.getHealth() <= 0 && player2.getHealth() <= 0) {
                pane.showScoreCard();
                gameLoop.stop();
            }

            // Every second, create and add enemy to the game scene.
            if (System.currentTimeMillis() - lastEnemyAddedToGame >= ENEMY_CREATION_TIMER_IN_MS) {
                String enemySprite = "fly.gif";
                Enemy enemy = null;
                try {
                    enemy = new Enemy(enemySprite, ENEMY_WIDTH_IN_PIXELS, ENEMY_HEIGHT_IN_PIXELS);
                } catch (URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }

                enemies.add(enemy);
                entities.add(enemy);

                pane.getChildren().add(enemy);
                enemy.makeEnemyAppear();
                lastEnemyAddedToGame = System.currentTimeMillis();
            }
            for (Entity entity : entities) {

                // Move all entities.
                entity.doMovement();

                // Check if player 1 is dead; if so, set death sprite and stop player from moving.
                // Remove hit box, so other entities pass through player.
                if (entity instanceof Player1 && ((Player1) entity).getHealth() <= 0) {
                    Image deathSprite = new Image("dead_bee.png");

                    entity.setSprite(deathSprite);
                    entity.setHeightToZero();
                    entity.setWidthToZero();

                    ((Player1) entity).setIsAlive(false);
                }

                // Check if Player 2 is dead; if so, set death sprite and stop player from moving.
                // Remove hit box, so other entities pass through player.
                if (entity instanceof Player2 && ((Player2) entity).getHealth() <= 0) {
                    Image deathSprite = new Image("dead_dragonfly.png");

                    entity.setSprite(deathSprite);
                    entity.setHeightToZero();
                    entity.setWidthToZero();

                    ((Player2) entity).setIsAlive(false);
                }
            }
            // Check if player projectiles hit player or enemy
            for (Projectile projectile : projectiles) {
                if (projectile instanceof Player1Projectile) {

                    // Check if Player 1 projectile hits Player 2;
                    // If so, remove health from P2, increase P1 score, and remove projectile.
                    if (projectile.intersects(player2.getX(), player2.getY(),
                            player2.getWidth(), player2.getHeight())) {

                        player2.subtractHealth(projectile.getDamage());
                        playerOneScore++;
                        pane.playerOneScoreLabel.setText("Honeybee Score: " + playerOneScore);
                        queueRemoval(projectile);
                    }
                }
                if (projectile instanceof Player2Projectile) {

                    // Check if Player 1 projectile hits Player 2;
                    // If so, remove health from P2, increase P1 score, and remove projectile.
                    if (projectile.intersects(player1.getX(), player1.getY(),
                            player1.getWidth(), player1.getHeight())
                            && System.currentTimeMillis() - lastPlayerOneHit >= PLAYER_PROJECTILE_COOLDOWN_IN_MS)  {

                        lastPlayerOneHit = System.currentTimeMillis();
                        player1.subtractHealth(projectile.getDamage());
                        playerTwoScore++;
                        pane.playerTwoScoreLabel.setText("Dragonfly Score: " + playerTwoScore);
                    }
                }
                // Remove projectile if it leaves visible screen.
                if (projectile.getY() < 0 || projectile.getY() > Constants.SCREEN_HEIGHT) {
                    queueRemoval(projectile);
                }
            }

            for (Enemy enemyUnit : enemies) {

                // Checks if enemy hits player 1; if so, character is damaged and enemy dies.
                if (enemyUnit.intersects(player1.getX(), player1.getY(), player1.getWidth(), player1.getHeight())
                        &&
                        enemyUnit.getHasNotHitPlayer()) {

                    player1.subtractHealth(enemyUnit.getEnemyAttackDamage());
                    enemyUnit.setHasHitPlayer();
                    enemyUnit.enemyIsDead();

                    enemyUnit.setHeightToZero();
                    enemyUnit.setWidthToZero();

                    Image deadEnemySprite = new Image("dead_fly.png");
                    enemyUnit.setImage(deadEnemySprite);

                    enemyUnit.fadeOutEnemyWhenDead();
                    enemyUnit.makeEnemyDeathSound();
                }
                // Checks if enemy hits player 2; if so, character is damaged and enemy dies.
                if (enemyUnit.intersects(player2.getX(), player2.getY(), player2.getWidth(), player2.getHeight())
                        &&
                        enemyUnit.getHasNotHitPlayer()) {

                    player2.subtractHealth(enemyUnit.getEnemyAttackDamage());
                    enemyUnit.setHasHitPlayer();
                    enemyUnit.enemyIsDead();

                    enemyUnit.setHeightToZero();
                    enemyUnit.setWidthToZero();

                    Image deadEnemySprite = new Image("dead_fly.png");
                    enemyUnit.setImage(deadEnemySprite);

                    enemyUnit.fadeOutEnemyWhenDead();
                    enemyUnit.makeEnemyDeathSound();
                }
                // Check if enemy hit by player 2 projectile
                for (Projectile projectile : projectiles) {
                    if (projectile instanceof Player1Projectile) {
                        if (projectile.intersects(enemyUnit.getX(), enemyUnit.getY(),
                                enemyUnit.getWidth(), enemyUnit.getHeight())
                                &&
                                enemyUnit.getHasNotHitPlayer()) {

                            enemyUnit.setHasHitPlayer();
                            enemyUnit.enemyIsDead();

                            enemyUnit.setHeightToZero();
                            enemyUnit.setWidthToZero();

                            Image deadEnemySprite = new Image("dead_fly.png");
                            enemyUnit.setImage(deadEnemySprite);

                            queueRemoval(projectile);

                            enemyUnit.fadeOutEnemyWhenDead();
                            enemyUnit.makeEnemyDeathSound();

                            playerOneScore++;
                            pane.playerOneScoreLabel.setText("Honeybee Score: " + playerOneScore);
                        }
                    }
                    if (projectile instanceof Player2Projectile) {
                        if (projectile.intersects(enemyUnit.getX(), enemyUnit.getY(),
                                enemyUnit.getWidth(), enemyUnit.getHeight())
                                &&
                                enemyUnit.getHasNotHitPlayer()) {

                            enemyUnit.setHasHitPlayer();
                            enemyUnit.enemyIsDead();

                            enemyUnit.setHeightToZero();
                            enemyUnit.setWidthToZero();

                            Image deadEnemySprite = new Image("dead_fly.png");
                            enemyUnit.setImage(deadEnemySprite);

                            enemyUnit.fadeOutEnemyWhenDead();
                            enemyUnit.makeEnemyDeathSound();

                            queueRemoval(projectile);

                            playerTwoScore++;
                            pane.playerTwoScoreLabel.setText("DragonFly Score: " + playerTwoScore);
                        }
                    }
                }
                // Checks if enemy is offscreen; if so, removes enemy.
                if (enemyUnit.getCenterX() < 0 || enemyUnit.getCenterX() > Constants.SCREEN_WIDTH) {
                    queueRemoval(enemyUnit);
                }
                if (enemyUnit.getCenterY() < 0 || enemyUnit.getCenterY() > Constants.SCREEN_HEIGHT) {
                    queueRemoval(enemyUnit);
                }
            }
            //removes entities that have been flagged for removal earlier.
            for (Entity entity : entitiesToRemove) {
                remove(entity);
            }
            entitiesToRemove.clear();
            //adds entities that have been queued to add.
            for (Entity entity : entitiesToAdd) {
                add(entity);
            }
            entitiesToAdd.clear();
            if (player1.getHealth() > 0 || player2.getHealth() > 0) {
                pane.survivalTimeLabel.setText(String.valueOf(
                        (System.currentTimeMillis() - gameStartTime) / ONE_SECOND_REPRESENTED_AS_MS));
            }
        }));

        // Game runs until both players die.
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }
}
