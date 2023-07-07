package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import model.Surface.Surface;

public class MainSimulationController implements Initializable {
    
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Slider KSlider;
    @FXML
    private Slider SSlider;

    private Surface surface = new Surface();
    private AnimationController animation;
    private SurfaceController surfaceController;
    private StatisticController statisticController;
    private InputController inputController;
    private DragDropController dragDropController = new DragDropController();
    // private ForceController forceController = new ForceController();
    private StackPane statisticPane;
    private Pane forcePane;

    @FXML
    private ImageView cube;
    @FXML
    private ImageView cylinder;
    @FXML
    private GridPane grid;

    @FXML
    private ImageView mainObject;

    @FXML
    private DialogPane dialog;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loadStatistic();
        loadSurfacePanel();
        dragDropController.initializeCube(cube,mainObject);
        dragDropController.initializeCylinder(cylinder, mainObject);



    }

    public void loadStatistic() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/Statistic.fxml"));

            statisticPane = (StackPane) loader.load();

            AnchorPane.setTopAnchor(statisticPane, 50.0);
            AnchorPane.setRightAnchor(statisticPane, 50.0);

            mainPane.getChildren().add(statisticPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSurfacePanel() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/SurfacePanel.fxml"));

            forcePane = (Pane) loader.load();

            AnchorPane.setBottomAnchor(forcePane, 25.0);
            AnchorPane.setRightAnchor(forcePane, 100.0);

            mainPane.getChildren().add(forcePane);
            this.surfaceController = loader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public void reset() {
        System.out.println("ok");
        loadStatistic();
        loadSurfacePanel();
        dragDropController.initializeCube(cube,mainObject);
        dragDropController.initializeCylinder(cylinder,mainObject);
    }
    
}
