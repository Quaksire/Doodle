package com.quaksire.android.handwritenotes.util;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.TextView;

import com.quaksire.android.handwritenotes.R;


/**
 * HandWriteNotes
 * Created by domingj on 26/01/2016.
 */
public class ColorPicker extends Dialog implements SeekBar.OnSeekBarChangeListener {

    public Activity c;

    View colorView;
    SeekBar redSeekBar, greenSeekBar, blueSeekBar;
    TextView redToolTip, greenToolTip, blueToolTip;
    private int red, green, blue;
    int seekBarLeft;

    /**
     * Creator of the class. It will initialize the class with the rgb color passed as default
     *
     * @param a The reference to the activity where the color picker is called
     * @param r Red color for RGB values (0 - 255)
     * @param g Green color for RGB values (0 - 255)
     * @param b Blue color for RGB values (0 - 255)
     *
     * If the value of the colors it's not in the right range (0 - 255) it will be place at 0.
     */
    public ColorPicker(Activity a, int r, int g, int b) {
        super(a);

        this.c = a;

        if(0 <= r && r <=255)
            this.red = r;
        else
            this.red = 0;

        if(0 <= r && r <=255)
            this.green = g;
        else
            this.green = 0;

        if(0 <= r && r <=255)
            this.blue = b;
        else
            this.green = 0;

    }

    /**
     * Simple onCreate function. Here there is the init of the GUI.
     *
     * @param savedInstanceState As usual ...
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.layout_color_picker);

        colorView = findViewById(R.id.colorView);

        redSeekBar = (SeekBar)findViewById(R.id.redSeekBar);
        greenSeekBar = (SeekBar)findViewById(R.id.greenSeekBar);
        blueSeekBar = (SeekBar)findViewById(R.id.blueSeekBar);

        seekBarLeft = redSeekBar.getPaddingLeft();

        redToolTip = (TextView)findViewById(R.id.redToolTip);
        greenToolTip = (TextView)findViewById(R.id.greenToolTip);
        blueToolTip = (TextView)findViewById(R.id.blueToolTip);

        redSeekBar.setOnSeekBarChangeListener(this);
        greenSeekBar.setOnSeekBarChangeListener(this);
        blueSeekBar.setOnSeekBarChangeListener(this);

        redSeekBar.setProgress(red);
        greenSeekBar.setProgress(green);
        blueSeekBar.setProgress(blue);

        colorView.setBackgroundColor(Color.rgb(red, green, blue));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        setColorToolTips();
    }

    private void setColorToolTips() {
        redToolTip.setText("" + red);
        greenToolTip.setText("" + green);
        blueToolTip.setText("" + blue);
    }

    /**
     * Method called when the user change the value of the bars. This sync the colors.
     *
     * @param seekBar SeekBar that has changed
     * @param progress The new progress value
     * @param fromUser If it coem from User
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if (seekBar.getId() == R.id.redSeekBar) {
            red = progress;
        }
        else if (seekBar.getId() == R.id.greenSeekBar) {
            green = progress;
        }
        else if (seekBar.getId() == R.id.blueSeekBar) {
            blue = progress;
        }

        setColorToolTips();

        colorView.setBackgroundColor(Color.rgb(red, green, blue));
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    /**
     * Getter for the color as Android Color class value.
     *
     * From Android Reference: The Color class defines methods for creating and converting color ints.
     * Colors are represented as packed ints, made up of 4 bytes: alpha, red, green, blue.
     * The values are unpremultiplied, meaning any transparency is stored solely in the alpha
     * component, and not in the color components.
     *
     * @return Selected color as Android Color class value.
     */
    public int getColor() {
        return Color.rgb(red, green, blue);
    }
}
