package com.sayadev.finalproject.kitchen;


import com.sayadev.finalproject.R;

class KitchenDevice {

    private KitchenDeviceType type;
    private String name;
    private String model;
    private long id;

    public KitchenDevice(KitchenDeviceType type, String name, String model, long id) {
        this.type = type;
        this.name = name;
        this.model = model;
        this.id = id;
    }

    public KitchenDeviceType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public long getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }
}

enum KitchenDeviceType {
    REFRIGERATOR,
    COFFEE_MAKER
}
