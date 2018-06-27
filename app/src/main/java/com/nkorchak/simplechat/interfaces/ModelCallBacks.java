package com.nkorchak.simplechat.interfaces;

import com.nkorchak.simplechat.models.ChatPojo;

import java.util.ArrayList;

public interface ModelCallBacks {
    void onModelUpdated(ArrayList<ChatPojo> messages);
}
