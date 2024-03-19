package businessLogic;

import dataModels.Monomial;
import dataModels.Polynomial;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Operations {

    public Polynomial addOrSubtractPolynomials(Polynomial firstPolynomial, Polynomial secondPolynomial, String operator) {
        Polynomial resultPolynomial = new Polynomial();

        Map<Integer, Monomial> firstMonoms = firstPolynomial.getMonomials();
        Map<Integer, Monomial> secondMonoms = secondPolynomial.getMonomials();
        Map<Integer, Monomial> resultMonoms = resultPolynomial.getMonomials();

        if (operator.equals("-")) {
            changeSigns(secondPolynomial);
        }
        int firstSize = firstMonoms.size();
        int secondSize = secondMonoms.size();
        int iterFirst = 0;
        int iterSecond = 0;
        int iterRes = 0;

        List<Integer> iterators = iterateThroughBothPolynomials(firstMonoms, secondMonoms, resultMonoms,iterFirst, iterSecond, iterRes, firstSize, secondSize);
        iterFirst = iterators.getFirst();
        iterSecond = iterators.get(1);
        iterRes = iterators.getLast();
        iterRes = iterateThroughOnePolynomial(firstMonoms, resultMonoms, iterFirst, iterRes, firstSize);
        iterRes = iterateThroughOnePolynomial(secondMonoms, resultMonoms, iterSecond, iterRes, secondSize);
        return resultPolynomial;
    }

    public void changeSigns(Polynomial polynomial) {
        for (Map.Entry<Integer, Monomial> m : polynomial.getMonomials().entrySet()) {
            double newCoef = m.getValue().getCoefficient().doubleValue();
            newCoef *= -1;
            m.getValue().setCoefficient(newCoef);
        }
    }

    List<Integer> iterateThroughBothPolynomials(Map<Integer, Monomial> first, Map<Integer,Monomial> second, Map<Integer, Monomial> result,
                                                Integer iterFirst, Integer iterSecond, Integer iterRes, Integer firstSize, Integer secondSize) {
        List<Integer> iterators = new ArrayList<>();

        while (iterFirst < firstSize && iterSecond < secondSize) {
            Monomial firstMonomial = first.get(iterFirst);
            Monomial secondMonomial = second.get(iterSecond);
            int firstDeg = firstMonomial.getDegree();
            int secondDeg = secondMonomial.getDegree();
            if (firstDeg > secondDeg) {
                result.put(iterRes++, firstMonomial);
                iterFirst++;
            } else if (firstDeg < secondDeg) {
                result.put(iterRes++, secondMonomial);
                iterSecond++;
            } else {
                double firstCoef = firstMonomial.getCoefficient().doubleValue();
                double secondCoef = secondMonomial.getCoefficient().doubleValue();
                if (firstCoef + secondCoef != 0) {
                    Monomial sumMonomial = new Monomial(firstCoef + secondCoef, secondDeg);
                    result.put(iterRes++, sumMonomial);
                }
                iterFirst++;
                iterSecond++;
            }
        }
        iterators.add(iterFirst);
        iterators.add(iterSecond);
        iterators.add(iterRes);
        return iterators;
    }

    Integer iterateThroughOnePolynomial(Map<Integer, Monomial> monoms, Map<Integer, Monomial> result, Integer iter, Integer iterRes, Integer size) {
        while (iter < size) {
            result.put(iterRes++, monoms.get(iter));
            iter++;
        }
        return iterRes;
    }

    public Polynomial derivatePolynomial(Polynomial polynomial) {
        Polynomial resultPolynomial = new Polynomial();
        Map<Integer, Monomial> monomials = polynomial.getMonomials();
        for (Map.Entry<Integer, Monomial> monomial : monomials.entrySet()) {
            double coef = monomial.getValue().getCoefficient().intValue();
            Integer degree = monomial.getValue().getDegree();
            coef *= degree;
            degree -= 1;
            monomial.getValue().setCoefficient(coef);
            monomial.getValue().setDegree(degree);
            if (degree >= 0) {
                resultPolynomial.getMonomials().put(monomial.getKey(), monomial.getValue());
            }
        }
        return resultPolynomial;
    }

    public Polynomial integratePolynomial(Polynomial polynomial) {
        Polynomial resultPolynomial = new Polynomial();
        Map<Integer, Monomial> monomials = polynomial.getMonomials();
        for (Map.Entry<Integer, Monomial> monomial : monomials.entrySet()) {
            double coef = monomial.getValue().getCoefficient().intValue();
            Integer degree = monomial.getValue().getDegree();
            degree += 1;
            monomial.getValue().setDegree(degree);
            if (coef % degree == 0) {
                coef /= degree;
                monomial.getValue().setCoefficient(coef);
            } else {
                DecimalFormat format = new DecimalFormat("#.##");
                double newCoef = coef;
                newCoef /= degree;
                monomial.getValue().setCoefficient(Double.parseDouble(format.format(newCoef)));
            }
            resultPolynomial.getMonomials().put(monomial.getKey(), monomial.getValue());
        }
        Monomial constant = new Monomial(0, -1);
        int size = monomials.size();
        resultPolynomial.getMonomials().put(size, constant);
        return resultPolynomial;
    }

    public Polynomial multiplyPolynomials(Polynomial firstPolynomial, Polynomial secondPolynomial) {
        Polynomial resultPolynomial = new Polynomial();
        for (Map.Entry<Integer, Monomial> m1 : firstPolynomial.getMonomials().entrySet()) {
            int index = 0;
            Polynomial tempPolynomial = new Polynomial();
            for (Map.Entry<Integer, Monomial> m2 : secondPolynomial.getMonomials().entrySet()) {
                double coef1 = m1.getValue().getCoefficient().doubleValue();
                double coef2 = m2.getValue().getCoefficient().doubleValue();
                Integer degree1 = m1.getValue().getDegree();
                Integer degree2 = m2.getValue().getDegree();
                Monomial term = new Monomial(coef1 * coef2, degree1 + degree2);
                tempPolynomial.addMonomial(index++, term);
            }
            resultPolynomial = addOrSubtractPolynomials(resultPolynomial, tempPolynomial, "+");
        }
        return resultPolynomial;
    }

    public List<Polynomial> dividePolynomials(Polynomial firstPolynomial, Polynomial secondPolynomial) {
        List<Polynomial> result = new ArrayList<>();
        if(secondPolynomial.getMonomials().isEmpty()) {
            throw new IllegalArgumentException();
        } else if (firstPolynomial.getMonomials().isEmpty() && secondPolynomial.getMonomials().get(0).getDegree() == 0) {
            result.add(new Polynomial());
            result.add(new Polynomial());
            result.getFirst().getMonomials().put(0, new Monomial(0.0, 0));
            result.getLast().getMonomials().put(0, new Monomial(0.0, 0));
        } else if(firstPolynomial.getMonomials().get(0).getDegree() < secondPolynomial.getMonomials().get(0).getDegree()) {
            throw new IllegalArgumentException();
        } else {
            Polynomial quotient = new Polynomial();
            Polynomial tempQuotient = new Polynomial();
            DecimalFormat format = new DecimalFormat("#.##");
            int index = 0;
            while (firstPolynomial.getMonomials().get(0).getDegree() >= secondPolynomial.getMonomials().get(0).getDegree()) {
                Monomial mon1 = firstPolynomial.getMonomials().get(0);
                Monomial mon2 = secondPolynomial.getMonomials().get(0);
                double newCoef = mon1.getCoefficient().doubleValue() / mon2.getCoefficient().doubleValue();
                //double modifiedCoef = Double.parseDouble(format.format(newCoef));
                int newDegree = mon1.getDegree() - mon2.getDegree();
                tempQuotient.addMonomial(0, new Monomial(newCoef, newDegree));
                quotient.addMonomial(index++, new Monomial(newCoef, newDegree));
                Polynomial productPolynomial = multiplyPolynomials(secondPolynomial, tempQuotient);
                firstPolynomial = addOrSubtractPolynomials(firstPolynomial, productPolynomial, "-");
                if(firstPolynomial.getMonomials().isEmpty()) {
                    break;
                }
            }
            result.add(quotient);
            result.add(firstPolynomial);
        }
        return result;
    }
}