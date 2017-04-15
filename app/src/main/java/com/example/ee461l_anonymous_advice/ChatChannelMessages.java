package com.example.ee461l_anonymous_advice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodolfo on 4/14/2017.
 */

public class ChatChannelMessages {
    String channelId;
    List<FriendlyMessage> messages = new ArrayList<>();

    public ChatChannelMessages(String id)
    {
        channelId=id;
    }

    public void addMessages(FriendlyMessage message)
    {
        messages.add(message);
    }
}
