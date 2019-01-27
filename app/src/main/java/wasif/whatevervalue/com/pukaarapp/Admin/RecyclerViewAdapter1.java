package wasif.whatevervalue.com.pukaarapp.Admin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import wasif.whatevervalue.com.pukaarapp.Login.LoginActivity;
import wasif.whatevervalue.com.pukaarapp.Models.Therapist;
import wasif.whatevervalue.com.pukaarapp.Models.User;
import wasif.whatevervalue.com.pukaarapp.R;
import wasif.whatevervalue.com.pukaarapp.Utils.FirebaseMethods;

/**
 * Created by User on 1/1/2018.
 */

public class RecyclerViewAdapter1 extends RecyclerView.Adapter<RecyclerViewAdapter1.ViewHolder> implements View.OnClickListener {

    private static final String TAG = "RecyclerViewAdapter1";

    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mUserIds = new ArrayList<>();
    private Context mContext;
    String phoneNo;
    String message;
    final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1000;
    final int PERMISSION_REQUEST_CODE=1;


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseMethods mFirebaseMethods;


    public RecyclerViewAdapter1(Context context, ArrayList<String> imageNames, ArrayList<String> images, ArrayList<String> ids) {
        Log.d(TAG, "RecyclerViewAdapter: I am hereee");
        mImageNames = imageNames;
        mImages = images;
        mUserIds = ids;
        mContext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: I am inside");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem1, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder.image);

        holder.imageName.setText(mImageNames.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mImageNames.get(position));

                Toast.makeText(mContext, mImageNames.get(position), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, GalleryActivity.class);
                intent.putExtra("image_url", mImages.get(position));
                intent.putExtra("image_name", mImageNames.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

    @Override
    public void onClick(View v) {

        if (v.equals(R.id.btn_cancel)) {

        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView imageName;
        RelativeLayout parentLayout;
        Button removeUser;
        Button addUser;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.image_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            removeUser = itemView.findViewById(R.id.btn_cancel);
            addUser= itemView.findViewById(R.id.btn_accept);

            removeUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeAt(getAdapterPosition());
                }
            });

            addUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addUser(getAdapterPosition());
                    Log.d(TAG, "onClick: "+ mUserIds);
                    removeAt(getAdapterPosition());
                    Log.d(TAG, "onClick: "+ mUserIds);

                }
            });
        }

        public void addUser(int position){
            final String userID= mUserIds.get(position);

            Log.d(TAG, "addUser: "+ userID);

            Log.d(TAG, "addUser: "+ userID);

            mFirebaseDatabase = FirebaseDatabase.getInstance();
            myRef = mFirebaseDatabase.getReference();

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    assignUser(dataSnapshot,userID);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        public void assignUser(DataSnapshot dataSnapshot,String userID){
            mAuth = FirebaseAuth.getInstance();

            String therapist_current_id= mAuth.getCurrentUser().getUid();

            myRef = mFirebaseDatabase.getReference("users");
            myRef.child(userID).child("assignedToTherapist").setValue(therapist_current_id);
            myRef = mFirebaseDatabase.getReference("therapists");


            Log.d(TAG, "addQuestion: I am inside");

            for(DataSnapshot ds: dataSnapshot.child("therapists").getChildren()){
                Therapist therapist= ds.getValue(Therapist.class);
                if(therapist.getTherapist_id().equals(therapist_current_id) && therapist.getAssignedToUser().equals("none")){
                    String assignedTemp=therapist.getAssignedTemp();

                    Log.d(TAG, "assignUser: "+ assignedTemp);

                    myRef.child(therapist_current_id).child("assignedToUser").setValue(assignedTemp);
                    myRef.child(therapist_current_id).child("assignedTemp").setValue("none");

                    myRef = mFirebaseDatabase.getReference("therapists");
                }
            }
        }



        public void removeAt(int position) {
            mImageNames.remove(position);
            mUserIds.remove(position);
            mImages.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mImages.size());
        }
    }
}















