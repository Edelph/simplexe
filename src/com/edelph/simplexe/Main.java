package com.edelph.simplexe;

import com.edelph.simplexe.util.*;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {
	// write your code here
//        System.out.println("2x_1 +3x_3");
//        TestSimplex testSimplex = new TestSimplex();

//

//        String equation = " 2 x1 + 3 x2 - 2 x3 -  2 x1 + 3 x2 - 2 x3 <= 122223 ";
////
//
//        Equation equation1 = new Equation(equation);
////        System.out.println(equation.split("").length);
//        System.out.println(equation1.get());

        Simplex simplex = new Simplex();
        Equation equation1 = new Equation("x1 <= 1000 ");
        Equation equation2 = new Equation("x2 <= 500 ");
        Equation equation3 = new Equation("x3 <= 1500 ");
        Equation equation4 = new Equation("3 x1 + 6x2 + 2 x3 <= 6750 ");
        Equation max = new Equation("4 x1 + 12 x2 + 3 x3");
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
