import businessLogic.Convertor;
import businessLogic.Operations;
import dataModels.Monomial;
import dataModels.Polynomial;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OperationsTest {

    static Operations operations = new Operations();
    static Convertor convertor = new Convertor();

    @ParameterizedTest
    @MethodSource("provideInputForAddPolynomials")
    public void testAddPolynomials(Polynomial polynomial1, Polynomial polynomial2, Polynomial result){
        assertEquals(result, operations.addOrSubtractPolynomials(polynomial1, polynomial2, "+"));
    }


    public static List<Arguments> provideInputForAddPolynomials() {
        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("3X^3+X^2+6"), convertor.convertStringToPolynomial("2X^2+1"), convertor.convertStringToPolynomial("3X^3+3X^2+7")));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("3X^3+X^2+6"), convertor.convertStringToPolynomial("4X^3+5X^2-8"), convertor.convertStringToPolynomial("7X^3+6X^2-2")));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("3X^2+6"), convertor.convertStringToPolynomial("0"), convertor.convertStringToPolynomial("3X^2+6")));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("0"), convertor.convertStringToPolynomial("3X^2+6"), convertor.convertStringToPolynomial("3X^2+6")));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("0"), convertor.convertStringToPolynomial("0"), convertor.convertStringToPolynomial("0")));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("4X^2-7"), convertor.convertStringToPolynomial("-4X^2+7"), convertor.convertStringToPolynomial("0")));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("400X^3-700"), convertor.convertStringToPolynomial("300X^3+100X^2+600"), convertor.convertStringToPolynomial("700X^3+100X^2-100")));
        return arguments;
    }

    @ParameterizedTest
    @MethodSource("provideInputForSubtractPolynomials")
    public void testSubtractPolynomials(Polynomial polynomial1, Polynomial polynomial2, Polynomial result) {
        assertEquals(result, operations.addOrSubtractPolynomials(polynomial1, polynomial2, "-"));
    }

    public static List<Arguments> provideInputForSubtractPolynomials() {
        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("3X^3+X^2+6"), convertor.convertStringToPolynomial("2X^2+1"), convertor.convertStringToPolynomial("3X^3-X^2+5")));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("3X^3+X^2+6"), convertor.convertStringToPolynomial("4X^3+5X^2-8"), convertor.convertStringToPolynomial("-X^3-4X^2+14")));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("3X^2+6"), convertor.convertStringToPolynomial("0"), convertor.convertStringToPolynomial("3X^2+6")));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("0"), convertor.convertStringToPolynomial("3X^2+6"), convertor.convertStringToPolynomial("-3X^2-6")));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("0"), convertor.convertStringToPolynomial("0"), convertor.convertStringToPolynomial("0")));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("4X^2-7"), convertor.convertStringToPolynomial("-4X^2+7"), convertor.convertStringToPolynomial("8X^2-14")));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("400X^3-700"), convertor.convertStringToPolynomial("300X^3+100X^2+600"), convertor.convertStringToPolynomial("100X^3-100X^2-1300")));
        return arguments;
    }

    @ParameterizedTest
    @MethodSource("provideInputForDerivativePolynomial")
    public void testDerivativePolynomial(Polynomial polynomial, Polynomial result) {
        assertEquals(result, operations.derivatePolynomial(polynomial));
    }

    public static List<Arguments> provideInputForDerivativePolynomial() {
        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("4X^3"), convertor.convertStringToPolynomial("12X^2")));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("-4X^3"), convertor.convertStringToPolynomial("-12X^2")));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("4X"), convertor.convertStringToPolynomial("4")));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("-3X"),convertor.convertStringToPolynomial("-3")));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("-3"),convertor.convertStringToPolynomial("0")));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("4"),convertor.convertStringToPolynomial("0")));
        return arguments;
    }

    @ParameterizedTest
    @MethodSource("provideInputForIntegratePolynomial")
    public void testIntegratePolynomial(Polynomial polynomial, Polynomial result) {
        assertEquals(result, operations.integratePolynomial(polynomial));
    }

    public static List<Arguments> provideInputForIntegratePolynomial() {
        List<Arguments> arguments = new ArrayList<>();
        Polynomial p1 = convertor.convertStringToPolynomial("X^4");
        Polynomial p2 = convertor.convertStringToPolynomial("2X^2");
        Polynomial p3 = convertor.convertStringToPolynomial("4X");
        Polynomial p4 = convertor.convertStringToPolynomial("0.33X^3-X^2");

        p1.getMonomials().put(1, new Monomial(0, -1));
        p2.getMonomials().put(1, new Monomial(0, -1));
        p3.getMonomials().put(1, new Monomial(0, -1));
        p4.getMonomials().put(2, new Monomial(0, -1));

        arguments.add(Arguments.of(convertor.convertStringToPolynomial("4X^3"), p1));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("4X"), p2));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("4"), p3));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("X^2-2X"), p4));

        System.out.println(p4.displayPolynomial());
        System.out.println(operations.integratePolynomial(convertor.convertStringToPolynomial("X^2-2X")).displayPolynomial());

        return arguments;
    }

    @ParameterizedTest
    @MethodSource("provideInputForMultiplyPolynomial")
    public void testMultiplyPolynomial(Polynomial polynomial1, Polynomial polynomial2, Polynomial result) {
        assertEquals(result, operations.multiplyPolynomials(polynomial1, polynomial2));
    }

    public static List<Arguments> provideInputForMultiplyPolynomial() {
        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("3X"), convertor.convertStringToPolynomial("2X^2+3"), convertor.convertStringToPolynomial("6X^3+9X")));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("6"), convertor.convertStringToPolynomial("5X+3"), convertor.convertStringToPolynomial("30X+18")));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("-4X"), convertor.convertStringToPolynomial("-4X"), convertor.convertStringToPolynomial("16X^2")));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("0"), convertor.convertStringToPolynomial("3X^2+6"), convertor.convertStringToPolynomial("0")));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("3X^2+6"), convertor.convertStringToPolynomial("0"), convertor.convertStringToPolynomial("0")));
        return arguments;
    }

    @ParameterizedTest
    @MethodSource("provideInputForDividePolynomial")
    public void testDividePolynomial(Polynomial polynomial1, Polynomial polynomial2, List<Polynomial> result) {
        List<Polynomial> p = operations.dividePolynomials(polynomial1, polynomial2);
        assertEquals(result, operations.dividePolynomials(polynomial1, polynomial2));
    }

    public static List<Arguments> provideInputForDividePolynomial() {
        List<Arguments> arguments = new ArrayList<>();
        List<Polynomial> result1 = new ArrayList<>();
        List<Polynomial> result2 = new ArrayList<>();
        List<Polynomial> result3 = new ArrayList<>();
        List<Polynomial> result4 = new ArrayList<>();
        List<Polynomial> result5 = new ArrayList<>();
        result1.add(0, convertor.convertStringToPolynomial("2X+1"));
        result1.add(1, convertor.convertStringToPolynomial("0"));
        result2.addFirst(convertor.convertStringToPolynomial("0.60X^2 + 0.80X"));
        result2.add(1, convertor.convertStringToPolynomial("2"));
        result3.add(0, convertor.convertStringToPolynomial("1.6666666666666667"));
        result3.add(1, convertor.convertStringToPolynomial("0"));
        result4.addFirst(new Polynomial());
        result4.add(1, new Polynomial());
        result4.getFirst().getMonomials().put(0, new Monomial(0.0, 0));
        result4.get(1).getMonomials().put(0, new Monomial(0.0, 0));
        result5.add(0, convertor.convertStringToPolynomial("1.6666666666666667X - 0.44444444444444464"));
        result5.add(1, convertor.convertStringToPolynomial("3.222222222222223"));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("4X+2"), convertor.convertStringToPolynomial("2"), result1));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("3X^3+4X^2+2"), convertor.convertStringToPolynomial("5X"), result2));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("5"), convertor.convertStringToPolynomial("3"), result3));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("0"), convertor.convertStringToPolynomial("3"), result4));
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("5X^2+7X+1"), convertor.convertStringToPolynomial("3X+5"), result5));
        return arguments;
    }

    @ParameterizedTest
    @MethodSource("provideInputForDividePolynomialsWith0")
    public void testDivideWith0(Polynomial polynomial1, Polynomial polynomial2) {
        assertThrows(IllegalArgumentException.class , () -> operations.dividePolynomials(polynomial1, polynomial2));
    }

    public static List<Arguments> provideInputForDividePolynomialsWith0() {
        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("4X^2+3"), convertor.convertStringToPolynomial("0")));
        return arguments;
    }

    @ParameterizedTest
    @MethodSource("provideInputForDividePolynomialsWhenDegreeOfFirstSmaller")
    public void testDivideWhenDegreeOfFirstSmaller(Polynomial polynomial1, Polynomial polynomial2) {
        assertThrows(IllegalArgumentException.class , () -> operations.dividePolynomials(polynomial1, polynomial2));
    }

    public static List<Arguments> provideInputForDividePolynomialsWhenDegreeOfFirstSmaller() {
        List< Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.of(convertor.convertStringToPolynomial("4X^2+3X"), convertor.convertStringToPolynomial("X^3-1")));
        return arguments;
    }
}
