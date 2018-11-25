package sumsar1812.github.io.calculator;

import sumsar1812.github.io.calculator.models.Calculation;
import sumsar1812.github.io.calculator.models.History;
import sumsar1812.github.io.calculator.models.Operation;

public class Presenter {
    private View view;
    private Calculation calculation;
    private History history;
    public Presenter(View view) {
        this.view = view;
        history = new History();
        calculation = new Calculation(history);
    }
    public void doCalculation(String btnName) {
        Operation op = Operation.getOperation(btnName);
        if (op == null) {
            calculation.addToNumber(btnName);
        } else {
            calculation.addOperation(op);
        }
        view.addToCurrentItem(calculation);
    }


    public interface View {
        void addToScrollView(Calculation calc);
        void addToCurrentItem(Calculation calc);
    }
}
