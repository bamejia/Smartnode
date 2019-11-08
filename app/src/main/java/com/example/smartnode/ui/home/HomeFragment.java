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
    private String btn1Command;
    private String btn2Command;
    private String btn3Command;
    private String postNotif;
    private String statusNotif;
    private String sendNotif;

    private String username = "bamejia";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        btn1Command = getString(R.string.btn1_command);
        btn2Command = getString(R.string.btn2_command);
        btn3Command = getString(R.string.btn3_command);
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
        Button btn1 = root.findViewById(R.id.button1);
        Button btn2 = root.findViewById(R.id.button2);
        Button btn3 = root.findViewById(R.id.button3);
        Button btn4 = root.findViewById(R.id.button4);
        Button btn5 = root.findViewById(R.id.button5);

//        btn1.setText(btn1Name);
//        btn2.setText(btn2Name);
//        btn3.setText(btn3Name);
//        showLastBtn.setText(btn4Name);
//        showStatusBtn.setText(btn5Name);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);

        return root;
    }//onCreateView

    //what to do when each button is pressed
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                homeViewModel.writeNewPost(username, btn1Command);
                Toast.makeText(context, sendNotif, Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.button2:
                homeViewModel.writeNewPost(username, btn2Command);
                Toast.makeText(context, sendNotif, Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.button3:
                homeViewModel.writeNewPost(username, btn3Command);
                Toast.makeText(context, sendNotif, Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.button4:
                PopUpFragment lastPostFrag = new PopUpFragment(homeViewModel.getPostDisplay());  //homeViewModel.getStatus().getValue()
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, lastPostFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.button5:
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