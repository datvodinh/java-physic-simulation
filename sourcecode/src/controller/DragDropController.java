package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import model.object.Cube;
import model.object.Cylinder;
import model.object.MainObject;

public class DragDropController {
    @FXML
    private GridPane grid;

    @FXML
    private ImageView cube;

    @FXML
    private ImageView cylinder;

    @FXML
    private ImageView myObj;

    private double xOffset;
    private double yOffset;

    private double initialTranslateX;
    private double initialTranslateY;



    @FXML
    private void initializeCube() {
        cube.setOnMousePressed(mouseEvent -> {
            xOffset = mouseEvent.getSceneX() - cube.getTranslateX();
            yOffset = mouseEvent.getSceneY() - cube.getTranslateY();
            
            initialTranslateX = cube.getX();
            initialTranslateY = cube.getY();

        });
        cube.setOnMouseDragged(mouseEvent -> {
            cube.setTranslateX(mouseEvent.getSceneX() - xOffset);
            cube.setTranslateY(mouseEvent.getSceneY() - yOffset);
        });
        cube.setOnMouseReleased(mouseEvent -> {
            cube.setTranslateX(initialTranslateX);
            cube.setTranslateY(initialTranslateY);

            cubeInput();
            
            
        });
    }

    @FXML
    private void initializeCylinder() {
        cylinder.setOnMousePressed(mouseEvent -> {
            xOffset = mouseEvent.getSceneX() - cylinder.getTranslateX();
            yOffset = mouseEvent.getSceneY() - cylinder.getTranslateY();
            initialTranslateX = cylinder.getX();
            initialTranslateY = cylinder.getY();

        });
        cylinder.setOnMouseDragged(mouseEvent -> {
            cylinder.setTranslateX(mouseEvent.getSceneX() - xOffset);
            cylinder.setTranslateY(mouseEvent.getSceneY() - yOffset);
        });
        cylinder.setOnMouseReleased(mouseEvent -> {
            cylinder.setTranslateX(initialTranslateX);
            cylinder.setTranslateY(initialTranslateY);

            cylinderInput();
            
            
        });
    }

    private void cubeInput(){
        Dialog<Pair<String, String>> dialog = new Dialog<>();

        dialog.initStyle(StageStyle.UNDECORATED);

        dialog.setTitle("Input Property for Cube");
        dialog.setHeaderText("Cube property");

        ButtonType OKEType = new ButtonType("OK", ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().add(OKEType);

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField cubeMass = new TextField();
        cubeMass.setPromptText("Input mass for cube");


        TextField cubeSide = new TextField();
            
        cubeSide.setPromptText("Input side-length for cube");

        // Node OKEButton = dialog.getDialogPane().lookupButton(OKEType);

        Button OKEButton = new Button("OK");
        // OKEButton.setDisable(true);

        grid.add(new Label("Cube Mass: (> 0, default " + MainObject.MASS_DEFAULT + ")"), 0, 0);
		grid.add(cubeMass, 1, 0);
		grid.add(new Label("Cube Side: (>= " + Cube.MIN_SIZE + " and <=" + Cube.MAX_SIZE + ", default "
				+ Cube.MAX_SIZE * 0.3 + ")"), 0, 1);
		grid.add(cubeSide, 1, 1);
        grid.add(OKEButton,2,2);

        int[] condition1 = { 0 };
		int[] condition2 = { 0 };

		cubeMass.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!cubeMass.getText().isEmpty()) {
				condition1[0] = 1;
			} else {
				condition1[0] = 0;
			}
			OKEButton.setDisable(condition1[0] == 0 || condition2[0] == 0);
		});

		cubeSide.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!cubeSide.getText().isEmpty()) {
				condition2[0] = 1;
			} else {
				condition2[0] = 0;
			}
			OKEButton.setDisable(condition1[0] == 0 || condition2[0] == 0);
		});


        OKEButton.setOnAction(event ->{
            try {
                myObj.setImage(new Image("img/cube.png"));
                grid.setVisible(false);

            } catch (Exception e) {
                // TODO Auto-generated catch block
            }
        });

    }

    private void cylinderInput(){
        Dialog<Pair<String, String>> dialog = new Dialog<>();

        dialog.initStyle(StageStyle.UNDECORATED);

        dialog.setTitle("Input Property for cylinder");
        dialog.setHeaderText("Cylinder property");

        ButtonType OKEType = new ButtonType("OK", ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().add(OKEType);

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField cubeMass = new TextField();
        cubeMass.setPromptText("Input mass for cube");


        TextField cubeSide = new TextField();
            
        cubeSide.setPromptText("Input side-length for cube");

        // Node OKEButton = dialog.getDialogPane().lookupButton(OKEType);
        Button OKEButton = new Button("OK");

        OKEButton.setDisable(true);

        grid.add(new Label("Cylinder Mass: (> 0, default " + MainObject.MASS_DEFAULT + ")"), 0, 0);
		grid.add(cubeMass, 1, 0);
		grid.add(new Label("Cylinde Radius: (>= " + Cylinder.MIN_RADIUS + " and <=" + Cylinder.MAX_RADIUS + ", default "
				+ Cylinder.MAX_RADIUS * 0.3 + ")"), 0, 1);
		grid.add(cubeSide, 1, 1);
        grid.add(OKEButton,2,2);

        int[] condition1 = { 0 };
		int[] condition2 = { 0 };

		cubeMass.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!cubeMass.getText().isEmpty()) {
				condition1[0] = 1;
			} else {
				condition1[0] = 0;
			}
			OKEButton.setDisable(condition1[0] == 0 || condition2[0] == 0);
		});

		cubeSide.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!cubeSide.getText().isEmpty()) {
				condition2[0] = 1;
			} else {
				condition2[0] = 0;
			}
			OKEButton.setDisable(condition1[0] == 0 || condition2[0] == 0);
		});


        OKEButton.setOnAction(event ->{
            try {
                myObj.setImage(new Image("img/cylinder.png"));
                grid.setVisible(false);

            } catch (Exception e) {
                // TODO Auto-generated catch block
            }
        });


    }

    
}
