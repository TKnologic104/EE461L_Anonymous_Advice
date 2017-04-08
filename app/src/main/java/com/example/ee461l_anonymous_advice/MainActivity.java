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
import android.util.Log;
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
    private static final int REQ_CODE = 9901;
    private Button gotoLanding;
    private Button gotoFAQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Prof_Section = (LinearLayout) findViewById(R.id.prof_section);
        SignOut = (Button) findViewById(R.id.bn_logout);
        Name = (TextView) findViewById(R.id.name);
        Gmail = (TextView) findViewById(R.id.gmail);
        Prof_Pic = (ImageView) findViewById(R.id.prof_pic);
        SignOut.setOnClickListener(this);
        gotoLanding = (Button) findViewById(R.id.bn_landing);
        gotoFAQ = (Button) findViewById(R.id.bn_faq);

        //String Name = String.valueOf(getIntent());
        Intent intent = getIntent();
        Name.setText(intent.getStringExtra("username"));
        Gmail.setText(intent.getStringExtra("userEmail"));
        //import picture using glide. 27:00 in youtube video
        //updateUI(true);


    }

    public void showNotification(View v) {
        //Uses the builder design Pattern for implementation a notification system.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        // by using this we can set the parameters for the notification.
        builder.setSmallIcon(R.drawable.photo);
        builder.setContentTitle("My Notification");
        builder.setContentText("This is my first notification...");
        //we have to make an intent for starting the activity.
        //Intent gotoNoti = new Intent(this, SecondClass.class);
        Intent gotoNoti = new Intent(this, LandingActivity.class);
        //the purpose of the stackuilder object is to return you to home screen
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(SecondClass.class);
        stackBuilder.addNextIntent(gotoNoti);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager NM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NM.notify(0, builder.build());
        //testfixed
    }

//    private void updateUI(boolean b) {
//    }

    @Override
    public void onClick(View v) {
        Toast.makeText(MainActivity.this, "inside Onclick", Toast.LENGTH_LONG).show();
        onLogout();
    }

    public void gotoLanding(View v) {
        String tempName = (getIntent().getStringExtra("username"));
        String tempEmail = (getIntent().getStringExtra("userEmail"));
        Intent gotoLanding = new Intent(this, com.example.ee461l_anonymous_advice.LandingActivity.class);
        gotoLanding.putExtra("username", tempName);
        gotoLanding.putExtra("userEmail", tempEmail);
        startActivity(gotoLanding);
    }

    public void gotoFAQ(View v) {
        String tempName = (getIntent().getStringExtra("username"));
        String tempEmail = (getIntent().getStringExtra("userEmail"));
        Intent gotoFAQ = new Intent(this, com.example.ee461l_anonymous_advice.FAQ.class);
        gotoFAQ.putExtra("username", tempName);
        gotoFAQ.putExtra("userEmail", tempEmail);
        startActivity(gotoFAQ);
    }


//    private void signOut(){
//        googleApiClient.disconnect();
//        Toast.makeText(MainActivity.this, "user Disconnected", Toast.LENGTH_LONG).show();
//        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
//            @Override
//            public void onResult(@NonNull Status status) {
//                Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_LONG).show();
//                Intent googleLogout = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(googleLogout);
//            }
//        });
//    }
//    private void handleResult(GoogleSignInResult result){
//
//    }

    //----------------------------------testing sign out ---------------------------------

    private static final int RC_SIGN_IN = 0;

    protected GoogleApiClient mGoogleApiClient;
    protected Context mContext;

    /* "mIntentInProgress" is A flag indicating that a PendingIntent is in progress and prevents
     * us from starting further intents.
     * True if we are in the process of resolving a ConnectionResult
     * "mSignInClicked" FLAG is True if the sign-in button was clicked.  When true, we know to resolve all
     * issues preventing sign-in without waiting.
     */

    public boolean mIntentInProgress;
    public boolean mSignInClicked;

    public String mPersonName;
    public String mImageUrl;
    public String mEmailAddress;

    public void CreateClient(Context mContext) {
        //Client builder that return GoogleAPI client, make the connection from the app to the G+ service
        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)//add OnConnected and OnConnectionSuspended methods to control the connection state
//                .addOnConnectionFailedListener(this) // add OnConnectionFaild listener in case connection was failed.
//                .addApi(Plus.API)
//                .addScope(Plus.SCOPE_PLUS_PROFILE)//profile permission from the user
//                .addScope(Plus.SCOPE_PLUS_LOGIN)// login details from the user
                .build();
        mGoogleApiClient.connect();
    }

    public void onLogout() {

        Log.i("base class", "logout invoked");

        Log.i("base class", "logout invoked");
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_LONG).show();
                Intent googleLogout = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(googleLogout);
            }
        });

        mGoogleApiClient.disconnect();
        //mGoogleApiClient.connect();

    }
}

