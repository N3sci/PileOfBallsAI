package application.pileofballs.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;
import java.util.Objects;

public class MenuController {

    @FXML
    private Button playButton;

    @FXML
    private Button exitButton;

    @FXML
    void onPlayClicked(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/application/pileofballs/game.fxml")));
            Scene scene = new Scene(root);
            Stage stage = (Stage) playButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Pile of Balls - Gioco");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onExitClicked(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}