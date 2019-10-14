package com.example.it18078510.Database;

import android.provider.BaseColumns;

public final class UserProfile {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private UserProfile() {}

    /* Inner class that defines the table contents */
    public static class Users implements BaseColumns {
        public static final String TABLE_NAME = "UserInfo";
        public static final String COLUMN_Username = "userName";
        public static final String COLUMN_DOB = "dateOfBirth";
        public static final String COLUMN_Gender = "Gender";
        public static final String COLUMN_Pass = "password";
    }
}