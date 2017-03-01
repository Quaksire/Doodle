package com.quaksire.android.handwritenotes.util;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by Quaksire on 20/10/2016.
 */

public class Animations {
    public static Animation getAnimation(Context context, int resId) {
        return AnimationUtils.loadAnimation(context, resId);
    }
}
