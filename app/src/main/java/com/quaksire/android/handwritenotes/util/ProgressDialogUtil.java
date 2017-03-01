package com.quaksire.android.handwritenotes.util;

import android.app.ProgressDialog;
import android.content.Context;

import com.quaksire.android.handwritenotes.R;


/**
 * Created by Quaksire on 20/10/2016.
 */

public class ProgressDialogUtil {

    public static ProgressDialog createIndeterminateLoadingDialog(Context context) {
        ProgressDialog loadingDialog
                = new ProgressDialog(context);
        loadingDialog.setMessage(
                context.getResources()
                        .getString(R.string.activity_login_alertdialog_loading_description)
        );
        loadingDialog.setIndeterminate(true);
        loadingDialog.setCancelable(false);
        loadingDialog.setCanceledOnTouchOutside(false);
        return loadingDialog;
    }

}
