package controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import model.object.Cube;
import model.object.Cylinder;
import model.object.MainObject;

public class DragDropController {

    private double xOffset;
    private double yOffset;

    private double initialTranslateX;
    private double initialTranslateY;

    double init_distance;
    double end_distance;

    boolean is_cube = false;
    boolean is_cylinder = false;

    double mass;
    double size;

    Cube MainCube;
    Cylinder MainCylinder;


    @FXML
    void initializeObject(ImageView cube, ImageView cylinder, ImageView myObj, ImageView surface, Runnable onObjectInitialized) {
        this.initializeCube(cube,cylinder,myObj,surface,onObjectInitialized);
        this.initializeCylinder(cube, cylinder, myObj, surface,onObjectInitialized);
    }



    @FXML void initializeCube(ImageView cube, ImageView cylinder, ImageView myObj, ImageView surface, Runnable onObjectInitialized) {
        cube.setOnMousePressed(mouseEvent -> {
            xOffset = mouseEvent.getSceneX() - cube.getTranslateX();
            yOffset = mouseEvent.getSceneY() - cube.getTranslateY();
            
            initialTranslateX = cube.getX();
            initialTranslateY = cube.getY();

        });
        cube.setOnMouseDragged(mouseEvent -> {
            cube.setScaleX(1.5);
            cube.setScaleY(1.5);
            cube.setTranslateX(mouseEvent.getSceneX() - xOffset);
            cube.setTranslateY(mouseEvent.getSceneY() - yOffset);
        });
        cube.setOnMouseReleased(mouseEvent -> {
            cube.setScaleX(1);
            cube.setScaleY(1);
            cube.setTranslateX(initialTranslateX);
            cube.setTranslateY(initialTranslateY);
            cube.setVisible(false);
            cubeInput(cube,cylinder, myObj,surface,onObjectInitialized);
            
            
            
        });

        
    }

    
    @FXML void initializeCylinder(ImageView cube, ImageView cylinder, ImageView myObj, ImageView surface, Runnable onObjectInitialized) {
        cylinder.setOnMousePressed(mouseEvent -> {
            xOffset = mouseEvent.getSceneX() - cylinder.getTranslateX();
            yOffset = mouseEvent.getSceneY() - cylinder.getTranslateY();
            initialTranslateX = cylinder.getX();
            initialTranslateY = cylinder.getY();
        });
        cylinder.setOnMouseDragged(mouseEvent -> {
            cylinder.setScaleX(1.5);
            cylinder.setScaleY(1.5);
            cylinder.setTranslateX(mouseEvent.getSceneX() - xOffset);
            cylinder.setTranslateY(mouseEvent.getSceneY() - yOffset);
        });
        cylinder.setOnMouseReleased(mouseEvent -> {
            cylinder.setScaleX(1);
            cylinder.setScaleY(1);
            cylinder.setVisible(false);
            cylinder.setTranslateX(initialTranslateX);
            cylinder.setTranslateY(initialTranslateY);
            cylinderInput(cube, cylinder, myObj,surface, onObjectInitialized);

        });

        
    }

    private void cubeInput(ImageView cube, ImageView cylinder, ImageView myObj, ImageView surface, Runnable onObjectInitialized) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();

        dialog.initStyle(StageStyle.DECORATED);

        dialog.setTitle("Input Property for Cube");
        dialog.setHeaderText("Cube property");

        ButtonType OKButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(OKButtonType);

        GridPane dialogGrid = new GridPane();
        dialogGrid.setHgap(10);
        dialogGrid.setVgap(10);
        dialogGrid.setPadding(new Insets(20, 150, 10, 10));

        TextField cubeMass = new TextField();
        cubeMass.setPromptText("Input mass for cube");

        TextField cubeSide = new TextField();
        cubeSide.setPromptText("Input side-length for cube");

        Button OKButton = (Button) dialog.getDialogPane().lookupButton(OKButtonType);
        OKButton.setDisable(true);

        dialogGrid.add(new Label("Cube Mass: (> 0, default " + MainObject.MASS_DEFAULT + ")"), 0, 0);
        dialogGrid.add(cubeMass, 1, 0);
        dialogGrid.add(new Label("Cube Side: (>= " + Cube.MIN_SIZE + " and <=" + Cube.MAX_SIZE + ", default "
                + Cube.MAX_SIZE * 0.5 + ")"), 0, 1);
        dialogGrid.add(cubeSide, 1, 1);
        dialogGrid.add(OKButton, 2, 2);

        BooleanProperty condition1 = new SimpleBooleanProperty(false);
        BooleanProperty condition2 = new SimpleBooleanProperty(false);

        cubeMass.textProperty().addListener((observable, oldValue, newValue) -> {
            condition1.set(!cubeMass.getText().isEmpty());
            OKButton.setDisable(!(condition1.get() && condition2.get()));
        });

