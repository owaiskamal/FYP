package justeat.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import justeat.myapplication.Model.Product;

public class product_activity extends AppCompatActivity implements ProductAdapterClass.onNoteClickListener  {

    DatabaseReference reference;
    RecyclerView recyclerView;

    ArrayList<Product> list;
    String key;
    String parent_id;
    public static final List<String> keyArray = new ArrayList<>();

    ProductAdapterClass adapters;


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


        final Query query = FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("parentId");
        query.equalTo(parent_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren())
                {
                    Product ca=dataSnapshot1.getValue(Product.class);
                    list.add(ca);
                        key= dataSnapshot1.getRef().getKey().toString();
                        keyArray.add(key);

                }

                adapters = new ProductAdapterClass(product_activity.this,list,product_activity.this);
                recyclerView.setAdapter(adapters);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onNoteClick(int position) {

        /*Log.d("OWA", "onNoteClick: " + keyArray.get(position));
         */
         String key = keyArray.get(position);
//
        Intent intent = new Intent(this , ProductDetailsActivity.class);
        intent.putExtra("Key" , key);
        keyArray.clear();
        startActivity(intent);
        finish();
        //startActivity(getIntent());

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
//    @Override
//    protected void onResume() {
//
//        super.onResume();
////        this.onCreate(null);
//    }
}

