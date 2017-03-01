package com.quaksire.android.handwritenotes.util;

import android.content.Context;
import android.graphics.EmbossMaskFilter;
import android.graphics.Typeface;

/**
 * Created by Quaksire on 21/10/2016.
 */

public class TypeFaceUtils {



    public static Typeface getTypeFace(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "Comical_Regular.ttf");//"ShadedLarch.ttf");
    }

    public static EmbossMaskFilter getEmbossMaskFilter() {
        return new EmbossMaskFilter(
                new float[]{0f, -1f, 0.5f}, // direction of the light source
                0.8f, // ambient light between 0 to 1
                13, // specular highlights
                7.0f // blur before applying lighting
        );
    }
}
