package io.carpoolapp.screens;

import android.content.Intent;
import android.database.Cursor;
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
import io.carpoolapp.storage.UserDatabase;
import io.carpoolapp.model.UserDetails;
import io.carpoolapp.storage.MyPrefrence;
import io.carpoolapp.validation.Validation;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.mobile)
    EditText mobile;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.forgotPassword)
    TextView forgotPassword;

    private boolean isEmailValid = false;
    private boolean isPassValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle(getString(R.string.login));
        mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Validation.validateMobile(s.toString())) {
                    isEmailValid = true;
                    mobile.setError(null);
                } else {
                    mobile.setError(getString(R.string.invalidMob));
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
                int passCheck = Validation.validatePassword(pass);
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
                String id = mobile.getText().toString();
                String pass = password.getText().toString();
                Pattern pattern = Pattern.compile("a*b");
                pass.matches("[a-z]+");
                if (mobile.getText().toString().trim().equalsIgnoreCase("")) {
                    mobile.setError(getString(R.string.field_blank));
                } else if (password.getText().toString().trim().equalsIgnoreCase("")) {
                    password.setError(getString(R.string.field_blank));
                }
                if (isEmailValid && isPassValid) {
                    UserDatabase userDatabase = new UserDatabase(getApplicationContext());
                    Cursor res = userDatabase.loginValidate(id, pass);
                    if (res != null) {
                        MyPrefrence myPrefrence = new MyPrefrence(this);
                        myPrefrence.putValue("isLogIn", res.getString(0));
                        UserDetails userDetails = UserDetails.getInstance();
                        while (res.moveToNext()) {
                            userDetails.setMobile(res.getString(0));
                            userDetails.setGender(res.getString(1));
                            userDetails.setName(res.getString(2));
                            userDetails.setEmail(res.getString(3));
                            userDetails.setPassword(res.getString(4));
                            userDetails.setYearOfBirth(res.getString(5));
                            userDetails.setProfileCreationTime(Long.parseLong(res.getString(6)));
                            userDetails.setProfileModificationTime(Long.parseLong(res.getString(7)));
                            userDetails.setProfileUri(res.getString(8));
                            userDetails.setActiveRide(res.getString(9));
                        }
                        res.close();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, getString(R.string.noMatch), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.forgotPassword:
                break;
        }
    }
}

