package com.example.smartnode.ui.home;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

@IgnoreExtraProperties
public class Post {

    public String username;
    public String command;
    public String timestamp;  //command id


    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
        this.username = null;
        this.command = null;
        this.timestamp = null;
    }

    public Post(String username, String command, String timestamp) {
        this.username = username;
        this.command = command;
        this.timestamp = timestamp;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("command", command);
        result.put("timestamp", timestamp);
//        result.put("starCount", starCount);
//        result.put("stars", stars);

        return result;
    }

    @Exclude
    public void writeNewPost(DatabaseReference mDatabase, String username, String command, String timestamp) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
//        String key = mDatabase.child("posts").push().getKey();
//        Log.i(TAG, "IN WRITE FUNCTION");
        Post post = new Post(username, command, timestamp);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Log/" + timestamp, postValues);  //make further divisions by day, month, year
        childUpdates.put("/App_Commands/" + username + "/" + timestamp, postValues);

        mDatabase.updateChildren(childUpdates);
    }

    @Exclude
    @Override
    public boolean equals(Object o) {
        Post p1 = (Post) o;
//        Log.i(TAG, "MADE IT HERE");
        if (this.command == null) {
            return p1 == null;
        }
        if (p1 == null) {
            Log.i(TAG, "MADE IT HERE");
            this.username = null;
            this.command = null;
            this.timestamp = null;
            return true;
        }
        return this.username.equals(p1.username)
                && this.command.equals(p1.command)
                && this.timestamp.equals(p1.timestamp);
    }

    @Exclude
    @NonNull
    @Override
    public String toString() {
        if (this.command == null) {
            return "No command log is available";
        }
        return "Timestamp: " + this.timestamp + "\nCommand: " + this.command;
    }

}
