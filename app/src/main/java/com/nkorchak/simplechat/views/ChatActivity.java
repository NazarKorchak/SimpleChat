package com.nkorchak.simplechat.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.nkorchak.simplechat.R;
import com.nkorchak.simplechat.models.ChatPojo;
import com.nkorchak.simplechat.presenters.ChatPresenter;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener, IChatView {

    private ChatPresenter mChatPresenter;

    private EditText mEditTextChat;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mChatPresenter = new ChatPresenter(this);

        mEditTextChat = findViewById(R.id.edittext_chat_message);
        mRecyclerView = findViewById(R.id.recycler_view);
        mProgressBar = findViewById(R.id.progressBar);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.button_send_message).setOnClickListener(this);

        mProgressBar.setVisibility(View.VISIBLE);
        mChatPresenter.setListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_chat, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            mChatPresenter.logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_send_message:
                mChatPresenter.sendMessageToFirebase(mEditTextChat.getText().toString());
                break;
        }
    }

    @Override
    public void updateList(ArrayList<ChatPojo> list) {
        ChatAdapter chatAdapter = new ChatAdapter(this, list);
        mRecyclerView.setAdapter(chatAdapter);
        mRecyclerView.scrollToPosition(list.size() - 1);
    }

    @Override
    public void clearEditText() {
        mEditTextChat.setText("");
    }

    @Override
    public void openLoginScreen() {
        finish();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void hideProgress() {
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mChatPresenter.onDestory();
    }
}
