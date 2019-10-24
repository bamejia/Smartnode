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
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private DatabaseReference mDatabase;

    private Post p = new Post();

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
                str1 = dataSnapshot.getValue(Post.class).toString();
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
//                    str1 = singleSnapshot.getValue(Post.class).toString();
//                    str1 = singleSnapshot.toString();
                }
//                getValue(Post.class)
//                Toast.makeText(getActivity().getApplicationContext(), p.body, Toast.LENGTH_LONG)
//                        .show();
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };

        mDatabase = FirebaseDatabase.getInstance().getReference("posts/");

        mDatabase.child("posts").push().addValueEventListener(tmpListener);
//        mDatabase.push().addValueEventListener(tmpListener);

        Button sendBtn = root.findViewById(R.id.sendButton);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("Hello There", "Sending info");

                p.writeNewPost("101", "bamejia", "hello", "This is a test message");

                Toast.makeText(getActivity().getApplicationContext(), "Message Sent!", Toast.LENGTH_SHORT)
                        .show();

            }
        });

        Button receiveBtn = root.findViewById(R.id.receiveButton);
        receiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("Hello There", "Sending info");

//                p.writeNewPost("100", "bamejia", "hello", "This is a test message");
//                mDatabase.child("posts").getKey()
                Toast.makeText(getActivity().getApplicationContext(), str1, Toast.LENGTH_LONG)
                        .show();
//                Toast.makeText(getActivity().getApplicationContext(), "Message Received!", Toast.LENGTH_SHORT)
//                        .show();

            }
        });

        return root;
    }

    @IgnoreExtraProperties
    public class Post {

        public String uid;
        public String author;
        public String title;
        public String body;
        public int starCount = 0;
        public Map<String, Boolean> stars = new HashMap<>();

        public Post() {
            // Default constructor required for calls to DataSnapshot.getValue(Post.class)
        }

        public Post(String uid, String author, String title, String body) {
            this.uid = uid;
            this.author = author;
            this.title = title;
            this.body = body;
        }

        @Exclude
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("uid", uid);
            result.put("author", author);
            result.put("title", title);
            result.put("body", body);
            result.put("starCount", starCount);
            result.put("stars", stars);

            return result;
        }

        private void writeNewPost(String userId, String username, String title, String body) {
            // Create new post at /user-posts/$userid/$postid and at
            // /posts/$postid simultaneously
            String key = mDatabase.child("posts").push().getKey();
            Post post = new Post(userId, username, title, body);
            Map<String, Object> postValues = post.toMap();

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/posts/" + key, postValues);
            childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

            mDatabase.updateChildren(childUpdates);
        }
//        public String toString(){
//            return uid;
//        }

    }
}