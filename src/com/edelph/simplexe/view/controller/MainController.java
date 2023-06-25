package com.edelph.simplexe.view.controller;

import com.edelph.simplexe.util.EleMath;
import com.edelph.simplexe.util.Fraction;
import com.edelph.simplexe.util.Simplex;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private VBox tabContainer, resultsPane;

    @FXML
    private HBox container;

    @FXML
    private Button btn_equation, btn_calculez;
    private Double modal_xx, modal_xy;
    private static Stage modalEquation ;

    private static Simplex simplex;
    private int rowPivot=-1;
    private static MainController self;


    public void createTable(Simplex simplex) throws IOException {
        this.tabContainer.getChildren().add(Tableaux.build(simplex,rowPivot));
        tabContainer.setAlignment(Pos.CENTER);
        tabContainer.setFillWidth(true);
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
        tabContainer.getChildren().clear();
        if(container.getChildren().size()==2)
            container.getChildren().add(ShowEquationPane.build(simplex));
        else container.getChildren().set(2,ShowEquationPane.build(simplex));
        HBox.setHgrow(container.getChildren().get(2), Priority.ALWAYS);
        simplex.getMatrixEquations();
        simplex.getAllPivot();
        createTable(simplex);
        if(simplex.getLinePivot().isPresent())
            rowPivot = simplex.getLinePivot().get();
        Optional<Integer> pivot = simplex.getColumnPivot();

        while (pivot.isPresent()){
            simplex.next();
            simplex.getAllPivot();
            createTable(simplex);
            simplex.getAllPivot();
            pivot = simplex.getColumnPivot();
            if(simplex.getLinePivot().isPresent())
                rowPivot = simplex.getLinePivot().get();
        }
        Optional<HashMap<EleMath, Fraction>> resultes = simplex.getResults();

        if(resultes.isPresent()){
            resultsPane.getChildren().clear();
            Label l = new Label("***** Resultats *****");
            l.getStyleClass().add("title");
            resultsPane.getChildren().add(l);
            resultsPane.getChildren().add(ShowEquationPane.build(resultes.get(),simplex.getZ()));
            resultsPane.setAlignment(Pos.CENTER);
        }
        btn_calculez.setDisable(true);
    }

    private void showModalEquation(ActionEvent event) throws IOException {

        if(modalEquation == null) {
            modalEquation = new Stage();
            FXMLLoader load = new FXMLLoader(Objects.requireNonNull(MainController.class.getResource("/com/edelph/simplexe/view/fxml/modal-equation.fxml")));
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

    private Label setEquationLabel(String equation){
        return new Label(equation);
    }
    public void setSimplex(Simplex simplex){
        MainController.simplex = simplex;
    }
    public void setBtnAble(){
        btn_calculez.setDisable(false);
        rowPivot=-1;
    }

    public void setSelf(MainController mainController) {
        self = mainController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        container.setAlignment(Pos.CENTER);
        btn_calculez.setDisable(true);
    }
}
