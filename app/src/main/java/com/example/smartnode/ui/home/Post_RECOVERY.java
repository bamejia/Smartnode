package com.example.smartnode.ui.home;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

@IgnoreExtraProperties
public class Post_RECOVERY {

    public String author;
    public String body;
    //    protected boolean isOn = false;
    public String title;
    public int cid;  //command id
//    public String TAG = HomeFragment.TAGA;
//    public Map<String, Boolean> stars = new HashMap<>();


    public Post_RECOVERY() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
        this.author = null;
        this.body = null;
        this.title = null;
        this.cid = 100;
    }

    public Post_RECOVERY(int cid, String author, String title, String body) {
        this.cid = cid;
        this.author = author;
        this.title = title;
        this.body = body;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("cid", cid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);
//        result.put("starCount", starCount);
//        result.put("stars", stars);

        return result;
    }

    @Exclude
    public void writeNewPost(DatabaseReference mDatabase, int cid, String username, String title, String body) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
//        String key = mDatabase.child("posts").push().getKey();
        Post_RECOVERY post = new Post_RECOVERY(cid, username, title, body);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/Log/" + cid, postValues);
//        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }

    @Exclude
    @Override
    public boolean equals(Object o) {
        Post_RECOVERY p1 = (Post_RECOVERY) o;
        Log.i(TAG, "MADE IT HERE");
        if (this.body == null) {
            return p1 == null;
        }
        if (p1 == null) {
            this.author = null;
            this.body = null;
            this.title = null;
            this.cid = 100;
            return true;
        }
        return this.hashCode() == p1.hashCode()
                && this.author.equals(p1.author)
                && this.title.equals(p1.title)
                && this.body.equals(p1.body);
    }

    @Exclude
    @Override
    public int hashCode() {
        return this.cid;
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
    @Exclude
    @Override
    public String toString() {
        if (this.body == null) {
            return "No command log is available";
        }
        return "CID: " + this.cid + "\nCommand: " + this.body;
    }

}
