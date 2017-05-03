package com.example.ee461l_anonymous_advice;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sendbird.android.OpenChannel;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.Iterator;
import java.util.Map;

//import com.example.tk_whatsappclone.R;

public class LandingActivity extends AppCompatActivity {

    private Button profile;
    private EditText problem;
    private TextView charCount;
    private Button search;
    private Button friends;
    public String problemStatement;

    public static final String USER = "User";


    //private DatabaseReference mDatabaseReference;
    private DatabaseReference mUserDatabaseReference;

    public ArrayList<String> userIdArrayList = new ArrayList<>();
    public boolean popupflag;

    private String channelId;
    private String invitationId;
    private String tempId;

    private ValueEventListener openChannels;
    private Invitation receivedInvitation;

    private DatabaseReference mDatabaseReference;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;


    private ValueEventListener invitationEventListener;
    public Intent gotoChat;

    private Invitation invitation;//paramater for invitation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gotoChat = new Intent(this, IM_Activity.class);
        setContentView(R.layout.activity_landing);
        profile = (Button) findViewById(R.id.bn_profile);
        problem = (EditText) findViewById(R.id.problemText);
        search = (Button) findViewById(R.id.bn_search);
        charCount = (TextView) findViewById(R.id.charCountValue);
        //friends = (Button) findViewById(R.id.bn_friends);

        problem.addTextChangedListener(mTextEditorWatcher);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        //getting userId
        tempId = getIntent().getStringExtra("userId");

        mDatabaseReference =  FirebaseDatabase.getInstance().getReference("Invitation");
        invitationEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot i:dataSnapshot.getChildren())
                {
                    Invitation temp = i.getValue(Invitation.class);
                    if (temp==null) break;
                    if (!tempId.equals(temp.AdviseeId))
                    {
                        //deleting invitation
                        //i.getRef().removeValue();
                        Intent gotopopup = new Intent(LandingActivity.this, PopUpActivity.class);

                        gotopopup.putExtra("userId",tempId);
                        gotopopup.putExtra("channelId", temp.channelId);
                        gotopopup.putExtra("message", temp.question);
                        gotopopup.putExtra("invatationReference",i.getKey());
                        gotopopup.putExtra("adviseeQuestion", temp.question);
                        startActivity(gotopopup);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mDatabaseReference.addValueEventListener(invitationEventListener);

    }

    public void gotoProfile(View v){
        profileIntent();
    }

    public void profileIntent() {
        String tempName = (getIntent().getStringExtra("username"));
        String tempEmail = (getIntent().getStringExtra("userEmail"));
        Intent gotoProfile = new Intent(this, MainActivity.class);
        gotoProfile.putExtra("username",tempName);
        gotoProfile.putExtra("userEmail",tempEmail);
        gotoProfile.putExtra("userId",tempId);
        startActivity(gotoProfile);
    }

    public void search(View v){
        problemStatement = (String)problem.getText().toString();
        if (problemStatement.isEmpty())
        {
            Toast.makeText(this, "Please enter problem statement", Toast.LENGTH_SHORT).show();
            return;
        }


        createUserList();
        createChatChannelDBAndInvitation(gotoChat);


        //need to add usser id
        gotoChat.putExtra("userId",tempId);
        //flag for recognizing difference between advisee and adviser
        gotoChat.putExtra("isAdvisee",Boolean.valueOf(true));

        startActivity(gotoChat);
    }

    public void createUserList(){
        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference(USER);

        mUserDatabaseReference.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        userIdArrayList.clear();
                        for(DataSnapshot i: dataSnapshot.getChildren())
                        {
                            String temp = i.getValue(User.class).id;
                            userIdArrayList.add(temp);
                        }
                        printUserList();
                    //have to include code here to send notifications cannot do it else where
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("LandingActivity","Could not get datareference for value event");
                    }
                }
        );
    }


    public void createChatChannelDBAndInvitation(Intent gotoChat){

        mDatabaseReference =  FirebaseDatabase.getInstance().getReference();
        channelId = mDatabaseReference.child("ChatChannel").push().getKey();

        ChatChannel channel =  new ChatChannel(channelId,
                new User(getIntent().getStringExtra("userId"),
                         getIntent().getStringExtra("userEmail")));

        //passing the Channel and Advisee id to the IM_activity
        String userId=getIntent().getStringExtra("userId");
        gotoChat.putExtra("ChannelId",channelId);
        gotoChat.putExtra("UserId",userId);

        mDatabaseReference.child("ChatChannel").child(channelId).setValue(channel);

        //Invitation
        createInviteDB(gotoChat);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        FriendlyMessage invitationmssg = new FriendlyMessage(invitation.getquestion(),"Advisee",null,null,false);
        mDatabaseReference.child("ChatChannelMessages").child(channelId).push().setValue(invitationmssg);

    }

    public void createInviteDB(Intent gotoChat){
        mDatabaseReference =  FirebaseDatabase.getInstance().getReference();
        mDatabaseReference.child("Invitation");
        invitationId = mDatabaseReference.child("Invitation").push().getKey();
        tempId = getIntent().getStringExtra("userId");
        invitation = new Invitation(channelId,problem.getText().toString(),tempId);

        mDatabaseReference.child("Invitation").child(invitationId).setValue(invitation);

        gotoChat.putExtra("invitationId",invitationId);
        gotoChat.putExtra("adviseeQuestion", invitation.question);
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

    //Debbuging method
    private void printUserList(){
        System.out.println("-------------------------");
        for(String i:userIdArrayList)
        {
            System.out.println(i);
        }
        System.out.println("-------------------------");
    }

}
