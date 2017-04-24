package com.example.ee461l_anonymous_advice;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.builders.Actions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.Map;

//FriendlyChat's Imports



//import com.example.tk_whatsappclone.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private LinearLayout Prof_Section;
    private Button SignOut;
    private TextView Name, Gmail;
    private ImageView Prof_Pic;
    //private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9901;
    private Button gotoLanding;
    private Button gotoFAQ;
    private Button gotoPopUp;
    private User user;

    private String tempId;

    //Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    //Firebase database variables
    private DatabaseReference mUserRef;

    //FriendlyChat's variables
    private static final String TAG = "MainActivity";
    public static final String MESSAGES_CHILD = "messages";
    private static final int REQUEST_INVITE = 1;
    private static final int REQUEST_IMAGE = 2;
    private static final String LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 10;
    public static final String ANONYMOUS = "anonymous";
    private static final String MESSAGE_SENT_EVENT = "message_sent";
    private String mUsername;
    private String mPhotoUrl;
    private SharedPreferences mSharedPreferences;
    private GoogleApiClient mGoogleApiClient;
    private static final String MESSAGE_URL = "http://friendlychat.firebase.google.com/message/";

    // Firebase instance variables
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    private DatabaseReference mDatabaseReference;
//    private FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder>
//            mFirebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Set default username is anonymous.
        mUsername = ANONYMOUS;

        // Initialize Firebase Remote Config.
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        // Define Firebase Remote Config Settings.
        FirebaseRemoteConfigSettings firebaseRemoteConfigSettings =
                new FirebaseRemoteConfigSettings.Builder()
                        .setDeveloperModeEnabled(true)
                        .build();

// Define default config values. Defaults are used when fetched config values are not
// available. Eg: if an error occurred fetching values from the server.
        Map<String, Object> defaultConfigMap = new HashMap<>();
        defaultConfigMap.put("friendly_msg_length", 10L);

// Apply config settings and default values.
        mFirebaseRemoteConfig.setConfigSettings(firebaseRemoteConfigSettings);
        mFirebaseRemoteConfig.setDefaults(defaultConfigMap);

// Fetch remote config.
      //  fetchConfig();

        Prof_Section = (LinearLayout) findViewById(R.id.prof_section);
        SignOut = (Button) findViewById(R.id.bn_logout);
        Name = (TextView) findViewById(R.id.name);
        Gmail = (TextView) findViewById(R.id.gmail);
       // Prof_Pic = (ImageView) findViewById(R.id.prof_pic);
        SignOut.setOnClickListener(this);
        gotoLanding = (Button) findViewById(R.id.bn_landing);
        gotoFAQ = (Button) findViewById(R.id.bn_faq);
        gotoPopUp = (Button) findViewById(R.id.bn_popup);



        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        //String Name = String.valueOf(getIntent());
//        Intent intent = getIntent();
//        Name.setText(intent.getStringExtra("username"));
//        Gmail.setText(intent.getStringExtra("userEmail"));
        //import picture using glide. 27:00 in youtube video
        //updateUI(true);

        Name.setText(mUsername);
        Gmail.setText(mFirebaseUser.getEmail());

        //valueevent listners
        /*mDatabaseReference =  FirebaseDatabase.getInstance().getReference("User");
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot i:dataSnapshot.getChildren())
                {
                    User temp = i.getValue(User.class);
                    if (temp==null) break;
                    if (temp.email.equals(mFirebaseUser.getEmail()))
                    {
                        tempId=temp.id;
                        //gotoLanding.putExtra("userId", tempId);
                        mDatabaseReference.child(tempId).child("available").setValue(true);
                        //isUserDB=true;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


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
        signOut();
    }

    public void gotoLanding(View v) {
        String tempName = (getIntent().getStringExtra("username"));
        String tempEmail = (getIntent().getStringExtra("userEmail"));
        String tempId = (getIntent()).getStringExtra("userId");
        Intent gotoLanding = new Intent(this, LandingActivity.class);
        gotoLanding.putExtra("username", tempName);
        gotoLanding.putExtra("userEmail", tempEmail);
        gotoLanding.putExtra("email",mFirebaseUser.getEmail());
        gotoLanding.putExtra("userId",tempId);
        startActivity(gotoLanding);
    }

    public void gotoFAQ(View v) {
        String tempName = (getIntent().getStringExtra("username"));
        String tempEmail = (getIntent().getStringExtra("userEmail"));
        Intent gotoFAQ = new Intent(this, FAQ.class);
        gotoFAQ.putExtra("username", tempName);
        gotoFAQ.putExtra("userEmail", tempEmail);
        startActivity(gotoFAQ);
    }



    public void gotoPopUp(View v) {
//        String tempName = (getIntent().getStringExtra("username"));
//        String tempEmail = (getIntent().getStringExtra("userEmail"));
//        Intent gotoPopUp = new Intent(this, com.example.ee461l_anonymous_advice.PopUpActivity.class);
//        gotoPopUp.putExtra("username", tempName);
//        gotoPopUp.putExtra("userEmail", tempEmail);
//        startActivity(gotoPopUp);

        PopUpActivity popUp = new PopUpActivity();
        Intent gotoPopUp = new Intent(this, com.example.ee461l_anonymous_advice.PopUpActivity.class);
        startActivity(gotoPopUp);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
//ToDO pass userId when going back to Landing activity;
    private void signOut(){
        mFirebaseAuth.signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        mUsername = ANONYMOUS;

        mUserRef = FirebaseDatabase.getInstance().getReference("User");
        String userId = getIntent().getStringExtra("userId");
        mUserRef.child(userId).child("available").setValue(false);
        //pass the id to user.
        startActivity(new Intent(this, SignInActivity.class));

    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        return Actions.newView("Main", "http://[ENTER-YOUR-URL-HERE]");
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        FirebaseUserActions.getInstance().start(getIndexApiAction());
    }

    @Override
    public void onStop() {

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        FirebaseUserActions.getInstance().end(getIndexApiAction());
        super.onStop();
    }
}

