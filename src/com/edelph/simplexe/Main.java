package com.edelph.simplexe;

import com.edelph.simplexe.util.*;
import com.edelph.simplexe.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Optional;

public class Main {
    public static void main(String[] args) throws InterruptedException {
//        MainView mainView = new MainView();
//        mainView.start(args);


        Simplex simplex = new Simplex();

        simplex.setEquation(new Equation("2x1 - 3x2 <= 2"));
        simplex.setEquation(new Equation("2x1 + x2 <= 11"));
        simplex.setEquation(new Equation("-x1 + x2 <= 3"));
        simplex.setEquation(new Equation("x1 <= 4"));
        simplex.setEquation(new Equation("x2 <= 5"));

        simplex.setMax(new Equation("x1 + x2"));

        simplex.getMatrixEquations();
//        simplex.showEquations();

        simplex.showAn();

        simplex.calculate();


    }
}
