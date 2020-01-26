package justeat.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItem;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
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
import android.text.format.DateUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import  java.util.concurrent.TimeUnit;

import justeat.myapplication.Model.Category;

public class HomeActivity extends AppCompatActivity implements catAdapter.onNoteClickListener  {

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Category> list = new ArrayList<>();
    catAdapter adapter;
    String key;
    FloatingActionButton btn;
    public static final List<String> keyArray = new ArrayList<>();

    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 1;
    private TextToSpeech tts;
    private SpeechRecognizer speechRecog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initializeTextToSpeech();
        initializeSpeechRecognizer();

        Speeches();
        recyclerView=(RecyclerView) findViewById(R.id.myRecycler);
        recyclerView.setLayoutManager( new LinearLayoutManager(this  ));


        FloatingActionButton fab = findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Speeches();
                // Here, thisActivity is the current activity
//                if (ContextCompat.checkSelfPermission(HomeActivity.this,
//                        Manifest.permission.RECORD_AUDIO)
//                        != PackageManager.PERMISSION_GRANTED) {
//
//                    // Permission is not grantedHoem
//                    // Should we show an explanation?
//                    if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
//                            Manifest.permission.RECORD_AUDIO)) {
//                        // Show an explanation to the user *asynchronously* -- don't block
//                        // this thread waiting for the user's response! After the user
//                        // sees the explanation, try again to request the permission.
//                    } else {
//                        // No explanation needed; request the permission
//                        ActivityCompat.requestPermissions(HomeActivity.this,
//
//                                new String[]{Manifest.permission.RECORD_AUDIO},MY_PERMISSIONS_REQUEST_RECORD_AUDIO);
//
//                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                        // app-defined int constant. The callback method gets the
//                        // result of the request.
//                    }
//                } else {
//                    // Permission has already been granted
//                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//                    intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
//                    speechRecog.startListening(intent);
//                }

            }


        });
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

    private void Speeches() {


        if (ContextCompat.checkSelfPermission(HomeActivity.this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not grantedHoem
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                    Manifest.permission.RECORD_AUDIO)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(HomeActivity.this,

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

        Log.d("OWA", "onNoteClick: " + keyArray.get(position));
        String key = keyArray.get(position);

//        //List.get(position);
        Intent intent = new Intent(this , product_activity.class);
        intent.putExtra("Key" , key);
        startActivity(intent);
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
//        Fourth: Open a browser and open url


        if(result_message.indexOf("starter") != -1){
            //  speak("My Name is    iraza");
            onNoteClick(0);
        }


        else if (result_message.indexOf("burger")  != -1){

            Toast.makeText(getApplicationContext(),result_message,Toast.LENGTH_LONG).show();
            onNoteClick(1);


        } else if (result_message.indexOf("ADDTOCARD") != -1){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/AnNJPf-4T70"));
            startActivity(intent);
        }
        else   if (result_message.indexOf("sandwich") != -1){
            //String time_now = DateUtils.formatDateTime(this, new Date().getTime(),DateUtils.FORMAT_SHOW_TIME);
            //speak("T

            Toast.makeText(getApplicationContext(),result_message,Toast.LENGTH_LONG).show();
            //




            //
            // he time is now: " + time_now);
            onNoteClick(2);
        }
        else   if (result_message.indexOf("bbq") != -1){
            //String time_now = DateUtils.formatDateTime(this, new Date().getTime(),DateUtils.FORMAT_SHOW_TIME);
            //speak("The time is now: " + time_now);
            onNoteClick(3);

            Toast.makeText(getApplicationContext(),result_message,Toast.LENGTH_LONG).show();
        }

        else   if (result_message.indexOf("chinese") != -1){
            //String time_now = DateUtils.formatDateTime(this, new Date().getTime(),DateUtils.FORMAT_SHOW_TIME);
            //speak("The time is now: " + time_now);
            onNoteClick(4);

            Toast.makeText(getApplicationContext(),result_message,Toast.LENGTH_LONG).show();
        }
        else   if (result_message.indexOf("pakistani") != -1){
            //String time_now = DateUtils.formatDateTime(this, new Date().getTime(),DateUtils.FORMAT_SHOW_TIME);
            //speak("The time is now: " + time_now);

            Toast.makeText(getApplicationContext(),result_message,Toast.LENGTH_LONG).show();

            onNoteClick(5);
        }
        else   if (result_message.indexOf("desserts") != -1){
            //String time_now = DateUtils.formatDateTime(this, new Date().getTime(),DateUtils.FORMAT_SHOW_TIME);
            //speak("The time is now: " + time_now);

            Toast.makeText(getApplicationContext(),result_message,Toast.LENGTH_LONG).show();
            onNoteClick(6);
        }
        else   if (result_message.indexOf("drinks") != -1){
            //String time_now = DateUtils.formatDateTime(this, new Date().getTime(),DateUtils.FORMAT_SHOW_TIME);
            //speak("The time is now: " + time_now);

            Toast.makeText(getApplicationContext(),result_message,Toast.LENGTH_LONG).show();
            onNoteClick(7);
        }/*
           else   if (result_message.indexOf("") != -1){
               //String time_now = DateUtils.formatDateTime(this, new Date().getTime(),DateUtils.FORMAT_SHOW_TIME);
               //speak("The time is now: " + time_now);

               tts.setLanguage(Locale.US);
               speak("Please Again");
               Speeches();

           }*/

        else  {

            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tts.setLanguage(Locale.US);
            speak("OrderManual");
            //  Speeches();

        }

    }

    private void initializeTextToSpeech() {
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (tts.getEngines().size() == 0 ){
                    Toast.makeText(HomeActivity.this, getString(R.string.tts_no_engines),Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    tts.setLanguage(Locale.US);
                    //  speak("Welcome Sir Hello  i m waiter  I m agent , "+"We have  Starters" +"We have  Burgers"+"We have  SandWiches"+"We have  BBQ"+"We Have chinese   ");
                    speak("i'm  waiter. i am your pushy");
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

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