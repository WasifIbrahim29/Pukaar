package wasif.whatevervalue.com.pukaarapp.Admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import wasif.whatevervalue.com.pukaarapp.Login.LoginActivity;
import wasif.whatevervalue.com.pukaarapp.R;
import wasif.whatevervalue.com.pukaarapp.Session.SessionActivity;
import wasif.whatevervalue.com.pukaarapp.Utils.FirebaseMethods;
import wasif.whatevervalue.com.pukaarapp.Utils.UniversalImageLoader;

import static android.widget.Toast.LENGTH_SHORT;

public class AdminActivity extends AppCompatActivity {

    private static final String TAG = "AdminActivity";

    ImageView profile;

    String profile_photo;
    EditText mName,mEmail,mPassword,mPhoneNumber,mDescription,mSpeciality1,mSpeciality2,mSpeciality3,mSpeciality4;
    CheckBox checkBox1,checkBox2,checkBox3,checkBox4;
    String name;
    String email;
    String password;
    String phoneNumber;
    String description;
    String speciality1;
    String speciality2;
    String speciality3;
    String speciality4;
    String checkbox_messaging;
    String checkbox_audio,checkbox_video,checkbox_OneOnOne;
    ImageView icDone;
    FirebaseMethods firebaseMethods;
    EditText mon,tues,wed,thurs,fri,sat,sun,license;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mUserDatabase;

    private Context mContext;
    private ProgressBar mProgressBar;
    private TextView mPleaseWait;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private String therapist_id;
    private Intent intent;

    String profile_pic="";


