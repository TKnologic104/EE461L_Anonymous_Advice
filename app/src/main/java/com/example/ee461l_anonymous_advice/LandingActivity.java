package com.example.ee461l_anonymous_advice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//import com.example.tk_whatsappclone.R;

public class LandingActivity extends AppCompatActivity {

    private Button profile;
    private EditText problem;
    private TextView charCount;
    private Button search;
    private Button friends;
    public String problemStatement;

    private DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        profile = (Button)findViewById(R.id.bn_profile);
        problem = (EditText)findViewById(R.id.problemText);
        search = (Button)findViewById(R.id.bn_search);
        friends = (Button)findViewById(R.id.bn_friends);
        charCount = (TextView)findViewById(R.id.charCountValue);

        problem.addTextChangedListener(mTextEditorWatcher);



    }

    public void gotoProfile(View v){
        //TODO: create Profile Activity
        String tempName = (getIntent().getStringExtra("username"));
        String tempEmail = (getIntent().getStringExtra("userEmail"));
        Intent gotoProfile = new Intent(this, MainActivity.class);
        gotoProfile.putExtra("username",tempName);
        gotoProfile.putExtra("userEmail",tempEmail);
        startActivity(gotoProfile);
        //create intent to goto Profile
    }

    public void search(View v){
        problemStatement = (String)problem.getText().toString();
        Intent gotoChat = new Intent(this, IM_Activity.class);
        createChatChannelDB();
        startActivity(gotoChat);
        //TODO: Full networking functionality, also pass problem statement.
    }

    public void createChatChannelDB(){
        mDatabaseReference =  FirebaseDatabase.getInstance().getReference("ChatChannel");

        String temp = mDatabaseReference.push().getKey();
        System.out.println(getIntent().getStringExtra("userId")+" "+getIntent().getStringExtra("email"));

        ChatChannel channel =  new ChatChannel(temp,
                new User(getIntent().getStringExtra("userId"),
                         getIntent().getStringExtra("userEmail")));

        mDatabaseReference.child(temp).setValue(channel);

    }

    public void gotoFriends(){
        //TODO: create intent to goto Friends page
    }


    /*
     * Character counter. Shows how many characters are left for the user to use when typing the problem.
     */

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            charCount.setText(String.valueOf(150-s.length()));
        }

        public void afterTextChanged(Editable s) {
        }
    };

}
