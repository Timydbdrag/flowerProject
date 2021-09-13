package com.greenstreet.warehouse.config;

public class Constants {
    public static final String LOGIN_REGEX =
            "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final int PASSWORD_MIN_LENGTH = 4;
    public static final int PASSWORD_MAX_LENGTH = 50;
    public static final String PASSWORD_CHANGED = "Password changed!";
}
