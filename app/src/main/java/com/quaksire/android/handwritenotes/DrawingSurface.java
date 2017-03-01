package com.quaksire.android.handwritenotes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.quaksire.android.handwritenotes.model.shape.History;
import com.quaksire.android.handwritenotes.shape.IShape;
import com.quaksire.android.handwritenotes.shape.Point;

import java.util.ArrayList;

/**
 * HandWriteNotes
 * Created by domingj on 11/12/2014.
 */
public class DrawingSurface extends View implements View.OnTouchListener {

    public static final int POINT = 0;
    public static final int LINE = 1;
    public static final int RECTANGLE = 2;
    public static final int CIRCLE = 3;

    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;
    private Bitmap bitmap;
    private int currentColor;
    private int currentPencilWidth = -1;
    private final ArrayList<History> paths = new ArrayList<>();
    private final ArrayList<History> undonePaths = new ArrayList<>();

    private IShape shape;

    public DrawingSurface(Context context) {
        super(context);
        init();
    }

    public DrawingSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawingSurface(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);
        createPaint(Color.BLACK);
        mCanvas = new Canvas();
        mPath = new Path();
        shape = new Point();
    }

    public void setLineColor(int color) {
        createPaint(color);
        invalidate();
    }

    private void createPaint(int color) {
        this.currentColor = color;

        if(currentPencilWidth < 0) {
            setPencilWidth(2);
        } else {
            setPencilWidth(this.currentPencilWidth);
        }
    }

    public int getCurrentColor() {
        return this.currentColor;
    }

    public void setPencilWidth(int pencilWidth) {
        this.currentPencilWidth = pencilWidth;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(this.currentColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        //MaskFilter mEmboss = new EmbossMaskFilter(new float[] { 1, 1, 1 }, 0.4f, 6, 3.5f);
        //MaskFilter mBlur = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);

        //mPaint.setMaskFilter(mEmboss);

        mPaint.setStrokeWidth(this.currentPencilWidth);
        invalidate();
    }

    public void setImage(Bitmap bitmap) {
        this.bitmap = bitmap;
        invalidate();
    }

    public void setShape(IShape shape) {
        this.shape = shape;
    }

    /*public Bitmap getBitmap() {
        mCanvas = new Canvas();
        mCanvas.setBitmap(bitmap);

        for (History p : paths) {
            mCanvas.drawPath(p.path, p.paint);
        }
        if(bitmap == null) {
            return getDrawingCache();
        } else {
            return this.bitmap;
        }
    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        //Draw bitmap
        if(bitmap != null) {
            canvas.drawBitmap(bitmap, 0, 0, mPaint);
        }

        //Draw lines
        for (History p : paths) {
            canvas.drawPath(p.path, p.paint);
        }
        canvas.drawPath(mPath, mPaint);
    }

    public void onClickClear() {
        undonePaths.clear();
        paths.clear();
        init();
        invalidate();
    }

    public void onClickUndo () {
        if (paths.size()>0) {
            undonePaths.add(paths.remove(paths.size()-1));
            invalidate();
        }
    }

    public void onClickRedo () {
        if (undonePaths.size()>0) {
            paths.add(undonePaths.remove(undonePaths.size()-1));
            invalidate();
        }
    }

    @Override
    public boolean onTouch(View arg0, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                undonePaths.clear();
                mPath = shape.touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                mPath = shape.touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                History history = shape.touchUp();
                history.paint = mPaint;
                paths.add(history);
                mPath = new Path();
                invalidate();
                break;
        }
        return true;
    }

}
