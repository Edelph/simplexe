package com.edelph.simplexe.view.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ModalEquationController implements Initializable {
    @FXML
    private BorderPane boderPane;

    @FXML
    private Button btn_addEquation, btn_cancel, btn_valid;

    @FXML
    private VBox equationContainer;

    @FXML
    private CheckBox checkMax, checkMin;

    @FXML
    private TextField equation;

    public static List<Node> nodeList;

    private static int numberOfEquation = 2;

    Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkMax.setSelected(true);
        getAllEquation();

    }

    @FXML
    void checkMaxClicked(ActionEvent event) {
        checkMin.setSelected(!checkMax.isSelected());
    }

    @FXML
    void checkMinClicked(ActionEvent event) {
        checkMax.setSelected(!checkMin.isSelected());
    }

    @FXML
    void btn_addEquationClicked(ActionEvent event) {
        numberOfEquation +=1;
        nodeList.add(addEquation(numberOfEquation));
    }

    @FXML
    void btn_cancelClicked(ActionEvent event) {
        this.stage.close();
    }

    @FXML
    void btn_validClicked(ActionEvent event) {
        this.stage.close();
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }
    private void getAllEquation(){
        if(nodeList == null) {
            nodeList = new ArrayList<>();
            nodeList.add(addEquation(1));
            nodeList.add(addEquation(2));
        }
        else{
            for (Node equation: nodeList) {
                this.equationContainer.getChildren().add(equation);
            }
        }
    }
    

    private Node addEquation(int number) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("./../fxml/input-equation.fxml")));
            HBox newEquation = loader.load();
            InputController controller = loader.getController();
            controller.setNumberEquation(number);
            controller.setSelf(newEquation);
            newEquation.prefWidthProperty().bind(equationContainer.widthProperty());
            this.equationContainer.getChildren().add(newEquation);
            screenSizeChanged();
            return newEquation;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void screenSizeChanged(){
        if (stage!=null && numberOfEquation <= 10) {
            stage.sizeToScene();
        }
        btn_addEquation.setDisable(numberOfEquation >= 10);
    }

}
