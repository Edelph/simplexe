package com.edelph.simplexe.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.net.URL;
import java.util.ResourceBundle;

public class InputController implements Initializable {
    @FXML
    private Label label;

    @FXML
    private ImageView closeEquation;

    @FXML
    private TextField input;
    private HBox self;

    public Label getLabel() {
        return label;
    }
    public TextField getInput() {
        return input;
    }

    public void setNumberEquation(int number) {
        label.setText("Equation " + number + " : ");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HBox.setHgrow(input, Priority.ALWAYS);
        closeEquation.setVisible(true);
    }

    @FXML
    void mouseEntered(MouseDragEvent event) {
        closeEquation.setVisible(true);
    }

    @FXML
    void mouseExited(MouseDragEvent event) {
        closeEquation.setVisible(false);
    }
    @FXML
    void closeEquationClicked(MouseEvent event) {
        ModalEquationController.nodeList.remove(self);
        System.out.println("clicked close");
    }

    public void setSelf(HBox self) {
        this.self = self;
    }
}
