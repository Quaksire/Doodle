package com.quaksire.android.handwritenotes.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.quaksire.android.handwritenotes.DrawActivity;
import com.quaksire.android.handwritenotes.db.tables.TableImage;
import com.quaksire.android.handwritenotes.interfaces.IDrawActivityView;
import com.quaksire.android.handwritenotes.model.Image;

import java.io.File;
import java.io.FileOutputStream;

/**
 * HandWriteNotes
 * Created by domingj on 26/01/2016.
 */
public class SaveImage extends AsyncTask<Void, Void, Bitmap> {

    private Context mContext;
    private TableImage tableImage;
    private Bitmap bitmap;
    private boolean shared;
    private IDrawActivityView mView;
    private int userId, imageId;

    public SaveImage(Context context, IDrawActivityView view, TableImage tableImage,
                     boolean shared, int userId, int imageId) {
        this.mContext = context;
        this.mView = view;
        this.tableImage = tableImage;
        this.shared = shared;
        this.userId = userId;
        this.imageId = imageId;
    }

    @Override
    protected void onPreExecute() {
        mView.setDrawingCacheEnabled(true);
        bitmap = mView.getBitmap();
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            if(imageId == -1) {
                //Create
                Image image = new Image();
                image.createdBy = userId;
                image.createdOn = System.currentTimeMillis();
                image.image = bitmap;
                image.updatedOn = System.currentTimeMillis();
                image.name = "Name";
                image.description = "Description";

                tableImage.addEntry(image);
            } else {
                //Update
                Cursor cursor = tableImage.findImagesByImageId(imageId);
                cursor.moveToFirst();
                Image image = TableImage.cursorToImage(cursor);
                image.image = bitmap;
                image.updatedOn = System.currentTimeMillis();
                tableImage.updateImage(image);
            }

            return bitmap;
        } catch (Exception e) {
            Log.e("QUAKSIRE-HandWriter", "Exception saving draw", e);
        } catch (Error e) {
            Log.e("QUAKSIRE-HandWriter", "Error saving draw", e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(bitmap != null) {
            if(shared) {
                String type = "image/*";
                Shared.createSharedIntent(this.mContext, type, bitmap);
            } else {
                Toast.makeText(this.mContext, "Saved!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this.mContext, "Error!", Toast.LENGTH_SHORT).show();
        }
        mView.setDrawingCacheEnabled(false);
    }
}
