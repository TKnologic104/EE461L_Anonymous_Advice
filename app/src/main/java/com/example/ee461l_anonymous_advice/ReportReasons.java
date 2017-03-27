package com.example.ee461l_anonymous_advice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class ReportReasons extends AppCompatActivity {

    private CheckBox harrasment;
    private CheckBox spam;
    private CheckBox notAdvice;
    private CheckBox joke;
    private CheckBox offensive;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_reasons);

        harrasment = (CheckBox) findViewById(R.id.reason_harrasment);
        spam = (CheckBox) findViewById(R.id.reason_spam);
        notAdvice = (CheckBox) findViewById(R.id.reason_notadvice);
        joke = (CheckBox) findViewById(R.id.reason_joke);
        offensive = (CheckBox) findViewById(R.id.reason_offensive);
        submitButton = (Button) findViewById(R.id.report_button);

        if(harrasment.isChecked()){

        }
        if(spam.isChecked()){

        }
        if(notAdvice.isChecked()){

        }
        if(joke.isChecked()){

        }
        if(offensive.isChecked()){

        }


        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(MainActivity.this, otherClass.class);
//                i.putExtra("reason", reason);
//                startActivity(i);
            }
        });
    }

}


