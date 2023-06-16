package com.edelph.simplexe.view;

import javafx.event.Event;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static javafx.scene.input.KeyCode.V;

public class FormEquationModal {
    private Stage window;
    public static FormEquationModal formEquationModal = null;
    private BorderPane layout;
    private VBox bottomContainer;
    private VBox formContainer;
    private boolean isMaximization = true;


    public FormEquationModal() {
        window = new Stage();
        formContainer = new VBox();
        formContainer.setStyle("-fx-padding: 12");
        bottomContainer = new VBox();
        layout = new BorderPane(formContainer, null, null,bottomContainer, null);

        layout.setStyle(" -fx-background-color: green");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Ajouter des equation");
        window.setWidth(500);
        window.setResizable(false);
//        window.setHeight(300);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        this.baseInput();
        this.createInputZ();
        this.createButtonBottom();
    }
    public void showAndWait() {
        window.showAndWait();
    }

    public static void display(){
        System.out.println("clicked");
        if(FormEquationModal.formEquationModal == null) {
            FormEquationModal.formEquationModal = new FormEquationModal();
        };
         FormEquationModal.formEquationModal.showAndWait();
    }
    private void baseInput(){
        Label label1 = new Label("Equation1 : ");
        TextField textField1 = new TextField();
        Label label2 = new Label("Equation1 : ");
        TextField textField2 = new TextField();
        this.formContainer.getChildren().addAll(label1,textField1, label2,textField2);
    }

    private void createInput(){
        VBox newInput = new VBox();
        Label label1 = new Label("Equation : ");
        TextField textField1 = new TextField();
        newInput.getChildren().addAll(label1, textField1);
        this.formContainer.getChildren().addAll(newInput);
        this.window.setHeight(this.window.getHeight() + 50);
        System.out.println(this.window.getHeight() + 50);
    }

    private void createButtonBottom(){
        HBox btnContainer = new HBox();
        Button btnCancel = new Button("Cancel");
        Button btnOk = new Button("OK");
        btnContainer.setSpacing(10);
        btnContainer.setPrefHeight(50);
        btnContainer.setStyle("-fx-background-color: purple; -fx-padding: 10");
        btnContainer.setAlignment(Pos.CENTER_RIGHT);
        btnContainer.getChildren().addAll(btnCancel, btnOk);
        btnOk.setOnAction(e->createInput());
        bottomContainer.getChildren().add(btnContainer);
    }
    private void createInputZ(){
        Label label1 = new Label("MAX : ");
        TextField textField1 = new TextField();
        BorderPane ZInputContainer = new BorderPane(textField1, null, null,null,label1);
        HBox checkContainer = new HBox();
        VBox Zcontainer = new VBox();

        Zcontainer.setSpacing(12);

        CheckBox maximization = new CheckBox("Maximisation");
        CheckBox minimization = new CheckBox("Minimisation");
        maximization.setSelected(true);
        minimization.setSelected(false);

        maximization.setOnAction(e->{
            CheckBox checkBox = (CheckBox) e.getSource();
            this.isMaximization = checkBox.isSelected();
            minimization.setSelected(!this.isMaximization);
            System.out.println(this.isMaximization);
        });
        minimization.setOnAction(e->{
            CheckBox checkBox = (CheckBox) e.getSource();
            this.isMaximization = !checkBox.isSelected();
            maximization.setSelected(this.isMaximization);
            System.out.println(this.isMaximization);
        });

        checkContainer.setSpacing(10);
        checkContainer.setAlignment(Pos.CENTER);
        checkContainer.getChildren().addAll(maximization, minimization);

        Zcontainer.setStyle("-fx-padding: 12");
        Zcontainer.getChildren().addAll(checkContainer,ZInputContainer);

        bottomContainer.getChildren().add(Zcontainer);
    }



}
