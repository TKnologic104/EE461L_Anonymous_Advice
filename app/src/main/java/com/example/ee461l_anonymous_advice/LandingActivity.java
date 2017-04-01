package com.example.ee461l_anonymous_advice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//import com.example.tk_whatsappclone.R;

public class LandingActivity extends AppCompatActivity {

    private Button profile;
    private EditText problem;
    private Button search;
    private Button friends;
    public String problemStatement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        profile = (Button)findViewById(R.id.bn_profile);
        problem = (EditText)findViewById(R.id.problemText);
        search = (Button)findViewById(R.id.bn_search);
        friends = (Button)findViewById(R.id.bn_friends);

    }

    public void gotoProfile(View v){
        //TODO: create Profile Activity
        Intent gotoProfile = new Intent(this, com.example.ee461l_anonymous_advice.MainActivity.class);
        startActivity(gotoProfile);
        //create intent to goto Profile
    }

    public void search(){
        problemStatement = (String)problem.getText().toString();
        //TODO: Full networking functionality, also pass problem statement.
    }

    public void gotoFriends(){
        //TODO: create intent to goto Friends page
    }

}
