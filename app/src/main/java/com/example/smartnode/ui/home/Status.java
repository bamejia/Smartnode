package com.example.smartnode.ui.home;

import androidx.annotation.NonNull;

import java.util.Map;

public class Status {

    public Map<String, Object> audio_status;
    public Map<String, Object> finger_status;
    public Map<String, Object> ocr_status;

    //initialize
    public Status() {
        this.audio_status = null;
        this.finger_status = null;
        this.ocr_status = null;
    }

    public Status(Map<String, Object> audio_status, Map<String, Object> finger_status, Map<String, Object> ocr_status) {
        this.audio_status = audio_status;
        this.finger_status = finger_status;
        this.ocr_status = ocr_status;
    }

    //methods
    @Override
    @NonNull
    public String toString() {
        if (this.audio_status == null) {
            return "No status is available";
        }
//        else if (this.dataCaptured == null) {
//            return "camera: " + camera + "\ndataCaptured: no data" + "\nlight: " + light + "\npress: " + press;
//        }
        return "audio_status: " + audio_status + "\n\nocr_status: " + ocr_status + "\n\nfinger_status: " + finger_status;
    }

}
