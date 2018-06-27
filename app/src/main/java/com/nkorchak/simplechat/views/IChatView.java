package com.nkorchak.simplechat.views;

import com.nkorchak.simplechat.models.ChatPojo;

import java.util.ArrayList;


public interface IChatView {
    void updateList(ArrayList<ChatPojo> list);
    void clearEditText();
    void openLoginScreen();
    void hideProgress();
}
