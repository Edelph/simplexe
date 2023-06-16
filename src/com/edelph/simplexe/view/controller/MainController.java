package com.edelph.simplexe.view.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class MainController {
    @FXML
    private VBox tabContainer;
    @FXML
    private Button btn_equation;
    Double modal_xx, modal_xy;


    public void createTable() throws IOException {
        Node newTable = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("./../fxml/tableau.fxml")));
        this.tabContainer.getChildren().add(newTable);
    }

    @FXML
    void btn_equation_onClicked(ActionEvent event) throws IOException {
        showModalEquation(event);
    }

    private void showModalEquation(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader load = new  FXMLLoader(Objects.requireNonNull(MainController.class.getResource("./../fxml/modal-equation.fxml")));
        Parent root = load.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        ModalEquationController controller = load.getController();
        controller.setStage(stage);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
        stage.show();
        drawable(root,stage);
    }


    private void drawable(Parent root, Stage stage){
        root.setOnMousePressed(mouseEvent -> {
            modal_xx = mouseEvent.getSceneX();
            modal_xy = mouseEvent.getSceneY();
        });

        root.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - modal_xx);
            stage.setY(mouseEvent.getScreenY() - modal_xy);
        });
    }

}
