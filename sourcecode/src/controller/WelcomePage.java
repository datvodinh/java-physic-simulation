package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WelcomePage implements Initializable {

    @FXML
    private GridPane gridPane;


    public void start(ActionEvent event) throws IOException {
            // Load the new scene from FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainSimulation.fxml"));
        Parent root = loader.load();

        // Set the new scene on the primary stage
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Node child : gridPane.getChildren()) {
            TranslateTransition transition = new TranslateTransition(Duration.seconds(5), child);
            transition.setDuration((Duration.seconds(20)));
            transition.setByX(0);
            transition.setFromX(1280);
            transition.setToX(0);
            transition.setNode(child);
            transition.setCycleCount(Animation.INDEFINITE);
            transition.setAutoReverse(false);
            transition.play();
        }
    }
}