package sumsar1812.github.io.calculator.models;

import java.util.UUID;

public class Calculation {
    History history;
    private UUID uuid;
    private String text;
    private Operation currentOperation;
    private String currentFirst = "";
    private String currentSecond = "";
    private Double result = null;
    public Calculation(History history) {
        this.history = history;
        uuid = UUID.randomUUID();
    }
    public OperationAction addOperation(Operation operation) {
        if (operation == Operation.CLEARALL) {
            return OperationAction.CLEAR;
        }
        if (operation == Operation.EQUALS) {
            calculate();
            if (result == null)
                return OperationAction.CONTINUE;
            return OperationAction.CREATE;
        }
        if (operation == Operation.CLEAR) {
            clear();
            return OperationAction.CONTINUE;
        }
        if (currentFirst.equals("")) {
            if (history.getLatest() != null) {
                currentFirst = "" + history.getLatest().getResult();
            } else {
                currentFirst = "0";
            }
        }
        if (currentOperation != null && !currentSecond.isEmpty()) {
            calculate();
            return OperationAction.CREATE_AND_ADD_OP;
        }
        currentOperation = operation;
        return OperationAction.CONTINUE;
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
    public String getCurrentSecond() {
        return currentSecond;
    }

    public void calculate() throws ArithmeticException {
        if (currentFirst.contains("%")) {
            currentFirst = "";
            result = null;
            return;
        }
        if (currentOperation == null) {
            if (currentFirst.isEmpty()) {
                Calculation c = history.getLatest();
                if (c == null || c.getResult() == null) {
                    result = null;
                    return;
                }
                currentFirst = "" + c.getResult();
                currentOperation = c.getCurrentOperation();
                currentSecond = c.getCurrentSecond();
                if (currentOperation == null ) {
                    if (currentFirst.isEmpty()) {
                        result = null;
                        return;
                    } else {
                        result = Double.valueOf(currentFirst);
                        return;
                    }
                }
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
                result = null;
                break;
        }

    }
    public UUID getUuid() {
        return uuid;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Calculation)
            return this.uuid.equals(((Calculation)obj).uuid);
        return false;
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
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
        if (currentOperation == null && result == null)
            return currentFirst;
        else if (result != null && currentOperation == null) {
            return currentFirst + " = " + result;
        }
        if (result == null) {
            return currentFirst + " " + currentOperation.toString() + " " + currentSecond;
        } else {
            return currentFirst + " " + currentOperation.toString() + " " + currentSecond + " = " + result;
        }
    }
}
