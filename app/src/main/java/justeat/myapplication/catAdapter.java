package justeat.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class catAdapter extends RecyclerView.Adapter<catAdapter.myViewHolder> {

    Context context;
    ArrayList<Category > categories = new ArrayList<>() ;
    onNoteClickListener mOnNoteClickListener;

    public catAdapter( Context c , ArrayList<Category> x , onNoteClickListener mOnNoteClickListener)
    {
        context =c;
        categories = x;
        this.mOnNoteClickListener = mOnNoteClickListener;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new myViewHolder(LayoutInflater.from(context).inflate(R.layout.card_view,parent,false) , mOnNoteClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
       holder.name.setText(categories.get(position).getCategoryName());
        Picasso.get().load(categories.get(position).getUrl()).resize(600 , 200)
                .onlyScaleDown().centerCrop().into(holder.picture);

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
            TextView name;
            ImageView picture;

            Button btn;
            onNoteClickListener onNoteClickListener;
        public myViewHolder(View itemView , onNoteClickListener onNoteClickListener) {
         //   Button btn;
            super(itemView);


            name= (TextView) itemView.findViewById(R.id.name);
           picture=(ImageView )itemView.findViewById(R.id.category);
          this.onNoteClickListener = onNoteClickListener;
           itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onNoteClickListener.onNoteClick(getAdapterPosition());


           //btn=(Button)itemView.findViewById(R.id.checkDetails);
        }
    }

    public interface onNoteClickListener{
        public void onNoteClick(final int position);
    }

}
