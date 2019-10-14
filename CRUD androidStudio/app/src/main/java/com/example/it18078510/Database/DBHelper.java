package com.example.it18078510.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserProfile.Users.TABLE_NAME + " (" +
                    UserProfile.Users._ID + " INTEGER PRIMARY KEY," +
                    UserProfile.Users.COLUMN_Username + " TEXT," +
                    UserProfile.Users.COLUMN_DOB + " TEXT," +
                    UserProfile.Users.COLUMN_Gender + " TEXT," +
                    UserProfile.Users.COLUMN_Pass + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserProfile.Users.TABLE_NAME;

    //----------------------------------------------------------------------------------------------------------------------------
    //Add function
    //----------------------------------------------------------------------------------------------------------------------------

    public long addInfo(String username, String DOB, String gender, String password){
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(UserProfile.Users.COLUMN_Username, username);
        values.put(UserProfile.Users.COLUMN_DOB, DOB);
        values.put(UserProfile.Users.COLUMN_Gender, gender);
        values.put(UserProfile.Users.COLUMN_Pass, password);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(UserProfile.Users.TABLE_NAME, null, values);

        return newRowId;

    }
    //----------------------------------------------------------------------------------------------------------------------------
    //Update Function
    //----------------------------------------------------------------------------------------------------------------------------


    public Boolean updateinfo(String username, String DOB, String gender, String password){

        SQLiteDatabase db = getWritableDatabase();

    // New value for all columns

        ContentValues values = new ContentValues();
        values.put(UserProfile.Users.COLUMN_Username, username);
        values.put(UserProfile.Users.COLUMN_DOB, DOB);
        values.put(UserProfile.Users.COLUMN_Gender, gender);
        values.put(UserProfile.Users.COLUMN_Pass, password);

    // Which row to update, based on the title
        String selection = UserProfile.Users.COLUMN_Username + " LIKE ?";
        String[] selectionArgs = { username};

        int count = db.update(
                UserProfile.Users.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if(count > 0 ){
            return true;
        }else{
            return false;
        }

    }


    //----------------------------------------------------------------------------------------------------------------------------
    //Read All data
    //----------------------------------------------------------------------------------------------------------------------------

    public List readAll() {

        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                UserProfile.Users.COLUMN_Username,
                UserProfile.Users.COLUMN_DOB,
                UserProfile.Users.COLUMN_Gender,
                UserProfile.Users.COLUMN_Pass,

        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                UserProfile.Users.COLUMN_Username + " DESC";

        Cursor cursor = db.query(
                UserProfile.Users.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_Username));
            String DOB = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_DOB));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_Gender));
            String Password = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_Pass));

            itemIds.add(username);
            itemIds.add(DOB);
            itemIds.add(gender);
            itemIds.add(Password);
        }
        cursor.close();

        return itemIds;
    }


    //----------------------------------------------------------------------------------------------------------------------------
    //Search by overiding readAll()
    //----------------------------------------------------------------------------------------------------------------------------

    public List readAll(String username){
        SQLiteDatabase db = getReadableDatabase();

    // Define a projection that specifies which columns from the database
    // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                UserProfile.Users.COLUMN_Username,
                UserProfile.Users.COLUMN_DOB,
                UserProfile.Users.COLUMN_Gender,
                UserProfile.Users.COLUMN_Pass,

        };

    // Filter results WHERE "title" = 'My Title'
        String selection = UserProfile.Users.COLUMN_Username + " LIKE ?";
        String[] selectionArgs = { username };

    // How you want the results sorted in the resulting Cursor
        String sortOrder = UserProfile.Users.COLUMN_Username + " DESC";

        Cursor cursor = db.query(
                UserProfile.Users.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            String usernName = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_Username));
            String DOB = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_DOB));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_Gender));
            String Password = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_Pass));

            itemIds.add(usernName);//0
            itemIds.add(DOB);
            itemIds.add(gender);
            itemIds.add(Password);
        }
        cursor.close();

        return itemIds;

    }


    //----------------------------------------------------------------------------------------------------------------------------
    //Delete Function
    //----------------------------------------------------------------------------------------------------------------------------

    public int deleteInfo(String username){
        SQLiteDatabase db = getReadableDatabase();

        // Define 'where' part of query.
        String selection = UserProfile.Users.COLUMN_Username + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { username };
         // Issue SQL statement.
        int deletedRows = db.delete(UserProfile.Users.TABLE_NAME, selection, selectionArgs);

        return deletedRows;
    }



}