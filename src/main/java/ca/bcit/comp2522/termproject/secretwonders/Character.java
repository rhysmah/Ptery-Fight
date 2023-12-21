package ca.bcit.comp2522.termproject.secretwonders;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Character Class.
 * @author Olafson and Mahhanah
 * @version 2022.
 */
public abstract class Character extends Entity {
    /**
     * the current health of the character.
     * this is an integer property because it needs to implement observable in HealthMonitor
     */
    private final IntegerProperty healthProperty = new SimpleIntegerProperty(100);
    /**
     * Max health of the Character.
     */
    private int maxHealth;

    /**
     * Constructor.
     */
    public Character() { }

    /**
     * Constructs Character.
     * @param spriteName The String name for image.
     * @param width width of character.
     * @param height length of character.
     * @param maxHealth Health of character.
     */
    public Character(final String spriteName, final double width, final double height, final int maxHealth) {
        super(spriteName, width, height);
        this.maxHealth = maxHealth;
        healthProperty.set(maxHealth);
    }

    /**
     * gets max health.
     * @return int max health.
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * get current health.
     * @return simple Integer Property.
     */
    public int getHealth() {
        return healthProperty.get();
    }

    /**
     * decrements health of character.
     * @param health int amount of health.
     */
    public void subtractHealth(final int health) {
        healthProperty.set(healthProperty.get() - health);
    }

    /**
     * returns health property.
     * @return amount of health.
     */
    public ReadOnlyIntegerProperty healthPropertyUnmodifiable() {
        return healthProperty;
    }
}
