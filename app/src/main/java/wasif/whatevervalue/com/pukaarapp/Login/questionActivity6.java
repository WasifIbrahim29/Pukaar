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
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import wasif.whatevervalue.com.pukaarapp.R;
import wasif.whatevervalue.com.pukaarapp.Utils.FirebaseMethods;

public class questionActivity6 extends AppCompatActivity implements View.OnClickListener {

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;

    private int questionCount = 0;

    private static final String TAG = "questionActivity6";

    CheckBox answer1,answer2,answer3,answer4,answer5,answer6;
    CheckBox answer7,answer8,answer9,answer10,answer11,answer12;
    CheckBox answer13,answer14,answer15,answer16,answer17,answer18;
    FirebaseMethods firebaseMethods;
    TextView question1;
    Button button1;
    ArrayList<CheckBox> answers;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question6);
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
        question1=findViewById(R.id.question1);
        button1=findViewById(R.id.button1);
        mFirebaseMethods= new FirebaseMethods(questionActivity6.this);

        setupFirebaseAuth();

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

        button1.setOnClickListener(this);






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

    public void addMainQuestion(TextView question,String answer, int answerCount){

        Log.d(TAG, "addQuestion: I am inside");

        mFirebaseMethods.addMainQuestion(question.getText().toString(), answer,questionCount,answerCount);

    }

    public void goToIntroPage(){
        Intent intent= new Intent(this,IntroActivity.class);
        intent.putExtra("questionNumber",questionCount);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                int answerCount=0;
                for(int i=0;i<answers.size();i++){
                    if(answers.get(i).isChecked()){
                        answerCount++;
                        addMainQuestion(question1, answers.get(i).getTag().toString(), answerCount);
                    }
                }
                goToIntroPage();
                break;

        }
    }
}
