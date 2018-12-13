package io.carpoolapp.screens;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
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


public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TextWatcher {

    @BindView(R.id.gender)
    Spinner gender;
    @BindView(R.id.firstName)
    EditText firstName;
    @BindView(R.id.lastName)
    EditText lastName;
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

    private Dialog d;
    private Validation validation;
    private boolean isFnameValid = false;
    private boolean isLnameValid = false;
    private boolean isEmailValid = false;
    private boolean isYear = false;
    private boolean isPassValid = false;
    private boolean isConfPassValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        String[] genderList = {"Male", "Female"};
        gender.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, genderList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(aa);
        firstName.addTextChangedListener(this);
        lastName.addTextChangedListener(this);
        yearView.addTextChangedListener(this);
        email.addTextChangedListener(this);
        password.addTextChangedListener(this);
        confirmPassword.addTextChangedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //
//        Toast.makeText(getApplicationContext(), parent.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
        String fName = firstName.getText().toString().trim();
        String lName = lastName.getText().toString().trim();
        String birthYear = yearView.getText().toString().trim();
        String emailId = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String confPass = confirmPassword.getText().toString().trim();
        String genderSelected = gender.getSelectedItem().toString();
        if (fName.equalsIgnoreCase("")) {
            firstName.setError(getString(R.string.field_blank));
        } else if (lName.equalsIgnoreCase("")) {
            lastName.setError(getString(R.string.field_blank));
        } else if (birthYear.equalsIgnoreCase("")) {
            yearView.setError(getString(R.string.select_birth_year));
        } else if (emailId.equalsIgnoreCase("")) {
            email.setError(getString(R.string.field_blank));
        } else if (pass.equalsIgnoreCase("")) {
            password.setError(getString(R.string.field_blank));
        } else if (confPass.equalsIgnoreCase("")) {
            confirmPassword.setError(getString(R.string.field_blank));
        }
        if (isFnameValid && isLnameValid && isEmailValid && isPassValid && isConfPassValid && isYear) {
            Toast.makeText(this, "Calling Signup API", Toast.LENGTH_SHORT).show();
            //Signup API call
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
        validation = new Validation();
        if (firstName.getText().hashCode() == s.hashCode()) {
            boolean checkName = validation.validateName(str);
            if (!checkName) {
                firstName.setError(getString(R.string.wrong_name));
                isFnameValid = false;
            } else {
                firstName.setError(null);
                isFnameValid = true;
            }
        } else if (lastName.getText().hashCode() == s.hashCode()) {
            boolean checkName = validation.validateName(str);
            if (!checkName) {
                lastName.setError(getString(R.string.wrong_name));
                isLnameValid = false;
            } else {
                lastName.setError(null);
                isLnameValid = true;
            }
        } else if (email.getText().hashCode() == s.hashCode()) {
            boolean checkEmail = validation.validateEmail(str);
            if (!checkEmail) {
                email.setError(getString(R.string.wrong_name));
                isEmailValid = false;
            } else {
                email.setError(null);
                isEmailValid = true;
            }
        } else if (password.getText().hashCode() == s.hashCode()) {
            int passCheck = validation.validatePassword(str);
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

