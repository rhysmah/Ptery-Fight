package ca.bcit.comp2522.termproject.secretwonders;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controls the buttons in the Driver Class.
 * @author Olafson & Mahannah
 * @version December 2022
 */
public class DriverController {

    @FXML private Button startGameButton;
    @FXML private Button quitGameButton;

    @FXML
    private void onStartGameButtonClick() {
        GameEngine gameEngine = new GameEngine();
        Stage primaryScreen = new Stage();
        primaryScreen.setTitle("Bug Blaster");
        primaryScreen.setScene(gameEngine.getScene());
        primaryScreen.show();
    }

    @FXML
    private void onQuitGameButtonClick() {
        Stage stage = (Stage) quitGameButton.getScene().getWindow();
        stage.close();
    }
}
