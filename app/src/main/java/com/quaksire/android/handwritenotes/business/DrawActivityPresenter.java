package com.quaksire.android.handwritenotes.business;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.quaksire.android.handwritenotes.R;
import com.quaksire.android.handwritenotes.db.tables.TableImage;
import com.quaksire.android.handwritenotes.interfaces.IDrawActivityPresenter;
import com.quaksire.android.handwritenotes.interfaces.IDrawActivityView;
import com.quaksire.android.handwritenotes.model.Image;
import com.quaksire.android.handwritenotes.util.SaveImage;

import static com.quaksire.android.handwritenotes.parameters.IntentParameters.*;

/**
 * Created by julio on 28/02/17.
 */

public class DrawActivityPresenter
        implements IDrawActivityPresenter {

    private TableImage mTableImage;

    private final IDrawActivityView mView;
    private final Context mContext;

    private int mUserId;
    private int mImageId;

    public DrawActivityPresenter(Context context, IDrawActivityView iDrawActivityView) {
        this.mContext = context;
        this.mTableImage = new TableImage(context);
        this.mView = iDrawActivityView;
    }

    @Override
    public void openDatabase() {
        Log.d("QUAKSIRE", "DrawActivityPresenter.openDatabase()");
        this.mTableImage.open();
    }

    @Override
    public void closeDatabase() {
        Log.d("QUAKSIRE", "DrawActivityPresenter.closeDatabase()");
        this.mTableImage.close();
    }

    @Override
    public void loadContent() {
        Log.d("QUAKSIRE", "DrawActivityPresenter.loadContent()");
        if(mImageId > 0) {
            new LoadBackground().execute();
        }
    }

    @Override
    public void loadIntent(Intent intent) {
        Log.d("QUAKSIRE", "DrawActivityPresenter.loadIntent()");
        if(intent == null) {
            this.mUserId = VALUE_DEFAULT_USER_ID;
            this.mImageId = VALUE_DEFAULT_IMAGE_ID;
        } else {
            this.mUserId = intent.getIntExtra(PARAMETER_USER_ID, VALUE_DEFAULT_USER_ID);
            this.mImageId = intent.getIntExtra(PARAMETER_IMAGE_ID, VALUE_DEFAULT_IMAGE_ID);
        }
    }

    @Override
    public void onSaveImage() {
        Log.d("QUAKSIRE", "DrawActivityPresenter.onSaveImage()");
        new SaveImage(this.mContext, this.mView, this.mTableImage, false, mUserId, mImageId).execute();
    }

    @Override
    public void onShareImage() {
        Log.d("QUAKSIRE", "DrawActivityPresenter.onShareImage()");
        new SaveImage(this.mContext, this.mView, this.mTableImage, true, mUserId, mImageId).execute();
    }

    private class LoadBackground extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                Cursor cursor = mTableImage.findImagesByImageId(mImageId);
                cursor.moveToFirst();
                Image image = TableImage.cursorToImage(cursor);
                return image.image;
            } catch (Exception e) {
                Log.e("QUAKSIRE", "DrawActivityPresenter.LoadContent", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap != null) {
                mView.onLoadBackground(bitmap);
            } else {
                mView.onError(R.string.error_open_image);
            }
        }
    }
}
