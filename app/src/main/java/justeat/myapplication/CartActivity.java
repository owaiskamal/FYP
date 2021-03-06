package justeat.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

import justeat.myapplication.Model.Cart;
import justeat.myapplication.Model.Product;
import justeat.myapplication.Model.TableNo;
import justeat.myapplication.Model.orderList;
import justeat.myapplication.ViewHolder.CartViewHolder;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button Nextbutton;
    private TextView txtTotal;
    private int totalPrice;
    private TableNo table;
    private String tableno;
    FirebaseDatabase database;
    DatabaseReference requests;

    List<Cart> cart = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("orders");

        recyclerView = findViewById(R.id.cartList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Nextbutton = (Button) findViewById(R.id.btnNext);
        txtTotal = (TextView)findViewById(R.id.Pprice);


        Nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                cartList();


//                Intent intent = new Intent(CartActivity.this, ConfirmOrderActivity.class);
//                intent.putExtra("Total Price", String.valueOf(totalPrice));
//                //intent.putExtra("cart" , )
//                startActivity(intent);
//                finish();
            }
        });
        final DatabaseReference cartListRef =
                FirebaseDatabase.getInstance().getReference().child("CartList");

        FirebaseRecyclerOptions<Cart> options = new
                FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef.child(TableNo.tableNo),Cart.class).build();


        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter =
                new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {
                        holder.txtPname.setText(model.getProductName());
                        holder.txtPprice.setText("Price = "+model.getProductPrice()+"$");
                        holder.txtPquantity.setText("Quantity = "+model.getQuantity());

                        int subTotal = ((Integer.valueOf(model.getProductPrice())))
                                * Integer.valueOf(model.getQuantity());
                        totalPrice = totalPrice + subTotal;

                        txtTotal.setText("Total Price = " + String.valueOf(totalPrice) + "$");


                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[] = new CharSequence[]{
                                        "Edit",
                                        "Delete"
                                } ;

                                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                                builder.setTitle("Cart Options");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        if(i == 0){
                                            Intent intent = new Intent(CartActivity.this,ProductDetailsActivity.class);
                                            intent.putExtra("Key", model.getPid());
                                            startActivity(intent);
                                        }

                                        if(i == 1){
                                            cartListRef.child(TableNo.tableNo).child(model.getPid())
                                                    .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                             @Override
                                                                                             public void onComplete(@NonNull Task<Void> task) {
                                                                                                 if (task.isSuccessful()){
                                                                                                     Toast.makeText(CartActivity.this,"Item Removed successfully",Toast.LENGTH_LONG).show();
                                                                                                 }
                                                                                             }
                                                                                         }
                                            );
                                        }
                                    }
                                });
                                builder.show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                        CartViewHolder holder = new CartViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }




    public void cartList()
    {
        final Query query = FirebaseDatabase.getInstance().getReference("CartList").child(TableNo.tableNo).orderByChild("tableNo");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {

                   Cart td = dataSnapshot1.getValue(Cart.class);
                    cart.add(td);

                }
                orderList order = new orderList(TableNo.tableNo , String.valueOf(totalPrice) , cart);
                requests.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(order);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    @Override
    protected void onStart() {
        super.onStart();
    }

}
