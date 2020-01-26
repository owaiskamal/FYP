package justeat.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import justeat.myapplication.Model.Product;

public class product_activity extends AppCompatActivity implements ProductAdapterClass.onNoteClickListener  {

    DatabaseReference reference;
    RecyclerView recyclerView;

    ArrayList<Product> list;
    String key;
    String parent_id;
    public static final List<String> keyArray = new ArrayList<>();

    ProductAdapterClass adapters;
    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 1;
    private TextToSpeech tts;
    private SpeechRecognizer speechRecog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_activity);
        FloatingActionButton fab = findViewById(R.id.fab);

        Intent intent = getIntent();
        initializeTextToSpeech();
        initializeSpeechRecognizer();


        speeches();
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
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
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


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Here, thisActivity is the current activity
            speeches();
            }


        });
    }

    private void speeches() {

        if (ContextCompat.checkSelfPermission(product_activity.this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not grantedHoem
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(product_activity.this,
                    Manifest.permission.RECORD_AUDIO)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(product_activity.this,

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

//        Handle at least four sample cases

//        First: What is your Name?
//        Second: What is the time?
//        Third: Is the earth flat or a sphere?
//

        if(result_message.indexOf("item 1" ) != -1){
            //  speak("My Name is    iraza");
            onNoteClick(0);
        }


        else if (result_message.indexOf("item 2") !=-1){

            Toast.makeText(getApplicationContext(),result_message,Toast.LENGTH_LONG).show();
            onNoteClick(1);


        }

        else  {

          /*  try {
                TimeUnit.MILLISECONDS.sleep(10000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            */
            try {

                TimeUnit.MILLISECONDS.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tts.setLanguage(Locale.US);
            speak( "I cant  Understand what  you are saying.  Order Manual");

        }

    }

    private void initializeTextToSpeech() {
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (tts.getEngines().size() == 0 ){
                    Toast.makeText(product_activity.this, getString(R.string.tts_no_engines),Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    tts.setLanguage(Locale.US);
                    speak("Select the Product"   );

                    speeches();
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
       /* getMenuInflater().inflate(R.menu.menu_main, menu);
      */  return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
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
        speeches();
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

