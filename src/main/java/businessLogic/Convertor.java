package businessLogic;

import dataModels.Monomial;
import dataModels.Polynomial;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Convertor {

    private final String[] allowedChars = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "X", "x", "^", "+", "*", "-"};

    public Polynomial convertStringToPolynomial(String string) {
        Polynomial polynomial = new Polynomial();
        String trimmedString = returnTrimmedString(string);
        int index = 0;

        String patternString = "([+-]?\\d*\\.?\\d*)X\\^([+-]?\\d+)|([+-]?\\d*\\.?\\d*)X|([+-]?\\d+(\\.\\d+)?)";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(trimmedString);

        while (matcher.find()) {
            String monomialString = matcher.group();
            Monomial monomial;
            try {
                monomial = matchStringToMonomial(monomialString);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            polynomial.addMonomial(index++, monomial);
        }
        polynomial.sortPolynomial();
        return polynomial;
    }

    public String returnTrimmedString(String string) {
        String trimmedString = string.replaceAll(" ", "");
        trimmedString = trimmedString.replaceAll("\\*", "");
        trimmedString = trimmedString.replaceAll("x", "X");
        return trimmedString;
    }

    public Monomial matchStringToMonomial(String input) throws ParseException {
        Number coefficient = returnCoefficient(input);
        Integer degree = returnDegree(input);
        return new Monomial(coefficient, degree);
    }

    public Number returnCoefficient(String input) throws ParseException {
        return input.charAt(0) == '+' ? returnCoefficientWithoutPLus(input.substring(1)) : returnCoefficientWithoutPLus(input);
    }

    public Double returnCoefficientWithoutPLus(String input) {
        double coefficient;
        if(input.contains("X")) {
            int indexOfX = input.indexOf("X");
            String coefficientString = input.substring(0, indexOfX);
            if (coefficientString.equals("-")) {
                coefficient = -1.0;
            } else if (coefficientString.isEmpty()) {
                coefficient = 1.0;
            } else {
                coefficient = Double.parseDouble(coefficientString);
            }
        } else {
            coefficient = Double.parseDouble(input);
        }
        return coefficient;
    }

    public Integer returnDegree(String input) {
        int degree;
        if (input.contains("X^")) {
            int indexOfPowerSign = input.indexOf("^");
            String degreeString = input.substring(indexOfPowerSign + 1);
            degree = Integer.parseInt(degreeString);
        } else if(input.contains("X") && !input.contains("^")) {
            degree = 1;
        } else {
            degree = 0;
        }
        return degree;
    }

    public Integer checkInput(String input) {
        input = returnTrimmedString(input);
        if(input == null || input.isEmpty()) {
            return 0;
        } else {
            if (input.contains("^.") || input.contains("^+") || input.contains("^-")
                    || input.contains(".^") || input.contains("+^") || input.contains("-^")
                    || input.contains(" ^") || input.contains("^ ") || input.contains("XX")
                    || input.contains("xx")) {
                return 0;
            }
            boolean foundX = false;
            for (char c : input.toCharArray()) {
                String currentChar = String.valueOf(c);
                if (!isCharacterAllowed(currentChar)) {
                    return 0;
                }
                if (foundX && Character.isDigit(c)) {
                    return 0;
                }
                foundX = (c == 'X');
            }
        }
        return 1;
    }

    public boolean isCharacterAllowed(String character) {
        for (String allowed : allowedChars) {
            if (allowed.equals(character)) {
                return true;
            }
        }
        return false;
    }

}

