package com.sayadev.finalproject.kitchen;


class KitchenDevice {

    private KitchenDeviceType type;
    private String name;
    private String model;

    public KitchenDevice(KitchenDeviceType type, String name, String model) {
        this.type = type;
        this.name = name;
        this.model = model;
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

    public void setName(String name) {
        this.name = name;
    }
}

enum KitchenDeviceType {
    REFRIGERATOR,
    MICROWAVE
}
