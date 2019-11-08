package com.example.smartnode.ui.pop_up;

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

public class PopUpFragment extends Fragment {

    //data observed for display
    private LiveData<String> input_text;

    public PopUpFragment(LiveData<String> input_text) {
        this.input_text = input_text;
    }//PopUpFragment Constructor

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //for displaying fragment
        View root = inflater.inflate(R.layout.fragment_status, container, false);

        //text to display on pop up
        final TextView display_text = root.findViewById(R.id.text_status);

        //updates text displayed on pop up
        input_text.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                display_text.setText(s);
            }
        });

        return root;
    }//onCreateView

}//PopUpFragment
