package com.sayadev.finalproject.livingroom.TV;

/**
 * Created by saleh on 3/22/2017.
 */

public class SamsungTV {
    private long _id;
    private int tv_volume;
    private int tv_brightness;
    private long next_channel;
    private long prev_channel;
    private String tv_name;
    private String tv_status;

    public SamsungTV(long _id, int tv_volume, int tv_brightness, long next_channel, long prev_channel, String tv_name, String tv_status) {
        this._id = _id;
        this.tv_volume = tv_volume;
        this.tv_brightness = tv_brightness;
        this.next_channel = next_channel;
        this.prev_channel = prev_channel;
        this.tv_name = tv_name;
        this.tv_status = tv_status;
    }

    public SamsungTV() {
        this._id = 0;
        this.tv_volume = 0;
        this.tv_brightness = 0;
        this.next_channel = 0;
        this.prev_channel = 0;
        this.tv_name = "";
        this.tv_status = "";
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public int getTv_volume() {
        return tv_volume;
    }

    public void setTv_volume(int tv_volume) {
        this.tv_volume = tv_volume;
    }

    public int getTv_brightness() {
        return tv_brightness;
    }

    public void setTv_brightness(int tv_brightness) {
        this.tv_brightness = tv_brightness;
    }

    public long getNext_channel() {
        return next_channel;
    }

    public void setNext_channel(long next_channel) {
        this.next_channel = next_channel;
    }

    public long getPrev_channel() {
        return prev_channel;
    }

    public void setPrev_channel(long prev_channel) {
        this.prev_channel = prev_channel;
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_status() {
        return tv_status;
    }

    public void setTv_status(String tv_status) {
        this.tv_status = tv_status;
    }
}
