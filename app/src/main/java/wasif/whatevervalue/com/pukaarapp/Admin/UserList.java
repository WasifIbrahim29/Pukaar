package wasif.whatevervalue.com.pukaarapp.Admin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import wasif.whatevervalue.com.pukaarapp.Models.Therapist;
import wasif.whatevervalue.com.pukaarapp.Models.User;
import wasif.whatevervalue.com.pukaarapp.Models.user_questions;
import wasif.whatevervalue.com.pukaarapp.R;

public class UserList extends AppCompatActivity {

    private static final String TAG = "UserList";


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mUserIds = new ArrayList<>();
    private ArrayList<User> mUsers= new ArrayList<>();

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        Log.d(TAG, "onCreate: started.");

        initImageBitmaps();
    }


    private void initImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                getUsers(new FirebaseCallBack() {
                    @Override
                    public void onCallback(ArrayList<User> users ) {

                        for(int i=0;i<users.size();i++){
                            mNames.add(users.get(i).getNickname());
                            mImageUrls.add(users.get(i).getProfile_photo());
                            mUserIds.add(users.get(i).getUser_id());
                        }

                        initRecyclerView();

                    }
                },dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getUsers(final FirebaseCallBack firebaseCallback,DataSnapshot dataSnapshot){

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("therapists");
        mAuth = FirebaseAuth.getInstance();

        String current_id=mAuth.getCurrentUser().getUid();

        Log.d(TAG, "getUsers: "+ current_id);


        for(DataSnapshot ds: dataSnapshot.child("therapists").getChildren()) {
            Therapist therapist = ds.getValue(Therapist.class);

            Log.d(TAG, "getUsers: "+ therapist.getTherapist_id());

            if(therapist.getTherapist_id().equals(current_id)){

                if (!therapist.getAssignedTemp().equals("none")) {

                    Log.d(TAG, "matchSpecialities: user is found");

                    for (DataSnapshot ab : dataSnapshot.child("users").getChildren()) {
                        User user = ab.getValue(User.class);

                        if (user.getUser_id().equals(therapist.getAssignedTemp())) {
                            mUsers.add(user);
                        }
                    }
                }

            }
        }

        firebaseCallback.onCallback(mUsers);
    }


    public interface FirebaseCallBack {
        void onCallback(ArrayList<User> users);
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter1 adapter = new RecyclerViewAdapter1(this, mNames, mImageUrls,mUserIds);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}






















