package ca.bcit.comp2522.termproject.secretwonders;
/**
 * Player2 projectile.
 * @author Olafson and Mahannah
 * @version 2022.
 */
public class Player2Projectile extends Projectile {
    /**
     * default constructor.
     */
    public Player2Projectile() {
        super("smokeyFireball.gif", Constants.PROJECTILE_PLAYER_TWO_WIDTH,
                Constants.PROJECTILE_PLAYER_TWO_HEIGHT, Constants.PROJECTILE_PLAYER_DMG);
    }
    /**
     * constructs projectile at a certain location with angle, speed, and size.
     * @param originX starting X location.
     * @param originY starting Y location.
     * @param angle direction projectile is facing.
     */
    public Player2Projectile(final double originX, final double originY, final double angle) {
        super("smokeyFireball.gif", Constants.PROJECTILE_PLAYER_TWO_WIDTH,
                Constants.PROJECTILE_PLAYER_TWO_HEIGHT, Constants.PROJECTILE_PLAYER_DMG,
                originX - (Constants.PROJECTILE_PLAYER_TWO_WIDTH / 2),
                originY - (Constants.PROJECTILE_PLAYER_TWO_HEIGHT / 2),
                Constants.PROJECTILE_PLAYER_TWO_DELTA_X,
                Constants.PROJECTILE_PLAYER_TWO_DELTA_Y, angle);
    }
}
