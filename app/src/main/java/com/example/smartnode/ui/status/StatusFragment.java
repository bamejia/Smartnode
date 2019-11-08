package com.example.smartnode.ui.status;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.smartnode.R;

public class StatusFragment extends Fragment {

    //    private StatusViewModel statusViewModel;
    private LiveData<String> input_text;

    public StatusFragment(LiveData<String> input_text) {
        this.input_text = input_text;
//        StatusFragment.activity = getActivity();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Log.i(TAG, "I AM HERE");

//        statusViewModel =
//                ViewModelProviders.of(this).get(StatusViewModel.class);
        View root = inflater.inflate(R.layout.fragment_pop_up, container, false);

        //text to display on pop up
        final TextView display_text = root.findViewById(R.id.text_pop_up);
//        statusViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                display_text.setText(s);
//            }
//        });

        //updates text displayed on pop up
        input_text.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                display_text.setText(s);
            }
        });

//        activity.setContentView(R.layout.fragment_status);

//        DisplayMetrics dm = new DisplayMetrics();
//        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//        int width = dm.widthPixels;
//        int height = dm.heightPixels;
//
//        activity.getWindow().setLayout((int) 0.8 * width, (int) 0.6 * height);
        return root;
    }

}
