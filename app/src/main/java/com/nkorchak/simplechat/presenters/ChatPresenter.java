package com.nkorchak.simplechat.presenters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.nkorchak.simplechat.interfaces.FirebaseCallBacks;
import com.nkorchak.simplechat.interfaces.ModelCallBacks;
import com.nkorchak.simplechat.models.ChatPojo;
import com.nkorchak.simplechat.models.MessageModel;
import com.nkorchak.simplechat.utils.FirebaseManager;
import com.nkorchak.simplechat.views.IChatView;

import java.util.ArrayList;

public class ChatPresenter implements FirebaseCallBacks, ModelCallBacks {

    private IChatView mIChatView;
    private MessageModel mModel;

    public ChatPresenter(IChatView iChatView) {
        this.mIChatView = iChatView;
        this.mModel = new MessageModel();
    }

    public void sendMessageToFirebase(String message) {
        if (!message.trim().isEmpty()) {
            FirebaseManager.getInstance(this).sendMessageToFirebase(message);
        }
        mIChatView.clearEditText();
    }

    public void setListener() {
        FirebaseManager.getInstance(this).addMessageListeners();
    }

    public void onDestory() {
        FirebaseManager.getInstance(this).removeListeners();
        FirebaseManager.getInstance(this).destroy();
        mIChatView = null;
    }

    @Override
    public void onNewMessage(DataSnapshot dataSnapshot) {
        mIChatView.hideProgress();
        mModel.addMessages(dataSnapshot, this);
    }

    @Override
    public void onModelUpdated(ArrayList<ChatPojo> messages) {
        if (messages.size() > 0) {
            mIChatView.updateList(messages);
        }
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        mIChatView.openLoginScreen();
    }
}
