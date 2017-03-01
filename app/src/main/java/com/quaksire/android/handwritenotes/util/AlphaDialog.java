package com.quaksire.android.handwritenotes.util;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.quaksire.android.handwritenotes.DrawActivity;
import com.quaksire.android.handwritenotes.R;

/**
 * HandWriteNotes
 * Created by domingj on 26/01/2016.
 */
public class AlphaDialog {

    public static void openPencilAlphaPopup(final DrawActivity mainActivity) {
        if(mainActivity.alpha == -1) {
            mainActivity.alpha = 100;
        }

        AlertDialog.Builder builder =
                new AlertDialog.Builder(mainActivity);//, R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Pencil alpha");
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mainActivity.setTransparency(mainActivity.alpha);
            }
        });

        builder.setNegativeButton("Cancel", null);


        View view = LayoutInflater.from(mainActivity).inflate(R.layout.layout_pencil_alpha, null);

        final SeekBar alphaSeekBar = (SeekBar) view.findViewById(R.id.alpha_size);
        final TextView alphaTextView = (TextView) view.findViewById(R.id.alpha_size_label);

        alphaSeekBar.setProgress(mainActivity.alpha);
        alphaTextView.setText("" + mainActivity.alpha);

        alphaSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                mainActivity.alpha = i;
                alphaTextView.setText("" + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        builder.setView(view);
        builder.show();
    }
}
