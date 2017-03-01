package com.quaksire.android.handwritenotes.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.quaksire.android.handwritenotes.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * HandWriteNotes
 * Created by domingj on 26/01/2016.
 */
public class Shared {

    private static final int PNG_COMPRESS_QUALITY = 100;

    public static void createSharedIntent(Context context, String type, Bitmap bitmap) {
        try {
            Log.d("QUAKSIRE", "Shared.createSharedIntent()");
            // Create the new Intent using the 'Send' action.
            Intent share = new Intent(Intent.ACTION_SEND);
            // Set the MIME type
            share.setType(type);
            // Create the URI from the media
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, PNG_COMPRESS_QUALITY, stream);
            byte[] byteArray = stream.toByteArray();

            //Cleanup
            stream.close();
            bitmap.recycle();

            // Add the URI to the Intent.
            share.putExtra(Intent.EXTRA_STREAM, byteArray);
            // Broadcast the Intent.
            context.startActivity(Intent.createChooser(share, context.getResources().getString(R.string.share_to)));
        } catch (IOException ioe) {
            Log.e("QUAKSIRE", ioe.getLocalizedMessage(), ioe);
        }

    }
}
