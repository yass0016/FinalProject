package com.sayadev.finalproject.House;

import java.util.Date;

/**
 * Created by Rudwan on 2017-03-25.
 */

public class HouseData {
    public static final int GARAGE = 0;
    public static final int TEMPIN = 1;
    public static final int TEMPOUT = 2;

    private long _id;
    private String title;
    private String imageUri;
    private int itemType;
    private String itemName;
    private Date lastVisitDate;
    private Date createdDate;

    public HouseData() {
        this._id = 0;
        this.title = "";
        this.imageUri = "";
        this.itemType = 0;
    }

    public HouseData(long _id, String title, String imageUri, int deviceType) {
        this._id = _id;
        this.title = title;
        this.imageUri = imageUri;
        this.itemType = deviceType;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}


