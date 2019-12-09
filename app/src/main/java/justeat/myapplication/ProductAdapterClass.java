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

import justeat.myapplication.Model.Product;


public class ProductAdapterClass  extends RecyclerView.Adapter<ProductAdapterClass.myViewHolder>  {


    Context context;
    ArrayList<Product> products = new ArrayList<>() ;


    onNoteClickListener mOnNoteClickListener;

    //   catAdapter.onNoteClickListener mOnNoteClickListener;
    public ProductAdapterClass( Context c , ArrayList<Product> p, onNoteClickListener mOnNoteClickListener)
    {
        context = c;
        products = p;
        this.mOnNoteClickListener = mOnNoteClickListener;
    }



    @NonNull
    @Override
    public  ProductAdapterClass.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(context).inflate((R.layout.product_card_view), parent, false), mOnNoteClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.name.setText(products.get(position).getProductName());
        holder.Description.setText(products.get(position).getProductDescription());
        holder.price.setText("RS " + products.get(position).getProductPrice());

        Picasso.get().load(products.get(position).getUrl()).into(holder.imgs);

    }


    @Override
    public int getItemCount() {
        return products.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener
    {
        onNoteClickListener onNoteClickListener;

        TextView name,Description,price;

        ImageView imgs;

        public myViewHolder(View itemView , onNoteClickListener onNoteClickListener ) {
            super(itemView);

            name =(TextView)itemView.findViewById(R.id.name);
            Description=(TextView)itemView.findViewById(R.id.Description);
            price=(TextView)itemView.findViewById(R.id.price);
            imgs=(ImageView)itemView.findViewById(R.id.Productimage);
            this.onNoteClickListener = onNoteClickListener;
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

            onNoteClickListener.onNoteClick(getAdapterPosition());

        }


        //btn=(Button)itemView.findViewById(R.id.checkDetails);
    }

    public interface onNoteClickListener{
        public void onNoteClick(final int position);
    }
}