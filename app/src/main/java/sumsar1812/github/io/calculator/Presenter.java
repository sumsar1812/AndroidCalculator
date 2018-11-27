package sumsar1812.github.io.calculator;

import android.content.Context;
import android.widget.Toast;

import sumsar1812.github.io.calculator.models.Calculation;
import sumsar1812.github.io.calculator.models.History;
import sumsar1812.github.io.calculator.models.Operation;
import sumsar1812.github.io.calculator.models.OperationAction;

public class Presenter {
    private Context context;
    private View view;
    private Calculation calculation;
    private History history;
    public Presenter(Context context, View view) {
        this.view = view;
        history = new History();
        calculation = new Calculation(history);
        this.context = context;
    }
    public void doCalculation(String btnName) {
        Operation op = Operation.getOperation(btnName);
        if (op == null) {
            calculation.addToNumber(btnName);
        } else {
            try {
                OperationAction opAct = calculation.addOperation(op);
                if (opAct == OperationAction.CREATE || opAct == OperationAction.CREATE_AND_ADD_OP) {
                    history.addCalculation(calculation);
                    view.addResult(calculation.getResult());
                    view.addToCurrentItem(calculation);
                    calculation = new Calculation(history);
                    if (opAct == OperationAction.CREATE_AND_ADD_OP) {
                        calculation.addOperation(op);
                        view.addToCurrentItem(calculation);
                    }
                    return;
                } else if (opAct == OperationAction.CLEAR) {
                    history.clear();
                    view.clearAll();
                    calculation.clear();
                    return;
                }
            } catch (ArithmeticException e) {
                Toast toast = Toast.makeText(context,"Can't devide by 0",Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        view.addToCurrentItem(calculation);
    }


    public interface View {
        void addResult(double val);
        void clearAll();
        void addToCurrentItem(Calculation calc);
    }
}
