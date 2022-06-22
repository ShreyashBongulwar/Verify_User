package com.example.verify_user;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    private static final int REQUEST_CODE = 100;

    private TextToSpeech t1;
    private TextView ip_text;
    private TextView ans;
    private Button ip_button;
    private String rec_text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ip_text = findViewById(R.id.input_text);
        ip_button = findViewById(R.id.click_btn);
        ans = findViewById(R.id.final_ans);

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.ENGLISH);
                }
            }
        });

        ip_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = ip_text.getText().toString();
//                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        ip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                try {


                    startActivityForResult(intent, REQUEST_CODE);
                } catch (ActivityNotFoundException a) {

                }
            }
        });
    }

//    @Override
//    protected void onPause() {
//        if (t1 != null) {
//            t1.stop();
//            t1.shutdown();
//        }
//        super.onPause();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    rec_text = result.get(0);
                    ans.setText(result.get(0));
                    Log.d("TEST",rec_text);
                    if(Objects.equals(rec_text, "4")) {
                        Toast.makeText(this, "It is correct", Toast.LENGTH_SHORT).show();
                        t1.speak("It is Correct", TextToSpeech.QUEUE_FLUSH, null);
                        Log.d("TEST", "crt");
                    }
                    else {
                        t1.speak("it is incorrect",TextToSpeech.QUEUE_FLUSH,null);
                    }
                }
                break;
            }
        }
    }
}