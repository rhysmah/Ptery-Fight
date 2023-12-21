package ca.bcit.comp2522.termproject.secretwonders;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Represents a main menu screen.
 * @author Olafson & Mahannah
 * @version December 2022
 */
public class Driver extends Application {

    private static final String MENU_WINDOW_TITLE  = "Ptery Fight";
    private static final int    MENU_WINDOW_HEIGHT = 300;
    private static final int    MENU_WINDOW_WIDTH  = 400;

    /**
     * Creates a main menu screen.
     * @param stage a scene
     * @throws IOException if FXML file cannot be found.
     */
    @Override
    public void start(final Stage stage) throws IOException {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/main-menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), MENU_WINDOW_WIDTH, MENU_WINDOW_HEIGHT);
            stage.setTitle(MENU_WINDOW_TITLE);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ioe) {
            throw new IOException("Cannot find file");
        }
    }

    /**
     * Drives the game.
     * @param args arguments.
     */
    public static void main(final String[] args) {
        launch();
    }
}
