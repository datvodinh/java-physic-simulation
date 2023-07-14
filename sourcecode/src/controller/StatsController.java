package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class StatsController implements Initializable {
    @FXML
    TextField AccelerationText, VelocityText, PositionText, AngularAccText, AngularVelText, AngularPosText;

    /**
     * Getter for the AccelerationText field.
     *
     * @return The TextField object representing acceleration.
     */
    public TextField getAccelerationText() {
        return AccelerationText;
    }

    /**
     * Getter for the VelocityText field.
     *
     * @return The TextField object representing velocity.
     */
    public TextField getVelocityText() {
        return VelocityText;
    }

    /**
     * Getter for the PositionText field.
     *
     * @return The TextField object representing position.
     */
    public TextField getPositionText() {
        return PositionText;
    }

    /**
     * Getter for the AngularAccText field.
     *
     * @return The TextField object representing angular acceleration.
     */
    public TextField getAngularAccText() {
        return AngularAccText;
    }

    /**
     * Getter for the AngularVelText field.
     *
     * @return The TextField object representing angular velocity.
     */
    public TextField getAngularVelText() {
        return AngularVelText;
    }

    /**
     * Getter for the AngularPosText field.
     *
     * @return The TextField object representing angular position.
     */
    public TextField getAngularPosText() {
        return AngularPosText;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // This method is called when the controller is initialized.
        // Any initialization tasks can be performed here.
    }

}
