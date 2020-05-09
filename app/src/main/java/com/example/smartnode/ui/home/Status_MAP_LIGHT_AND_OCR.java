package com.example.smartnode.ui.home;

import androidx.annotation.NonNull;

import java.util.Map;

public class Status_MAP_LIGHT_AND_OCR {

    public Map<String, String> light;
    public Map<String, String> ocr_sampling;

    //initialize
    public Status_MAP_LIGHT_AND_OCR() {
        this.light = null;
        this.ocr_sampling = null;
    }

    public Status_MAP_LIGHT_AND_OCR(Map<String, String> light, Map<String, String> ocr_sampling) {
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
        return "light:\n    status: " + light.get("status") + "\n    last modified by: " + light.get("username") + "\n    date modified on: " + light.get("timestamp")
                + "\n\nocr sampling:\n    status: " + ocr_sampling.get("status") + "\n    last modified by: " + ocr_sampling.get("username") + "\n    date modified on: " + ocr_sampling.get("timestamp");
    }

}
