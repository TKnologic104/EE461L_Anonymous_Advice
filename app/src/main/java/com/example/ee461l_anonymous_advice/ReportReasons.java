package com.example.ee461l_anonymous_advice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

public class ReportReasons extends AppCompatActivity {

    private CheckBox reason1;
    private CheckBox reason2;
    private CheckBox reason3;
    private CheckBox reason4;
    private CheckBox reason5;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_reasons);

        reason1 = (CheckBox) findViewById(R.id.reason_harrasment);
        reason2 = (CheckBox) findViewById(R.id.reason_spam);
        reason3 = (CheckBox) findViewById(R.id.reason_notadvice);
        reason4 = (CheckBox) findViewById(R.id.reason_joke);
        reason5 = (CheckBox) findViewById(R.id.reason_offensive);
        submitButton = (Button) findViewById(R.id.report_button);


    }
}
