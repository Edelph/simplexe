package com.edelph.simplexe.util;

public class EleMath {
    Fraction value;
    int index;
    String variable = "x";

    public EleMath() {
    }

    public EleMath(String elemMath) {
        build(elemMath);
    }

    public void setEleMath(String elemMath) {
        build(elemMath.trim());
    }
    public EleMath(int value, String variable, int index) {
        this.value = Fraction.build(String.valueOf(value));
        this.index = index;
        this.variable = variable;
    }
    public EleMath(Fraction value, String variable, int index) {
        this.value = value;
        this.index = index;
        this.variable = variable;
    }
    public EleMath(Fraction value, int index) {
        this.value = value;
        this.index = index;
    }
    public EleMath(int value, int index) {
        this.value = Fraction.build(String.valueOf(value));
        this.index = index;
    }
    private void build(String eleMath){
        if("".equals(this.variable.trim())) this.variable="x";
        String[] newElem = eleMath.toLowerCase().split(this.variable);
        value = Fraction.build(newElem[0].trim());
        index = Integer.parseInt(newElem[1].trim());
    }

    public Fraction getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }

    public String getVariable() {
        return variable;
    }

    public void setValue(int value) {
        this.value = Fraction.build(String.valueOf(value));
    }
    public void setValue(Fraction value) {
        this.value = value;
    }


    public void setIndex(int index) {
        this.index = index;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    @Override
    public String toString() {
        return "EleMath{" +
                "value=" + value +
                ", variable='" + variable + '\'' +
                ", index=" + index +
                '}';
    }
    public String get(){
        return value.get() + " " + variable + index;
    }
    public String getAbs(){
        return value.getAbs() + " " + variable + index;
    }
}
