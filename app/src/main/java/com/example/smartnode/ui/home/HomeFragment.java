package com.example.smartnode.ui.home;

import android.content.Context;
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

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;

    private static Context context;
    private Post pLast = new Post(); //last post on firebase
    private DatabaseReference mDatabase;  //instance of Firebase data tree
    private Post pTmp;
    private Post[] pList;  //list of all posts by the phone app
    private int postUID = 100;
    private Status p1Status = new Status();
    private String str1;  //for testing
    private Status p1Tmp;
    //    private int count1 = 0;  //for testing
    private ValueEventListener firebaseListener;  //listens for any changes made in Firebase
    private ValueEventListener statusListener;  //Listens for current status of RaspberryPi

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

        HomeFragment.context = getActivity().getApplicationContext();

        firebaseListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    pTmp = singleSnapshot.getValue(Post.class);
                }
                if (pLast.equals(pTmp) == false) {
                    pLast = pTmp;
                    postUID = pLast.uid + 1;
                    Toast.makeText(context, "Last command has been updated!", Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
                Toast.makeText(context, "Last Command Stopped!", Toast.LENGTH_SHORT)
                        .show();
            }
        };

        statusListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                p1Status = dataSnapshot.getValue(Status.class);
                Toast.makeText(context, "Status has been updated!", Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };

//        Log.i(TAG, "HELLO USER");
//        System.out.println(pLast.);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("user-posts").addValueEventListener(firebaseListener);
        mDatabase.child("status/Pi1").addValueEventListener(statusListener);
//        mDatabase.child("posts").addValueEventListener(firebaseListener);

        Button newBtn = root.findViewById(R.id.newButton);
        Button addBtn = root.findViewById(R.id.addButton);
        Button subBtn = root.findViewById(R.id.subtractButton);
        Button showLastBtn = root.findViewById(R.id.showLastButton);
        Button showStatusBtn = root.findViewById(R.id.showStatusButton);

        newBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        subBtn.setOnClickListener(this);
        showLastBtn.setOnClickListener(this);
        showStatusBtn.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newButton:
                pLast.writeNewPost(mDatabase, postUID, "bamejia", "Command", "New");
//                postUID = pLast.uid + 1;
//                postUID++;

                Toast.makeText(context, "Message Sent!", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.addButton:
                pLast.writeNewPost(mDatabase, postUID, "bamejia", "Command", "Add");
//                postUID = pLast.uid + 1;
//                postUID++;

                Toast.makeText(context, "Message Sent!", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.subtractButton:
                pLast.writeNewPost(mDatabase, postUID, "bamejia", "Command", "Subtract");
//                postUID = pLast.uid + 1;
//                postUID++;

                Toast.makeText(context, "Message Sent!", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.showLastButton:
                Toast.makeText(context, pLast.uid + ": " + pLast.body, Toast.LENGTH_LONG)
                        .show();
                break;
            case R.id.showStatusButton:
//                startActivity(new Intent(getActivity().getApplicationContext(), Pop.class));
                Toast.makeText(context, p1Status.toString(), Toast.LENGTH_LONG)
                        .show();
                break;
            default:
                break;
        }
    }
}