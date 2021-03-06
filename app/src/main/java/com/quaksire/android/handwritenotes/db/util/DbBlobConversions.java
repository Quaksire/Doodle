package com.quaksire.android.handwritenotes.db.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by Quaksire on 21/10/2016.
 */

public class DbBlobConversions {

    /**
     * convert from bitmap to byte array
     * @param bitmap
     * @return bitmap in bytes
     */
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    /**
     * convert from byte array to bitmap
     * @param image in byte[]
     * @return bitmap
     */
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
