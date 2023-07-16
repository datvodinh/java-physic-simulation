# `OOP.DSAI.20222.05`

## `Table of Contents`
- [`OOP.DSAI.20222.05`](#oopdsai2022205)
  - [`Table of Contents`](#table-of-contents)
  - [`Background`](#background)
  - [`Installation`](#installation)
    - [`Requirement`](#requirement)
    - [`Run`](#run)
  - [`Structure`](#structure)
  - [`Member`](#member)
  - [`Contributing`](#contributing)
    - [`1. Vo Dinh Dat`](#1-vo-dinh-dat)
    - [`2. Tran Quoc De`](#2-tran-quoc-de)
    - [`3. Nguyen Minh Cuong`](#3-nguyen-minh-cuong)
    - [`4. Hoang Thanh Dat`](#4-hoang-thanh-dat)


## `Background`

This project is an interactive simulation designed to demonstrate Newton's laws of motion. It allows users to control a physical system consisting of a main object, a surface, and an actor, and observe the resulting motion of the main object. The simulation provides a graphical user interface (GUI) similar to the reference provided, allowing users to manipulate various parameters and visualize the forces at play.

## `Installation`

###  `Requirement`

```
JavaFX 20.0.1
JDK-17.0.6.10
```

### `Run`

- Run `MainSimulation.java`
## `Structure`

<details>
  <summary>Click to show Details</summary>

  <div id="markdownContent">

```
│   .gitignore
│   LICENSE
│   README.md
│
├───.vscode
│       launch.json
│       settings.json
│
├───Design
│       Controller diagram 1.png
│       Controller Diagram 2.png
│       Controller Diagram.asta
│       force_class_diagram.asta
│       force_diagram.png
│       general_class_diagram.asta
│       general_class_diagram.png
│       model_diagram.asta
│       model_diagram.png
│       Object Diagram.png
│       Object_diagram.asta
│       Surface Diagram.png
│       Surface_diagram.asta
│       usecase_diagram.asta
│       usecase_diagram.png
│
└───sourcecode
    │   .DS_Store
    │   README.md
    │
    ├───lib
    └───src
        │   MainSimulation.java
        │
        ├───controller
        │       AnimationController.java
        │       DragDropController.java
        │       ForceController.java
        │       MainSimulationController.java
        │       StatisticController.java
        │       SurfaceController.java
        │
        ├───img
        │       appIcon.png
        │       applyForceArrow.png
        │       background.jpg
        │       background.png
        │       cube.png
        │       cylinder.png
        │       frictionForceArrow.png
        │       sumForceArrow.png
        │       surface.png
        │
        ├───model
        │   ├───force
        │   │       AppliedForce.java
        │   │       Force.java
        │   │       ForceSimulation.java
        │   │       FrictionForce.java
        │   │       Vector.java
        │   │
        │   ├───object
        │   │       Cube.java
        │   │       Cylinder.java
        │   │       MainObject.java
        │   │
        │   └───surface
        │           Surface.java
        │
        └───view
                ForcePanel.fxml
                MainObject.fxml
                MainSimulation.fxml
                StatsPanel.fxml
                SurfacePanel.fxml
```

  </div>
</details>


## `Member`


| Member            |MSSV         | Github        | 
| :--               |:---         |    :---       |
| Vo Dinh Dat       |20214890     | @datvodinh10  |
| Tran Quoc De      |20210179     | @tranquocde   |
| Nguyen Minh Cuong |20210140     | @sohm2        |
| Hoang Thanh Dat   |20214889     | @datht4889    |    

## `Contributing`
### `1. Vo Dinh Dat`
|Work |Contribute |
| :--               |    :---       |
| [`Design`]()  | `25%` |
| [`model/object/MainObject`](sourcecode/src/model/object/MainObject.java)  | `100%` |
| [`model/object/Cylinder`](sourcecode/src/model/object/Cylinder.java)  | `100%` |
| [`model/force/ForceSimulation`](sourcecode/src/model/force/AppliedForce.java)  | `25%` |
| [`controller/AnimationController`](sourcecode/src/controller/AnimationController.java)  | `70%` |
| [`controller/DragDropController`](sourcecode/src/controller/DragDropController.java)  | `50%` |
| [`controller/MainSimulationController`](sourcecode/src/controller/MainSimulationController.java)  | `50%` |
| [`controller/WelcomePage.java`](sourcecode/src/controller/WelcomePage.java)  | `100%` |
| [`view/MainSimulation.fxml`](sourcecode/src/view/MainSimulation.fxml)  | `75%` |
| [`view/Welcome.fxml`](sourcecode/src/view/Welcome.fxml)  | `100%` |
| [`view/ForcePanel.fxml`](sourcecode/src/view/ForcePanel.fxml) | `50%` |
### `2. Tran Quoc De`
|Work |Contribute |
| :--               |    :---       |
| [`Design`]() | `25%` |
| [`model/object/Cube`](sourcecode/src/model/object/Cube.java) | `100%` |
| [`model/surface`](sourcecode/src/model/surface/Surface.java) | `100%` |
| [`controller/AnimationController`](sourcecode/src/controller/AnimationController.java) | `30%` |
| [`controller/MainSimulationController`](sourcecode/src/controller/MainSimulationController.java) | `25%` |
| [`controller/StatisticController`](sourcecode/src/controller/StatisticController.java) | `50%` |
| [`controller/CheckboxController`](sourcecode/src/controller/CheckboxController.java) | `100%` |
| [`controller/SurfaceController`](sourcecode/src/controller/SurfaceController.java) | `100%` |
| [`view/StatsPanel`](sourcecode/src/view/StatsPanel.fxml) | `100%` |
| [`view/Checkbox`](sourcecode/src/view/Checkbox.fxml) | `100%` |
### `3. Nguyen Minh Cuong`
|Work |Contribute |
| :--               |    :---       |
| [`Design`]() | `25%` |
| [`model/force/AppliedFoce`](sourcecode/src/model/force/AppliedForce.java) | `100%` |
| [`model/force/Force`](sourcecode/src/model/force/AppliedForce.java) | `100%` |
| [`model/force/ForceSimulation`](sourcecode/src/model/force/AppliedForce.java) | `75%` |
| [`model/force/FrictionForce`](sourcecode/src/model/force/AppliedForce.java) | `100%` |
| [`model/force/Vector`](sourcecode/src/model/force/AppliedForce.java) | `100%` |
| [`controller/MainSimulationController`](sourcecode/src/controller/MainSimulationController.java)  | `25%` |
| [`view/ForcePanel.fxml`](sourcecode/src/view/ForcePanel.fxml)| `50%` |
| [`view/MainSimulation.fxml`](sourcecode/src/view/MainSimulation.fxml)  | `25%` |
### `4. Hoang Thanh Dat`
|Work |Contribute |
| :--               |    :---       |
| [`Design`]() | `25%` |
| [`controller/DragDropController`](sourcecode/src/controller/DragDropController.java) | `50%` |
| [`controller/StatisticController`](sourcecode/src/controller/StatisticController.java) | `50%` |
| [`demo`]() | `100%` |
| [`report`]() | `100%` |
| [`presentation`]() | `100%` |