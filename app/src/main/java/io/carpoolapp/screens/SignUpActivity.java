package io.carpoolapp.screens;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.carpoolapp.R;
import io.carpoolapp.storage.UserDatabase;
import io.carpoolapp.model.UserDetails;
import io.carpoolapp.storage.MyPrefrence;
import io.carpoolapp.validation.Validation;


public class SignUpActivity extends AppCompatActivity implements TextWatcher {

    @BindView(R.id.gender)
    Spinner gender;
    @BindView(R.id.yearPicker)
    Button yearPicker;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.confirmPassword)
    EditText confirmPassword;
    @BindView(R.id.signup)
    Button signup;
    @BindView(R.id.yearView)
    EditText yearView;
    @BindView(R.id.mobile)
    EditText mobileNumber;
    @BindView(R.id.name)
    EditText fullName;

    private Dialog d;
    private boolean isFnameValid = false;
    private boolean isMobileValid = false;
    private boolean isEmailValid = false;
    private boolean isYear = false;
    private boolean isPassValid = false;
    private boolean isConfPassValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle(getString(R.string.signup));
        String[] genderList = {"Male", "Female"};
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, genderList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(aa);
        fullName.addTextChangedListener(this);
        mobileNumber.addTextChangedListener(this);
        yearView.addTextChangedListener(this);
        email.addTextChangedListener(this);
        password.addTextChangedListener(this);
        confirmPassword.addTextChangedListener(this);
    }



    @OnClick({R.id.yearPicker, R.id.signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.yearPicker:
                showYearDialog();
                break;
            case R.id.signup:
                checkAllFields();
                break;
        }
    }

    private void checkAllFields() {
        String userGender = gender.getSelectedItem().toString();
        String fName = fullName.getText().toString().trim();
        String mobile = mobileNumber.getText().toString().trim();
        String birthYear = yearView.getText().toString().trim();
        String emailId = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String confPass = confirmPassword.getText().toString().trim();
        if (fName.equalsIgnoreCase("")) {
            fullName.setError(getString(R.string.field_blank));
        } else if (birthYear.equalsIgnoreCase("")) {
            yearView.setError(getString(R.string.select_birth_year));
        } else if (mobile.equalsIgnoreCase("")) {
            mobileNumber.setError(getString(R.string.field_blank));
        } else if (emailId.equalsIgnoreCase("")) {
            email.setError(getString(R.string.field_blank));
        } else if (pass.equalsIgnoreCase("")) {
            password.setError(getString(R.string.field_blank));
        } else if (confPass.equalsIgnoreCase("")) {
            confirmPassword.setError(getString(R.string.field_blank));
        }
        if (isFnameValid && isMobileValid && isEmailValid && isPassValid && isConfPassValid && isYear) {
            UserDetails userDetails = UserDetails.getInstance();
            userDetails.setGender(userGender);
            userDetails.setName(fName);
            userDetails.setMobile(mobile);
            userDetails.setYearOfBirth(birthYear);
            userDetails.setEmail(emailId);
            userDetails.setPassword(pass);
            userDetails.setProfileCreationTime(System.currentTimeMillis());
            userDetails.setProfileModificationTime(System.currentTimeMillis());
            UserDatabase userDatabase = new UserDatabase(getApplicationContext());
            String status = userDatabase.addTasks(userDetails);
            if (status.equalsIgnoreCase(getString(R.string.success))) {
                MyPrefrence myPrefrence = new MyPrefrence(getApplicationContext());
                myPrefrence.putValue("isLogIn", mobile);
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void showYearDialog() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        d = new Dialog(this);
        d.setTitle(getString(R.string.year_picker));
        d.setContentView(R.layout.year_dialog);
        Button set = (Button) d.findViewById(R.id.button1);
        Button cancel = (Button) d.findViewById(R.id.button2);
        final NumberPicker nopicker = (NumberPicker) d.findViewById(R.id.numberPicker1);
        nopicker.setMaxValue(year);
        nopicker.setMinValue(year - 110);
        nopicker.setWrapSelectorWheel(false);
        nopicker.setValue(year);
        nopicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearView.setText(String.valueOf(nopicker.getValue()));
                isYear = true;
                yearView.setError(null);
                d.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String str = s.toString();
        if (fullName.getText().hashCode() == s.hashCode()) {
            boolean checkName = Validation.validateName(str);
            if (!checkName) {
                fullName.setError(getString(R.string.wrong_name));
                isFnameValid = false;
            } else {
                fullName.setError(null);
                isFnameValid = true;
            }
        } else if (mobileNumber.getText().hashCode() == s.hashCode()) {
            String fMobile = mobileNumber.getText().toString();
            if (Validation.validateMobile(fMobile)) {
                mobileNumber.setError(null);
                isMobileValid = true;
            } else {
                mobileNumber.setError(getString(R.string.invalidMob));
                isMobileValid = false;
            }
        } else if (email.getText().hashCode() == s.hashCode()) {
            boolean checkEmail = Validation.validateEmail(str);
            if (!checkEmail) {
                email.setError(getString(R.string.wrong_name));
                isEmailValid = false;
            } else {
                email.setError(null);
                isEmailValid = true;
            }
        } else if (password.getText().hashCode() == s.hashCode()) {
            int passCheck = Validation.validatePassword(str);
            if (passCheck == 0) {
                password.setError(getString(R.string.min_char));
                isPassValid = false;
            } else if (passCheck == 1) {
                password.setError(getString(R.string.wrong_pass));
                isPassValid = false;
            } else {
                password.setError(null);
                isPassValid = true;
            }
        } else if (confirmPassword.getText().hashCode() == s.hashCode()) {
            if (str.compareTo(password.getText().toString()) == 0) {
                confirmPassword.setError(null);
                isConfPassValid = true;
            } else {
                confirmPassword.setError(getString(R.string.pass_not_match));
                isConfPassValid = false;
            }
        }

    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}

