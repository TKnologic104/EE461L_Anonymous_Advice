package com.example.ee461l_anonymous_advice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodolfo on 4/12/2017.
 */

public class ChatChannel  {
    User advisee;
    User adviser;

    String id;

    Boolean isLocked;

    List<FriendlyMessage> messages;

    ChatChannel(String id, User advisee)
    {
        this.id=id;
        this.advisee=advisee;
        isLocked=false;
        messages= new ArrayList<>();
    }

    public void addMessage(FriendlyMessage message)
    {
        messages.add(message);
    }

    public void addAdviser(User adviser)
    {
        this.adviser=adviser;
        isLocked=true;
    }
}
