package com.quaksire.android.handwritenotes.interfaces;

import android.content.Intent;

/**
 * Created by julio on 28/02/17.
 */
public interface IMainActivityPresenter {
    void openDatabase();
    void closeDatabase();
    void loadContent();

    void deleteImage(int id);
    void startDrawActvitivy(int imageId);
    void loadIntent(Intent intent);
}
