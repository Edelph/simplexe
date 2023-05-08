package com.edelph.simplexe.util;

import java.util.List;

public class Equation {
    private List<String> equation;
    private String MAX;

    public Equation() {
    }

    public Equation(List<String> equation) {
        this.equation = equation;
    }

    public void setEquation(List<String> equation) {
        this.equation = equation;
    }
    public void setEquation(String equation) {
        this.equation.add(equation);
    }

    public List<String> getEquation() {
        return equation;
    }

    public void setMAX(String MAX) {
        this.MAX = MAX;
    }

}
