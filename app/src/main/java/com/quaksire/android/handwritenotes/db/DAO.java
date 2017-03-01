package com.quaksire.android.handwritenotes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Quaksire on 21/10/2016.
 */

public class DAO {
    private final DbHelper photoDBHelper;
    public SQLiteDatabase database;

    public DAO(Context context) {
        this.photoDBHelper = new DbHelper(context);
    }

    public void open() {
        database = this.photoDBHelper.getWritableDatabase();
    }

    public void close() {
        this.photoDBHelper.close();
    }

    public boolean isOpen() {
        return this.database.isOpen();
    }

    public boolean isClose() {
        return !this.database.isOpen();
    }
}
