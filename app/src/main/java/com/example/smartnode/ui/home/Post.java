package com.example.smartnode.ui.home;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Post {

    public String author;
    public String body;
    //    protected boolean isOn = false;
    public String title;
    public int uid;
//    public Map<String, Boolean> stars = new HashMap<>();

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(int uid, String author, String title, String body) {
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
//        result.put("starCount", starCount);
//        result.put("stars", stars);

        return result;
    }

    @Exclude
    public void writeNewPost(DatabaseReference mDatabase, int userId, String username, String title, String body) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
//        String key = mDatabase.child("posts").push().getKey();
        Post post = new Post(userId, username, title, body);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + userId, postValues);
//        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }

    @Exclude
    @Override
    public boolean equals(Object o) {
        Post p1 = (Post) o;
//        System.out.println("IT IS: " + body.equals(p1.body));
//        Log.i(TAG, "IT IS USER: " + this.body.equals(p1.body));
        if (this.author == null) {
            return p1 == null;
        }
        return this.hashCode() == p1.hashCode()
                && this.author.equals(p1.author)
                && this.title.equals(p1.title)
                && this.body.equals(p1.body);
    }

    @Exclude
    @Override
    public int hashCode() {
        return this.uid;
    }
//    protected void changeLastPost(DatabaseReference mDatabase, int userId, String username, String title, String body) {
//        // Create new post at /user-posts/$userid/$postid and at
//        // /posts/$postid simultaneously
//        for(DatabaseReference lastPost : mDatabase.child("posts").getDatabase() .)
//        String key = mDatabase.child("posts").push().getKey();
//        Post post = new Post(userId, username, title, body);
//        Map<String, Object> postValues = post.toMap();
//
//        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put("/posts/" + key, postValues);
////        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
//
//        mDatabase.updateChildren(childUpdates);
//    }

//    protected String toString() {
//        return uid + body;
////        Integer.toString(uid) + body
//    }

}
