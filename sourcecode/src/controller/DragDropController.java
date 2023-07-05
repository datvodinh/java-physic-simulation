package controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class DragDropController {
    @FXML
    private GridPane gridPane;

    @FXML
    private ImageView cube;

    private double xOffset;
    private double yOffset;

    @FXML
    private void initialize() {
        cube.setOnMousePressed(this::handleMousePressed);
        cube.setOnMouseDragged(this::handleMouseDragged);
    }

    private void handleMousePressed(MouseEvent event) {
        xOffset = event.getSceneX() - cube.getLayoutX();
        yOffset = event.getSceneY() - cube.getLayoutY();
    }

    private void handleMouseDragged(MouseEvent event) {
        cube.setLayoutX(event.getSceneX() - xOffset);
        cube.setLayoutY(event.getSceneY() - yOffset);
    }
}
