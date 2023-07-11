package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class WelcomePage implements Initializable {

    @FXML
    private GridPane gridPane;

    @FXML
    private ImageView surface;

    @FXML
    private ImageView cylinder;

    AnimationController animationController = new AnimationController();
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
    
    public void animation(TranslateTransition transition, double rate) {
        transition.setDuration((Duration.seconds(20)));
        transition.setByX(0);
        transition.setFromX(1280);
        transition.setToX(0);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.setAutoReverse(false);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.setRate(rate);
        transition.play();
    }

    public void rotation(RotateTransition rotate, double rate) {
        rotate.setCycleCount(TranslateTransition.INDEFINITE);
        rotate.setByAngle(360.0);
        rotate.setCycleCount(Animation.INDEFINITE);
        rotate.setAutoReverse(false);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setDelay(Duration.seconds(0));
        rotate.setRate(rate);
        rotate.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Node child : gridPane.getChildren()) {
            TranslateTransition cloudTransition = new TranslateTransition(Duration.seconds(5), child);
            animation(cloudTransition, 1);
        }
        TranslateTransition surfaceTransition = new TranslateTransition(Duration.seconds(5), surface);
        animation(surfaceTransition, 2);
        RotateTransition rotate = new RotateTransition(Duration.seconds(5),cylinder);
        rotation(rotate, 2);
    }
}