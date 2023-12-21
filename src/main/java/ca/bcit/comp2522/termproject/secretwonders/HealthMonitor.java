package ca.bcit.comp2522.termproject.secretwonders;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * HealthMonitor Class.
 * @author Olafson and Mahannah
 * @version 2022.
 */
public class HealthMonitor extends Rectangle {
    /**
     * Constructs HealthMonitor for passed Character.
     * @param character a Character.
     * Creates a rectangle that gets smaller when characters health goes down.
     */
    public HealthMonitor(final Character character) {
        super();
        setWidth(character.width);
        setHeight(2);
        setFill(Color.WHITE);
        xProperty().bind(character.xProperty());
        yProperty().bind(character.yProperty().subtract(2));
        widthProperty().bind(character.healthPropertyUnmodifiable().multiply(character.width).divide(character.getMaxHealth()));
    }
}
