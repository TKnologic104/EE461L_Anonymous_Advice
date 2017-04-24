package com.example.ee461l_anonymous_advice;
//hi

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.EventListener;


public class PopUpActivity extends AppCompatActivity{

    private Button ignoreButton;
    private Button acceptButton;
    private TextView problem;

    private DatabaseReference mDatabaseReference;
    private ValueEventListener ChannelEventListener;

    private String channelId;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    //node to be user to replace old node.
    private ChatChannel chatChannel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = -10;
        params.height = 400;
        params.width = 400;
        params.y = -10;
        this.getWindow().setAttributes(params);
        this.setFinishOnTouchOutside(true);
        String message =  getIntent().getStringExtra("message");

        problem =  (TextView) findViewById(R.id.problem);

        problem.setText(message);

        ignoreButton = (Button)findViewById(R.id.popup_button_ignore);
        acceptButton = (Button)findViewById(R.id.popup_button_accept);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        channelId = getIntent().getStringExtra("channelId");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("ChatChannel");
        mDatabaseReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot i : dataSnapshot.getChildren() )
                {

                    ChatChannel temp =  i.getValue(ChatChannel.class);
                    if (!temp.isLocked)
                        if (temp.id.equals(channelId))
                        {
                            mDatabaseReference.child(temp.id).setValue(
                                    new ChatChannel(temp.advisee,
                                            new User(null,mFirebaseUser.getEmail()),
                                            channelId,true)
                            );

                        }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //mDatabaseReference.addValueEventListener(ChannelEventListener);
        if (mFirebaseUser==null)
        {
            Log.d("PopUpActivity", "Could not get email from " +
                    "firebase auth");
        }

        ignoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(PopUpActivity.this, LandingActivity.class);
//                startActivity(i);
                finish();
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    Thread.sleep(500);
                }catch (Exception e){
                    e.printStackTrace();
                }

                gotoIM(channelId);
            }
        });
    }

    public void gotoIM(String channelId) {
        Intent goToProfile = new Intent(PopUpActivity.this, IM_Activity.class);
        goToProfile.putExtra("ChannelId",channelId);
        startActivity(goToProfile);
    }


}
