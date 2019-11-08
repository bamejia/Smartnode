package com.example.smartnode.ui.home;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeViewModel extends ViewModel {

    //Data being observed for changes by View class
    private MutableLiveData<Post> pLast;  //last post in "user-posts" on Firebase
    private MutableLiveData<Status> p1Status;  //current status of Raspberry Pi
    private MutableLiveData<String> post_display;
    private MutableLiveData<String> status_display;

    //local variables
    private DatabaseReference mDatabase; //instance of Firebase data tree
    private ValueEventListener userPostListener;  //Listens for any posts added to "user-posts" in Firebase
    private ValueEventListener statusListener;  //Listens for any changes to the current status of the RaspberryPi on Firebase
    private Post[] pList;  //list of all posts by the phone app
    private Post pTmp;  //for iterating through all posts in "user-posts"
    private Status statTmp;  //for iterating through multiple Raspberry Pi status (for later on)
    private int postCID = 100;  //for keeping track of post ID's sent by phone users

    //for testing
//    private String str1;  //for testing
//    private static Context context;
//    private int count1 = 0;  //for testing

    public HomeViewModel() {

//        HomeViewModel.context = AndroidViewModel.getApplicationContext();

        //initializing observed data
        pLast = new MutableLiveData<>();
        p1Status = new MutableLiveData();
        post_display = new MutableLiveData<>();
        status_display = new MutableLiveData<>();

        pLast.setValue(new Post());
        p1Status.setValue(new Status());

        //initializing Listeners
        userPostListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {  // Get Post object and use the values to update the UI
//                Log.i(TAG, "children: " + dataSnapshot.getChildrenCount());
                if (dataSnapshot.getChildrenCount() == 0) {
                    pTmp = null;
                } else {
                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                        pTmp = singleSnapshot.getValue(Post.class);
                    }
                }
                if (!(pLast.getValue().equals(pTmp))) {
                    pLast.setValue(pTmp);
                    postCID = pLast.getValue().cid + 1;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };//userPostListener
        statusListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "children: " + dataSnapshot.getChildrenCount());
                if (dataSnapshot.getChildrenCount() == 0) {
                    p1Status.setValue(new Status());
//                    Log.i(TAG, "YES");
                } else {
                    p1Status.setValue(dataSnapshot.getValue(Status.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };//statusListener

        //initializing instance of Firebase data tree and listeners for changes
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("user-posts").addValueEventListener(userPostListener);
        mDatabase.child("status/Pi1").addValueEventListener(statusListener);

    }//HomeViewModel Constructor

    //returns for observed data
    public LiveData<Post> getLastPost() {
        return pLast;
    }

    public LiveData<Status> getStatus() {
        return p1Status;
    }

    public LiveData<String> getPostDisplay() {
        return post_display;
    }

    //for setting text to display
    public void setPostDisplay(String display_text) {
        this.post_display.setValue(display_text);
    }

    public LiveData<String> getStatusDisplay() {
        return status_display;
    }

    public void setStatusDisplay(String display_text) {
        this.status_display.setValue(display_text);
    }

    //for creating a new post sent to Firebase
    public void writeNewPost(String username, String title, String body) {
        pLast.getValue().writeNewPost(this.mDatabase, this.postCID, username, title, body);
    }


}//HomeViewModel