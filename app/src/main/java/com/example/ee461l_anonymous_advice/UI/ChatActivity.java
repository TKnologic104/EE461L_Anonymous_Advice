package com.example.ee461l_anonymous_advice.UI;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ee461l_anonymous_advice.R;

import java.util.ArrayList;
//comment
public class ChatActivity extends AppCompatActivity {
    private Button mSendButton;
    private Button mCloseButton;
    private TextView mMessage;
    private ArrayList<String> mList_of_Messages =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mMessage = (TextView)findViewById(R.id.edit_text_chat_message);
        mSendButton = (Button)findViewById(R.id.button_chat_send);
        mCloseButton = (Button)findViewById(R.id.button_chat_close);

        mSendButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View arg){
                String message = mMessage.getText().toString();

                if (message.isEmpty())//when trying to send empty messages
                {
                    Toast.makeText(ChatActivity.this,"Ivalid",Toast.LENGTH_LONG).show();
                }//herrro
                else
                {
                    mList_of_Messages.add(message);
                    mMessage.setText("");
                    //TODO send message to other person
                    //TODO add messages to recyclerview
                }
            }
        });

        mCloseButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View arg)
            {
                /*TODO send special message so that other user
                 *      knows what is going on
                 *      NOTE when that happens one needs to disable
                 *      all the buttons
                 **/
                LayoutInflater layoutInflater
                        = (LayoutInflater)getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.chat_popup, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.WRAP_CONTENT);

                Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
                btnDismiss.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        popupWindow.dismiss();
                    }});
                popupWindow.showAtLocation(mCloseButton, Gravity.CENTER,0,0);

            }
        });
    }
}
