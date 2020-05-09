package com.example.smartnode.ui.home;

import androidx.annotation.NonNull;

public class Status {

    public String light_;
    public String ocrString;

    //initialize
    public Status() {
        this.light_ = null;
        this.ocrString = null;
    }

    public Status(String light, String ocrString) {
        this.light_ = light;
        this.ocrString = ocrString;
    }

    //methods
    @Override
    @NonNull
    public String toString() {
        if (this.light_ == null) {
            return "No status is available";
        }
//        else if (this.dataCaptured == null) {
//            return "camera: " + camera + "\ndataCaptured: no data" + "\nlight: " + light + "\npress: " + press;
//        }
        return "light: " + light_ + "\nocr string: " + ocrString;
    }

}
