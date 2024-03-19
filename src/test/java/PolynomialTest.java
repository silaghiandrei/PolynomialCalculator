import businessLogic.Convertor;
import dataModels.Monomial;
import dataModels.Polynomial;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PolynomialTest {


    @ParameterizedTest
    @MethodSource("provideInputAddMonomial")
    public void testAddMonomial(Integer index, Number coefficient, Integer degree, Monomial monomial) {
        Polynomial polynomial = new Polynomial();
        int counter = 0;
        while(counter < 5) {
            polynomial.addMonomial(counter, new Monomial(coefficient, degree));
            assertEquals(monomial, polynomial.getMonomials().get(counter));
            counter++;
        }
    }

    public static List<Arguments> provideInputAddMonomial() {
        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.of(0, 3, 2, new Monomial(3, 2)));
        arguments.add(Arguments.of(1, 4.0, 1, new Monomial(4.0, 1)));
        arguments.add(Arguments.of(2, 3.2, 0, new Monomial(3.2, 0)));
        arguments.add(Arguments.of(3, -3.2, 4, new Monomial(-3.2, 4)));
        arguments.add(Arguments.of(4, -3, 2, new Monomial(-3, 2)));
        return arguments;
    }

    @ParameterizedTest
    @MethodSource("provideInputDisplayPolynomial")
    public void testDisplayPolynomial(String result, Polynomial input) {
        System.out.println(input.displayPolynomial());
        assertEquals(result, input.displayPolynomial());
    }

    public static List<Arguments> provideInputDisplayPolynomial() {
        List<Arguments> arguments = new ArrayList<>();
        Polynomial polynomial1 = new Polynomial();
        Polynomial zeroPolynomial = new Polynomial();
        polynomial1.getMonomials().put(0, new Monomial(5, 2));
        polynomial1.getMonomials().put(1, new Monomial(-2, 1));
        polynomial1.getMonomials().put(2, new Monomial(3, 0));
        polynomial1.getMonomials().put(3, new Monomial(0, -1));
        zeroPolynomial.getMonomials().put(0, new Monomial(0, 0));
        arguments.add(Arguments.of("5X^2 - 2X + 3 + C", polynomial1));
        arguments.add(Arguments.of("0", zeroPolynomial));
        return arguments;
    }
}
