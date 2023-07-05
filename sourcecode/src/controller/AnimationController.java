package controller;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class AnimationController {
    public void setMovement(TranslateTransition transition, Node node, double rate, double screenWidth) {
        transition.setDuration((Duration.seconds(50)));
        transition.setByX(0);
        transition.setFromX(screenWidth);
        transition.setToX(0);
        transition.setNode(node);
        transition.setOnFinished(event -> {
            // Reset the position of surface when it slides off the screen
            transition.play();

        });
        transition.setInterpolator(Interpolator.LINEAR);
        transition.setRate(rate);
        transition.play();
    }

    public void setRotate(RotateTransition rotate, Node node, double rate) {

        rotate.setNode(node);
        rotate.setCycleCount(TranslateTransition.INDEFINITE);
        rotate.setDuration((Duration.millis(20000.0)));
        rotate.setByAngle(360.0);
        rotate.setAutoReverse(false);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setDelay(Duration.seconds(0));
        rotate.setRate(rate);
        rotate.play();
    }

    public void pause(TranslateTransition transition) {
        transition.pause();
    }

    public void play(TranslateTransition transition) {
        transition.play();
    }

    public void close() {
        System.exit(0);
    }
}
