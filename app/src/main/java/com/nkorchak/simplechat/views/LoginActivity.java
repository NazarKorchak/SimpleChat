package com.nkorchak.simplechat.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nkorchak.simplechat.R;
import com.nkorchak.simplechat.presenters.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ILoginView {
    private LoginPresenter mLoginPresenter;

    private EditText emailEditText, passwordEditText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLoginPresenter = new LoginPresenter(this);

        mLoginPresenter.isUserLogged();

        setContentView(R.layout.activity_login);

        findViewById(R.id.btn_login).setOnClickListener(this);

        emailEditText = findViewById(R.id.et_email);
        passwordEditText = findViewById(R.id.et_password);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                progressBar.setVisibility(View.VISIBLE);
                mLoginPresenter.loginUser(emailEditText.getText().toString(), passwordEditText.getText().toString());
                break;
        }
    }

    @Override
    public void showToast(String message) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void authSuccessful() {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
        finish();
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }
}