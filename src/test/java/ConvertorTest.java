import businessLogic.Convertor;
import dataModels.Monomial;
import dataModels.Polynomial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class ConvertorTest {
    Convertor convertor;

    @BeforeEach
    public void createConvertor() {
            convertor = new Convertor();
    }

    @ParameterizedTest
    @MethodSource("provideInputForDegree")
    public void testReturnDegree(String input, Integer degree) {
        assertEquals(degree, convertor.returnDegree(input));
    }

    public static List<Arguments> provideInputForDegree() {
        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.of("3X^3", 3));
        arguments.add(Arguments.of("2X", 1));
        arguments.add(Arguments.of("7", 0));
        return arguments;
    }

    @ParameterizedTest
    @MethodSource("provideInputForCoefficient")
    public void testReturnCoefficient(String input, Number coefficient) {
        try {
            assertEquals(coefficient, convertor.returnCoefficient(input));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Arguments> provideInputForCoefficient() {
        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.of("3X", 3.0));
        arguments.add(Arguments.of("-3X", -3.0));
        arguments.add(Arguments.of("4.0X", 4.0));
        arguments.add(Arguments.of("-5.0X", -5.0));
        arguments.add(Arguments.of("5.4X", 5.4));
        arguments.add(Arguments.of("-7.2X", -7.2));
        arguments.add(Arguments.of("X", 1.0));
        arguments.add(Arguments.of("-X", -1.0));
        arguments.add(Arguments.of("7", 7.0));
        arguments.add(Arguments.of("-7", -7.0));
        arguments.add(Arguments.of("4.0", 4.0));
        arguments.add(Arguments.of("-7.0", -7.0));
        arguments.add(Arguments.of("7.5", 7.5));
        arguments.add(Arguments.of("-7.2", -7.2));
        return arguments;
    }

    @ParameterizedTest
    @MethodSource("provideInputForMatchStringToMonomial")
    public void testMatchStringToPolynomial(String input, Number coefficient, Integer degree){
        try {
            assertEquals(new Monomial(coefficient, degree), convertor.matchStringToMonomial(input));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Arguments> provideInputForMatchStringToMonomial() {
        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.of("3X^2", 3.0, 2));
        arguments.add(Arguments.of("3.4X^5", 3.4, 5));
        arguments.add(Arguments.of("-3X^3", -3.0, 3));
        arguments.add(Arguments.of("-3.4X^2", -3.4, 2));
        arguments.add(Arguments.of("X^2", 1.0, 2));
        arguments.add(Arguments.of("-X^2", -1.0, 2));
        arguments.add(Arguments.of("3X", 3.0, 1));
        arguments.add(Arguments.of("3.4X", 3.4, 1));
        arguments.add(Arguments.of("-3X", -3.0, 1));
        arguments.add(Arguments.of("-3.4X", -3.4, 1));
        arguments.add(Arguments.of("X", 1.0, 1));
        arguments.add(Arguments.of("-X", -1.0, 1));
        arguments.add(Arguments.of("3", 3.0, 0));
        arguments.add(Arguments.of("3.4", 3.4, 0));
        arguments.add(Arguments.of("-3", -3.0, 0));
        arguments.add(Arguments.of("-3.4", -3.4, 0));
        arguments.add(Arguments.of("1", 1.0, 0));
        arguments.add(Arguments.of("-1", -1.0, 0));
        return arguments;
    }

    @ParameterizedTest
    @MethodSource("provideInputForReturnTrimmedString")
    public void testReturnTrimmedString(String input) {
        assertEquals("3X^4-4X^2+5X", convertor.returnTrimmedString(input));
    }

    public static List<Arguments> provideInputForReturnTrimmedString() {
        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.of("3*X^4-  4x^2+ 5*x"));
        return arguments;
    }

    @ParameterizedTest
    @MethodSource("provideInputForConvertStringToPolynomial")
    public void testConvertStringToPolynomial(String input, Polynomial polynomial) {
        assertEquals(polynomial, convertor.convertStringToPolynomial(input));
    }

    public static List<Arguments> provideInputForConvertStringToPolynomial() {
        List<Arguments> arguments = new ArrayList<>();
        Polynomial polynomial = new Polynomial();
        polynomial.addMonomial(0, new Monomial(3.0, 2));
        polynomial.addMonomial(1, new Monomial(23.0, 0));
        arguments.add(Arguments.of("3X^2 + 23", polynomial));
        return arguments;
    }

    @ParameterizedTest
    @MethodSource("provideInputForCheckInput")
    public void testCheckInput(String input, Integer result) {
        assertEquals(result, convertor.checkInput(input));
    }

    public static List<Arguments> provideInputForCheckInput() {
        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.of("2X^2-3x+1", 1));
        arguments.add(Arguments.of("2X^.2+#", 0));
        return arguments;
    }

    @ParameterizedTest
    @MethodSource("provideInputForIsCharacterAllowed")
    public void testIsCharacterAllowed(String input, boolean value) {
        assertEquals(value, convertor.isCharacterAllowed(input));
    }

    public static List<Arguments> provideInputForIsCharacterAllowed() {
        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.of("1", true));
        arguments.add(Arguments.of("X", true));
        arguments.add(Arguments.of("x", true));
        arguments.add(Arguments.of("+", true));
        arguments.add(Arguments.of("-", true));
        arguments.add(Arguments.of("*", true));
        arguments.add(Arguments.of("^", true));
        arguments.add(Arguments.of("%", false));
        arguments.add(Arguments.of(".", false));
        return arguments;
    }
}
