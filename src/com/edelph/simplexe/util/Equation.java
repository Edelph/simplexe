package com.edelph.simplexe.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Equation {
    private List<EleMath> left = new ArrayList<>();
    private Fraction right;
    private String separator= "=";

    public Equation(String equation) {
        if(equation.contains(">=")) separateEquationAndEqual(equation, ">=");
        if(equation.contains("<=")) separateEquationAndEqual(equation, "<=");
        if(!equation.contains("<=") && !equation.contains(">=")) equationToEleMath(equation);
    }

    private void changeSeparateEquationAndEqual(){
        for (EleMath element: this.left) {
            element.setOpposer();
        }
        this.right.setOpposer();
        this.separator="<=";
    }

    private void separateEquationAndEqual(String equation, String separator) {
        this.separator=separator;
        String[] tabTmp = equation.split(separator);
        this.right = Fraction.build(tabTmp[1].trim());
        equationToEleMath(tabTmp[0]);
        if(">=".equals(separator))changeSeparateEquationAndEqual();
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
        if(right==null) return "Equation{" +
                "left=" + left +
                '}';
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
            }if (e.value.isNegative() ){
                if( output.length()>0 )output.append(" - ");
                        else output.append("-");
            }
            output.append(e.getAbs());
        }
        return output.toString();
    }

    public String get(){
        if (right==null) return getLeft();
        return getLeft()+ " " + separator+ " " + right.get();
    }

    public int getMaxIndex(){
        int max = 0;
        for (EleMath elem: this.left) {
            if(elem.index>max) max = elem.index;
        }
        return max;
    }

    public boolean hasIndex(int index){
        for (EleMath elem: this.left) {
            if(elem.index==index) return true;
        }
        return false;
    }
    public Optional<Fraction> getValueIndex(int index){
        for (EleMath elem: this.left) {
            if(elem.index==index) return Optional.of(elem.value);
        }
        return Optional.empty();
    }
    public void addGapVariable(int index){
        if(right == null){
            this.left.add(new EleMath("0 x"+index)) ;
        }else if(separator.equals("<=")){
            this.left.add(new EleMath("x"+index)) ;
            separator = "=";
        }
    }
    public List<Fraction> getAllValueAsList(int max){
        List<Fraction> fractionList = new ArrayList<>();
        for (int i = 1; i <= max; i++){
            Optional<Fraction> optionalFraction = getValueIndex(i);

            if(optionalFraction.isPresent()) fractionList.add(optionalFraction.get());
            else fractionList.add(Fraction.build("0"));
        }
        return fractionList;
    }

    public String getSeparator() {
        return separator;
    }

    public Fraction getRight() {
        return right;
    }
}
