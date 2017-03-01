package com.quaksire.android.handwritenotes.db.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.quaksire.android.handwritenotes.db.DAO;
import com.quaksire.android.handwritenotes.model.User;

/**
 * Created by Quaksire on 21/10/2016.
 */

public class TableUser extends DAO {

    public static final String TABLE_USER_NAME = "User";

    public static final String TABLE_USER_COLUMN__ID = "_id";
    public static final String TABLE_USER_COLUMN_EMAIL = "email";
    public static final String TABLE_USER_COLUMN_PASSWORD = "password";
    public static final String TABLE_USER_COLUMN_PIN = "pin";

    // Drop table PROJECTS
    public static final String DROP_TABLE_USER = "DROP TABLE IF EXISTS "
            + TABLE_USER_NAME;

    public static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER_NAME + " ("
            + TABLE_USER_COLUMN__ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TABLE_USER_COLUMN_EMAIL + " TEXT NOT NULL, "
            + TABLE_USER_COLUMN_PASSWORD + " TEXT NOT NULL, "
            + TABLE_USER_COLUMN_PIN + " TEXT NOT NULL);";

    public static final String[] USER_ALL_COLUMNS = {
            TABLE_USER_COLUMN__ID,
            TABLE_USER_COLUMN_EMAIL,
            TABLE_USER_COLUMN_PASSWORD,
            TABLE_USER_COLUMN_PIN};
    
    public TableUser(Context context) {
        super(context);
    }

    public long addEntry(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_USER_COLUMN_EMAIL, user.email);
        contentValues.put(TABLE_USER_COLUMN_PASSWORD, user.password);
        contentValues.put(TABLE_USER_COLUMN_PIN, user.pin);

        return database.insert(
                TABLE_USER_NAME,
                null,
                contentValues);
    }

    public Cursor findUserByUserEmail(String email) {
        String constrains = email;
        return database.rawQuery("SELECT * From " + TABLE_USER_NAME + " where "
                + TABLE_USER_COLUMN_EMAIL + " Like ?",  new String[]{constrains});
    }

    public static User cursorToUser(Cursor cursor) {
        User image = new User();
        image._id = cursor.getInt(0);
        image.email = cursor.getString(1);
        image.password = cursor.getString(2);
        image.pin = cursor.getString(3);
        return image;
    }
}
