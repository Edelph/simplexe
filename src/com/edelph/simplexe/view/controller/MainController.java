package com.edelph.simplexe.view.controller;

import com.edelph.simplexe.util.Simplex;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class MainController {
    @FXML
    private VBox tabContainer;

    @FXML
    private HBox container;

    @FXML
    private Button btn_equation, btn_calculez;
    private Double modal_xx, modal_xy;
    private static Stage modalEquation ;

    private static Simplex simplex;
    private static MainController self;


    public void createTable(Simplex simplex) throws IOException {
        this.tabContainer.getChildren().add(Tableaux.build(simplex));
        tabContainer.setAlignment(Pos.CENTER);
        tabContainer.setPadding(new Insets(20,5,15,5));
    }

    @FXML
    void btn_equation_onClicked(ActionEvent event) throws IOException {
        showModalEquation(event);
    }

    @FXML
    void btn_calculez_onClicked(ActionEvent event) throws IOException {
        calculateSimplex();
    }

    void calculateSimplex() throws IOException {
        showEquation();
        simplex.getMatrixEquations();
        Optional<Integer> pivot = simplex.getPivot();
        while (pivot.isPresent()){
            simplex.next(pivot.get());
            createTable(simplex);
            pivot = simplex.getPivot();
        }
        simplex.showResults();
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
            controller.setMainController(self);
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

    private void showEquation(){
        GridPane equationContainer = new GridPane();
        equationContainer.add(setEquationLabel("eedelph1"), 0,0);
        equationContainer.add(setEquationLabel("eedelph2"), 0,1);
        container.getChildren().add(equationContainer);
    }
    private Label setEquationLabel(String equation){
        return new Label(equation);
    }
    public void setSimplex(Simplex simplex){
        MainController.simplex = simplex;
    }

    public void setSelf(MainController mainController) {
        self = mainController;
    }
}
