package justeat.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import justeat.myapplication.Model.Product;

public class ProductDetailsActivity extends AppCompatActivity {

    private TextView productName, productDescription, productPrice;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    String key;
    private String parent_id;
    private Button btnAddtoCart;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 1;
    private TextToSpeech tts;
    private SpeechRecognizer speechRecog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);

        initializeTextToSpeech();
        initializeSpeechRecognizer();

        Speeches();
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
        FloatingActionButton fab = findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Speeches();



            }


        });
    }

    private void Speeches() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(ProductDetailsActivity.this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not grantedHoem
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(ProductDetailsActivity.this,
                    Manifest.permission.RECORD_AUDIO)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(ProductDetailsActivity.this,

                        new String[]{Manifest.permission.RECORD_AUDIO},MY_PERMISSIONS_REQUEST_RECORD_AUDIO);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
            speechRecog.startListening(intent);
        }
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

        reference.child("products").child(parent_id).updateChildren(cartMap)
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
    private void initializeSpeechRecognizer() {
        if (SpeechRecognizer.isRecognitionAvailable(this)) {
            speechRecog = SpeechRecognizer.createSpeechRecognizer(this);
            speechRecog.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int error) {

                }

                @Override
                public void onResults(Bundle results) {
                    List<String> result_arr = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    // processResult(result_arr.get(0));
                    processResult(result_arr.get(0));


                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });
        }
    }

    private void processResult(String result_message) {
        result_message = result_message.toLowerCase();


        boolean numeric = true;

        try {
            Double num = Double.parseDouble(result_message);
        } catch (NumberFormatException e) {
            numeric = false;
        }
        if(numeric)
        {
            numberButton.setNumber(result_message);
        }

//        Toast.makeText(getApplicationContext(),result_message,Toast.LENGTH_LONG).show();
       /* if(result_message == (numeric) )
        {
            Toast.makeText(getApplicationContext(),result_message,Toast.LENGTH_LONG).show();

            numberButton.setNumber(result_message);
        }*/

        else if(result_message.indexOf("add") != -1){
            Toast.makeText(getApplicationContext(),result_message,Toast.LENGTH_LONG).show();

            //  speak("My Name is    iraza");
            addingToCart();

        }
          else
        {

            tts.setLanguage(Locale.US);

        /*    try {

                TimeUnit.MILLISECONDS.sleep(10000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
*/


            speak("Please Order Again ");
//

        }
        // System.out.println(string + " is not a number");
    }



    private void initializeTextToSpeech() {
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (tts.getEngines().size() == 0 ){
                    Toast.makeText(ProductDetailsActivity.this, getString(R.string.tts_no_engines),Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    tts.setLanguage(Locale.US);
                    //         speak("Welcome Sir Hello  i m waiter  I m agent , "+"We have  Starters" +"We have  Burgers"+"We have  SandWiches"+"We have  BBQ"+"We Have chinese   ");
                    speak("Enter your quantity");
                    Speeches();
                }
            }
        });
    }

    private void speak(String message) {
        if(Build.VERSION.SDK_INT >= 21){
            tts.speak(message,TextToSpeech.QUEUE_FLUSH,null,null);
        } else {
            tts.speak(message, TextToSpeech.QUEUE_FLUSH,null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      /*  getMenuInflater().inflate(R.menu.menu_main, menu);
      */  return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
    /*    if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        tts.shutdown();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Reinitialize the recognizer and tts engines upon resuming from background such as after openning the browser
        initializeSpeechRecognizer();
        initializeTextToSpeech();
        Speeches();

    }


}
