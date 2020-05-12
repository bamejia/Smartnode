package com.example.smartnode.ui.home;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public Status(Map<String, Object> ocr_status, Map<String, Object> audio_status, Map<String, Object> finger_status) {
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

        StringBuilder ocr_display = new StringBuilder("OCR Status:");
        StringBuilder audio_display = new StringBuilder("Audio Status:");
        StringBuilder finger_display = new StringBuilder("Finger Status:");


        // Getting OCR Values
        String ocr_running_val = (String) this.ocr_status.get("running");
        String ocr_run_mode_val = (String) this.ocr_status.get("run_mode");
        Map<String, Object> ocr_dataset_val = (Map<String, Object>) this.ocr_status.get("ocr_data");
        StringBuilder ocr_dataset_string = new StringBuilder();

        buildDataSetString(ocr_dataset_val, ocr_dataset_string, "ocr");

        ocr_display.append("\n\t\t\t\trunning: ").append(ocr_running_val).append("\n\t\t\t\trun mode: ")
                .append(ocr_run_mode_val).append("\n\t\t\t\taudio data:").append(ocr_dataset_string);


        // Getting Audio Values
        String audio_running_val = (String) this.audio_status.get("running");
        String audio_run_mode_val = (String) this.audio_status.get("run_mode");
        Map<String, String> audio_dataset_val = (Map<String, String>) this.audio_status.get("audio_data");
        StringBuilder audio_dataset_string = new StringBuilder();

        buildDataSetString(audio_dataset_val, audio_dataset_string, "audio");

        audio_display.append("\n\t\t\t\trunning: ").append(audio_running_val).append("\n\t\t\t\trun mode: ")
                .append(audio_run_mode_val).append("\n\t\t\t\taudio data:").append(audio_dataset_string);


        return ocr_display + "\n\n" + audio_display;
//        return ocr_display + "\n\n" + audio_display + "\n\n" + finger_display;
    }

    private String reformatDate(String input_date) {
        //Format of the date defined in the input String
        @SuppressLint("SimpleDateFormat") DateFormat df =
                new SimpleDateFormat("yyyy_MM_dd__hh_mm_ss__ms");
        //Desired format: 24 hour format: Change the pattern as per the need
        @SuppressLint("SimpleDateFormat") DateFormat outputformat =
                new SimpleDateFormat("MM-dd-yyyy hh:mm:ss aa");
        Date date = null;
        String output = null;
        try {
            //Converting the input String to Date
            date = df.parse(input_date);
            //Changing the format of date and storing it in String
            output = outputformat.format(date);
            //Displaying the date
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return output;
    }

    private void buildDataSetString(Map input_dataset_val, StringBuilder
            dataset_string, String choice) {
        if (choice.equals("ocr")) {
            if (input_dataset_val == null) {
                dataset_string.append("\n\t\t\t\t\t\t\t\tNo Data");
            } else {
                Map<String, Map<String, String>> dataset_val = (Map<String, Map<String, String>>) input_dataset_val;
                for (Map.Entry<String, Map<String, String>> entry : dataset_val.entrySet()) {
                    dataset_string.append("\n\t\t\t\t\t\t\t\t").append(entry.getValue().get("name")).append(": ")
                            .append(reformatDate(entry.getValue().get("text")));
                }
            }
        } else if (choice.equals("audio")) {
            if (input_dataset_val == null) {
                dataset_string.append("\n\t\t\t\t\t\t\t\tNo Data");
            } else {
                Map<String, String> dataset_val = (Map<String, String>) input_dataset_val;
                for (Map.Entry<String, String> entry : dataset_val.entrySet()) {
                    dataset_string.append("\n\t\t\t\t\t\t\t\t").append("time detected").append(":\n\t\t\t\t\t\t\t\t\t\t\t\t");
                    String date = entry.getValue();
                    if (date.equals("not detected")) {
                        dataset_string.append("Not Detected");
                    } else {
                        dataset_string.append(reformatDate(date));
                    }
                }
            }
        }
    }
}
