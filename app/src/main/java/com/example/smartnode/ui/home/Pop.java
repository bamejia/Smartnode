package com.example.smartnode.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.example.smartnode.R;

public class Pop extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.show_status);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) 0.8 * width, (int) 0.6 * height);
    }
}
