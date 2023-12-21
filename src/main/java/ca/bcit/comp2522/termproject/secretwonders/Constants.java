package ca.bcit.comp2522.termproject.secretwonders;

/**
 * Game Constants Class.
 * @author Olafson and Mahannah
 * @version 2022
 */
public class Constants {
    /**
     * angles in a 2D plane.
     */
    //Game Settings
    public static final int PLAYER_ROTATION = 360;
    /**
     * Width of Screen.
     */
    public static final double SCREEN_WIDTH = 640;
    /**
     * Height of screen.
     */
    public static final double SCREEN_HEIGHT = 640;
    /**
     * Size of health bar.
     */
    public static final double HEALTH_BAR_SIZE = 4;
    /**
     * duration of game loop in milliseconds.
     */
    public static final double TICK_LENGTH = 10;
    /**
     * Damage from Player projectiles.
     */
    public static final int PROJECTILE_PLAYER_DMG = 20;

    //Player One - Bee -------------------------------------------------------------------------------------------------
    /**
     * player one width.
     */
    public static final double PLAYER_ONE_WIDTH = 70;
    /**
     * player height.
     */
    public static final double PLAYER_ONE_HEIGHT = 70;
    /**
     * player one movement factor.
     */
    public static final int PLAYER_ONE_MOVEMENT = 4;
    /**
     * player one health.
     */
    public static final int PLAYER_ONE_HEALTH = 100;

    //Player Two - Dragonfly -------------------------------------------------------------------------------------------
    /**
     * player two width.
     */
    public static final double PLAYER_TWO_WIDTH = 80;
    /**
     * player two height.
     */
    public static final double PLAYER_TWO_HEIGHT = 80;
    /**
     * player two movement factor.
     */
    public static final int PLAYER_TWO_MOVEMENT = 5;
    /**
     * player two health.
     */
    public static final int PLAYER_TWO_HEALTH = 100;

    //Player One - Bee Projectiles--------------------------------------------------------------------------------------
    /**
     * projectile one width.
     */
    public static final double PROJECTILE_PLAYER_ONE_WIDTH = 6;
    /**
     * projectile one height.
     */
    public static final double PROJECTILE_PLAYER_ONE_HEIGHT = 29;
    /**
     * projectile one X value movement speed. Is Zero because whichever way the projectile is spawned is 'up', so only
     * Y is relevant.
     */
    public static final double PROJECTILE_PLAYER_ONE_DELTA_X = 0;
    /**
     * projectile one Y value movement speed.
     */
    public static final double PROJECTILE_PLAYER_ONE_DELTA_Y = 10.0;

    //Player Two - Dragonfly Projectiles--------------------------------------------------------------------------------
    /**
     * projectile two width.
     */
    public static final double PROJECTILE_PLAYER_TWO_WIDTH = 42;
    /**
     * projectile two height.
     */
    public static final double PROJECTILE_PLAYER_TWO_HEIGHT = 100;
    /**
     * projectile two X value movement speed. Is Zero because whichever way the projectile is spawned is 'up', so only
     * Y is relevant.
     */
    public static final double PROJECTILE_PLAYER_TWO_DELTA_X = 0;
    /**
     * projectile one Y value movement speed.
     */
    public static final double PROJECTILE_PLAYER_TWO_DELTA_Y = 6.0;
}
