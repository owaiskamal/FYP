package justeat.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class Catsandwich extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catsandwich);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.Sandwiches);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String[] Languages = {"Club sandwich","BBQ sandwich","Egg Sandwich", "Double Decker Sandwich"};
        recyclerView.setAdapter(new SandwichAdapter(Languages));
    }
}
