package sumsar1812.github.io.calculator.models;

public enum Operation {
    PLUS,
    MINUS,
    DIVIDE,
    MULTIPLY,
    CLEAR,
    CLEARALL,
    EQUALS;
    public static Operation getOperation(String val) {
        switch (val) {
            case "+":
                return PLUS;
            case "-":
                return MINUS;
            case "÷":
                return DIVIDE;
            case "x":
                return MULTIPLY;
            case "C":
                return CLEAR;
            case "=":
                return EQUALS;
            case "AC":
                return CLEARALL;
            default:
                return null;
        }
    }
    @Override
    public String toString() {
        switch(this) {
            case PLUS:
                return "+";
            case MINUS:
                return "-";
            case DIVIDE:
                return "÷";
            case MULTIPLY:
                return "x";
            case CLEAR:
                return "C";
            case EQUALS:
                return "=";
            case CLEARALL:
                return "AC";
            default:
                return null;
        }
    }
}
