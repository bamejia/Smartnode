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
    private Post pLast = new Post(); //last post on firebase
    private Post[] pList;
    private long postUID = 100;
    private String str1;  //for testing
    private ValueEventListener firebaseListener;

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

        firebaseListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    pLast = singleSnapshot.getValue(Post.class);
                    postUID = pLast.uid + 1;
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
        mDatabase.child("posts").addValueEventListener(firebaseListener);

        Button newBtn = root.findViewById(R.id.newButton);
        Button addBtn = root.findViewById(R.id.addButton);
        Button subBtn = root.findViewById(R.id.subtractButton);
        Button showLastBtn = root.findViewById(R.id.showLastButton);

        newBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        subBtn.setOnClickListener(this);
        showLastBtn.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newButton:
                pLast.writeNewPost(mDatabase, postUID, "bamejia", "Command", "New");
                postUID++;

                Toast.makeText(getActivity().getApplicationContext(), "Message Sent!", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.addButton:
                pLast.writeNewPost(mDatabase, postUID, "bamejia", "Command", "Add");
                postUID++;

                Toast.makeText(getActivity().getApplicationContext(), "Message Sent!", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.subtractButton:
                pLast.writeNewPost(mDatabase, postUID, "bamejia", "Command", "Subtract");
                postUID++;

                Toast.makeText(getActivity().getApplicationContext(), "Message Sent!", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.showLastButton:
                Toast.makeText(getActivity().getApplicationContext(), pLast.uid + ": " + pLast.body, Toast.LENGTH_LONG)
                        .show();
                break;
            default:
                break;
        }
    }
}