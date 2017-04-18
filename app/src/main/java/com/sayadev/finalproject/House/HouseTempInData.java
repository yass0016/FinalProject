package com.sayadev.finalproject.House;

/**
 * Created by Rudwan on 2017-04-18.
 */

public class HouseTempInData {
    private long _id;
    private int temp;
    private long datetime;

    public HouseTempInData(long _id, int temp, long datetime) {
        this._id = _id;
        this.temp = temp;
        this.datetime = datetime;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }
}
