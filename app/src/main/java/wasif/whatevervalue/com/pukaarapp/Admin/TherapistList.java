package wasif.whatevervalue.com.pukaarapp.Admin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import wasif.whatevervalue.com.pukaarapp.Models.Admin;
import wasif.whatevervalue.com.pukaarapp.Models.Therapist;
import wasif.whatevervalue.com.pukaarapp.R;

public class TherapistList extends AppCompatActivity {

    private static final String TAG = "TherapistList";


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mTherapistIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started.");

        initImageBitmaps();
    }


    private void initImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        getTherapists(new FirebaseCallBack() {
            @Override
            public void onCallback(ArrayList<Therapist> therapists ) {

                for(int i=0;i<therapists.size();i++){
                    mNames.add(therapists.get(i).getName());
                    mImageUrls.add(therapists.get(i).getProfile_photo());
                    mTherapistIds.add(therapists.get(i).getTherapist_id());
                    Log.d(TAG, "onCallback: "+ therapists.get(i).getName());
                    Log.d(TAG, "onCallback: "+ therapists.get(i).getProfile_photo());
                }

                initRecyclerView();

            }
        });

    }

    private void getTherapists(final FirebaseCallBack firebaseCallback){

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("therapists");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Therapist> therapists= new ArrayList<>();

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    therapists.add(ds.getValue(Therapist.class));
                }

                firebaseCallback.onCallback(therapists);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public interface FirebaseCallBack {
        void onCallback(ArrayList<Therapist> therapists);
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImageUrls,mTherapistIds);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}






















