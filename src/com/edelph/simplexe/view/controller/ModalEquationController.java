package com.edelph.simplexe.view.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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

    private static ModalEquationController self;

    private static LinkedHashMap<InputController, Node>  nodeHashMap;

    private static int numberOfEquation = 2;

    private static Stage stage;



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
        numberOfEquation ++;
        addEquation(numberOfEquation);
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
        if(nodeHashMap == null) {
            nodeHashMap = new LinkedHashMap<>();
            for (int i = 1; i <= numberOfEquation; i++) {
                addEquation(i);
            }
        }
        else{
            int i = 1;
            for (Map.Entry<InputController, Node> map : nodeHashMap.entrySet() ) {
                map.getKey().setNumberEquation(i);
                this.equationContainer.getChildren().add(map.getValue());
                i++;
            }
        }
    }
    

    private void addEquation(int number) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("./../fxml/input-equation.fxml")));
            HBox newEquation = loader.load();
            InputController controller = loader.getController();
            controller.setNumberEquation(number);
            controller.setSelf(Map.entry(controller,newEquation));
            if(controller.getParent() == null){
                controller.setParent(self);
            }

            newEquation.prefWidthProperty().bind(equationContainer.widthProperty());
            this.equationContainer.getChildren().add(newEquation);
            screenSizeChanged();
            nodeHashMap.put(controller, newEquation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSelf(ModalEquationController self) {
        ModalEquationController.self = self;
    }
    private void screenSizeChanged(){
        if (stage!=null && numberOfEquation <= 10) {
            stage.sizeToScene();
        }
        btn_addEquation.setDisable(numberOfEquation >= 10);
    }

    public void removeEquation(Map.Entry<InputController, Node> map){
        if(numberOfEquation>2) {
            nodeHashMap.remove(map.getKey());
            numberOfEquation = nodeHashMap.size();
            renameEquation();
            equationContainer.getChildren().remove(map.getValue());
            screenSizeChanged();
        }
    }

    private void renameEquation(){
        Iterator<Map.Entry<InputController, Node>> iterator = nodeHashMap.entrySet().iterator();
        int i = 1;
        while(iterator.hasNext()) {
            Map.Entry<InputController, Node> map = iterator.next();
            map.getKey().setNumberEquation(i);
            i++;
        }
    }


}
