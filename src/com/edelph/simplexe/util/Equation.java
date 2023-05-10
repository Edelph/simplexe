package com.edelph.simplexe.util;

import java.util.ArrayList;
import java.util.List;

public class Equation {
    private List<EleMath> left = new ArrayList<>();
    private Fraction right;
    private String separator= "=";

    public Equation(String equation) {
        if(equation.contains(">=")) separateEquationAndEqual(equation, ">=");
        if(equation.contains("<=")) separateEquationAndEqual(equation, "<=");
    }

    private void separateEquationAndEqual(String equation, String separator) {
        this.separator=separator;
        String[] tabTmp = equation.split(separator);
        this.right = Fraction.build(tabTmp[1].trim());
        equationToEleMath(tabTmp[0]);
    }
    private void equationToEleMath(String equation) {
        String elem = equation.replaceAll(" ", ""); ;
        String[] tabEquation = elem.split("");
        if((!elem.contains("+") && !elem.contains("-")) ||
                (!elem.contains("+") && tabEquation[0].equals("-") && !elem.substring(1).contains("-")))  {
            this.left.add(new EleMath(elem));
            return;
        }
        for(int i=0; i<tabEquation.length; i++) {
            if("+".equals(tabEquation[i])){
                if(i==0)equationToEleMath(elem.substring(1));
                else{
                    this.left.add(new EleMath(elem.substring(0,i)));
                    equationToEleMath(elem.substring(i));
                }
                break;
            }
            if( i!=0 && "-".equals(tabEquation[i])){
                this.left.add(new EleMath(elem.substring(0,i)));
                equationToEleMath(elem.substring(i));
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Equation{" +
                "left=" + left +
                ", right=" + right +
                ", separator='" + separator + '\'' +
                '}';
    }

    private String getLeft(){
        StringBuilder output = new StringBuilder();
        for (EleMath e: left) {
            if(!e.value.isNegative() && output.length()>0){
                output.append(" + ");
            }if (e.value.isNegative() && output.length()>0){
                output.append(" - ");
            }
            output.append(e.getAbs());
        }
        return output.toString();
    }

    public String get(){
        return getLeft()+ " " + separator+ " " + right.get();
    }
}
