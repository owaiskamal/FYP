package justeat.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import justeat.myapplication.Model.Product;
import justeat.myapplication.Model.TableNo;

public class ProductDetailsActivity extends AppCompatActivity {

    private TextView productName, productDescription, productPrice;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
     String key;
    private String parent_id;
    private Button btnAddtoCart;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);

        productName = (TextView)findViewById(R.id.product_Name);
        productDescription = (TextView)findViewById(R.id.product_Description);
        productPrice = (TextView) findViewById(R.id.product_Price);

        productImage = (ImageView) findViewById(R.id.product_Image);

        numberButton = (ElegantNumberButton)findViewById(R.id.number_button);
        btnAddtoCart = (Button) findViewById(R.id.btn_AddToCart);

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        parent_id = getIntent().getStringExtra("Key");

        getProductDetails(parent_id);

        btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingToCart();
            }
        });
    }

    private void addingToCart() {
        String saveCurrentDate,saveCurrentTime;
        Calendar callForDate =  Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, YYYY");
        saveCurrentDate = currentDate.format(callForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(callForDate.getTime());

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("CartList");
        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid",parent_id);
        cartMap.put("productName",productName.getText().toString());
        cartMap.put("productDescription",productDescription.getText().toString());
        cartMap.put("productPrice", productPrice.getText().toString());
        cartMap.put("time",  saveCurrentTime);
        cartMap.put("quantity",numberButton.getNumber());
        cartMap.put("tableNo" , TableNo.tableNo);

        reference.child(TableNo.tableNo).child(parent_id).updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ProductDetailsActivity.this,"added successfully",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ProductDetailsActivity.this,CartActivity.class);
                       startActivity(intent);
                    }
                });
    }

    private void getProductDetails(String parent_id) {
        DatabaseReference prodructRef = FirebaseDatabase.getInstance().getReference().child("Products");

        prodructRef.child(parent_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    Product product = dataSnapshot.getValue(Product.class);

                    productName.setText(product.getProductName());
                    productDescription.setText(product.getProductDescription());
                    productPrice.setText(product.getProductPrice());


                    Picasso.get().load(product.getUrl()).into(productImage);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
