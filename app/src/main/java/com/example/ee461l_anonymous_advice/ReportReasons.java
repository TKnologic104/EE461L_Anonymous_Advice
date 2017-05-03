package com.example.ee461l_anonymous_advice;

import android.content.Intent;
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
    int reason = 0;

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
            reason ++;
        }
        if(spam.isChecked()){
            reason++;
        }
        if(notAdvice.isChecked()){
            reason++;
        }
        if(joke.isChecked()){
            reason++;
        }
        if(offensive.isChecked()){
            reason++;
        }



        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ReportReasons.this, LandingActivity.class);

                if(reason != 0){
                    i.putExtra("reason", reason);
                }

                startActivity(i);
            }
        });
    }

}


