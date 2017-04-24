package com.example.ee461l_anonymous_advice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodolfo on 4/12/2017.
 */

public class User {


    public String email;
    public String id;

    public Boolean  available;
    public Boolean banned;

    public Integer rating;

    public List<String> reasons;

    public User(){}

    //this constructor is only called when retrieving from database
    public User(User user)
    {
        id=user.id;
    }
    public User(String id,String email)
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

    @Override
    public String toString()
    {
        return email+"  " +id;
    }
}
