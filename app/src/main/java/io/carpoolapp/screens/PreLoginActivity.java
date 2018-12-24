package io.carpoolapp.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.carpoolapp.R;


public class PreLoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.signup)
    Button signup;
    @BindView(R.id.login)
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_login);
        ButterKnife.bind(this);
        signup.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == signup.getId()) {
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
        } else if (v.getId() == login.getId()) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }
}

