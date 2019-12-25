package justeat.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import justeat.myapplication.Model.Category;

public class HomeActivity extends AppCompatActivity implements catAdapter.onNoteClickListener  {

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Category> list = new ArrayList<>();
    catAdapter adapter;
    String key;
    FloatingActionButton btn;
    public static final List<String> keyArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       recyclerView=(RecyclerView) findViewById(R.id.myRecycler);
       recyclerView.setLayoutManager( new LinearLayoutManager(this  ));
       btn = (FloatingActionButton) findViewById(R.id.goToCart);
       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(HomeActivity.this,CartActivity.class);
               startActivity(intent);
           }
       });

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        reference= FirebaseDatabase.getInstance().getReference().child("Categories");
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new  ArrayList<Category>();

                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren())
                {
                    Category ca = dataSnapshot1.getValue(Category.class);
                    list.add(ca);
                    key= dataSnapshot1.getRef().getKey().toString();
                    keyArray.add(key);

                }

                adapter = new catAdapter(HomeActivity.this , list , HomeActivity.this);
                recyclerView.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, "0sd", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onNoteClick(int position) {

        Log.d("OWA", "onNoteClick: " + keyArray.get(position));
        String key = keyArray.get(position);

//        //List.get(position);
        Intent intent = new Intent(this , product_activity.class);
        intent.putExtra("Key" , key);
        startActivity(intent);
            }
}
