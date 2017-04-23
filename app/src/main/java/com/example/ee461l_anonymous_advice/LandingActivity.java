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


    private ValueEventListener openChannels;

    private Invitation receivedInvitation;

    private DatabaseReference mDatabaseReference;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String tempId;

    private ValueEventListener invitationEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        profile = (Button) findViewById(R.id.bn_profile);
        problem = (EditText) findViewById(R.id.problemText);
        search = (Button) findViewById(R.id.bn_search);
        //friends = (Button) findViewById(R.id.bn_friends);
        charCount = (TextView) findViewById(R.id.charCountValue);

        problem.addTextChangedListener(mTextEditorWatcher);
        //addValueEventListeners();
        //valueevent listners
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
                    if (!tempId.equals(temp.AdviseeId)) {
                        Intent gotopopup = new Intent(LandingActivity.this, PopUpActivity.class);

                        gotopopup.putExtra("channelId", temp.channelId);
                        gotopopup.putExtra("message", temp.question);
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
    //public InvitationAdapter(Callback ca)
    private class InvitationChildEventListener implements ChildEventListener{

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            //Create pop-up
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            //remove from list
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            ///remove from local list
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            //not interested on moved
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }
    public void addValueEventListeners() {

        mDatabaseReference.child("Invitation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Invitation invitation = dataSnapshot.getValue(Invitation.class);
                popupflag=true;
                //invitation.message = R.id.poptext;
                //create popup

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //mDatabaseReference.child("I")
        //check if invitation is still there
    }

    private void createPopup()
    {
        DatabaseReference isLockedRef = mDatabaseReference.child("ChatChannel").child(receivedInvitation.channelId);
        isLockedRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean isLocked = dataSnapshot.getValue(boolean.class);
                if (isLocked)
                {
                    dissablePopUp();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void dissablePopUp()
    {
        //remove accept button
    }

    public void gotoProfile(View v){
        //TODO: create Profile Activity
        String tempName = (getIntent().getStringExtra("username"));
        String tempEmail = (getIntent().getStringExtra("userEmail"));
        String tempId = getIntent().getStringExtra("userId");
        Intent gotoProfile = new Intent(this, MainActivity.class);
        gotoProfile.putExtra("username",tempName);
        gotoProfile.putExtra("userEmail",tempEmail);
        gotoProfile.putExtra("userId",tempId);
        startActivity(gotoProfile);
        //create intent to goto Profile
    }

    public void search(View v){
        problemStatement = (String)problem.getText().toString();
        Intent gotoChat = new Intent(this, IM_Activity.class);

        createUserList();
        createChatChannelDB(gotoChat);

        createInviteDB(gotoChat);
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


    public void createChatChannelDB(Intent gotoChat){

        mDatabaseReference =  FirebaseDatabase.getInstance().getReference("ChatChannel");
        channelId = mDatabaseReference.push().getKey();

        ChatChannel channel =  new ChatChannel(channelId,
                new User(getIntent().getStringExtra("userId"),
                         getIntent().getStringExtra("userEmail")));

        //passing the Channel and Advisee id to the IM_activity
        String userId=getIntent().getStringExtra("userId");
        gotoChat.putExtra("ChannelId",channelId);
        gotoChat.putExtra("UserId",userId);

        mDatabaseReference.setValue(channel);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("ChatChannelMessages");
        FriendlyMessage emptymssg = new FriendlyMessage("emptyyy","Anonymous",null,null);
        mDatabaseReference.child(channelId).push().setValue(emptymssg);
        FriendlyMessage emptymssg1 = new FriendlyMessage("othermessage","Anonymous",null,null);
        mDatabaseReference.child(channelId).push().setValue(emptymssg1);

    }

    public void createInviteDB(Intent gotoChat){
        mDatabaseReference =  FirebaseDatabase.getInstance().getReference("Invitation");

        invitationId = mDatabaseReference.push().getKey();
        tempId = getIntent().getStringExtra("userId");
        Invitation invitation = new Invitation(channelId,problem.toString(),tempId);

        mDatabaseReference.child(invitationId).setValue(invitation);
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

    //debbuging method
    private void printUserList(){
        System.out.println("-------------------------");
        for(String i:userIdArrayList)
        {
            System.out.println(i);
        }
        System.out.println("-------------------------");
    }

}
