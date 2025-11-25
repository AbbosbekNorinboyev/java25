package uz.brb.java25.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordValidator {
    private PasswordValidator() {
    }

    public static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static boolean validatePassword(String rawPassword, String hashPassword) {
        return encoder.matches(rawPassword, hashPassword);
    }
}
