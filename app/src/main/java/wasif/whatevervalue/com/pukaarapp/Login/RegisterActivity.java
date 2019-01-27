package wasif.whatevervalue.com.pukaarapp.Login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import wasif.whatevervalue.com.pukaarapp.R;
import wasif.whatevervalue.com.pukaarapp.Utils.FirebaseMethods;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RegisterActivity";
    private static final Boolean CHECK_IF_VERIFIED = false;

    //firebase
    //private FirebaseAuth mAuth;
    //private FirebaseAuth.AuthStateListener mAuthListener;

    private Context mContext;
    private ProgressBar mProgressBar;
    private EditText mEmail, mPassword, mNickname;
    private TextView mPleaseWait;
    private TextView mSignin;
    private Button mRegisterButton;
    private String email, nickname, password;
    private FirebaseMethods firebaseMethods;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mContext = RegisterActivity.this;
        firebaseMethods = new FirebaseMethods(mContext);
        Log.d(TAG, "onCreate: started.");

        findViewById(R.id.sign_in).setOnClickListener(this);
        findViewById(R.id.back_arrow).setOnClickListener(this);

        initWidgets();
        setupFirebaseAuth();
        init();

    }

    private void initWidgets(){
        Log.d(TAG, "initWidgets: Initializing Widgets.");
        mRegisterButton=findViewById(R.id.btn_register);
        mEmail=findViewById(R.id.txt_email);
        mPassword=findViewById(R.id.txt_password);
        mNickname=findViewById(R.id.txt_nickname);



    }


    private void init(){
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getText().toString();
                nickname = mNickname.getText().toString();
                password = mPassword.getText().toString();

                if(checkInputs(email, nickname, password)){

                    //mProgressBar.setVisibility(View.VISIBLE);
                    //firebaseMethods.registerNewEmail(email, password, nickname);
                    goToGetNumberActivity();
                }
            }
        });
    }

    public void goToGetNumberActivity(){
        Intent intent= new Intent(this,getNumberActivity.class);
        int questionCount= getIntent().getIntExtra("questionNumber",0);
        intent.putExtra("Email",email);
        intent.putExtra("Nickname",nickname);
        intent.putExtra("Password",password);
        intent.putExtra("questionNumber",questionCount);
        startActivity(intent);
    }

    private boolean checkInputs(String email, String nickname, String password){
        Log.d(TAG, "checkInputs: checking inputs for null values.");
        if(email.equals("") || nickname.equals("") || password.equals("")){
            Toast.makeText(mContext, "All fields must be filled out.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void goToLoginPage(){
        Intent intent= new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
    public void goToIntroPage(){
        Intent intent= new Intent(this,IntroActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in:
                goToLoginPage();
                break;
            case R.id.back_arrow:
                goToIntroPage();
                break;

        }
    }

     /*
    ------------------------------------ Firebase ---------------------------------------------
     */

    /**
     * Check is @param username already exists in teh database
     * @param username
     */


    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        myRef=mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    myRef=database.getReference();


                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //checkIfUsernameExists(username);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    finish();

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }
}
