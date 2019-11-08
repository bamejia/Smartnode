package com.example.smartnode.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //get a static context for displaying notifications at the bottom of the screen with Toast
        HomeFragment.context = getActivity().getApplicationContext();

        //get instance of ViewModel from which to get data from
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        //for displaying fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //Display correct name for text field at the to of the page
        final TextView mainText = root.findViewById(R.id.text_home);
        mainText.setText("OCR BOX HOME");

        //data to watch for changes
        homeViewModel.getLastPost().observe(this, new Observer<Post>() {
            @Override
            public void onChanged(@Nullable Post post) {
                homeViewModel.setPostDisplay(post.toString());
                Toast.makeText(context, "Last command has been updated!", Toast.LENGTH_SHORT)
                        .show();
            }
        });
        homeViewModel.getStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(@Nullable Status status) {
                homeViewModel.setStatusDisplay(status.toString());
                Toast.makeText(context, "Status has been updated!", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        //set up buttons
        Button newBtn = root.findViewById(R.id.newButton);
        Button addBtn = root.findViewById(R.id.addButton);
        Button subBtn = root.findViewById(R.id.subtractButton);
        Button showLastBtn = root.findViewById(R.id.showLastButton);
        Button showStatusBtn = root.findViewById(R.id.showStatusButton);

        newBtn.setText("New");
        addBtn.setText("Add");
        subBtn.setText("Subtract");
        showLastBtn.setText("Show Last");
        showStatusBtn.setText("Show Status");

        newBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        subBtn.setOnClickListener(this);
        showLastBtn.setOnClickListener(this);
        showStatusBtn.setOnClickListener(this);

        return root;
    }//onCreateView

    //what to do when each button is pressed
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newButton:
                homeViewModel.writeNewPost("bamejia", "Command", "New");
                Toast.makeText(context, "Message Sent!", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.addButton:
                homeViewModel.writeNewPost("bamejia", "Command", "Add");
                Toast.makeText(context, "Message Sent!", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.subtractButton:
                homeViewModel.writeNewPost("bamejia", "Command", "Subtract");
                Toast.makeText(context, "Message Sent!", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.showLastButton:
                PopUpFragment lastPostFrag = new PopUpFragment(homeViewModel.getPostDisplay());  //homeViewModel.getStatus().getValue()
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, lastPostFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.showStatusButton:
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