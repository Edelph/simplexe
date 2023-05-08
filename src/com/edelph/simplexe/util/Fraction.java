package com.edelph.simplexe.util;

import java.util.Optional;

public class Fraction implements Calculable<Fraction> {
    private int numerator = 1;
    private int denominator = 1;


    public static Fraction build(int numerator, int denominator) {
        return new Fraction(numerator, denominator);
    }
    public static Fraction build(String number){
        String[] tmp = number.trim().split("/");
        if(tmp.length == 2){
            if(tmp[0].equals("")) return Fraction.build(1, Integer.parseInt(tmp[1].trim()));
            return new Fraction(Integer.parseInt(tmp[0].trim()),Integer.parseInt(tmp[1].trim()));
        }
        tmp = number.trim().split(":");
        if(tmp.length == 2){
            if(tmp[0].equals("")) return Fraction.build(1, Integer.parseInt(tmp[1].trim()));
            return new Fraction(Integer.parseInt(tmp[0].trim()),Integer.parseInt(tmp[1].trim()));
        }
        return new Fraction(Integer.parseInt(number));
    }

    public static Fraction build(Fraction fraction) {
        return new Fraction(fraction.numerator, fraction.denominator);
    }

    public Fraction(int numerator, int denominator) {
        if(numerator == 0 && denominator!=0) denominator = 1;
        if((numerator<0 && denominator<0) || (numerator>0 && denominator<0)) {
            this.denominator = -denominator;
            this.numerator = -numerator;
        }
        else{
            this.denominator = denominator;
            this.numerator = numerator;
        }
        this.simplify();
    }
    public Fraction(int numerator) {
        this.numerator = numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public Fraction setDenominator(int denominator) {
        this.denominator = denominator;
        return this;
    }

    public int getNumerator() {
        return numerator;
    }

    public Fraction setNumerator(int numerator) {
        this.numerator = numerator;
        return this;
    }

    @Override
    public Fraction plus(Fraction fraction) {
        if(Math.abs(denominator) == Math.abs(fraction.denominator))
            return Fraction.build(numerator + fraction.numerator, denominator);
        int num = (numerator * fraction.denominator) + (fraction.numerator * denominator);
        return Fraction.build(num, denominator*fraction.denominator);
    }
    public Fraction plus(int number){
        return this.plus(new Fraction(number));
    }
    public Fraction plus(String fraction){
        return this.plus(Fraction.build(fraction));
    }

    @Override
    public Fraction less(Fraction fraction) {
        if(Math.abs(denominator) == Math.abs(fraction.denominator)){
            return Fraction.build(numerator - fraction.numerator, denominator);
        }
        int num = (numerator * fraction.denominator) - (fraction.numerator * denominator);
        return Fraction.build(num, denominator * fraction.denominator);
    }
    public Fraction less(int number){
        return this.less(new Fraction(number));
    }
    public Fraction less(String fraction){
        return this.less(Fraction.build(fraction));
    }

    @Override
    public Fraction divide(Fraction fraction) {
        return this.multiply(fraction.inverse());
    }
    public Fraction divide(int number) {
        return this.divide(new Fraction(number));
    }
    public Fraction divide(String fraction) {
        return this.divide(Fraction.build(fraction));
    }
    @Override
    public Fraction multiply(Fraction fraction) {
        return Fraction.build(numerator * fraction.numerator, denominator * fraction.denominator);
    }
    public Fraction multiply(int number){
        return Fraction.build(numerator * number , denominator);
    }

    public Fraction multiply(String fraction){
        return multiply(Fraction.build(fraction));
    }
    @Override
    public Fraction exponent(int number) {
        Fraction fraction = Fraction.build(this);
        Fraction fractionReturn = fraction;
        for (int i = number; i >1 ; i--) {
            fractionReturn = fractionReturn.multiply(fraction);
        }
        return fractionReturn;
    }

    @Override
    public Fraction square() {
        return Fraction.build((numerator * numerator), (denominator*denominator));
    }

    @Override
    public Fraction inverse() {
        return Fraction.build(denominator,numerator);
    }

    public void simplify() {
        this.set(simplify(simplifyDirect(),2));
    }
    public Optional<Integer> isMultipleOf(int number, int of){
        if(Math.abs(number) == of) return  Optional.of(1);
        for (int i = 2; i < Math.abs(number); i++){
            int result = i*of;
            if(result == Math.abs(number)) return Optional.of(number > 0 ? i : -i);
            if(result > Math.abs(number)) return Optional.empty();
        }
        return Optional.empty();
    }


     private Fraction simplify(Fraction fraction, int number){
         Optional<Integer> optNum = isMultipleOf(fraction.getNumerator(),number);
         Optional<Integer> optDen = isMultipleOf(fraction.getDenominator(),number);
         if(number>Math.min(Math.abs(fraction.numerator), Math.abs(fraction.denominator))) return fraction;
         if(optNum.isEmpty()  || optDen.isEmpty()) return simplify(fraction,++number);
         return simplify(Fraction.build(optNum.get(), optDen.get()),2);
     }
     private Fraction simplifyDirect(){
         int min = Math.min(Math.abs(numerator), Math.abs(denominator));
         if(min == Math.abs(numerator)){
             Optional<Integer> optDen = isMultipleOf(denominator,min);
             optDen.ifPresent(den->set(numerator > 0 ? 1 : -1, den));
         }else{
             Optional<Integer> optNum = isMultipleOf(numerator,min);
             optNum.ifPresent(num->set(num,1));
         }
         return this;
     }

    @Override
    public String toString() {
        if(isInfinite()) return "Fraction(infinie)";
        if(denominator == 1) return "Fraction(" + numerator + ")";
        return "Fraction(" + numerator +"/" + denominator +')';
    }
    public String get(){
        if(isInfinite()) {
            if(numerator<0) return "-infinie";
            return "+infinie";
        };
        if(denominator == 1) return String.valueOf(numerator);
        return  + numerator +"/" + denominator ;
    }
    public Optional<Double> toDouble() {
        try{
            double d = Double.parseDouble(String.valueOf(numerator)) / Double.parseDouble(String.valueOf(denominator));
            return Optional.of(d);
        }catch (ArithmeticException ae){
            return Optional.empty();
        }
    }
    public Optional<Float> toFloat() {
        try{
            float d = Float.parseFloat(String.valueOf(numerator)) / Float.parseFloat(String.valueOf(denominator));
            return Optional.of( (d));
        }catch (ArithmeticException ae){
            return Optional.empty();
        }
    }
    public void set(Fraction fraction){
        this.denominator = fraction.denominator;
        this.numerator = fraction.numerator;
    }
    public void set(int numerator, int denominator){
        this.denominator = denominator;
        this.numerator = numerator;
    }

    public boolean isInfinite(){
        return (numerator != 0 && denominator == 0 );
    }
    public boolean isNegative(){
        return numerator < 0;
    }
}