    CheckBox answer1,answer2,answer3,answer4,answer5,answer6;
    CheckBox answer7,answer8,answer9,answer10,answer11,answer12;
    CheckBox answer13,answer14,answer15,answer16,answer17,answer18;
    ArrayList<CheckBox> answers;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_therapist);
        answer1=findViewById(R.id.answer1);
        answer2=findViewById(R.id.answer2);
        answer3=findViewById(R.id.answer3);
        answer4=findViewById(R.id.answer4);
        answer5=findViewById(R.id.answer5);
        answer6=findViewById(R.id.answer6);
        answer7=findViewById(R.id.answer7);
        answer8=findViewById(R.id.answer8);
        answer9=findViewById(R.id.answer9);
        answer10=findViewById(R.id.answer10);
        answer11=findViewById(R.id.answer11);
        answer12=findViewById(R.id.answer12);
        answer13=findViewById(R.id.answer13);
        answer14=findViewById(R.id.answer14);
        answer15=findViewById(R.id.answer15);
        answer16=findViewById(R.id.answer16);
        answer17=findViewById(R.id.answer17);
        answer18=findViewById(R.id.answer18);
        firebaseMethods= new FirebaseMethods(this);

        initWidgets();


        answers= new ArrayList<>();
        answers.add(answer1);
        answers.add(answer2);
        answers.add(answer3);
        answers.add(answer4);
        answers.add(answer5);
        answers.add(answer6);
        answers.add(answer7);
        answers.add(answer8);
        answers.add(answer9);
        answers.add(answer10);
        answers.add(answer11);
        answers.add(answer12);
        answers.add(answer13);
        answers.add(answer14);
        answers.add(answer15);
        answers.add(answer16);
        answers.add(answer17);
        answers.add(answer18);

        profile= findViewById(R.id.profile_image);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToShareActivity();
            }
        });

        icDone=findViewById(R.id.icDone);
        setupFirebaseAuth();

        init();


        try{

            profile_pic=getIntent().getExtras().getString(getString(R.string.selected_image));
            Log.d(TAG, "onClick: "+ profile_pic);

            Glide.with(this).asBitmap()
                    .load(profile_pic).into(profile);

            //UniversalImageLoader.setImage(profile_pic, profile, null, "");

        }catch (NullPointerException e){
            Log.d(TAG, "onCreate: Nullpointer exception is here");

        }


    }

    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    public void init(){
        icDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=mEmail.getText().toString();
                password=mPassword.getText().toString();
                Log.d(TAG, "onClick: "+ email+ " and " + password);
                firebaseMethods.registerNewTherapistEmail(email,password);

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(AdminActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                                FirebaseUser user = mAuth.getCurrentUser();

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Log.w(TAG, "signInWithEmail:failed", task.getException());

                                    Toast.makeText(AdminActivity.this, "Auth failed",LENGTH_SHORT).show();
                                    //mProgressBar.setVisibility(View.GONE);
                                    //mPleaseWait.setVisibility(View.GONE);
                                }
                                else{

                                    therapist_id= mAuth.getCurrentUser().getUid();
                                    Log.d(TAG, "onComplete: therapist id= "+ therapist_id);

                                    //String imgUrl="wasif the man";

                                    // if(intent.hasExtra(getString(R.string.selected_image))){
                                    //imgUrl = intent.getStringExtra(getString(R.string.selected_image));
                                    //}

                                    Log.d(TAG, "onClick: profile photo= "+ profile_pic);
                                    String services=checkBox1.getText().toString()+checkBox2.getText().toString()+checkBox3.getText().toString()+checkBox4.getText().toString();

                                    Map therapistMap= new HashMap();
                                    therapistMap.put("name",mName.getText().toString());
                                    therapistMap.put("therapist_id",therapist_id);
                                    therapistMap.put("email",mEmail.getText().toString());
                                    therapistMap.put("phone_number",mPhoneNumber.getText().toString());
                                    therapistMap.put("description",mDescription.getText().toString());
                                    therapistMap.put("profile_photo",profile_pic);
                                    therapistMap.put("services",services);
                                    therapistMap.put("monday",mon.getText().toString());
                                    therapistMap.put("tuesday",tues.getText().toString());
                                    therapistMap.put("wednesday",wed.getText().toString());
                                    therapistMap.put("thursday",thurs.getText().toString());
                                    therapistMap.put("friday",fri.getText().toString());
                                    therapistMap.put("saturday",sat.getText().toString());
                                    therapistMap.put("sunday",sun.getText().toString());
                                    therapistMap.put("license",license.getText().toString());
                                    therapistMap.put("assignedToUser","none");
                                    therapistMap.put("assignedTemp","none");

                                    mFirebaseDatabase = FirebaseDatabase.getInstance();
                                    myRef = mFirebaseDatabase.getReference("therapists");

                                    myRef.child(therapist_id).updateChildren(therapistMap, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                            if (databaseError != null) {

                                                Log.d(TAG, "onComplete: There was some error in sending request");

                                                //Toast.makeText(ViewProfileFragment.this, "There was some error in sending request", Toast.LENGTH_SHORT).show();

                                            } else {

                                                for(int i=0;i<answers.size();i++){
                                                    if(answers.get(i).isChecked()){
                                                        firebaseMethods.addSpecialities(answers.get(i).getTag().toString(),therapist_id);
                                                    }
                                                }

                                                Log.d(TAG, "onComplete: therapist data is added.");

                                                firebaseMethods.uploadNewPhoto(profile_pic,null);

                                            }


                                        }
                                    });

                                }

                                // ...
                            }
                        });

            }
        });
    }

    public void initWidgets(){
        Log.d(TAG, "init: inside init");
        mName=findViewById(R.id.therapist_name);
        mEmail=findViewById(R.id.therapist_email);
        mPassword=findViewById(R.id.therapist_password);
        mPhoneNumber=findViewById(R.id.therapist_phoneNo);
        mDescription=findViewById(R.id.therapist_description);
        checkBox1=findViewById(R.id.checkbox_messaging);
        checkBox2=findViewById(R.id.checkbox_audioCall);
        checkBox3=findViewById(R.id.checkbox_videoCall);
        checkBox4=findViewById(R.id.checkbox_OneonOne);
        mon=findViewById(R.id.mon);
        tues=findViewById(R.id.tues);
        wed=findViewById(R.id.wed);
        thurs=findViewById(R.id.thurs);
        fri=findViewById(R.id.fri);
        sat=findViewById(R.id.sat);
        sun=findViewById(R.id.sun);
        license=findViewById(R.id.license);

    }

    public void goToShareActivity(){
        Intent intent= new Intent(this,ShareActivity.class);
        startActivity(intent);
    }


}
