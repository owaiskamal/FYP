package justeat.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class orderview extends AppCompatActivity {

    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderview);

        Button b=(Button)findViewById(R.id.Addtocard);
        Intent intent = getIntent();
        key = intent.getStringExtra("Key");

        Log.d("OWA" , key);
     b.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent i = new Intent(orderview.this,ADDTOCART.class);
             startActivity(i);
         }
     });
    }

}
