package justeat.myapplication.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import justeat.myapplication.Interface.ItemClickListner;
import justeat.myapplication.R;


public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

     public TextView txtPname, txtPprice,txtPquantity;
    private ItemClickListner itemClickListner;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        txtPname = itemView.findViewById(R.id.cart_Pname);
        txtPprice = itemView.findViewById(R.id.cart_Pprice);
        txtPquantity = itemView.findViewById(R.id.cart_Pquantity);
    }

    @Override
    public void onClick(View v) {
            itemClickListner.onClick(v,getAdapterPosition(),false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
