package dataModels;

import java.util.*;

public class Polynomial {
    private final Map<Integer,Monomial> monomials = new LinkedHashMap<>();

    public void addMonomial(Integer index, Monomial  monomial){
        Double coef = monomial.getCoefficient().doubleValue();
        if (!coef.equals(0.0)) {
            monomials.put(index, monomial);
        }
    }

    public Map<Integer, Monomial> getMonomials() {
        return monomials;
    }

    public String displayPolynomial() {
        StringBuilder poly = new StringBuilder();
        if (!monomials.isEmpty()) {
            for (Map.Entry<Integer, Monomial> m : monomials.entrySet()) {
                Number displayCoef = m.getValue().getCoefficient().doubleValue();
                int displayDegree = m.getValue().getDegree();
                String sign = displayCoef.doubleValue() >= 0 ? "+" : "-";
                poly.append(sign);
                poly.append(" ");
                String stringCoef = displayCoef.doubleValue() != displayCoef.intValue() ? String.format("%.2f", displayCoef.doubleValue()) : String.format("%d", displayCoef.intValue());
                if(stringCoef.charAt(0) == '-') {
                    stringCoef = stringCoef.substring(1);
                }
                if(displayDegree == -1) {
                    stringCoef = "C";
                }
                if(stringCoef.equals("1") && displayDegree > 0) {
                    stringCoef = "";
                }
                poly.append(stringCoef);
                if(displayDegree > 1) {
                    poly.append("X^");
                    poly.append(displayDegree);
                    poly.append(" ");
                } else if (displayDegree == 1){
                    poly.append("X");
                    poly.append(" ");
                } else {
                    poly.append(" ");
                }
            }
        } else {
            poly.append("0");
        }
        String polyString = poly.toString();
        if(polyString.charAt(0) == '+') {
            polyString = polyString.substring(2);
        }
        return polyString.substring(0, polyString.length() - 1);
    }

    public void sortPolynomial() {
        List<Map.Entry<Integer, Monomial>> monomialList = new ArrayList<>(monomials.entrySet());
        monomialList.sort((firstMonomial, secondMonomial) -> secondMonomial.getValue().getDegree() - firstMonomial.getValue().getDegree());

        LinkedHashMap<Integer, Monomial> sortedMonomials = new LinkedHashMap<>();
        int index = 0;
        for(Map.Entry<Integer, Monomial> monomial : monomialList) {
            sortedMonomials.put(index, monomial.getValue());
            index++;
        }
        monomials.clear();
        monomials.putAll(sortedMonomials);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Polynomial that = (Polynomial) o;
        return Objects.equals(monomials, that.monomials);
    }

    @Override
    public int hashCode() {
        return Objects.hash(monomials);
    }

}