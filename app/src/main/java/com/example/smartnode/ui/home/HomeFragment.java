package com.example.smartnode.ui.home;

import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static androidx.constraintlayout.widget.Constraints.TAG;

//import Post.class;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;

    private DatabaseReference mDatabase;

    private Post p = new Post();

    private Post[] pList;

    private int postUID = 100;

    private String str1;

    private ValueEventListener tmpListener;

//    private Snapshot mSnapshot;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        tmpListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
//                dataSnapshot.getValue(Post.class);
//                str1 = dataSnapshot.getChildren().toString();
//                str1 = dataSnapshot.toString();
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
//                    str1 = singleSnapshot.getValue(Post.class).toString();
//                    str1 = singleSnapshot.toString();
                    p = singleSnapshot.getValue(Post.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("posts").addValueEventListener(tmpListener);
//        mDatabase.addValueEventListener(tmpListener);

        Button newBtn = root.findViewById(R.id.newButton);
        newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("Hello There", "Sending info");

                p.writeNewPost(mDatabase, postUID, "bamejia", "hello", "This is a test message");
                postUID++;

                Toast.makeText(getActivity().getApplicationContext(), "Message Sent!", Toast.LENGTH_SHORT)
                        .show();

            }
        });

        Button showLastBtn = root.findViewById(R.id.showLastButton);
        showLastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("Hello There", "Sending info");

//                p.writeNewPost("100", "bamejia", "hello", "This is a test message");
//                mDatabase.child("posts").getKey()
//                Toast.makeText(getActivity().getApplicationContext(), str1, Toast.LENGTH_LONG)
//                        .show();
                Toast.makeText(getActivity().getApplicationContext(), p.uid + ": " + p.body, Toast.LENGTH_LONG)
                        .show();
//                Toast.makeText(getActivity().getApplicationContext(), "Can't receive yet!", Toast.LENGTH_SHORT)
//                        .show();

            }
        });

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newButton:
                p.writeNewPost(mDatabase, postUID, "bamejia", "hello", "This is a test message");
                postUID++;

                Toast.makeText(getActivity().getApplicationContext(), "Message Sent!", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.showLastButton:
                Toast.makeText(getActivity().getApplicationContext(), p.uid + ": " + p.body, Toast.LENGTH_LONG)
                        .show();
                break;
        }
    }
}