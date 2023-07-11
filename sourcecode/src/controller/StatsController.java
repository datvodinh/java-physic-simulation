package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class StatsController implements Initializable{
    @FXML
    TextField AccelerationText,VelocityText,PositionText,AngularAccText,AngularVelText,AngularPosText;

    public TextField getAccelerationText() {
        return AccelerationText;
    }

    public TextField getVelocityText() {
        return VelocityText;
    }

    public TextField getPositionText() {
        return PositionText;
    }

    public TextField getAngularAccText() {
        return AngularAccText;
    }

    public TextField getAngularVelText() {
        return AngularVelText;
    }

    public TextField getAngularPosText() {
        return AngularPosText;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}