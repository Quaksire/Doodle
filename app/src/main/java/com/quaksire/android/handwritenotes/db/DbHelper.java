package com.quaksire.android.handwritenotes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.quaksire.android.handwritenotes.db.tables.TableImage;
import com.quaksire.android.handwritenotes.db.tables.TableUser;

/**
 * Created by Quaksire on 21/10/2016.
 */

public class DbHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "doodle.db";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableUser.CREATE_TABLE_USER);
        db.execSQL(TableImage.CREATE_TABLE_IMAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL(TableUser.DROP_TABLE_USER);
        db.execSQL(TableImage.DROP_TABLE_IMAGE);

        // create new table
        onCreate(db);
    }
}
