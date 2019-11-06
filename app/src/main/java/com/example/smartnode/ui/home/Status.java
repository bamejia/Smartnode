package com.example.smartnode.ui.home;

import java.util.Map;

public class Status {

    public String camera;
    public Map<String, String> dataCaptured;
    public String light;
    public String press;

    //initialize
    public Status() {
        this.camera = null;
        this.dataCaptured = null;
        this.light = null;
        this.press = null;
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
        if (this.camera == null) {
            return "No status is available";
        } else if (this.dataCaptured == null) {
            return "camera: " + camera + "\ndataCaptured: no data" + "\nlight: " + light + "\npress: " + press;
        }
        return "camera: " + camera + "\ndataCaptured: " + dataCaptured + "\nlight: " + light + "\npress: " + press;
    }

}
