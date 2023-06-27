package com.edelph.simplexe.util;

import java.util.*;

public class Simplex {
    private int maxIndexVariable = 0;
    private List<Equation> equations;
    private Equation MAX ;
    private List<Fraction> deltaJ ;
    private List<Fraction> Ci;
    private List<Fraction> Cj;
    private List<Fraction> I;
    private List<List<Fraction>> mainSimplex_An;
    private Fraction Z = Fraction.build("0");
    private List<Fraction> A0;
    private HashMap<EleMath, Fraction> results;
    private boolean maximize = true;
    private Optional<Integer> linePivot, columnPivot;

    public Simplex() {
        deltaJ = new ArrayList<>();
        Ci = new ArrayList<>();
        Cj = new ArrayList<>();
        I = new ArrayList<>();
        A0 = new ArrayList<>();
        equations = new ArrayList<>();
        mainSimplex_An = new ArrayList<>();
        linePivot = Optional.empty();
        columnPivot = Optional.empty();
    }


    public void setEquation(List<Equation> equations) {
        this.equations = equations;
    }
    public void setEquation(Equation equation) {
        this.equations.add(equation);
    }
    public void setMax(Equation maxZ){
        MAX = maxZ;
        if(!isMaximize()) MAX.changeSeparateEquationAndEqual();
    }

    public Equation getMAX() {
        return MAX;
    }

    public void next(){
        if(linePivot.isPresent() && columnPivot.isPresent()){
            setLinePivot();
            exchangeCiCjAndI();
            calculateAnAndA0();
            calculateDeltaJAndZ();
        }
    }
    public void nextStep(){
        if(columnPivot.isEmpty()) return;
        if(linePivot.isPresent()){
            setLinePivot();
            exchangeCiCjAndI();
            calculateAnAndA0();
            calculateDeltaJAndZ();
            System.out.println("\n\n");
            showAn();
        }else{
            System.exit(1);
        }
    }
    public void calculate(){
        getIndexColumnPivotInDeltaJ();

        while (columnPivot.isPresent()) {
            System.out.println("columnPivot = " + columnPivot.get());
            getIndexLinePivot();
            nextStep();
            getIndexColumnPivotInDeltaJ();
        }
        this.showResults();
    }

    private void getIndexLinePivot(){
        //get New List
        if(columnPivot.isEmpty()) return ;

        List<Fraction> newList = new ArrayList<Fraction>();
        for (int i = 0; i < this.A0.size(); i++){
            Fraction currentA0 = this.A0.get(i);
            Fraction linePivot = this.mainSimplex_An.get(i).get(columnPivot.get());
            newList.add(currentA0.divide(linePivot));
        }
        newList.forEach(System.out::println);
        // get Index of Minimum and positive
        Fraction fraction = newList.get(0);
        int index = 0;
        for (int i = 1; i < newList.size(); i++) {
            Fraction currentFraction = newList.get(i);
            if(!currentFraction.isInfinite()){
                if(fraction.isInfinite() || fraction.isNegative() || fraction.toDouble().get() == 0){
                    fraction = currentFraction;
                    index = i;
                }else{
                    System.out.println("else");
                    if(currentFraction.toDouble().isPresent() && fraction.toDouble().isPresent()){
                        if( currentFraction.toDouble().get() > 0 &&
                                (fraction.toDouble().get() > currentFraction.toDouble().get()))

                        {
                            System.out.println("index in" + i);
                            fraction = currentFraction;
                            index = i;
                        }
                    }
                }
            }
        }
        System.out.println("_________________________________");
        System.out.println("index : "+ index);
        System.out.println("_________________________________");
        linePivot = Optional.of(index);
        if(fraction.isInfinite() || fraction.isNegative()) {
            linePivot = Optional.empty();
            columnPivot = Optional.empty();
        };
    }

