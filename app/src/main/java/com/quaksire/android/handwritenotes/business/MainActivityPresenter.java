package com.quaksire.android.handwritenotes.business;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.quaksire.android.handwritenotes.DrawActivity;
import com.quaksire.android.handwritenotes.R;
import com.quaksire.android.handwritenotes.db.tables.TableImage;
import com.quaksire.android.handwritenotes.interfaces.IMainActivityPresenter;
import com.quaksire.android.handwritenotes.interfaces.IMainActivityView;
import com.quaksire.android.handwritenotes.parameters.IntentParameters;

import static com.quaksire.android.handwritenotes.parameters.IntentParameters.*;

/**
 * Created by julio on 28/02/17.
 */

public class MainActivityPresenter
        implements IMainActivityPresenter {

    private final IMainActivityView iMainActivityView;

    private TableImage mTableImage;
    private Context mContext;
    private int mUserId;

    public MainActivityPresenter(Context context, IMainActivityView iMainActivityView) {
        this.mContext = context;
        this.iMainActivityView = iMainActivityView;
        this.mTableImage = new TableImage(context);
    }

    public void openDatabase() {
        Log.d("QUAKSIRE", "MainActivityPresenter.openDatabase()");
        this.mTableImage.open();
    }

    @Override
    public void closeDatabase() {
        Log.d("QUAKSIRE", "MainActivityPresenter.closeDatabase()");
        this.mTableImage.close();
    }

    @Override
    public void deleteImage(int id) {
        Log.d("QUAKSIRE", "MainActivityPresenter.deleteImage(" + id + ")");
        this.mTableImage.deleteImage(id);
    }

    @Override
    public void loadContent() {
        Log.d("QUAKSIRE", "MainActivityPresenter.loadContent()");
        new LoadContent().execute(this.mUserId);
    }

    @Override
    public void startDrawActvitivy(int imageId) {
        Log.d("QUAKSIRE", "MainActivityPresenter.startDrawActivity(" + imageId + ")");
        Intent intent = new Intent(this.mContext, DrawActivity.class);
        intent.putExtra(PARAMETER_USER_ID, this.mUserId);
        intent.putExtra(IntentParameters.PARAMETER_IMAGE_ID, imageId);
        this.mContext.startActivity(intent);
    }

    @Override
    public void loadIntent(Intent intent) {
        Log.d("QUAKSIRE", "MainActivityPresenter.loadIntent()");
        if(intent == null || !intent.hasExtra(PARAMETER_USER_ID)) {
            this.iMainActivityView.onError(R.string.error_user_id);
            this.mUserId = VALUE_DEFAULT_USER_ID;
        } else if(intent.hasExtra(PARAMETER_USER_ID)) {
            this.mUserId = intent.getIntExtra(PARAMETER_USER_ID, VALUE_DEFAULT_USER_ID);
        }
    }

    //TODO - Move to RxJava
    class LoadContent extends AsyncTask<Integer, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Integer... params) {
            return mTableImage.findImagesByUserId(params[0]);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            cursor.moveToFirst();
            iMainActivityView.populateListValues(cursor);
            iMainActivityView.setPlaceholderVisible(cursor.getCount() == 0);
        }
    }
}
