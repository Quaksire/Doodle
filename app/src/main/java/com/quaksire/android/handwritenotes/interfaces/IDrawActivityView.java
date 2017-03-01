package com.quaksire.android.handwritenotes.interfaces;

import android.graphics.Bitmap;
import android.support.annotation.StringRes;

/**
 * Created by julio on 28/02/17.
 */

public interface IDrawActivityView {

    void onLoadBackground(Bitmap bitmap);

    Bitmap getBitmap();

    void setDrawingCacheEnabled(boolean enabled);

    void onError(@StringRes int stringId);
}
