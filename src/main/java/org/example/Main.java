package org.example;

import businessLogic.Convertor;
import businessLogic.Operations;

import dataModels.Monomial;
import dataModels.Polynomial;

import java.text.ParseException;
import java.util.List;
import java.util.Map;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Convertor convertor = new Convertor();
        Operations operations = new Operations();

        Polynomial p = convertor.convertStringToPolynomial("5X^2+7X+1");
        Polynomial q = convertor.convertStringToPolynomial("3X+5");
        List<Polynomial> result = operations.dividePolynomials(p, q);

        System.out.println(result.getFirst().displayPolynomial());
        System.out.println(result.getLast().displayPolynomial());

        System.out.println(result.getFirst().getMonomials().get(0).getCoefficient());
        System.out.println(result.getFirst().getMonomials().get(1).getCoefficient());

        System.out.println(result.getLast().getMonomials().get(0).getCoefficient());
    }
}