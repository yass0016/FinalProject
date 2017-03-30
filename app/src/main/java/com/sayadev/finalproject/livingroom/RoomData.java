package com.sayadev.finalproject.livingroom;

import android.util.Log;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by saleh on 2017-03-25.
 */

public class RoomData {
    public enum DeviceType {
        TV,
        LAMP,
        BLINDING
    }

    private int _id;
    private String title;
    private String imageUri;
    private int itemType;
    private String itemName;
    private Date lastVisitDate;
    private Date createdDate;

    public RoomData() {
        this._id = 0;
        this.title = "";
        this.imageUri = "";

        this.itemType = DeviceType.TV.ordinal();
        itemName = "";
        lastVisitDate = new Date();
        createdDate = new Date();
    }

    public RoomData(int _id, String title, String imageUri, int itemType, String itemName, Date lastVisitDate, Date createdDate) {
        this._id = _id;
        this.title = title;
        this.imageUri = imageUri;
        this.itemType = itemType;
        this.itemName = itemName;
        this.lastVisitDate = lastVisitDate;
        this.createdDate = createdDate;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
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

    public Date getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(Date lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
