package com.example.ee461l_anonymous_advice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class FAQ extends AppCompatActivity {
    private Button goToProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        goToProfile = (Button)findViewById(R.id.FAQ_button);
        goToProfile.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent i = new Intent(FAQ.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
}
