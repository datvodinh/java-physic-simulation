package controller;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class AnimationController {

    // Create a TranslateTransition for linear movement
    public TranslateTransition setTransition(Node node, double duration, double x_from, double x_to) {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(duration));
        transition.setByX(0);
        transition.setFromX(x_from);
        transition.setToX(x_to);
        transition.setNode(node);
        transition.setInterpolator(Interpolator.LINEAR);
        return transition;
    }

    // Set the movement animation with the provided TranslateTransition
    public void setMovement(TranslateTransition transition, Node node, double rate, double screenWidth) {
        transition.setDuration(Duration.seconds(50));
        transition.setByX(0);
        transition.setFromX(screenWidth);
        transition.setToX(0);
        transition.setNode(node);
        transition.setOnFinished(event -> {
            // Reset the position of the surface when it slides off the screen
            transition.play();
        });
        transition.setInterpolator(Interpolator.LINEAR);
        transition.setRate(rate);
        transition.play();
    }

    // Set the rotation animation with the provided RotateTransition
    public void setRotate(RotateTransition rotate, Node node, double rate) {
        rotate.setNode(node);
        rotate.setCycleCount(TranslateTransition.INDEFINITE);
        rotate.setDuration(Duration.millis(20000.0));
        rotate.setByAngle(360.0);
        rotate.setOnFinished(event -> {
            // Reset the position of the surface when it completes a full rotation
            rotate.play();
        });
        rotate.setAutoReverse(false);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setDelay(Duration.seconds(0));
        rotate.setRate(rate);
        rotate.play();
    }
}
