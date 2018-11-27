package sumsar1812.github.io.calculator;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sumsar1812.github.io.calculator.models.Calculation;

public class MainActivity extends AppCompatActivity implements Presenter.View{
    private List<Button> buttons = new ArrayList<>();

    private Presenter presenter;
    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        presenter = new Presenter(this, this);
        for (int i = 1; i <= 20; i++) {
            int id = getResources().getIdentifier("button" + i, "id", getPackageName());
            Button b = findViewById(id);
            buttons.add(b);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.doCalculation(((Button) v).getText().toString());
                }
            });
        }
    }


    public void initViews() {
        initRecyclerView();
        textView = findViewById(R.id.textView);

    }
    public void addToListView(Calculation calc) {
        if (calc.toString().isEmpty())
            return;
        adapter.getCalc().add(calc);
        addAdapter();
    }
    public void addResult(double result) {
        textView.setText("" + result);
    }
    @Override
    public void addToCurrentItem(Calculation calc) {
        if (adapter.getCalc().size() == 0 || !adapter.getCalc().contains(calc)) {
            addToListView(calc);
            return;
        }
        adapter.getCalc().set(adapter.getItemCount() - 1, calc);
        addAdapter();
    }
    @Override
    public void clearAll() {
        adapter.getCalc().clear();
        addAdapter();
    }
    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter(this, new ArrayList<Calculation>());
        addAdapter();
        RecyclerViewMargin decoration = new RecyclerViewMargin(10);
        recyclerView.addItemDecoration(decoration);
    }
    private void addAdapter() {
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (adapter != null && adapter.getItemCount() > 0) {
            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        }

    }

}
