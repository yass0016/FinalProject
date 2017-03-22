package com.sayadev.finalproject.livingroom.TV;

/**
 * Created by saleh on 3/22/2017.
 */

public class Channel {
    private long _id;
    private long channel_id;
    private long tv_id;
    private String channel_name;
    private String favChannel;
    private String last_channel;
    private String channel_category;
    private String channel_description;

    public Channel() {
        _id = 0;
        channel_id = 0;
        channel_name = "";

        favChannel = "";
        last_channel = "";
        channel_category = "";
        channel_description = "";
    }

    public Channel(long _id, long channel_id, long tv_id, String channel_name, String favChannel, String last_channel, String channel_category, String channel_description) {
        this._id = _id;
        this.channel_id = channel_id;
        this.tv_id = tv_id;
        this.channel_name = channel_name;
        this.favChannel = favChannel;
        this.last_channel = last_channel;
        this.channel_category = channel_category;
        this.channel_description = channel_description;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(long channel_id) {
        this.channel_id = channel_id;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public String getFavChannel() {
        return favChannel;
    }

    public void setFavChannel(String favChannel) {
        this.favChannel = favChannel;
    }

    public String getLast_channel() {
        return last_channel;
    }

    public void setLast_channel(String last_channel) {
        this.last_channel = last_channel;
    }

    public String getChannel_category() {
        return channel_category;
    }

    public void setChannel_category(String channel_category) {
        this.channel_category = channel_category;
    }

    public String getChannel_description() {
        return channel_description;
    }

    public void setChannel_description(String channel_description) {
        this.channel_description = channel_description;
    }

    public long getTv_id() {
        return tv_id;
    }

    public void setTv_id(long tv_id) {
        this.tv_id = tv_id;
    }
}
