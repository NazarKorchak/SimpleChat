package com.nkorchak.simplechat.presenters;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nkorchak.simplechat.views.ILoginView;

public class LoginPresenter {

    private ILoginView mILoginView;

    public LoginPresenter(ILoginView iLoginCallbacks) {
        this.mILoginView = iLoginCallbacks;
    }

    private void signUpUser(String email, String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) mILoginView, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mILoginView.authSuccessful();
                        } else {
                            mILoginView.showToast("Authentication failed, please check your internet connection");
                        }
                    }
                });
    }

    public void loginUser(final String email, final String password) {
        if (email.isEmpty() || password.isEmpty()) {
            mILoginView.showToast("Please, fill all fields");
        } else {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) mILoginView, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                mILoginView.authSuccessful();
                            } else {
                                signUpUser(email, password);
                            }
                        }
                    });
        }
    }

    public void isUserLogged() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            mILoginView.authSuccessful();
        }
    }
}