package com.example.ee461l_anonymous_advice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TK on 4/12/2017.
 */

public class User_TK {

    String email;
    String id;

    Boolean  available;
    Boolean banned;

    Integer rating;

    List<String> reasons;


    public User_TK(String id,String email)
    {
        this.id=id;
        this.email=email;
        available=true;
        rating=0;
        reasons =  new ArrayList<>();
        banned=false;
    }

    public void setId(String id)
    {
        this.id=id;
    }

    public boolean addReason(String reason)
    {
        if (reasons.size()<3)
        {
            reasons.add(reason);
            return true;
        }
        else
        {
            setBanned();
            return false;
        }
    }

    private void setBanned() {
        banned=true;
    }

}