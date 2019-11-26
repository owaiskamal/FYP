package justeat.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import justeat.myapplication.ADDTOCART;
import justeat.myapplication.Product;
import justeat.myapplication.ProductAdapterClass;

public class product_activity extends AppCompatActivity implements ProductAdapterClass.onNoteClickListener  {

    DatabaseReference reference;
    RecyclerView recyclerView;

    ArrayList<Product> list;
    String key;
    public static final List<String> keyArray = new ArrayList<>();

    ProductAdapterClass adapters;
    String parent_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_activity);

        Intent intent = getIntent();
        if(intent!= null) {
            parent_id = intent.getStringExtra("Key");
        }
        recyclerView=(RecyclerView)findViewById(R.id.myRecycler);
        recyclerView.setLayoutManager( new LinearLayoutManager(this ));
        list= new ArrayList<Product>();
        //reference= FirebaseDatabase.getInstance().getReference().child("Products").child(parent_id);

        final Query query = FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("parentId");
        query.equalTo(parent_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren())
                {

                    Product ca=dataSnapshot1.getValue(Product.class);
                    list.add(ca);/*
                        key= dataSnapshot1.getRef().getKey().toString();
                        keyArray.add(key);*/

                }

                adapters = new ProductAdapterClass(product_activity.this,list,product_activity.this);
                recyclerView.setAdapter(adapters);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren())
//                {
//
//                    Product ca=dataSnapshot1.getValue(Product.class);
//                    list.add(ca);/*
//                        key= dataSnapshot1.getRef().getKey().toString();
//                        keyArray.add(key);*/
//
//                }
//
//                adapters = new ProductAdapterClass(product_activity.this,list);
//                recyclerView.setAdapter(adapters);
//
//
//            }

//            @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });

    }

    @Override
    public void onNoteClick(int position) {

        /*Log.d("OWA", "onNoteClick: " + keyArray.get(position));
         */
        // String key = keyArray.get(position);

        Intent intent = new Intent(this , ADDTOCART.class);
        //intent.putExtra("Key" , key);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

