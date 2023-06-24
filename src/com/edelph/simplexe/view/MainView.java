package com.edelph.simplexe.view;

import com.edelph.simplexe.view.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainView extends Application {

    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/edelph/simplexe/view/fxml/main-window.fxml"));
        Parent root = loader.load();
        MainController mainController = loader.getController();
        mainController.setSelf(mainController);
        stage.setScene(new Scene(root));
        stage.setTitle("Algorithm Simplex");
        stage.setResizable(false);
        stage.show();
    }
}
