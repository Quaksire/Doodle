package com.quaksire.android.handwritenotes.shape;

import android.graphics.Path;

import com.quaksire.android.handwritenotes.model.shape.History;

/**
 * Created by Quaksire on 29/04/2016.
 */
public class Line implements IShape {

    private Path mPath;

    private float x, y, initX, initY;

    @Override
    public Path touchStart(float x, float y) {
        this.mPath = new Path();
        this.mPath.reset();
        this.mPath.moveTo(x, y);
        this.initX = x;
        this.initY = y;
        return this.mPath;
    }

    @Override
    public Path touchMove(float x, float y) {
        this.x = x;
        this.y = y;
        this.mPath.lineTo(this.x, this.y);//Help line
        return this.mPath;
    }

    @Override
    public History touchUp() {
        this.mPath.reset();
        this.mPath.moveTo(initX, initY);
        this.mPath.lineTo(this.x, this.y);
        // kill this so we don't double draw
        History history = new History();
        history.path = this.mPath;
        return history;
    }
}
