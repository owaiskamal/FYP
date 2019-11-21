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

import org.w3c.dom.Text;

import java.util.ArrayList;
public class ProductAdapterClass  extends RecyclerView.Adapter<ProductAdapterClass.myViewHolder>  {


    Context context;
    ArrayList<Product> products = new ArrayList<>() ;
 //   catAdapter.onNoteClickListener mOnNoteClickListener;
    public ProductAdapterClass( Context c , ArrayList<Product> p)
    {
        context = c;
        products = p;
        //this.mOnNoteClickListener = mOnNoteClickListener;
    }



    @NonNull
    @Override
    public  ProductAdapterClass.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         return new myViewHolder(LayoutInflater.from(context).inflate((R.layout.product_card_view), parent, false));

    }

    @Override
     public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
          holder.name.setText(products.get(position).getProductName());
          holder.Description.setText(products.get(position).getProductDescription());
         holder.price .setText(products.get(position).getProductPrice());
        Picasso.get().load(products.get(position).getUrl()).into(holder.imgs);




    }


    @Override
    public int getItemCount() {
        return products.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder
    {
            TextView name,Description,price;
        ImageView imgs;

        public myViewHolder(View itemView) {
                    super(itemView);

          name =(TextView)itemView.findViewById(R.id.name);
          Description=(TextView)itemView.findViewById(R.id.Description);
          price=(TextView)itemView.findViewById(R.id.price);
             imgs=(ImageView)itemView.findViewById(R.id.Productimage);
        }


        //btn=(Button)itemView.findViewById(R.id.checkDetails);
        }

}






