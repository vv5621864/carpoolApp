package io.carpoolapp.validation;

import android.util.Patterns;

public class Validation {
    public static boolean validateEmail(String email) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false;
        } else {
            return true;
        }
    }

    public static int validatePassword(String pass) {
        String regex = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        if (pass.length() < 8) {
            return 0;
        } else if (!pass.matches(regex)) {
            return 1;
        } else {
            return 2;
        }
    }

    public static boolean validateName(String name) {
        String regex = "[A-Za-z\\s]+";

        if (!name.trim().matches(regex)) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean validateMobile(String mobile) {
        String regex = "[0-9]{10}";
        if (mobile.trim().replaceAll("[\\s]*", "").matches(regex)) {
            return true;
        }
        return false;
    }
}
