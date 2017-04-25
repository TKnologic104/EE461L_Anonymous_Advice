package com.example.ee461l_anonymous_advice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodolfo on 4/12/2017.
 */

public class ChatChannel  {
    public User advisee;
    public User adviser;

    public String id;

    public Boolean isLocked;

//    List<Message> messages;
    ChatChannel(){}

    ChatChannel(String id, User advisee)
    {
        this.id=id;
        this.advisee=advisee;
        isLocked=false;
   //     messages= new ArrayList<>();
  //      messages.add(new Message("anonymous","emptyy"));
    }

    /*
    public void addMessage(Message message)
    {
        messages.add(message);
    }
    */
    ChatChannel(User advisee, User adviser, String id, Boolean isLocked)
    {
        this.advisee =  advisee;
        this.adviser = adviser;
        this.id = id;
        this.isLocked = isLocked;
    }
    public void addAdviser(User adviser)
    {
        this.adviser=adviser;
        isLocked=true;
    }
}
