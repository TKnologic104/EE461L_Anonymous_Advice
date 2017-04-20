package com.example.ee461l_anonymous_advice;

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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;




public class PopUpActivity extends AppCompatActivity{

    private Button ignoreButton;
    private Button acceptButton;
    private TextView problem;

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

       // problem.setText(getIntent().getStringExtra("problem"));

        ignoreButton = (Button)findViewById(R.id.popup_button_ignore);
        acceptButton = (Button)findViewById(R.id.popup_button_accept);

        ignoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //close and go to activity the user was previously before this was called

                finish();
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Chat intent here and stuff
                Intent goToProfile = new Intent(PopUpActivity.this, LandingActivity.class);
                startActivity(goToProfile);

            }
        });
    }


}
