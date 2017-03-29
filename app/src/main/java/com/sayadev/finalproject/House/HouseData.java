package com.sayadev.finalproject.House;

/**
 * Created by Rudwan on 2017-03-25.
 */

public class HouseData {
    private int _id;
    private String title;
    private String imageUri;

    public HouseData() {
        this._id = 0;
        this.title = "";
        this.imageUri = "";
    }

    public HouseData(int _id, String title, String imageUri) {
        this._id = _id;
        this.title = title;
        this.imageUri = imageUri;
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
}


