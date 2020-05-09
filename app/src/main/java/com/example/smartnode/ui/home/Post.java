package com.example.smartnode.ui.home;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

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
        childUpdates.put("/command_log/" + timestamp, postValues);  //make further divisions by day, month, year
        childUpdates.put("/recent_commands/" + timestamp, postValues);
//        childUpdates.put("/commands_to_pi/" + timestamp, postValues);
//        childUpdates.put("/recent_commands/" + username + "/" + timestamp, postValues);

        mDatabase.updateChildren(childUpdates);
    }

    @Exclude
    @Override
    public boolean equals(Object o) {
        if (o.getClass() != this.getClass()) {
            return true;
        }
//        Log.i(TAG, "MADE IT HERE");
        Post p1 = (Post) o;
        if (this.command == null) {
            return false;
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
