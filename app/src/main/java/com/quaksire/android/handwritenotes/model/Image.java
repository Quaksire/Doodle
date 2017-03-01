package com.quaksire.android.handwritenotes.model;

import android.graphics.Bitmap;

/**
 * Created by Quaksire on 21/10/2016.
 */

public class Image {

    public int id;

    public String name;

    public Bitmap image;

    public String description;

    public int createdBy;

    public long createdOn;

    public long updatedOn;

    public Image() {
        this.name = "";
        this.description = "";
        this.createdOn = 0;
        this.updatedOn = 0;
    }

}