    private void setLinePivot(){
        if(linePivot.isPresent()&& columnPivot.isPresent()){
            Fraction pivot = this.mainSimplex_An.get(linePivot.get()).get(columnPivot.get());
            for(int i = 0; i < this.mainSimplex_An.get(linePivot.get()).size(); i++){
                Fraction currentFraction = this.mainSimplex_An.get(linePivot.get()).get(i);
                this.mainSimplex_An.get(linePivot.get()).set(i, currentFraction.divide(pivot));
            }
            this.A0.set(linePivot.get(), this.A0.get(linePivot.get()).divide(pivot));
        }
    }
    private void calculateAnAndA0(){
        if(linePivot.isPresent() && columnPivot.isPresent()){
            for(int i = 0; i < this.mainSimplex_An.size(); i++){
                Fraction pivot = this.mainSimplex_An.get(i).get(columnPivot.get());
                if(i!= linePivot.get() && !this.mainSimplex_An.get(i).get(columnPivot.get()).get().equals("0")){
                    for (int j = 0; j <this.mainSimplex_An.get(i).size(); j++){
                        Fraction currentFraction = this.mainSimplex_An.get(i).get(j);
                        Fraction fLess = pivot.multiply(this.mainSimplex_An.get(linePivot.get()).get(j));
                        this.mainSimplex_An.get(i).set(j, currentFraction.less(fLess));
                    }
                    //calculate A0
                    Fraction lastA0 = this.A0.get(i);
                    Fraction fLess = pivot.multiply(this.A0.get(linePivot.get()));
                    this.A0.set(i, lastA0.less(fLess));
                }
            }
        }
    }
    private void calculateDeltaJAndZ(){
        //calculate deltaJ
        if(linePivot.isPresent()&& columnPivot.isPresent()) {
            Fraction pivot = this.deltaJ.get(columnPivot.get());
            for (int i = 0; i < this.deltaJ.size(); i++) {
                Fraction currentFraction = this.deltaJ.get(i);
                Fraction fLess = pivot.multiply(this.mainSimplex_An.get(linePivot.get()).get(i));
                this.deltaJ.set(i, currentFraction.less(fLess));
            }

            // calculate Z
            Fraction lastZ = this.Z;
            Fraction fPlus = pivot.multiply(this.A0.get(linePivot.get()));
            this.Z = lastZ.plus(fPlus);
        }
    }

