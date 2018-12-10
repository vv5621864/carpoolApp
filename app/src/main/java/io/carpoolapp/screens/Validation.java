package io.carpoolapp.screens;

import android.util.Patterns;

public class Validation {
    public boolean validateEmail(String email) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false;
        } else {
            return true;
        }
    }

    public int validatePassword(String pass) {
        String regex = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        if (pass.length() < 8) {
            return 0;
        } else if (!pass.matches(regex)) {
            return 1;
        } else {
            return 2;
        }
    }

    public boolean validateName(String name) {
        String regex = "[A-Z][a-z\\s]*";
        if (!name.matches(regex)) {
            return false;
        } else {
            return true;
        }
    }
}
