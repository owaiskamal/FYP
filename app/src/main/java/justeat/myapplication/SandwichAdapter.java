package justeat.myapplication;

import android.icu.util.ValueIterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SandwichAdapter extends RecyclerView.Adapter<SandwichAdapter.sandwichHolder> {
    private String[] data;
    public SandwichAdapter(String[] languages)
    {
        this.data = languages;
    }   
    @Override
    public sandwichHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_layout,parent,false);
        return new sandwichHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull sandwichHolder holder, int position) {
        String title = data[position];
        holder.txt.setText(title);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class sandwichHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt;
        public sandwichHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView)itemView.findViewById(R.id.img);
            txt = (TextView)itemView.findViewById(R.id.txt);
        }
    }
}
