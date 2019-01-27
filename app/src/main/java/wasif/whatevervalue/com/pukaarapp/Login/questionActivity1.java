package wasif.whatevervalue.com.pukaarapp.Login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import wasif.whatevervalue.com.pukaarapp.R;
import wasif.whatevervalue.com.pukaarapp.Utils.FirebaseMethods;

public class questionActivity1 extends AppCompatActivity implements View.OnClickListener {

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;

    private int questionCount = 0;

    private static final String TAG = "questionActivity1";

    Button answer1,answer2,answer3,answer4,answer5;
    FirebaseMethods firebaseMethods;
    TextView question1;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question1);
        answer1=findViewById(R.id.answer1);
        answer2=findViewById(R.id.answer2);
        answer3=findViewById(R.id.answer3);
        answer4=findViewById(R.id.answer4);
        answer5=findViewById(R.id.answer5);
        question1=findViewById(R.id.question1);
        mFirebaseMethods= new FirebaseMethods(questionActivity1.this);

        setupFirebaseAuth();

        findViewById(R.id.answer1).setOnClickListener(this);
        findViewById(R.id.answer2).setOnClickListener(this);
        findViewById(R.id.answer3).setOnClickListener(this);
        findViewById(R.id.answer4).setOnClickListener(this);
        findViewById(R.id.answer5).setOnClickListener(this);





    }

    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        Log.d(TAG, "onDataChange: image count: " + questionCount);

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



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                questionCount = mFirebaseMethods.getQuestionsCount(dataSnapshot);
                Log.d(TAG, "onDataChange: question count: " + questionCount);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addQuestion(TextView question,Button answer){

        Log.d(TAG, "addQuestion: I am inside");

        mFirebaseMethods.addQuestion(question.getText().toString(),answer.getText().toString(),questionCount);

    }

    public void goToQuestionActivity2(){
        Intent intent= new Intent(this,questionActivity2.class);
        startActivity(intent);
    }

    public void goToIntroPage(){
        Intent intent= new Intent(this,IntroActivity.class);
        intent.putExtra("questionNumber",questionCount);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.answer1:
                addQuestion(question1,answer1);
                goToQuestionActivity2();
                break;
            case R.id.answer2:
                addQuestion(question1,answer2);
                goToQuestionActivity2();
                break;
            case R.id.answer3:
                addQuestion(question1,answer3);
                goToQuestionActivity2();
                break;

            case R.id.answer4:
                addQuestion(question1,answer4);
                goToQuestionActivity2();
                break;

            case R.id.answer5:
                addQuestion(question1,answer5);
                goToQuestionActivity2();
                break;

        }
    }
}
