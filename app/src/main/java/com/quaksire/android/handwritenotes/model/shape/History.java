package com.quaksire.android.handwritenotes.model.shape;

import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quaksire on 29/04/2016.
 */
public class History {

    public List<Coordinate> coordinates = new ArrayList<>();

    public Path path;
    public Paint paint;
}
