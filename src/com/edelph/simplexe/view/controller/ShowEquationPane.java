package com.edelph.simplexe.view.controller;

import com.edelph.simplexe.util.EleMath;
import com.edelph.simplexe.util.Equation;
import com.edelph.simplexe.util.Fraction;
import com.edelph.simplexe.util.Simplex;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ShowEquationPane {
    public static Pane build(Simplex simplex){
        GridPane container = new GridPane();
        List<Equation> equationList = simplex.getEquations();
        int column = 0;
        int row;
        container.getStylesheets().add(Objects.requireNonNull(ShowEquationPane.class.getResource("/com/edelph/simplexe/view/style/showText.css")).toExternalForm());
        container.setHgap(20);
        container.add(getText(simplex.isMaximize()?"MAXIMISATION":"MINIMISATION","title"),0,0);
        container.add(getText(simplex.getMAX().get(), "max"),0,1);
        int nbLine = getNumberLine(equationList.size());

        for (row = 2; row < equationList.size() + 2; row++) {
            if(row % nbLine == 0 )
                column++;
            Equation equation = equationList.get(row-2);
            container.add(getText(equation.get(),"equation"),column,row % nbLine);
        }
        container.setAlignment(Pos.CENTER);
        return container;
    }

    public static Pane build(HashMap<EleMath, Fraction> resultes, Fraction Z) {
        GridPane container = new GridPane();
        container.getStylesheets().add(Objects.requireNonNull(ShowEquationPane.class.getResource("/com/edelph/simplexe/view/style/showText.css")).toExternalForm());
        container.setHgap(20);
        int nbLine = getNumberLine2(resultes.size());
        container.add(getText(" Z = "+Z.get(),"title"),0,0);
        int row = 1;
        int column = 0;
        for (Map.Entry<EleMath, Fraction> element : resultes.entrySet()) {
            if(row % nbLine == 0 )
                column++;

            String resp =  element.getKey().get()+ " = " + element.getValue().get();
            container.add(getText(resp, "equation"),column,row % nbLine);
            row++;
        }
        container.setAlignment(Pos.CENTER);
        return container;
    }

    private static Label getText(String text, String clazz){
        Label label = new Label(text);
        label.getStyleClass().add(clazz);
        return label;
    }
    private static Integer getNumberLine(Integer element){
        String d =Double.toString(Math.ceil((double) (element + 2) /2));
        return Integer.parseInt(d.split("\\.")[0]);
    }
    private static Integer getNumberLine2(Integer element){
        String d =Double.toString(Math.ceil((double) (element +1) /4));
        return Integer.parseInt(d.split("\\.")[0]);
    }
}
