package justeat.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConfirmOrderActivity extends AppCompatActivity {

    private EditText txtName, txtTable;
    private Button btnConfirm;
    private String totalAmount = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);


        txtName = (EditText)findViewById(R.id.txtName);
        txtTable = (EditText)findViewById(R.id.txtTable);

        totalAmount = getIntent().getStringExtra("Total Price");
        Toast.makeText(this,"Total Price =" + totalAmount, Toast.LENGTH_LONG).show();
    }
}
