package sumsar1812.github.io.calculator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sumsar1812.github.io.calculator.models.Calculation;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<Calculation> mCalculations;
    private Context context;
    private static int position;

    public RecyclerViewAdapter(Context context, ArrayList<Calculation> mCalculations) {
        this.mCalculations = mCalculations;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem,viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        position = i;
        viewHolder.calculation.setText(mCalculations.get(i).toString());
    }

    @Override
    public int getItemCount() {
        return mCalculations.size();
    }

    public static int getPosition() {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView calculation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            calculation = itemView.findViewById(R.id.calculation);
        }
    }
    public List<Calculation> getCalc() {
        return mCalculations;
    }
}
