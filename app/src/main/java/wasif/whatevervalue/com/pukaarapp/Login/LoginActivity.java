package wasif.whatevervalue.com.pukaarapp.Login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.security.MessageDigest;
import java.util.ArrayList;

import wasif.whatevervalue.com.pukaarapp.Admin.AdminActivity;
import wasif.whatevervalue.com.pukaarapp.Admin.AdminPanel;
import wasif.whatevervalue.com.pukaarapp.Admin.UserList;
import wasif.whatevervalue.com.pukaarapp.Models.Admin;
import wasif.whatevervalue.com.pukaarapp.Models.Therapist;
import wasif.whatevervalue.com.pukaarapp.Models.User;
import wasif.whatevervalue.com.pukaarapp.Models.user_questions;
import wasif.whatevervalue.com.pukaarapp.More.MoreActivity;
import wasif.whatevervalue.com.pukaarapp.R;
import wasif.whatevervalue.com.pukaarapp.Session.SessionActivity;
import wasif.whatevervalue.com.pukaarapp.Utils.FirebaseMethods;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by User on 6/19/2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private static final Boolean CHECK_IF_VERIFIED = false;

    //firebase
    //private FirebaseAuth mAuth;
    //private FirebaseAuth.AuthStateListener mAuthListener;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mUserDatabase;

    private Context mContext;
    private ProgressBar mProgressBar;
    private EditText mEmail, mPassword;
    private TextView mPleaseWait;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mContext = LoginActivity.this;
        Log.d(TAG, "onCreate: started.");
        mUserDatabase= FirebaseDatabase.getInstance().getReference().child("users");
        mEmail=findViewById(R.id.txt_email);
        mPassword=findViewById(R.id.txt_password);
        mFirebaseMethods= new FirebaseMethods(LoginActivity.this);


        findViewById(R.id.sign_up).setOnClickListener(this);
        findViewById(R.id.back_arrow).setOnClickListener(this);

        setupFirebaseAuth();
        init();

    }


    public void goToRegisterPage(){
        Intent intent= new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
    public void goToIntroPage(){
        Intent intent= new Intent(this,IntroActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_up:
                goToRegisterPage();
                break;
            case R.id.back_arrow:
                goToIntroPage();
                break;

        }
    }

    private boolean isStringNull(String string){
        Log.d(TAG, "isStringNull: checking string if null.");

        if(string.equals("")){
            return true;
        }
        else{
            return false;
        }
    }

    /*
    ------------------------------------ Firebase ---------------------------------------------
     */

    private void init(){

        //initialize the button for logging in
        Button btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Log.d(TAG, "onClick: attempting to log in.");

                                            final String email = mEmail.getText().toString();
                                            final String password = mPassword.getText().toString();

                                            if (isStringNull(email) && isStringNull(password)) {
                                                Toast.makeText(mContext, "You must fill out all the fields", LENGTH_SHORT).show();
                                            } else {

                                                getAdmin(new FirebaseCallBack() {
                                                    @Override
                                                    public void onCallback(Admin adminObj) {
                                                        if (adminObj.getEmail().equals(email) && adminObj.getPassword().equals(password)) {
                                                            Log.d(TAG, "onCallback: I am inside admin");
                                                            goToAdminPanel();

                                                        } else {

                                                            mAuth.signInWithEmailAndPassword(email, password)
                                                                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                                                                            FirebaseUser user = mAuth.getCurrentUser();

                                                                            // If sign in fails, display a message to the user. If sign in succeeds
                                                                            // the auth state listener will be notified and logic to handle the
                                                                            // signed in user can be handled in the listener.
                                                                            if (!task.isSuccessful()) {
                                                                                Log.w(TAG, "signInWithEmail:failed", task.getException());

                                                                                Toast.makeText(LoginActivity.this, "Auth failed", LENGTH_SHORT).show();
                                                                                //mProgressBar.setVisibility(View.GONE);
                                                                                //mPleaseWait.setVisibility(View.GONE);
                                                                            } else{
                                                                                checkThePerson();
                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    });


         /*
         If the user is logged in then navigate to HomeActivity and call 'finish()'
          */
    }

    public void goToAdminPanel(){
        Intent intent=new Intent(this, AdminPanel.class);
        startActivity(intent);
    }

    private void checkThePerson(){

        String current_user_id = mAuth.getCurrentUser().getUid();

        Log.d(TAG, "onComplete: " + current_user_id);

        checkTherapist(new FirebaseCallBack2() {
            @Override
            public void onCallback(String isThisTherapist) {

                Log.d(TAG, "onCallback: " + isThisTherapist);

                if (isThisTherapist.equals("IamTherapist")) {
                    Log.d(TAG, "onCallback: here inside therapist");
                    goToUserList();
                }
                else{
                    checkUser(new FirebaseCallBack1() {
                        @Override
                        public void onCallback(String therapistIsAssigned) {
                            Log.d(TAG, "onCallback: "+ therapistIsAssigned);
                            if(!therapistIsAssigned.equals("therapistAssigned")){
                                Log.d(TAG, "onCallback: in user shit");
                                goToIntroPage();
                            }
                            else{
                                goToSessionActivity();
                            }
                        }
                    });
                }
            }
        }, current_user_id);
    }


    private void checkUser(final FirebaseCallBack1 firebaseCallback){

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("users");


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String found="false";

                for (DataSnapshot ds: dataSnapshot.getChildren()){

                    User user= ds.getValue(User.class);
                    if(!user.getAssignedToTherapist().equals("none")){
                        found="true";
                        break;
                    }
                }

                if(found.equals("true")){
                    firebaseCallback.onCallback("therapistAssigned");
                }
                else{
                    firebaseCallback.onCallback("therapistNotAssigned");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public interface FirebaseCallBack1 {
        void onCallback(String isUserAvailable);
    }

    public void goToUserList(){
        Intent intent=new Intent(this, UserList.class);
        startActivity(intent);
    }

    public void goToSessionActivity(){

        Intent intent = new Intent(LoginActivity.this, SessionActivity.class);
        startActivity(intent);
    }


    private void getAdmin(final FirebaseCallBack firebaseCallback){

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("Admin");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Admin admin=dataSnapshot.getValue(Admin.class);
                Log.d(TAG, "onDataChange: "+ admin.getEmail());
                Log.d(TAG, "onDataChange: "+ admin.getPassword());
                firebaseCallback.onCallback(admin);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void checkTherapist(final FirebaseCallBack2 firebaseCallback, final String therapistID){

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("therapists");
        Log.d(TAG, "checkTherapist: here now");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String found="false";

                for (DataSnapshot ds: dataSnapshot.getChildren()){

                    Therapist therapist= ds.getValue(Therapist.class);
                    if(therapist.getTherapist_id().equals(therapistID)){
                        Log.d(TAG, "onDataChange: matched");
                        found="true";
                        break;
                    }
                }
                if(found.equals("true")){
                    firebaseCallback.onCallback("IamTherapist");
                }
                else{
                    firebaseCallback.onCallback("IamNotTherapist");
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    public interface FirebaseCallBack2 {
        void onCallback(String isThisTherapist);
    }


    public interface FirebaseCallBack {
        void onCallback(Admin adminObj);
    }

    /**
     * Setup the firebase auth object
     */
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

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}

