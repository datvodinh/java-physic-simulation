package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class CheckBoxController implements Initializable {
    @FXML
    private CheckBox ForceBox,SumForceBox,MassBox,VelocityBox,AccelerationBox,PositionBox,ValueBox;
    @FXML

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
    public void reset(){
        
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
