package com.edelph.simplexe.util;

import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Simplex {
    private List<String> equation;
    private String MAX ;
    private List<Fraction> deltaJ ;
    private List<Fraction> Ci;
    private List<Fraction> Cj;
    private List<Fraction> I;
    private List<List<Fraction>> mainSimplex_An;
    private Fraction Z = Fraction.build("0");
    private List<Fraction> A0;

    public Simplex() {
        deltaJ = new ArrayList<>();
        Ci = new ArrayList<>();
        Cj = new ArrayList<>();
        I = new ArrayList<>();
        A0 = new ArrayList<>();
        mainSimplex_An = new ArrayList<>();
    }


    public void setEquation(List<String> equation) {
        this.equation = equation;
    }
    public Simplex setEquation(String equation) {
        this.equation.add(equation);
        return this;
    }
    public void setMax(String maxZ){
        MAX = maxZ;
    }

    public void next(){
        Optional<Integer> indexColumnPivot = this.getIndexColumnPivotInDeltaJ();
        indexColumnPivot.ifPresent(this::next);
    }
    public void next(int indexColumnPivot){
        Optional<Integer> indexLinePivot = getIndexLinePivot(indexColumnPivot);
        if(indexLinePivot.isPresent()){
            setLinePivot(indexLinePivot.get(), indexColumnPivot);
            exchangeCiCjAndI(indexLinePivot.get(), indexColumnPivot);
            calculateAnAndA0(indexLinePivot.get(), indexColumnPivot);
            calculateDeltaJAndZ(indexLinePivot.get(), indexColumnPivot);
            System.out.println("\n\n");
            showAn();
        }
    }
    public void calculate(){
        Optional<Integer> indexColumnPivot = this.getIndexColumnPivotInDeltaJ();
        while (indexColumnPivot.isPresent()) {
            next(indexColumnPivot.get());
            indexColumnPivot = this.getIndexColumnPivotInDeltaJ();
        }
    }

    private Optional<Integer> getIndexLinePivot(int indexColumnPivot){
        //get New List
        List<Fraction> newList = new ArrayList<Fraction>();
        for (int i = 0; i < this.A0.size(); i++){
            Fraction currentA0 = this.A0.get(i);
            Fraction linePivot = this.mainSimplex_An.get(i).get(indexColumnPivot);
            newList.add(currentA0.divide(linePivot));
        }
        // get Index of Minimum and positive

        Fraction fraction = newList.get(0);
        int index = 0;
        for (int i = 1; i < newList.size(); i++) {
            Fraction currentFraction = newList.get(i);
            if(!currentFraction.isInfinite()){
                if(fraction.isInfinite()){
                    fraction = currentFraction;
                    index = i;
                }else{
                    if(currentFraction.toDouble().isPresent() && fraction.toDouble().isPresent()){
                        if( currentFraction.toDouble().get() > 0 &&
                                (fraction.toDouble().get() > currentFraction.toDouble().get()))

                        {
                            fraction = currentFraction;
                            index = i;
                        }
                    }
                }
            }
        }
        if(fraction.isInfinite() || fraction.isNegative()) return Optional.empty();
        return Optional.of(index);
    }

    private void setLinePivot(int indexLinePivot, int indexColumnPivot){
        List<Fraction> linePivot = new ArrayList<Fraction>();
        Fraction pivot = this.mainSimplex_An.get(indexLinePivot).get(indexColumnPivot);
        for(int i = 0; i < this.mainSimplex_An.get(indexLinePivot).size(); i++){
            Fraction currentFraction = this.mainSimplex_An.get(indexLinePivot).get(i);
            this.mainSimplex_An.get(indexLinePivot).set(i, currentFraction.divide(pivot));
        }
        this.A0.set(indexLinePivot, this.A0.get(indexLinePivot).divide(pivot));
    }
    private void calculateAnAndA0(int indexLinePivot, int indexColumnPivot){
        for(int i = 0; i < this.mainSimplex_An.size(); i++){
            Fraction pivot = this.mainSimplex_An.get(i).get(indexColumnPivot);
            if(i!= indexLinePivot && !this.mainSimplex_An.get(i).get(indexColumnPivot).get().equals("0")){
                for (int j = 0; j <this.mainSimplex_An.get(i).size(); j++){
                    Fraction currentFraction = this.mainSimplex_An.get(i).get(j);
                    Fraction fLess = pivot.multiply(this.mainSimplex_An.get(indexLinePivot).get(j));
                    this.mainSimplex_An.get(i).set(j, currentFraction.less(fLess));
                }
                //calculate A0
                Fraction lastA0 = this.A0.get(i);
                Fraction fLess = pivot.multiply(this.A0.get(indexLinePivot));
                this.A0.set(i, lastA0.less(fLess));
            }
        }

    }
    private void calculateDeltaJAndZ(int indexLinePivot, int indexColumnPivot){
        //calculate deltaJ
        Fraction pivot = this.deltaJ.get(indexColumnPivot);
        for(int i = 0 ; i < this.deltaJ.size(); i++) {
            Fraction currentFraction = this.deltaJ.get(i);
            Fraction fLess = pivot.multiply(this.mainSimplex_An.get(indexLinePivot).get(i));
            this.deltaJ.set(i, currentFraction.less(fLess));
        }

        // calculate Z
        Fraction lastZ = this.Z;
        Fraction fPlus = pivot.multiply(this.A0.get(indexLinePivot));
        this.Z = lastZ.plus(fPlus);
    }


    public void showAn(){
        for (int l = 0; l < mainSimplex_An.size(); l++){
            List<Fraction> line = mainSimplex_An.get(l);
            // affichage Ci
            System.out.print(this.Ci.get(l).get()+ "|");
            System.out.print(this.I.get(l).get()+ "\t\t");
            for (Fraction column : line) {
                System.out.print(column.get() + "  ");
            }
            System.out.print("\t\t" + this.A0.get(l).get());
            System.out.println("");
        }
        System.out.println("");
        //affichage Cj
        System.out.print("\t\tCj: ");
        for (Fraction cj : this.Cj) {
            System.out.print(cj.get() + " ");
        }
        System.out.println("");
        //affichage deltatJ
        System.out.print("\t\tDj: ");
        for (Fraction dj : this.deltaJ) {
            System.out.print(dj.get() + " ");
        }
        System.out.print("\t\t Z = " + this.Z.get()+"\n");
    }

    public void setMainSimplex_An(String mainSimplex_An) {
        for (String line : mainSimplex_An.split("\n")){
            List<Fraction> listLine = new ArrayList<Fraction>();
            for (String colomn : line.split(" ")){
                listLine.add(Fraction.build(colomn));
            }
            this.mainSimplex_An.add(listLine);
        }
    }
    public void setCi(String Ci){
        for (String colomn : Ci.split(" ")){
            this.Ci.add(Fraction.build(colomn));
        }
    }
    public void setI(String I){
        for (String colomn : I.split(" ")){
           this.I.add(Fraction.build(colomn));
        }
    }
    public void setA0(String A0){
        for (String colomn : A0.split(" ")){
            this.A0.add(Fraction.build(colomn));
        }
    }
    public void setDeltaJ(String deltaJ){
        for (String colomn : deltaJ.split(" ")){
            this.deltaJ.add(Fraction.build(colomn));
        }
    }
    public void setCj(String Cj){
        for (String colomn : Cj.split(" ")){
            this.Cj.add(Fraction.build(colomn));
        }
    }

    private Optional<Integer> getIndexColumnPivotInDeltaJ(){
        int index = 0;
        Fraction fraction = this.deltaJ.get(0);
        for (int i = 1; i < this.deltaJ.size(); i++) {
            Fraction currentFraction = this.deltaJ.get(i);
            if(currentFraction.toDouble().isPresent() && fraction.toDouble().isPresent() ){
                if(currentFraction.toDouble().get() > 0 && (fraction.toDouble().get() < currentFraction.toDouble().get())){
                    fraction = currentFraction;
                    index = i;
                }
            }
        }
       if(fraction.toDouble().isPresent()){
           if (fraction.toDouble().get() > 0) return Optional.of(index);
       }
        return Optional.empty();
    }

    public Fraction getZ() {
        return Z;
    }
    public void setZ(Fraction z) {
        Z = z;
    }

    private void exchangeCiCjAndI(int indexLinePivot, int indexColumnPivot){
        this.Ci.set(indexLinePivot, this.Cj.get(indexColumnPivot));
        this.I.set(indexLinePivot, Fraction.build(String.valueOf(indexColumnPivot + 1)));
    }


}