        cubeSide.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double value = Double.parseDouble(cubeSide.getText());
                condition2.set(!cubeSide.getText().isEmpty() && value >= Cube.MIN_SIZE && value <= Cube.MAX_SIZE);
                OKButton.setDisable(!(condition1.get() && condition2.get()));
            } catch (NumberFormatException e) {
                condition2.set(false);
                OKButton.setDisable(true);
            }
        });

        OKButton.setOnAction(event -> {
            try {
                mass = Double.parseDouble(cubeMass.getText());
                size = Double.parseDouble(cubeSide.getText());
                Double scale = size / 0.5;

                myObj.setImage(new Image("img/cube.png"));
                myObj.setScaleX(scale);
                myObj.setScaleY(scale);

                cube.setVisible(false);
                cylinder.setVisible(true);

                this.is_cube = true;
                this.is_cylinder = false;
                this.MainCube = new Cube(mass, size);

                myObj.setLayoutY(300 - (scale - 1) * 100 / 2);

                onObjectInitialized.run();
            } catch (Exception e) {
                // Handle the exception accordingly (e.g., log or display an error message)
            }
        });

        dialogGrid.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));

        dialog.setOnCloseRequest((EventHandler<DialogEvent>) new EventHandler<DialogEvent>() {
            @Override
            public void handle(DialogEvent event) {
                if (!is_cube){cube.setVisible(true);}
            }
        });

        dialog.getDialogPane().setContent(dialogGrid);
        dialog.showAndWait();
    }

    


    private void cylinderInput(ImageView cube, ImageView cylinder, ImageView myObj, ImageView surface, Runnable onObjectInitialized) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();

        dialog.initStyle(StageStyle.DECORATED);

        dialog.setTitle("Input Property for Cylinder");
        dialog.setHeaderText("Cylinder property");
    
        // Set the color for the dialog
        // dialog.getDialogPane().getStyleClass().add("dialog-pane");
        GridPane dialogGrid = new GridPane();
        ButtonType OKButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(OKButtonType);
    
        dialogGrid.setHgap(10);
        dialogGrid.setVgap(10);
        dialogGrid.setPadding(new Insets(20, 150, 10, 10));
    
        TextField cylinderMass = new TextField();
        cylinderMass.setPromptText("Input mass for cylinder");
    
        TextField cylinderRadius = new TextField();
        cylinderRadius.setPromptText("Input radius for cylinder");
    
        Button OKButton = (Button) dialog.getDialogPane().lookupButton(OKButtonType);
        OKButton.setDisable(true);
        dialogGrid.add(new Label("Cylinder Mass: (> 0, default " + MainObject.MASS_DEFAULT + ")"), 0, 0);
        dialogGrid.add(cylinderMass, 1, 0);
        dialogGrid.add(new Label("Cylinder Radius: (>= " + Cylinder.MIN_RADIUS + " and <=" + Cylinder.MAX_RADIUS + ", default " + Cylinder.MAX_RADIUS * 0.5 + ")"), 0, 1);
        dialogGrid.add(cylinderRadius, 1, 1);
        dialogGrid.add(OKButton, 2, 2);
    
        BooleanProperty condition1 = new SimpleBooleanProperty(false);
        BooleanProperty condition2 = new SimpleBooleanProperty(false);
    
        cylinderMass.textProperty().addListener((observable, oldValue, newValue) -> {
            condition1.set(!cylinderMass.getText().isEmpty());
            OKButton.setDisable(!(condition1.get() && condition2.get()));
        });
    
        cylinderRadius.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double value = Double.parseDouble(cylinderRadius.getText());
                condition2.set(!cylinderRadius.getText().isEmpty() && value >= Cylinder.MIN_RADIUS && value <= Cylinder.MAX_RADIUS);
                OKButton.setDisable(!(condition1.get() && condition2.get()));
            } catch (NumberFormatException e) {
                condition2.set(false);
                OKButton.setDisable(true);
            }
        });
    
        OKButton.setOnAction(event -> {
            try {
                mass = Double.parseDouble(cylinderMass.getText());
                size = Double.parseDouble(cylinderRadius.getText());
                Double scale = size / 0.5;

                myObj.setImage(new Image("img/cylinder.png"));
                myObj.setScaleX(scale);
                myObj.setScaleY(scale);

                cylinder.setVisible(false);
                cube.setVisible(true);
                
                this.is_cylinder = true;
                this.is_cube = false;
                this.MainCylinder = new Cylinder(mass, size);

                myObj.setLayoutY(300 - (scale - 1) * 100 / 2);

                onObjectInitialized.run();
            } catch (Exception e) {
                // Handle the exception accordingly (e.g., log or display an error message)
            }
        });

        dialogGrid.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        dialog.setOnCloseRequest((EventHandler<DialogEvent>) new EventHandler<DialogEvent>() {
            @Override
            public void handle(DialogEvent event) {
                if (!is_cylinder){cylinder.setVisible(true);}
                
            }
        });
    
        dialog.getDialogPane().setContent(dialogGrid);
        dialog.showAndWait();
        
    }
    

    
}
