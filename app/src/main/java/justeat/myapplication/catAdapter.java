package justeat.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class catAdapter extends RecyclerView.Adapter<catAdapter.myViewHolder> {

    Context context;
    ArrayList<Category > categories ;

    public catAdapter( Context c , ArrayList<Category> x)
    {
        context =c;
        categories = x;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new myViewHolder(LayoutInflater.from(context).inflate(R.layout.card_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
       holder.name.setText(categories.get(position).getCategoryName());
        Picasso.get().load(categories.get(position).getUrl()).into(holder.picture);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
            ImageView picture;
        public myViewHolder(View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.name);
           picture=(ImageView )itemView.findViewById(R.id.category);
        }
    }
}
