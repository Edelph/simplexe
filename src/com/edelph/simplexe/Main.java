package com.edelph.simplexe;

import com.edelph.simplexe.util.EleMath;
import com.edelph.simplexe.util.Equation;
import com.edelph.simplexe.util.Fraction;
import com.edelph.simplexe.util.TestSimplex;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {
	// write your code here
//        System.out.println("2x_1 +3x_3");
//        TestSimplex testSimplex = new TestSimplex();

//        EleMath eleMath = new EleMath();
//        eleMath.setVariable("M");
//        eleMath.setEleMath("267M7");
//        System.out.println(eleMath);

        String equation = " 2 x1 + 3 x2 - 2 x3 -  2 x1 + 3 x2 - 2 x3 <= 122223 ";
//        System.out.println(equation.indexOf("+"));
//        System.out.println(equation.indexOf("-"));
//        System.out.println(equation.substring(1));
//        System.out.println(equation.substring(equation.indexOf("+")+1));

        Equation equation1 = new Equation(equation);
//        System.out.println(equation.split("").length);
        System.out.println(equation1.get());
    }
}
