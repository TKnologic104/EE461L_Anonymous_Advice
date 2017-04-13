package com.example.ee461l_anonymous_advice;

import java.util.Date;

/**
 * Created by rodolfo on 3/27/2017.
 */

public class Message {
    private String user;
    private String message;
    private Date time;

    Message(String user,String message)
    {
        this.user=user;
        this.message=message;
        time= new Date();
    }

    public Date getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public String getUser() {
        return user;
    }
}
