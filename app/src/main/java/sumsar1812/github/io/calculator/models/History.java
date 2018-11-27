package sumsar1812.github.io.calculator.models;

import java.util.ArrayList;
import java.util.List;

public class History {
    private List<Calculation> history = new ArrayList<>();

    public Calculation getLatest() {
        if (history.size() == 0)
            return null;
        return history.get(history.size() - 1);
    }
    public void addCalculation(Calculation calculation) {
        history.add(calculation);
    }
    public void clear() {
        history.clear();
    }
}
