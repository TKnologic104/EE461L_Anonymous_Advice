package com.example.ee461l_anonymous_advice;

import android.content.Intent;
import android.net.sip.SipAudioCall;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


public class RateAdvice extends AppCompatActivity {
    private Button submitButton;
    private  SeekBar seekBar;
    private int rating;
    private TextView seekBarTextview;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_advice);
        //call setup 

        setUp();
        seekBar = (SeekBar) findViewById(R.id.seekBarAdviceRating);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBarTextview.setText("i" + " of " + seekBar.getMax());
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void setUp() {
        rating = 0;
        seekBarTextview = (TextView) findViewById(R.id.seekBarProgressTextView);
        submitButton = (Button) findViewById(R.id.rateAdviceButton);
        submitButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                //  Intent i = new Intent(RateAdvice.this, nextActivity.class);
                //  i.putExtra("rating", rating);
                //           startActivity(i);
            }
        });
    }
}
