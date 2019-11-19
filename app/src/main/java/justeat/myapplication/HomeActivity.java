package justeat.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements catAdapter.onNoteClickListener  {

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Category> list = new ArrayList<>();
    catAdapter adapter;
    String key;
    public static final List<String> keyArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       recyclerView=(RecyclerView) findViewById(R.id.myRecycler);
       recyclerView.setLayoutManager( new LinearLayoutManager(this  ));

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        reference= FirebaseDatabase.getInstance().getReference().child("Categories");
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new  ArrayList<Category>();

                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren())
                {
                    Category ca=dataSnapshot1.getValue(Category.class);
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






//        ImageButton img = (ImageButton) findViewById(R.id.Catsand);
//        img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomeActivity.this, Sandwich.class));
//            }
//        });
    }


    @Override
    public void onNoteClick(int position) {

        Log.d("OWA", "onNoteClick: " + keyArray.get(position));
//        //List.get(position);
//        Intent intent = new Intent(this , orderview.class);
//        intent.putExtra("Key" , key);
    }
}
