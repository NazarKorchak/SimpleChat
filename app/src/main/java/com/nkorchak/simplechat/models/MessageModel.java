package com.nkorchak.simplechat.models;

import com.google.firebase.database.DataSnapshot;
import com.nkorchak.simplechat.interfaces.ModelCallBacks;

import java.util.ArrayList;

public class MessageModel {
    private ArrayList<ChatPojo> mMessages;

    public void addMessages(DataSnapshot dataSnapshot, ModelCallBacks callBacks) {
        if (mMessages == null) {
            mMessages = new ArrayList<>();
        }
        ChatPojo chatPojo = new ChatPojo(dataSnapshot);
        mMessages.add(chatPojo);
        callBacks.onModelUpdated(mMessages);
    }
}
