package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class CheckBoxController implements Initializable {
    @FXML
    private CheckBox ForceBox,SumForceBox,MassBox,VelocityBox,AccelerationBox,PositionBox,ValueBox;
    @FXML
    private TextField MassText,VelocityText,AccelerationText,PositionText,ValueText,SumForceText,ForceText;
    public CheckBox getForceBox() {
        return ForceBox;
    }
    public CheckBox getSumForceBox() {
        return SumForceBox;
    }
    public TextField getValueText() {
        return ValueText;
    }
    public TextField getSumForceText() {
        return SumForceText;
    }
    public TextField getForceText() {
        return ForceText;
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
    public TextField getMassText() {
        return MassText;
    }
    public TextField getVelocityText() {
        return VelocityText;
    }
    public TextField getAccelerationText() {
        return AccelerationText;
    }

    public TextField getPositionText() {
        return PositionText;
    }
    public void reset(){
        ForceText.setText(null);
        SumForceText.setText(null);
        ValueText.setText(null);
        MassText.setText(null);
        VelocityText.setText(null);
        AccelerationText.setText(null);
        PositionText.setText(null);
        ForceBox.setSelected(false);
        SumForceBox.setSelected(false);
        ValueBox.setSelected(false);
        MassBox.setSelected(false);
        VelocityBox.setSelected(false);
        AccelerationBox.setSelected(false);
        PositionBox.setSelected(false);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
