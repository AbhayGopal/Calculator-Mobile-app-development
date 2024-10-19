package com.example.androidcalculator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView resultTextView;
    private Calculator calculator;
    private double firstNumber = 0;
    private String currentOperation = "";
    private StringBuilder fullExpression = new StringBuilder();
    private boolean isNewOperation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.resultTextView);
        calculator = new Calculator();

        setupButtons();
    }

    private void setupButtons() {
        int[] buttonIds = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide,
                R.id.btnEquals, R.id.btnClear, R.id.btnDecimal};

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();

        switch (buttonText) {
            case "+":
            case "-":
            case "*":
            case "/":
                handleOperation(buttonText);
                break;
            case "=":
                calculateResult();
                break;
            case "C":
                clearCalculator();
                break;
            default:
                updateDisplay(buttonText);
                break;
        }
    }

    private void handleOperation(String operation) {
        if (!isNewOperation) {
            calculateResult();
        }
        currentOperation = operation;
        firstNumber = Double.parseDouble(resultTextView.getText().toString());
        fullExpression.append(firstNumber).append(" ").append(operation).append(" ");
        isNewOperation = true;
    }

    private void calculateResult() {
        if (!currentOperation.isEmpty()) {
            double secondNumber = Double.parseDouble(resultTextView.getText().toString());
            fullExpression.append(secondNumber);
            double result = 0;

            try {
                switch (currentOperation) {
                    case "+":
                        result = calculator.add(firstNumber, secondNumber);
                        break;
                    case "-":
                        result = calculator.subtract(firstNumber, secondNumber);
                        break;
                    case "*":
                        result = calculator.multiply(firstNumber, secondNumber);
                        break;
                    case "/":
                        result = calculator.divide(firstNumber, secondNumber);
                        break;
                }
                fullExpression.append(" = ").append(result);
                resultTextView.setText(fullExpression.toString());
            } catch (IllegalArgumentException e) {
                resultTextView.setText("Error");
            }

            currentOperation = "";
            isNewOperation = true;
        }
    }

    private void clearCalculator() {
        resultTextView.setText("0");
        firstNumber = 0;
        currentOperation = "";
        fullExpression.setLength(0);
        isNewOperation = true;
    }

    private void updateDisplay(String digit) {
        if (isNewOperation) {
            resultTextView.setText(digit);
            isNewOperation = false;
        } else {
            String currentText = resultTextView.getText().toString();
            if (currentText.equals("0") && !digit.equals(".")) {
                resultTextView.setText(digit);
            } else {
                resultTextView.setText(currentText + digit);
            }
        }
    }
}