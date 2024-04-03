package com.rashlight.carnival.value;

import com.rashlight.carnival.entity.User;
import io.jmix.core.Messages;
import io.jmix.core.security.CurrentAuthentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Locale;

/**
 * Set of common variables and functions to works globally
 */
public class CarnivalToolbox {
    public static final String AppName = "com.rashlight.carnival";
    public static final String ErrorBundle = AppName + ".error";
    public static String getError(Messages messages, String key) {
        return messages.getMessage(
                     ErrorBundle,
                     key,
                     Locale.getDefault()
        );
    }
    public static User getLoggedInUser(CurrentAuthentication auth) {
        if (auth.isSet()) {
            UserDetails userDetails = auth.getUser();
            // Cast UserDetails to your specific User class
            return (User)userDetails;
        } else {
            throw new IllegalStateException("No user is currently authenticated.");
        }
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}