    public void showEquations(){
        for (Equation equation : this.equations) {
            System.out.println(equation.get());
        }
        System.out.println(this.getMAX().get());
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


    private void getIndexColumnPivotInDeltaJ(){
        columnPivot = Optional.empty();
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
       if(fraction.toDouble().isPresent() && fraction.toDouble().get() > 0){
           columnPivot = Optional.of(index);
       }else linePivot = Optional.empty();
    }

    public Fraction getZ() {
        return Z;
    }
    public void setZ(Fraction z) {
        Z = z;
    }

    private void exchangeCiCjAndI(){
        if(linePivot.isPresent() && columnPivot.isPresent()){
            this.Ci.set(linePivot.get(), this.Cj.get(columnPivot.get()));
            this.I.set(linePivot.get(), Fraction.build(String.valueOf(columnPivot.get() + 1)));
        }
    }

    public boolean isAllLowerAndEquals(){
        for (Equation equation: this.equations){
            if(equation.getSeparator().equals(">=")) return false;
        }
        return true;
    }
    private void setI(){
        for (int i = this.maxIndexVariable + 1; i <= MAX.getMaxIndex(); i++){
            this.I.add(Fraction.build(String.valueOf(i)));
        }
    }
    private void setA0(){
        for (Equation equation : this.equations) {
            this.A0.add(equation.getRight());
        }
    }

    private void setCj(){
        this.Cj = this.MAX.getAllValueAsList(this.MAX.getMaxIndex());
    }

    private void setCi(){
        for (Fraction f : this.I) {
            Optional<Fraction> optionalFraction = this.MAX.getValueIndex(Integer.parseInt(f.get()));
            optionalFraction.ifPresent(this.Ci::add);
        }
    }

    private int getMaxIndexInEquations(){
        int maxIndex = 0;
        for (Equation equation: this.equations) {
            int tmp = equation.getMaxIndex();
            if(tmp > maxIndex) maxIndex = tmp;
        }
        return maxIndex;
    }

    public void getMatrixEquations(){
        this.maxIndexVariable = getMaxIndexInEquations();
        this.addGapVariableInEquations();
        int max = getMaxIndexInEquations();
        for (Equation equation : this.equations) {
            List<Fraction> list = equation.getAllValueAsList(max);
            this.mainSimplex_An.add(list);
        }
        this.setI();
        this.setA0();
        this.setCj();
        this.setCi();
        this.setDeltaJ();
    }

    public void getAllPivot(){
        getIndexColumnPivotInDeltaJ();
        getIndexLinePivot();
    }
    private void setDeltaJ(){
        for (int i = 0; i < this.Cj.size(); i++) {
            Fraction sum = Fraction.build("0");
            for (int j = 0; j < this.mainSimplex_An.size(); j++) {
                sum = sum.plus(this.mainSimplex_An.get(j).get(i).multiply(this.Ci.get(j)));
            }
            this.deltaJ.add(this.Cj.get(i).less(sum));
        }
    }

    public void addGapVariableInEquations(){
        int maxIndex = getMaxIndexInEquations();
        if(isAllLowerAndEquals()){
            for (Equation equation : equations) {
                int tmp = ++maxIndex;
                equation.addGapVariable(tmp);
                this.MAX.addGapVariable(tmp);
            }
        }
    }

    public void showResults(){
//        getResults();
        Iterator<EleMath> iterator = this.results.keySet().iterator();
        System.out.println("\n******** Results *******");
        while (iterator.hasNext()) {
            EleMath eleMath = iterator.next();
            Fraction fraction = this.results.get(eleMath);
            System.out.print(eleMath.get()+ " = "+ fraction.get()+"  ");
        }
        System.out.print("\t\t Z = "+this.Z.get());
        System.out.println("\n");
    }



    public Optional<HashMap<EleMath, Fraction>> getResults(){
        if(columnPivot.isEmpty() || linePivot.isEmpty()){
            this.results = new HashMap<>();
            for (int i = 0 ; i < this.I.size(); i++) {
                int index = Integer.parseInt(this.I.get(i).get());
                if(index <= this.maxIndexVariable){
                    this.results.put(new EleMath("x"+index),this.A0.get(i));
                }
            }
            return Optional.of(this.results);
        }
        return Optional.empty();
    }
    public void trasposeAn(){
        List<List<Fraction>> copyAn = new ArrayList<>();
        int size = this.mainSimplex_An.get(0).size();
        for (int i = 0; i <size ; i++) {
            copyAn.add(new ArrayList<>());
        }
        for (int r = 0; r < mainSimplex_An.size(); r++) {
            for (int c = 0; c < size; c++) {
                copyAn.get(c).add(mainSimplex_An.get(r).get(c));
            }
        }
        this.mainSimplex_An = copyAn;
    }


    public int getMaxIndexVariable() {
        return maxIndexVariable;
    }

    public List<Equation> getEquations() {
        return equations;
    }

    public List<Fraction> getDeltaJ() {
        return deltaJ;
    }

    public List<Fraction> getCi() {
        return Ci;
    }

    public List<Fraction> getCj() {
        return Cj;
    }

    public List<Fraction> getI() {
        return I;
    }

    public List<List<Fraction>> getMainSimplex_An() {
        return mainSimplex_An;
    }

    public List<Fraction> getA0() {
        return A0;
    }

    public Optional<Integer> getLinePivot() {
        return linePivot;
    }

    public Optional<Integer> getColumnPivot() {
        return columnPivot;
    }

    public boolean isMaximize() {
        return maximize;
    }

    public void setMaximize(boolean maximize) {
        this.maximize = maximize;
        if(!isMaximize() && this.MAX !=null) MAX.changeSeparateEquationAndEqual();
    }
}
