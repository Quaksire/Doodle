package com.quaksire.android.handwritenotes.db.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;

import com.quaksire.android.handwritenotes.db.DAO;
import com.quaksire.android.handwritenotes.db.util.DbBlobConversions;
import com.quaksire.android.handwritenotes.model.Image;

/**
 * Created by Quaksire on 21/10/2016.
 */

public class TableImage extends DAO {

    public static final String TABLE_IMAGE_NAME = "Image";

    public static final String TABLE_IMAGE_COLUMN__ID = "_id";
    public static final String TABLE_IMAGE_COLUMN_NAME = "name";
    public static final String TABLE_IMAGE_COLUMN_IMAGE = "image";
    public static final String TABLE_IMAGE_COLUMN_DESCRIPTION = "description";
    public static final String TABLE_IMAGE_COLUMN_CREATED_ON = "createdOn";
    public static final String TABLE_IMAGE_COLUMN_CREATED_BY = "createdBy";
    public static final String TABLE_IMAGE_COLUMN_UPDATED_ON = "updatedOn";

    // Drop table PROJECTS
    public static final String DROP_TABLE_IMAGE = "DROP TABLE IF EXISTS "
            + TABLE_IMAGE_NAME;

    public static final String CREATE_TABLE_IMAGE = "CREATE TABLE "
            + TABLE_IMAGE_NAME + " ("
            + TABLE_IMAGE_COLUMN__ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TABLE_IMAGE_COLUMN_NAME + " TEXT, "
            + TABLE_IMAGE_COLUMN_IMAGE + " BLOB NOT NULL, "
            + TABLE_IMAGE_COLUMN_DESCRIPTION + " TEXT NOT NULL, "
            + TABLE_IMAGE_COLUMN_CREATED_ON + " INTEGER, "
            + TABLE_IMAGE_COLUMN_CREATED_BY + " TEXT NOT NULL, "
            + TABLE_IMAGE_COLUMN_UPDATED_ON + " INTEGER);";

    public static final String[] IMAGE_ALL_COLUMNS = {
            TABLE_IMAGE_COLUMN__ID,
            TABLE_IMAGE_COLUMN_NAME,
            TABLE_IMAGE_COLUMN_IMAGE,
            TABLE_IMAGE_COLUMN_DESCRIPTION,
            TABLE_IMAGE_COLUMN_CREATED_ON,
            TABLE_IMAGE_COLUMN_CREATED_BY,
            TABLE_IMAGE_COLUMN_UPDATED_ON};

    public TableImage(Context context) {
        super(context);
    }

    public void addEntry(Image image) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_IMAGE_COLUMN_NAME, image.name);
        contentValues.put(TABLE_IMAGE_COLUMN_IMAGE, DbBlobConversions.getBytes(image.image));
        contentValues.put(TABLE_IMAGE_COLUMN_DESCRIPTION, image.description);
        contentValues.put(TABLE_IMAGE_COLUMN_CREATED_ON, image.createdOn);
        contentValues.put(TABLE_IMAGE_COLUMN_CREATED_BY, image.createdBy);
        contentValues.put(TABLE_IMAGE_COLUMN_UPDATED_ON, image.updatedOn);

        database.insert(
                TABLE_IMAGE_NAME,
                null,
                contentValues);
    }

    public Cursor findImagesByUserId(int userId) {
        String constrains = "" + userId;
        return database.rawQuery("SELECT * From " + TABLE_IMAGE_NAME + " where "
                + TABLE_IMAGE_COLUMN_CREATED_BY + " = ?",  new String[]{constrains});
    }

    public Cursor findImagesByImageId(int imageId) {
        String constrains = "" + imageId;
        return database.rawQuery("SELECT * From " + TABLE_IMAGE_NAME + " where "
                + TABLE_IMAGE_COLUMN__ID + " = ?",  new String[]{constrains});
    }

    public void updateImage(Image image) {
        String [] args = new String[]{"" + image.id};
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_IMAGE_COLUMN_NAME, image.name);
        contentValues.put(TABLE_IMAGE_COLUMN_IMAGE, DbBlobConversions.getBytes(image.image));
        contentValues.put(TABLE_IMAGE_COLUMN_DESCRIPTION, image.description);
        contentValues.put(TABLE_IMAGE_COLUMN_CREATED_ON, image.createdOn);
        contentValues.put(TABLE_IMAGE_COLUMN_CREATED_BY, image.createdBy);
        contentValues.put(TABLE_IMAGE_COLUMN_UPDATED_ON, image.updatedOn);
        database.update(TABLE_IMAGE_NAME,
                contentValues,
                TABLE_IMAGE_COLUMN__ID + " = ?",
                args);
    }

    public int deleteImage(int id) {
        String [] args = new String[]{"" + id};
        return database.delete(
                TABLE_IMAGE_NAME,
                TABLE_IMAGE_COLUMN__ID + " = ?",
                args);
    }

    public static Image cursorToImage(Cursor cursor) {
        Image image = new Image();
        image.id = cursor.getInt(0);
        image.name = cursor.getString(1);
        image.image = DbBlobConversions.getImage(cursor.getBlob(2));
        image.description = cursor.getString(3);
        image.createdOn = cursor.getLong(4);
        image.createdBy = cursor.getInt(5);
        image.updatedOn = cursor.getLong(6);
        return image;
    }
}
