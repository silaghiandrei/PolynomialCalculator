package gui;

import businessLogic.Convertor;
import dataModels.Polynomial;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import businessLogic.Operations;

public class CalculatorGUI extends JFrame{
    private JPanel calcPanel;
    private JPanel operationsPanel;
    private JTextField pTextField;
    private JTextField qTextField;
    private JTextField rTextField;
    private JLabel rLabel;
    private JButton additionButton;
    private JButton subtractionButton;
    private JButton multiplicationButton;
    private JButton derivationButton;
    private JButton divisionButton;
    private JButton integrationButton;
    private JButton clearButton;
    private JButton delButton;
    private JButton a1Button;
    private JButton a4Button;
    private JButton a7Button;
    private JButton a2Button;
    private JButton a5Button;
    private JButton a8Button;
    private JButton a3Button;
    private JButton a6Button;
    private JButton a9Button;
    private JButton xButton;
    private JButton plusButton;
    private JButton minusButton;
    private JButton timesButton;
    private JLabel pLabel;
    private JLabel qLabel;
    private JButton a0Button;
    private JButton powerButton;

    public CalculatorGUI() {
        setTitle("Polynomial calculator");
        setContentPane(calcPanel);
        setSize(420, 600);
        setMinimumSize(new Dimension(420, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pTextField.setBorder(null);
        qTextField.setBorder(null);
        rTextField.setBorder(null);

        Convertor convertor = new Convertor();
        Operations operations = new Operations();

        JButton[] buttons = {
                additionButton, subtractionButton, multiplicationButton,
                derivationButton, divisionButton, integrationButton,
                clearButton, delButton, a1Button, a4Button, a7Button,
                a2Button, a5Button, a8Button, a3Button, a6Button, a9Button,
                xButton, plusButton, minusButton, timesButton, a0Button, powerButton
        };

        for (JButton button : buttons) {
            button.setBorderPainted(false);
            button.setFocusable(false);
        }

        additionButton.addActionListener(e -> {
            qLabel.setVisible(true);
            qTextField.setVisible(true);
            if (convertor.checkInput(pTextField.getText()).equals(1) && convertor.checkInput(qTextField.getText()).equals(1)) {
                Polynomial p = convertor.convertStringToPolynomial(pTextField.getText());
                Polynomial q = convertor.convertStringToPolynomial(qTextField.getText());
                if (p.getMonomials().isEmpty() && q.getMonomials().isEmpty()) {
                    rTextField.setText("0");
                } else {
                    Polynomial result = operations.addOrSubtractPolynomials(p, q, "+");
                    rTextField.setText(result.displayPolynomial());
                }
            } else {
                showErrorDialog("Input Error", "INCORRECT OR MISSING INPUT/INPUTS!");
                System.out.println("INCORRECT OR MISSING INPUT/INPUTS, CHECK AGAIN!");
                throw new IllegalArgumentException();
            }
        });

        subtractionButton.addActionListener(e -> {
            qLabel.setVisible(true);
            qTextField.setVisible(true);
            if (convertor.checkInput(pTextField.getText()).equals(1) && convertor.checkInput(qTextField.getText()).equals(1)) {
                Polynomial p = convertor.convertStringToPolynomial(pTextField.getText());
                Polynomial q = convertor.convertStringToPolynomial(qTextField.getText());
                if (p.getMonomials().isEmpty() && q.getMonomials().isEmpty()) {
                    rTextField.setText("0");
                } else {
                    Polynomial result = operations.addOrSubtractPolynomials(p, q, "-");
                    if (result.getMonomials().isEmpty()) {
                        rTextField.setText("0");
                    } else {
                        rTextField.setText(result.displayPolynomial());
                    }
                }
            } else {
                showErrorDialog("Input Error", "INCORRECT OR MISSING INPUT/INPUTS!");
                System.out.println("INCORRECT OR MISSING INPUT/INPUTS, CHECK AGAIN!");
                throw new IllegalArgumentException();
            }
        });

        multiplicationButton.addActionListener(e -> {
            qLabel.setVisible(true);
            qTextField.setVisible(true);
            if (convertor.checkInput(pTextField.getText()).equals(1) && convertor.checkInput(qTextField.getText()).equals(1)) {
                Polynomial p = convertor.convertStringToPolynomial(pTextField.getText());
                Polynomial q = convertor.convertStringToPolynomial(qTextField.getText());
                if (p.getMonomials().isEmpty() || q.getMonomials().isEmpty()) {
                    rTextField.setText("0");
                } else {
                    Polynomial result = operations.multiplyPolynomials(p, q);
                    rTextField.setText(result.displayPolynomial());
                }
            } else {
                showErrorDialog("Input Error", "INCORRECT OR MISSING INPUT/INPUTS!");
                System.out.println("INCORRECT OR MISSING INPUT/INPUTS, CHECK AGAIN!");
                throw new IllegalArgumentException();
            }
        });

        divisionButton.addActionListener(e -> {
            qLabel.setVisible(true);
            qTextField.setVisible(true);
            if (convertor.checkInput(pTextField.getText()).equals(1) && convertor.checkInput(qTextField.getText()).equals(1)) {
                Polynomial p = convertor.convertStringToPolynomial(pTextField.getText());
                Polynomial q = convertor.convertStringToPolynomial(qTextField.getText());
                //adauga pentru impartitor = 0
                if (!p.getMonomials().isEmpty() && (p.getMonomials().get(0).getDegree() < q.getMonomials().get(0).getDegree())) {
                    showErrorDialog("Input Error", "THE DEGREE OF THE NUMERATOR IS SMALLER\n " +
                            "THAN THE DEGREE OF THE DENOMINATOR,\n " +
                            "CANNOT EFFECTUATE THE DIVISION!");
                }
                if (p.getMonomials().isEmpty() && !(q.getMonomials().isEmpty())) {
                    rTextField.setText("0");
                } else {
                    List<Polynomial> result = operations.dividePolynomials(p, q);
                    String quotient = result.getFirst().displayPolynomial();
                    String reminder = result.getLast().getMonomials().isEmpty() ? "0" : result.getLast().displayPolynomial();
                    rTextField.setText("Q: " + quotient + ", R: " + reminder);
                }
            } else {
                showErrorDialog("Input Error", "INCORRECT OR MISSING INPUT/INPUTS!");
                System.out.println("INCORRECT OR MISSING INPUT/INPUTS, CHECK AGAIN!");
                throw new IllegalArgumentException();
            }
        });

        derivationButton.addActionListener(e -> {
            qLabel.setVisible(false);
            qTextField.setVisible(false);
            if (convertor.checkInput(pTextField.getText()).equals(1)) {
                Polynomial p = convertor.convertStringToPolynomial(pTextField.getText());
                Polynomial result = operations.derivatePolynomial(p);
                rTextField.setText(result.displayPolynomial());
            } else {
                showErrorDialog("Input Error", "INCORRECT OR MISSING INPUT/INPUTS!");
                System.out.println("INCORRECT OR MISSING INPUT/INPUTS, CHECK AGAIN!");
                throw new IllegalArgumentException();
            }
        });

        integrationButton.addActionListener(e -> {
            qLabel.setVisible(false);
            qTextField.setVisible(false);
            if (convertor.checkInput(pTextField.getText()).equals(1)) {
                Polynomial p = convertor.convertStringToPolynomial(pTextField.getText());
                Polynomial result = operations.integratePolynomial(p);
                rTextField.setText(result.displayPolynomial());
            } else {
                showErrorDialog("Input Error", "INCORRECT OR MISSING INPUT/INPUTS!");
                System.out.println("INCORRECT OR MISSING INPUT/INPUTS, CHECK AGAIN!");
                throw new IllegalArgumentException();
            }
        });

        JButton[] inputButtons = {a1Button, a4Button, a7Button, a2Button, a5Button,
                a8Button, a3Button, a6Button, a9Button, a0Button, xButton, powerButton,
                plusButton, minusButton, timesButton};

        for(JButton button : inputButtons) {
            button.addActionListener(e -> {
                JTextField selectedTextField = null;
                if (pTextField != null && pTextField.isFocusOwner()) {
                    selectedTextField = pTextField;
                } else if(qTextField != null && qTextField.isFocusOwner()) {
                    selectedTextField = qTextField;
                }
                if (selectedTextField != null) {
                    selectedTextField.setText(selectedTextField.getText() + button.getText());
                }
            });
        }

        clearButton.addActionListener(e-> {
            if (pTextField != null && pTextField.isFocusOwner()) {
                pTextField.setText(null);
            } else if(qTextField != null && qTextField.isFocusOwner()) {
                qTextField.setText(null);
            }
        });

        delButton.addActionListener(e-> {
            if (pTextField != null && pTextField.isFocusOwner()) {
                if (!pTextField.getText().isEmpty()) {
                    pTextField.setText(pTextField.getText().substring(0, pTextField.getText().length() - 1));
                }
            }
            if(qTextField != null && qTextField.isFocusOwner()) {
                if (!qTextField.getText().isEmpty()) {
                    qTextField.setText(qTextField.getText().substring(0, qTextField.getText().length() - 1));
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        CalculatorGUI calculator = new CalculatorGUI();
    }

    public void showErrorDialog(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
