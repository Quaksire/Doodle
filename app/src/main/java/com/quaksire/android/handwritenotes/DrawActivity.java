package com.quaksire.android.handwritenotes;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.quaksire.android.handwritenotes.business.DrawActivityPresenter;
import com.quaksire.android.handwritenotes.interfaces.IDrawActivityPresenter;
import com.quaksire.android.handwritenotes.interfaces.IDrawActivityView;
import com.quaksire.android.handwritenotes.shape.Line;
import com.quaksire.android.handwritenotes.shape.Oval;
import com.quaksire.android.handwritenotes.shape.Point;
import com.quaksire.android.handwritenotes.util.AlphaDialog;
import com.quaksire.android.handwritenotes.util.ColorPicker;
import com.quaksire.android.handwritenotes.util.SizeDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DrawActivity
        extends AppCompatActivity
        implements IDrawActivityView {

    //======================================================================
    // View injection
    //======================================================================

    @BindView(R.id.surface)
    public DrawingSurface surface;

    private IDrawActivityPresenter mPresenter;

    //public int progress = -1;
    public int alpha = -1;
    private int color = Color.parseColor("#000000"); //Default

    //======================================================================
    // Activity lifecycle
    //======================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("QUAKSIRE", "DrawActivity.onCreate()");
        setContentView(R.layout.activity_draw);
        ButterKnife.bind(this);

        this.mPresenter = new DrawActivityPresenter(getApplicationContext(), this);
        this.mPresenter.loadIntent(getIntent());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("QUAKSIRE", "DrawActivity.onPause()");
        this.mPresenter.closeDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("QUAKSIRE", "DrawActivity.onResume()");
        this.mPresenter.openDatabase();
        this.mPresenter.loadContent();
    }

    @Override
    public void onBackPressed() {
        Log.d("QUAKSIRE", "DrawActivity.onBackPressed()");
        onExit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_draw, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("QUAKSIRE", "DrawActivity.onOptionsItemSelected()");
        switch (item.getItemId()) {
            case R.id.action_undo:
                surface.onClickUndo();
                return true;
            case R.id.action_clear:
                surface.onClickClear();
                return true;
            case R.id.action_save:
                mPresenter.onSaveImage();
                return true;
            case R.id.action_share:
                mPresenter.onShareImage();
                return true;
            case R.id.action_size:
                SizeDialog.openPencilSizePopup(this);
                return true;
            case R.id.action_alpha:
                AlphaDialog.openPencilAlphaPopup(this);
                return true;
            case R.id.action_color:
                final ColorPicker cp = new ColorPicker(this, Color.red(color), Color.green(color), Color.blue(color));
                cp.show();
                Button okColor = (Button)cp.findViewById(R.id.okColorButton);

                okColor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setColorBar(cp.getColor());
                        cp.dismiss();
                    }
                });
                return true;
            case android.R.id.home:
                onExit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //======================================================================
    // View actions
    //======================================================================

    @SuppressWarnings("unused")
    @OnClick(R.id.white)
    public void onClickWhite() {
        setColorBar(Color.parseColor("#FFFFFF"));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.grey)
    public void onClickGrey() {
        setColorBar(Color.parseColor("#E0E0E0"));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.dark_grey)
    public void onClickDarkGrey() {
        setColorBar(Color.parseColor("#616161"));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.black)
    public void onClickBlack() {
        setColorBar(Color.parseColor("#000000"));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.purple)
    public void onClickPurple() {
        setColorBar(Color.parseColor("#673AB7"));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.light_purple)
    public void onClickLightPurple() {
        setColorBar(Color.parseColor("#9C27B0"));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.indigo)
    public void onClickIndigo() {
        setColorBar(Color.parseColor("#3F51B5"));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.blue)
    public void onClickBlue() {
        setColorBar(Color.parseColor("#01579B"));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.light_blue)
    public void onClickLightBlue() {
        setColorBar(Color.parseColor("#03A9F4"));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.cyan)
    public void onClickCyan() {
        setColorBar(Color.parseColor("#00BCD4"));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.teal)
    public void onClickTeal() {
        setColorBar(Color.parseColor("#009688"));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.blue_grey)
    public void onClickBlueGrey() {
        setColorBar(Color.parseColor("#607D8B"));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.green)
    public void onClickGreen() {
        setColorBar(Color.parseColor("#4CAF50"));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.light_green)
    public void onClickLighGreen() {
        setColorBar(Color.parseColor("#8BC34A"));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.lime)
    public void onClickLime() {
        setColorBar(Color.parseColor("#CDDC39"));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.brown)
    public void onClickBrown() {
        setColorBar(Color.parseColor("#795548"));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.red)
    public void onClickRed() {
        setColorBar(Color.parseColor("#F44336"));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.pink)
    public void onClickPink() {
        setColorBar(Color.parseColor("#E91E63"));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.orange)
    public void onClickOrange() {
        setColorBar(Color.parseColor("#FF5722"));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.deep_orange)
    public void onClickDeepOrange() {
        setColorBar(Color.parseColor("#FF9800"));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.amber)
    public void onClickAmber() {
        setColorBar(Color.parseColor("#FFC107"));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.yellow)
    public void onClickYellow() {
        setColorBar(Color.parseColor("#FFEB3B"));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.line)
    public void onClickLine() {
        surface.setShape(new Line());
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.point)
    public void onClickPoint() {
        surface.setShape(new Point());
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.oval)
    public void onClickOval() {
        surface.setShape(new Oval());
    }

    //======================================================================
    // Other
    //======================================================================

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setColorBar(int colorBar) {
        Log.d("QUAKSIRE", "DrawActivity.setColorBar()");
        if(this.alpha == -1) {
            this.alpha = 100;
        }
        this.color = colorBar;
        setColor();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void setTransparency(int alpha) {
        Log.d("QUAKSIRE", "DrawActivity.setTransparency()");
        this.alpha = alpha;
        setColor();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setColor() {
        Log.d("QUAKSIRE", "DrawActivity.setColor()");
        try {
            ColorDrawable colorDrawable = new ColorDrawable(this.color);
            colorDrawable.setAlpha((this.alpha * 255) / 100);
            this.surface.setLineColor(colorDrawable.getColor());
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(colorDrawable.getColor()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void onExit() {
        Log.d("QUAKSIRE", "DrawActivity.onExit()");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(R.string.activity_draw_alertdialog_exit_title);
        builder.setMessage(R.string.activity_draw_alertdialog_exit_description);
        builder.setPositiveButton(R.string.activity_draw_alertdialog_exit_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DrawActivity.this.finish();
            }
        });
        builder.setNegativeButton(R.string.activity_draw_alertdialog_exit_negative, null);
        builder.show();
    }

    @Override
    public void onLoadBackground(Bitmap bitmap) {
        Log.d("QUAKSIRE", "DrawActivity.onLoadBackground()");
        surface.setImage(bitmap);
        surface.setBackground(new BitmapDrawable(getApplicationContext().getResources(), bitmap));
    }

    @Override
    public Bitmap getBitmap() {
        Log.d("QUAKSIRE", "DrawActivity.getBitmap()");
        return surface.getDrawingCache();
    }

    @Override
    public void setDrawingCacheEnabled(boolean enabled) {
        Log.d("QUAKSIRE", "DrawActivity.setDrawingCacheEnabled(" + enabled + ")");
        surface.setDrawingCacheEnabled(enabled);
    }

    @Override
    public void onError(@StringRes int stringId) {
        Log.d("QUAKSIRE", "DrawActivity.onError()");
        Toast.makeText(this, stringId, Toast.LENGTH_LONG).show();
    }
}
