package com.edelph.simplexe;

import com.edelph.simplexe.util.*;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        Simplex simplex = new Simplex();
//        Equation equation2 = new Equation("x1 <= 1000 ");
//        Equation equation4 = new Equation("x2 <= 500 ");
//        Equation equation1 = new Equation("x3 <= 1500 ");
//        Equation equation3 = new Equation("3 x1 + 6x2 + 2 x3 <= 6750 ");
//        Equation max = new Equation("4 x1 + 12 x2 + 3 x3");

        Equation equation1 = new Equation("x1 <= 4900 ");
        Equation equation2 = new Equation("x2 <= 5400 ");
        Equation equation3 = new Equation("x3 <= 2000 ");
        Equation equation4 = new Equation("36 x1 + 28x2 + 63 x3 <= 252000 ");
        Equation max = new Equation("3 x1 + 2 x2 + 4 x3");
        simplex.setMax(max);
        simplex.
                setEquation(equation1).
                setEquation(equation3).
                setEquation(equation2).
                setEquation(equation4);



                simplex.getMatrixEquations();
        simplex.showAn();
        simplex.calculate();
    }
}
