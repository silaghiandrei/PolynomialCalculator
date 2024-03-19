package dataModels;

import java.util.Objects;

public class Monomial {
    private Integer degree;
    private Number coefficient;

    public Monomial(Number coefficient, Integer degree) {
        this.coefficient = coefficient;
        this.degree = degree;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public Number getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Number coefficient) {
        this.coefficient = coefficient;
    }

    public String toString(){
        return "[ " + coefficient + ", " + degree + " ]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Monomial monomial = (Monomial) o;
        return Objects.equals(coefficient, monomial.coefficient) && Objects.equals(degree, monomial.degree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coefficient, degree);
    }
}