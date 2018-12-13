package io.carpoolapp.screens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.carpoolapp.R;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.forgotPassword)
    TextView forgotPassword;

    private boolean isEmailValid = false;
    private boolean isPassValid = false;
    private Validation validation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        validation = new Validation();
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean emailCheck = validation.validateEmail(s.toString());
                if (!emailCheck) {
                    email.setError(getString(R.string.wrong_email));
                } else {
                    isEmailValid = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pass = s.toString();
                int passCheck = validation.validatePassword(pass);
                if (passCheck == 0) {
                    password.setError(getString(R.string.min_char));
                } else if (passCheck == 1) {
                    password.setError(getString(R.string.wrong_pass));
                } else {
                    isPassValid = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @OnClick({R.id.login, R.id.forgotPassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                String emailId = email.getText().toString();
                String pass = password.getText().toString();
                Pattern pattern = Pattern.compile("a*b");
                pass.matches("[a-z]+");
                if (email.getText().toString().trim().equalsIgnoreCase("")) {
                    email.setError(getString(R.string.field_blank));
                } else if (password.getText().toString().trim().equalsIgnoreCase("")) {
                    password.setError(getString(R.string.field_blank));
                }
                if (isEmailValid && isPassValid) {
                    Toast.makeText(this, "Calling Login Api", Toast.LENGTH_SHORT).show();
                    //Login API to call
                }
                break;
            case R.id.forgotPassword:
                break;
        }
    }
}

