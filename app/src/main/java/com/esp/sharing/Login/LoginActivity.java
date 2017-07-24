package com.esp.sharing.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.esp.sharing.R;

public class LoginActivity extends AppCompatActivity {

    private Button facebookButton;
    private Button googleButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        facebookButton = (Button) findViewById(R.id.login_facebook_button);
        googleButton = (Button) findViewById(R.id.login_google_button);
        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFacebookLogin();
            }
        });
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGoogleLogin();
            }
        });
    }

    private void onGoogleLogin() {
        finish();
    }

    private void onFacebookLogin() {
        finish();
    }


}
