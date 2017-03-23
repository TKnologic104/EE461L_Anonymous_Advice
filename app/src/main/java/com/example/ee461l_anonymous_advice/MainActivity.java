package com.example.ee461l_anonymous_advice;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

//import com.example.tk_whatsappclone.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout Prof_Section;
    private Button SignOut;
    private TextView Name, Gmail;
    private ImageView Prof_Pic;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE= 9901;
    private Button gotoLanding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Prof_Section = (LinearLayout)findViewById(R.id.prof_section);
        SignOut = (Button)findViewById(R.id.bn_logout);
        Name = (TextView)findViewById(R.id.name);
        Gmail = (TextView)findViewById(R.id.gmail);
        Prof_Pic = (ImageView)findViewById(R.id.prof_pic);
        SignOut.setOnClickListener(this);
        gotoLanding = (Button)findViewById(R.id.bn_landing);

        //String Name = String.valueOf(getIntent());
        Intent intent = getIntent();
        Name.setText(intent.getStringExtra("username"));
        Gmail.setText(intent.getStringExtra("userEmail"));
        //import picture using glide. 27:00 in youtube video
        //updateUI(true);


    }

    public void showNotification(View v){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        // by using this we can set the parameters for the notification.
        builder.setSmallIcon(R.drawable.photo);
        builder.setContentTitle("My Notification");
        builder.setContentText("This is my first notification...");
        //we have to make an intent for starting the activity.
        Intent gotoNoti = new Intent(this, SecondClass.class);

        //the purpose of the stackuilder object is to return you to home screen
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(SecondClass.class);
        stackBuilder.addNextIntent(gotoNoti);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager NM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NM.notify(0, builder.build());

    }

//    private void updateUI(boolean b) {
//    }

    @Override
    public void onClick(View v) {
        signOut();
    }

    public void gotoLanding(View v){
        Intent gotoLanding = new Intent(this, com.example.ee461l_anonymous_advice.LandingActivity.class);
        startActivity(gotoLanding);
    }

    private void signOut(){
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                Toast.makeText(MainActivity.this, "this should sign you out", Toast.LENGTH_LONG).show();

            }
        });
    }
//    private void handleResult(GoogleSignInResult result){
//
//    }
}
