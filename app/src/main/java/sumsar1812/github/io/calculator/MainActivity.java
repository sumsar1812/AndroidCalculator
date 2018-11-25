package sumsar1812.github.io.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import sumsar1812.github.io.calculator.models.Calculation;
import sumsar1812.github.io.calculator.models.History;

public class MainActivity extends AppCompatActivity implements Presenter.View{
    private List<Button> buttons = new ArrayList<>();
    private Presenter presenter;
    private RecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        presenter = new Presenter(this);
        for (int i = 1; i <= 20;i++) {
            int id = getResources().getIdentifier("button" + i,"id", getPackageName());
            Button b = findViewById(id);
            buttons.add(b);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.doCalculation(((Button)v).getText().toString());
                }
            });
        }
    }
    public void initViews() {
        initRecyclerView();
    }
    @Override
    public void addToScrollView(Calculation calc) {
        if (calc.toString().isEmpty())
            return;
        adapter.getCalc().add(calc);
        addAdapter();
    }

    @Override
    public void addToCurrentItem(Calculation calc) {
        if (calc.toString().isEmpty())
            return;
        if (adapter.getCalc().size() == 0)
            addToScrollView(calc);
        adapter.getCalc().set(adapter.getItemCount() - 1, calc);
        addAdapter();
    }
    private void initRecyclerView() {

        ArrayList<Calculation> temp = new ArrayList<>();
        temp.add(new Calculation(new History()));
        temp.add(new Calculation(new History()));
        temp.get(0).addToNumber("55");
        temp.get(1).addToNumber("54");
        adapter = new RecyclerViewAdapter(this, temp);
        addAdapter();
    }
    private void addAdapter() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        int pos = RecyclerViewAdapter.getPosition();
        recyclerView.scrollTo(0,pos);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
