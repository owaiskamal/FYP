package justeat.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import justeat.myapplication.Model.Cart;
import justeat.myapplication.Model.TableNo;
import justeat.myapplication.Model.orderList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ConfirmOrderActivity extends AppCompatActivity {

    private EditText txtName, txtTable;
    private Button btnConfirm;
    private String totalAmount = "";
    List<Cart> cart = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);


//        database = FirebaseDatabase.getInstance();
//        requests = database.getReference("orders");
        txtName = (EditText)findViewById(R.id.txtName);
        txtTable = (EditText)findViewById(R.id.txtTable);

        //totalAmount = getIntent().getStringExtra("Total Price");
       // Toast.makeText(this,"Total Price =" + totalAmount, Toast.LENGTH_LONG).show();
        btnConfirm = (Button) findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  table.setTableNo(txtTable.getText().toString());
                TableNo.tableNo = txtTable.getText().toString();
                Intent intent = new Intent( ConfirmOrderActivity.this , HomeActivity.class);
                startActivity(intent);

//                orderList order = new orderList(txtName.getText().toString() , txtTable.getText().toString() , totalAmount , cart);
//
//                requests.child(String.valueOf(System.currentTimeMillis()))
//                        .setValue(order);
            }
        });
    }
}
