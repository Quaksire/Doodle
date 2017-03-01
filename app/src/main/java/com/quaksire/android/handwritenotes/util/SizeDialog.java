package com.quaksire.android.handwritenotes.util;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.quaksire.android.handwritenotes.R;
import com.quaksire.android.handwritenotes.DrawActivity;

/**
 * HandWriteNotes
 * Created by domingj on 26/01/2016.
 */
public class SizeDialog {
    public static void openPencilSizePopup(final DrawActivity mainActivity) {
        if(mainActivity.progress == -1) {
            mainActivity.progress = 2;
        }

        AlertDialog.Builder builder =
                new AlertDialog.Builder(mainActivity);//, R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Pencil size");
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mainActivity.surface.setPencilWidth(mainActivity.progress);
            }
        });

        builder.setNegativeButton("Cancel", null);


        View view = LayoutInflater.from(mainActivity).inflate(R.layout.layout_pencil_size, null);

        final SeekBar sizeSeekBar = (SeekBar) view.findViewById(R.id.pencil_size);
        final TextView sizeTextView = (TextView) view.findViewById(R.id.pencil_size_label);

        sizeSeekBar.setProgress(mainActivity.progress);
        sizeTextView.setText("" + mainActivity.progress);

        sizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                mainActivity.progress = i;
                sizeTextView.setText("" + i);
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
