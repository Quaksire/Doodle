package com.quaksire.android.handwritenotes.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.quaksire.android.handwritenotes.DrawActivity;
import com.quaksire.android.handwritenotes.MainActivity;
import com.quaksire.android.handwritenotes.R;
import com.quaksire.android.handwritenotes.db.tables.TableImage;
import com.quaksire.android.handwritenotes.interfaces.IMainActivityPresenter;
import com.quaksire.android.handwritenotes.model.Image;
import com.quaksire.android.handwritenotes.util.Shared;
import com.quaksire.android.handwritenotes.util.TypeFaceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Quaksire on 22/10/2016.
 */

public class DoodleCursorAdapter
        extends CursorRecyclerViewAdapter<DoodleCursorAdapter.ViewHolder> {

    private IMainActivityPresenter mPresenter;
    private Typeface mTypeface;

    public DoodleCursorAdapter(Context context, Cursor cursor, IMainActivityPresenter tableImage) {
        super(context, cursor);
        this.mPresenter = tableImage;
        this.mTypeface = TypeFaceUtils.getTypeFace(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {
        final Image mImage = TableImage.cursorToImage(cursor);

        // TODO - Move setOnClickListener to ButterKnife
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDrawActivity(mImage.id);
            }
        });

        holder.mDeleteButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelete(mImage.id);
            }
        });

        holder.mShareButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(mImage.image);
            }
        });

        holder.mImageView.setImageBitmap(mImage.image);
    }

    private void startDrawActivity(int id) {
        this.mPresenter.startDrawActvitivy(id);
    }

    private void share(Bitmap bitmap) {
        String type = "image/*";
        Shared.createSharedIntent(mContext, type, bitmap);
    }

    private void onDelete(final int id) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.activity_main_alertdialog_delete_title);

        builder.setMessage(R.string.activity_main_alertdialog_delete_description);
        builder.setPositiveButton(R.string.activity_main_alertdialog_delete_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.deleteImage(id);
                mPresenter.loadContent();
            }
        });
        builder.setNegativeButton(R.string.activity_main_alertdialog_delete_negative, null);
        builder.show();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        //TextView mTitleTextView;
        @BindView(R.id.image)
        ImageView mImageView;

        @BindView(R.id.delete)
        Button mDeleteButtonView;

        @BindView(R.id.shared)
        Button mShareButtonView;

        View v;

        ViewHolder(View v) {
            super(v);
            this.v = v;
            ButterKnife.bind(this, v);

            this.mDeleteButtonView.setTypeface(mTypeface);
            this.mShareButtonView.setTypeface(mTypeface);
        }
    }
}
