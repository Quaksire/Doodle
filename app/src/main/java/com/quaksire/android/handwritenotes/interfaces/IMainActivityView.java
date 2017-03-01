package com.quaksire.android.handwritenotes.interfaces;

import android.database.Cursor;

/**
 * Created by julio on 28/02/17.
 */

public interface IMainActivityView {
    void populateListValues(Cursor cursor);

    void setPlaceholderVisible(boolean visible);

    void onError(int stringId);
}
