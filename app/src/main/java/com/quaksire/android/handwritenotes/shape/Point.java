package com.quaksire.android.handwritenotes.shape;

import android.graphics.Path;

import com.quaksire.android.handwritenotes.model.shape.Coordinate;
import com.quaksire.android.handwritenotes.model.shape.History;

/**
 * Created by Quaksire on 29/04/2016.
 */
public class Point implements IShape {

    private Path mPath;

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 0;

    History history = new History();

    @Override
    public Path touchStart(float x, float y) {
        mPath = new Path();
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
        history.coordinates.add( new Coordinate(x, y));
        return mPath;
    }

    @Override
    public Path touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
            mX = x;
            mY = y;
        }
        history.coordinates.add( new Coordinate(x, y));
        return mPath;
    }

    @Override
    public History touchUp() {
        this.mPath.lineTo(mX, mY);
        // kill this so we don't double draw
        //this.history.path = this.mPath;

        History hist = new History();
        hist.path = this.mPath;
        return hist;
    }
}
