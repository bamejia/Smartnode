package com.example.smartnode.ui.home;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Post {

    protected String author;
    protected String body;
    protected int starCount = 100;
    protected String title;
    protected int uid;
    protected Map<String, Boolean> stars = new HashMap<>();
//    @Exclude
//    public DatabaseReference mDatabase;

    protected Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    protected Post(int uid, String author, String title, String body) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
    }

    @Exclude
    protected Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);
        result.put("starCount", starCount);
        result.put("stars", stars);

        return result;
    }

    protected void writeNewPost(DatabaseReference mDatabase, int userId, String username, String title, String body) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("posts").push().getKey();
        Post post = new Post(userId, username, title, body);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
//        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }

//    protected String toString() {
//        return uid + body;
////        Integer.toString(uid) + body
//    }

}
