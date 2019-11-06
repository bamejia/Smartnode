package com.example.smartnode.ui.home;

import java.util.Map;

public class Status {

    public String camera;
    public Map<String, String> dataCaptured;
    public String light;
    public String press;

    //initialize
    public Status() {

    }

    public Status(String camera, Map<String, String> dataCaptured, String light, String press) {
        this.camera = camera;
        this.dataCaptured = dataCaptured;
        this.light = light;
        this.press = press;
    }

    //methods
    @Override
    public String toString() {
        return "camera: " + camera + "\ndataCaptured: " + dataCaptured + "\nlight: " + light + "\npress: " + press;
    }

}
