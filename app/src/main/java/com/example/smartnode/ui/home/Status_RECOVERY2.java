package com.example.smartnode.ui.home;

import androidx.annotation.NonNull;

public class Status_RECOVERY2 {

    public String light;
    public String ocr_sampling;

    //initialize
    public Status_RECOVERY2() {
        this.light = null;
        this.ocr_sampling = null;
    }

    public Status_RECOVERY2(String light, String ocr_sampling) {
        this.light = light;
        this.ocr_sampling = ocr_sampling;
    }

    //methods
    @Override
    @NonNull
    public String toString() {
        if (this.light == null) {
            return "No status is available";
        }
//        else if (this.dataCaptured == null) {
//            return "camera: " + camera + "\ndataCaptured: no data" + "\nlight: " + light + "\npress: " + press;
//        }
        return "light: " + light + "\nocr sampling: " + ocr_sampling;
    }

}
