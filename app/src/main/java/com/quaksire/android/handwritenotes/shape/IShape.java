package com.quaksire.android.handwritenotes.shape;

import android.graphics.Path;

import com.quaksire.android.handwritenotes.model.shape.History;

/**
 * Created by Quaksire on 29/04/2016.
 */
public interface IShape {

    Path touchStart(float x, float y);

    Path touchMove(float x, float y);

    History touchUp();

}
