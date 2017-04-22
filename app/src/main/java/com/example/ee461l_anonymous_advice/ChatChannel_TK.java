package com.example.ee461l_anonymous_advice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TK on 4/12/2017.
 */

public class ChatChannel_TK  {
    User_TK advisee;
    User_TK adviser;

    String id;

    Boolean isLocked;

    List<Message> messages;

    ChatChannel_TK(String id, User_TK advisee)
    {
        this.id=id;
        this.advisee=advisee;
        isLocked=false;
        messages= new ArrayList<>();
    }

    public void addMessage(Message message)
    {
        messages.add(message);
    }

    public void addAdviser(User_TK adviser)
    {
        this.adviser=adviser;
        isLocked=true;
    }
}