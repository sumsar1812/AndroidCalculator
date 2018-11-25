package sumsar1812.github.io.calculator.models;

import android.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class Calculation {
    History history;
    private String text;
    private Operation currentOperation;
    private String currentFirst = "";
    private String currentSecond = "";
    private Double result = null;
    public Calculation(History history) {
        this.history = history;
    }
    public boolean addOperation(Operation operation) {
        if (operation == Operation.EQUALS) {
            calculate();
            return true;
        }
        if (operation == Operation.CLEAR) {
            clear();
            return true;
        }
        if (currentFirst.equals(""))
            currentFirst = "0";
        currentOperation = operation;
        return true;
    }
    public void addToNumber(String number) {
        if (currentOperation == null) {
            if (!currentFirst.isEmpty() && Double.valueOf(currentFirst) == 0)  {
                currentFirst = number;
            } else {
                currentFirst = currentFirst + number;
            }
        } else {
            if (!currentSecond.isEmpty() && Double.valueOf(currentSecond) == 0) {
                currentSecond = number;
            } else {
                currentSecond = currentSecond + number;
            }
        }
    }
    public void clear() {
        currentSecond = "";
        currentFirst = "";
        currentOperation = null;
        result = null;
    }

    public Operation getCurrentOperation() {
        return currentOperation;
    }

    public void calculate() throws ArithmeticException {
        if (currentOperation == null) {
            if (currentFirst.isEmpty()) {
                Calculation c = history.getLatest();
                if (c == null || c.getResult() == null) {
                    result = 0.0;
                    return;
                }
                currentFirst = "" + c.getResult();

            } else {
                result = Double.valueOf(currentFirst);
                return;
            }

        }
        Double first = Double.valueOf(currentFirst);
        Double second;
        if (currentSecond.contains("%")) {
            second = doPercent(first, currentSecond);
        } else if (currentSecond.isEmpty()) {
            second = first;
        } else {
            second = Double.valueOf(currentSecond);
        }
        switch(currentOperation) {
            case PLUS:
                result = first + second;
                break;
            case MINUS:
                result = first - second;
                break;
            case DIVIDE:
                if (second == 0)
                    throw new ArithmeticException();
                result = first / second;
                break;
            case MULTIPLY:
                result = first * second;
                break;
            default:
                result = 0.0;
                break;
        }

    }
    private double doPercent(Double val, String percent) {
        if (percent.equals("%")) {
            return 0;
        }
        Double percentVal = Double.valueOf(percent.substring(0,percent.length() - 1));
        return (percentVal / 100) * val;
    }

    public Double getResult() {
        return result;
    }
    @Override
    public String toString() {
        if (currentOperation == null)
            return currentFirst;
        if (result == null) {
            return currentFirst + " " + currentOperation.toString() + " " + currentSecond;
        } else {
            return currentFirst + " " + currentOperation.toString() + " " + currentSecond + " = " + result;
        }
    }
}
