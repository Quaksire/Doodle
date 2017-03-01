package com.quaksire.android.handwritenotes.interfaces;

import android.content.Intent;

/**
 * Created by julio on 28/02/17.
 */

public interface IDrawActivityPresenter {
    void openDatabase();
    void closeDatabase();
    void loadContent();

    void loadIntent(Intent intent);

    void onSaveImage();
    void onShareImage();
}
