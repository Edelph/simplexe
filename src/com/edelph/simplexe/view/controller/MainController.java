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
    private Double modal_xx, modal_xy;
    private static Stage modalEquation ;


    public void createTable() throws IOException {
        Node newTable = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("./../fxml/tableau.fxml")));
        this.tabContainer.getChildren().add(newTable);
    }

    @FXML
    void btn_equation_onClicked(ActionEvent event) throws IOException {
        showModalEquation(event);
    }

    private void showModalEquation(ActionEvent event) throws IOException {

        if(modalEquation == null) {
            modalEquation = new Stage();
            FXMLLoader load = new FXMLLoader(Objects.requireNonNull(MainController.class.getResource("./../fxml/modal-equation.fxml")));
            Parent root = load.load();
            Scene scene = new Scene(root);
            modalEquation.setScene(scene);
            ModalEquationController controller = load.getController();
            controller.setSelf(controller);
            controller.setStage(modalEquation);
            modalEquation.setResizable(false);
            modalEquation.sizeToScene();
            modalEquation.initStyle(StageStyle.UNDECORATED);
            modalEquation.initModality(Modality.WINDOW_MODAL);
            modalEquation.initOwner(((Node) event.getSource()).getScene().getWindow());
            drawable(root, modalEquation);
        }
        modalEquation.show();
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
