package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;

public class CheckBoxController implements Initializable {

    @FXML
    private CheckBox ForceBox, SumForceBox, MassBox, VelocityBox, AccelerationBox, PositionBox, ValueBox;

    // Getter methods for the checkboxes
    public CheckBox getForceBox() {
        return ForceBox;
    }

    public CheckBox getSumForceBox() {
        return SumForceBox;
    }

    public CheckBox getMassBox() {
        return MassBox;
    }

    public CheckBox getVelocityBox() {
        return VelocityBox;
    }

    public CheckBox getAccelerationBox() {
        return AccelerationBox;
    }

    public CheckBox getPositionBox() {
        return PositionBox;
    }

    public CheckBox getValueBox() {
        return ValueBox;
    }

    // Reset all checkboxes to unselected state
    public void reset() {
        ForceBox.setSelected(false);
        SumForceBox.setSelected(false);
        ValueBox.setSelected(false);
        MassBox.setSelected(false);
        VelocityBox.setSelected(true);
        AccelerationBox.setSelected(true);
        PositionBox.setSelected(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialization code for the controller (if any)
    }

}
