package com.example.ee461l_anonymous_advice;

/**
 * Created by rodolfo on 4/18/2017.
 */

public class Invitation {
    String channelId;
    String question;
    String AdviseeId;

    public Invitation(){}

    public Invitation(String channelId, String question, String AdviseeId)
    {
        this.channelId=channelId;
        this.question= question;
        this.AdviseeId= AdviseeId;
    }
}
