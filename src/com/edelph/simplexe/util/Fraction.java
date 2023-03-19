package com.edelph.simplexe.util;

import java.awt.*;
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
    public Fraction plus(Fraction thing) {
        if(denominator == thing.denominator){
            return Fraction.build(numerator+thing.numerator, denominator).simplify();
        }
        int num = (numerator * thing.denominator)+(thing.numerator * denominator);
        return Fraction.build(num, denominator*thing.denominator).simplify();
    }
    public Fraction plus(int number){
        return this.plus(new Fraction(number));
    }

    @Override
    public Fraction less(Fraction thing) {
        if(denominator == thing.denominator){
            return Fraction.build(numerator-thing.numerator, denominator).simplify();
        }
        int num = (numerator * thing.denominator)-(thing.numerator * denominator);
        return Fraction.build(num, denominator*thing.denominator).simplify();
    }
    public Fraction less(int number){
        return this.less(new Fraction(number));
    }

    @Override
    public Fraction divide(Fraction thing) {
        return this.multiply(thing.invert());
    }
    public Fraction divide(int number) {
        return this.divide(new Fraction(number));
    }

    @Override
    public Fraction multiply(Fraction thing) {
        Fraction fraction = thing.simplify();
        return Fraction.build(numerator*fraction.numerator, denominator * fraction.denominator).simplify();
    }
    public Fraction multiply(int thing){
        return Fraction.build(numerator*thing , denominator).simplify();
    }

    @Override
    public Fraction exponent(int number) {
        Fraction fraction = Fraction.build(this).simplify();
        Fraction fractionReturn = fraction;
        for (int i = number; i >1 ; i--) {
            fractionReturn = fractionReturn.multiply(fraction);
        }
        return fractionReturn.simplify();
    }

    @Override
    public Fraction square() {
        return Fraction.build((numerator * numerator), (denominator*denominator));
    }

    @Override
    public Fraction invert() {
        return Fraction.build(denominator,numerator);
    }

    @Override
    public Fraction simplify() {
        this.set(simplify(simplifyDirect(),2));
        return this;
    }
    public Optional<Integer> isMultipleOf(int number, int of){
        if(Math.abs(number) == of) return  Optional.empty();
        for (int i = 2; i < number; i++){
            int result = i*of;
            if( result == number) return Optional.of(i);
            if (result> number) return Optional.empty();
        }
        return Optional.empty();
    }


     private Fraction simplify(Fraction fraction, int number){
         Optional<Integer> optNum = isMultipleOf(fraction.getNumerator(),number);
         Optional<Integer> optDen = isMultipleOf(fraction.getDenominator(),number);
         if(number>Math.min(fraction.numerator, fraction.denominator)) return fraction;
         if(optNum.isEmpty()  || optDen.isEmpty()) return simplify(fraction,++number);
         return simplify(Fraction.build(optNum.get(),optDen.get()),2);
     }
     private Fraction simplifyDirect(){
         int min = Math.min(Math.abs(numerator),Math.abs(denominator));
         if(min == Math.abs(numerator)){
             Optional<Integer> optDen = isMultipleOf(denominator,min);
             optDen.ifPresent(den->set(1,den));
         }else{
             Optional<Integer> optNum = isMultipleOf(numerator,min);
             optNum.ifPresent(num->set(num,1));
         }
         return this;
     }

    @Override
    public String toString() {
        if(denominator == 1) return "Fraction(" + numerator + ")";
        return "Fraction(" + numerator +"/" + denominator +')';
    }
    public String get(){
        if(denominator == 1) return String.valueOf(numerator);
        return  + numerator +"/" + denominator ;
    }
    public Double toDouble() {
        return (double) (numerator / denominator);
    }
    public Float toFloat() {
        return (float) (numerator / denominator);
    }
    public void set(Fraction fraction){
        this.denominator = fraction.denominator;
        this.numerator = fraction.numerator;
    }
    public void set(int numerator, int denominator){
        this.denominator = denominator;
        this.numerator = numerator;
    }
}