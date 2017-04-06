package com.sayadev.finalproject.livingroom;

import java.util.Date;

/**
 * Created by saleh on 2017-03-25.
 */

public class RoomData {

    public static final int DEVICE_TV = 0;
    public static final int DEVICE_LAMP1 = 1;
    public static final int DEVICE_LAMP2 = 2;
    public static final int DEVICE_LAMP3 = 3;
    public static final int DEVICE_BLINDING = 4;

    private long _id;
    private String title;
    private String imageUri;
    private int itemType;
    private String itemName;
    private long lastVisitDate;
    private long createdDate;

    public RoomData() {
        this._id = 0;
        this.title = "";
        this.imageUri = "";

        this.itemType = DEVICE_TV;
        itemName = "";
        lastVisitDate = 0;
        createdDate = 0;
    }

    public RoomData(long _id, String title, String imageUri, int itemType, String itemName, long lastVisitDate, long createdDate) {
        this._id = _id;
        this.title = title;
        this.imageUri = imageUri;
        this.itemType = itemType;
        this.itemName = itemName;
        this.lastVisitDate = lastVisitDate;
        this.createdDate = createdDate;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public long getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(long lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }
}
