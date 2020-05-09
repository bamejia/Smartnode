package com.example.smartnode.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.smartnode.R;
import com.example.smartnode.ui.pop_up.PopUpFragment;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private static Context context;  //for displaying notifications at bottom of phone screen with "Toast.makeText"
    private HomeViewModel homeViewModel;  //all data comes from an instance of the ViewModel

    //make it easier to rename commands and message to user from string.xml
    private String btnOcrOnOffCommand;
    private String btnAudioOnOffCommand;
    private String btnOcrOnCommand;
    private String btnOcrOffCommand;
    private String btnOcrOnceCommand;
    private String btnFlashOnCommand;
    private String btnFlashOffCommand;
    private String btnPressCommand;
    private String postNotif;
    private String statusNotif;
    private String sendNotif;

    private String username = "bamejia";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        btnOcrOnOffCommand = getString(R.string.btn_ocr_on_off);
        btnAudioOnOffCommand = getString(R.string.btn_audio_on_off);

        btnOcrOnCommand = getString(R.string.btn_ocr_on_command);
        btnOcrOffCommand = getString(R.string.btn_ocr_off_command);
        btnOcrOnceCommand = getString(R.string.btn_ocr_once_command);
        btnFlashOnCommand = getString(R.string.btn_flash_on_command);
        btnFlashOffCommand = getString(R.string.btn_flash_off_command);
        btnPressCommand = getString(R.string.btn_press);
        postNotif = getString(R.string.post_notif);
        statusNotif = getString(R.string.status_notif);
        sendNotif = getString(R.string.send_notif);

        //get a static context for displaying notifications at the bottom of the screen with Toast
        HomeFragment.context = getActivity().getApplicationContext();

        //get instance of ViewModel from which to get data from
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        //for displaying fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //data to watch for changes
        homeViewModel.getLastPost().observe(this, new Observer<Post>() {
            @Override
            public void onChanged(@Nullable Post post) {
                homeViewModel.setPostDisplay(post.toString());
                Toast.makeText(context, postNotif, Toast.LENGTH_SHORT)
                        .show();
            }
        });
        homeViewModel.getStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(@Nullable Status status) {
                homeViewModel.setStatusDisplay(status.toString());
                Toast.makeText(context, statusNotif, Toast.LENGTH_SHORT)
                        .show();
            }
        });

        //set up buttons
        Button btnOcrOnOff = root.findViewById(R.id.button_ocr_on_off);
        Button btnAudioOnOFF = root.findViewById(R.id.button_audio_on_off);
        Button btnOcrOff = root.findViewById(R.id.button_ocr_off);
        Button btnLightOn = root.findViewById(R.id.button_light_on);
        Button btnLightOff = root.findViewById(R.id.button_light_off);
        Button btnLast = root.findViewById(R.id.button_show_last);
        Button btnStatus = root.findViewById(R.id.button_show_status);
        Button btnPress = root.findViewById(R.id.button_press);

//        btn1.setText(btn1Name);
//        btn2.setText(btn2Name);
//        btn3.setText(btn3Name);
//        showLastBtn.setText(btn4Name);
//        showStatusBtn.setText(btn5Name);

        btnOcrOnOff.setOnClickListener(this);
        btnAudioOnOFF.setOnClickListener(this);
        btnOcrOff.setOnClickListener(this);
        btnOcrOff.setOnClickListener(this);
        btnLightOn.setOnClickListener(this);
        btnLightOff.setOnClickListener(this);
        btnLast.setOnClickListener(this);
        btnStatus.setOnClickListener(this);
        btnPress.setOnClickListener(this);

        return root;
    }//onCreateView

    //what to do when each button is pressed
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_ocr_on_off:
                homeViewModel.writeNewPost(username, btnOcrOnOffCommand);
                Toast.makeText(context, sendNotif, Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.button_audio_on_off:
                homeViewModel.writeNewPost(username, btnAudioOnOffCommand);
                Toast.makeText(context, sendNotif, Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.button_ocr_off:
                homeViewModel.writeNewPost(username, btnOcrOffCommand);
                Toast.makeText(context, sendNotif, Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.button_light_on:
                homeViewModel.writeNewPost(username, btnFlashOnCommand);
                Toast.makeText(context, sendNotif, Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.button_light_off:
                homeViewModel.writeNewPost(username, btnFlashOffCommand);
                Toast.makeText(context, sendNotif, Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.button_press:
                homeViewModel.writeNewPost(username, btnPressCommand);
                Toast.makeText(context, sendNotif, Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.button_show_last:
                PopUpFragment lastPostFrag = new PopUpFragment(homeViewModel.getPostDisplay());  //homeViewModel.getStatus().getValue()
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, lastPostFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.button_show_status:
                PopUpFragment statusFrag = new PopUpFragment(homeViewModel.getStatusDisplay());  //homeViewModel.getStatus().getValue()
                int i = getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, statusFrag)
                        .addToBackStack(null)
                        .commit();
//                Log.i(TAG, "" + i);
                break;
            default:
                break;
        }
    }
}