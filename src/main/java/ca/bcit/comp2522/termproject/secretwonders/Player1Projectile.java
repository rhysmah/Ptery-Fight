package ca.bcit.comp2522.termproject.secretwonders;

/**
 * Player1 projectile.
 * @author Olafson and Mahannah
 * @version 2022.
 */
public class Player1Projectile extends Projectile {
    /**
     * default constructor.
     */
    public Player1Projectile() {
        super("short_spear.png", Constants.PROJECTILE_PLAYER_ONE_WIDTH,
                Constants.PROJECTILE_PLAYER_ONE_HEIGHT, Constants.PROJECTILE_PLAYER_DMG);
    }

    /**
     * constructs projectile at a certain location with angle, speed, and size.
     * @param originX starting X location.
     * @param originY starting Y location.
     * @param angle direction projectile is facing.
     */
    public Player1Projectile(final double originX, final double originY, final double angle) {
        super("short_spear.png", Constants.PROJECTILE_PLAYER_ONE_WIDTH,
                Constants.PROJECTILE_PLAYER_ONE_HEIGHT,
                Constants.PROJECTILE_PLAYER_DMG,
                originX - (Constants.PROJECTILE_PLAYER_ONE_WIDTH / 2),
                originY - (Constants.PROJECTILE_PLAYER_ONE_HEIGHT / 2),
                Constants.PROJECTILE_PLAYER_ONE_DELTA_X,
                Constants.PROJECTILE_PLAYER_ONE_DELTA_Y, angle);
    }
}
