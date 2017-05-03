package com.example.ee461l_anonymous_advice;

import android.content.Intent;
import android.net.sip.SipAudioCall;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

//comment
public class RateAdvice extends AppCompatActivity {
    private Button submitButton;
    private Button reportButton;
    private  SeekBar seekBar;
    private int rating;
    private TextView seekBarTextview;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_advice);
        //call setup 

        rating = 0;
        seekBarTextview = (TextView) findViewById(R.id.seekBarProgressTextView);


        seekBar = (SeekBar) findViewById(R.id.seekBarAdviceRating);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarTextview.setText(progress + " of " + seekBar.getMax());
                rating = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        submitButton = (Button) findViewById(R.id.rateAdviceButton);
        submitButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent i = new Intent(RateAdvice.this, LandingActivity.class);
                i.putExtra("rating", rating);
                startActivity(i);
            }
        });

        reportButton = (Button) findViewById(R.id.reportUser);
        reportButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RateAdvice.this, ReportReasons.class);
                startActivity(i);
            }
        });

    }

}
