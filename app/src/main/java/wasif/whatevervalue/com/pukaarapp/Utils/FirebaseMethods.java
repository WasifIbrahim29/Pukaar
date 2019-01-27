package wasif.whatevervalue.com.pukaarapp.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.util.Strings;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import wasif.whatevervalue.com.pukaarapp.Admin.AdminPanel;
import wasif.whatevervalue.com.pukaarapp.Login.LoginActivity;
import wasif.whatevervalue.com.pukaarapp.Login.verifyNumberActivity;
import wasif.whatevervalue.com.pukaarapp.Models.Therapist;
import wasif.whatevervalue.com.pukaarapp.Models.User;
import wasif.whatevervalue.com.pukaarapp.Models.user_questions;
import wasif.whatevervalue.com.pukaarapp.R;
import wasif.whatevervalue.com.pukaarapp.Session.SessionActivity;

import static android.widget.Toast.LENGTH_SHORT;

public class FirebaseMethods extends Activity {

    private static final String TAG = "FirebaseMethods";
    private Context mContext;


    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private DatabaseReference myRef1;
    private String userID;
    private DatabaseReference mUserDatabase;
    private StorageReference mStorageReference;
    ArrayList<String> specialitiesMatched= new ArrayList<>();
    final int PERMISSION_REQUEST_CODE=1;

    public FirebaseMethods(Context context) {
        //Log.d(TAG, "FirebaseMethods: hello sup?");
        //mAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        myRef1 = mFirebaseDatabase.getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mContext = context;
        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }
    }

    public void addNewUser(String email,String userID, String nickname, String profile_photo,String phoneNumber,String assignedToTherapist,int questionCount,String status, int userNumber){

        User user = new User( userID, email,  nickname, profile_photo,phoneNumber,assignedToTherapist,status, userNumber );

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("users");

        myRef.child(userID).setValue(user);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef1 = mFirebaseDatabase.getReference("questions");

        myRef1=myRef1.child("user"+ (questionCount));

        myRef1.child("userID").setValue(userID);
    }

    public void registerNewEmail(final String email, final String password, final String nickname,String profile_photo,final String phoneNumber,String assignedToTherapist,final int questionCount,String status,final int userNumber){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(mContext, "Register didn't happen",
                                    Toast.LENGTH_SHORT).show();

                        }
                        else if(task.isSuccessful()){


                            String userID=mAuth.getCurrentUser().getUid();
                            addNewUser(email,userID,nickname,"none",phoneNumber,"none",questionCount,"firstTime",questionCount);




                            //send verificaton email
                            //sendVerificationEmail();
                            //userID = mAuth.getCurrentUser().getUid();
                            //Log.d(TAG, "onComplete: Authstate changed: " + userID);
                        }

                    }
                });
    }


    public void registerNewTherapistEmail(final String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(mContext, "Register didn't happen",
                                    Toast.LENGTH_SHORT).show();

                        }
                        else if(task.isSuccessful()){
                            //send verificaton email
                            //sendVerificationEmail();

                            //userID = mAuth.getCurrentUser().getUid();
                            //Log.d(TAG, "onComplete: Authstate changed: " + userID);
                        }

                    }
                });
    }

    public void uploadNewPhoto(final String imgUrl,
                               Bitmap bm){


        Log.d(TAG, "uploadNewPhoto: attempting to uplaod new photo.");
        String link="";
        FilePaths filePaths = new FilePaths();


        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            final StorageReference storageReference = mStorageReference
                    .child(filePaths.FIREBASE_IMAGE_STORAGE + "/" + user_id + "/profile_photo");

            //convert image url to bitmap
            if (bm == null) {
                bm = ImageManager.getBitmap(imgUrl);
            }
            byte[] bytes = ImageManager.getBytesFromBitmap(bm, 100);

            UploadTask uploadTask = null;
            uploadTask = storageReference.putBytes(bytes);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return storageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        System.out.println("Upload " + downloadUri);
                        //Toast.makeText(mActivity, "Successfully uploaded", Toast.LENGTH_SHORT).show();
                        if (downloadUri != null) {

                            String photoStringLink = downloadUri.toString(); //YOU WILL GET THE DOWNLOAD URL HERE !!!!
                            System.out.println("Upload " + photoStringLink);

                            Toast.makeText(mContext, "photo upload success", Toast.LENGTH_SHORT).show();

                            //add the new photo to 'photos' node and 'user_photos' node
                            //setProfilePhoto(photoStringLink);
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: Photo upload failed.");
                    Toast.makeText(mContext, "Photo upload failed ", Toast.LENGTH_SHORT).show();
                }
            });
        }

    private void setProfilePhoto(String url){
        Log.d(TAG, "setProfilePhoto: setting new profile image: " + url);

        myRef = mFirebaseDatabase.getReference("therapists");

        myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("profile_photo")
                .setValue(url);
    }

    public int getQuestionsCount(DataSnapshot dataSnapshot){
        int count = 0;
        for(DataSnapshot ds: dataSnapshot
                .child("questions")
                .getChildren()){
            count++;
        }
        return count;
    }

    public int getAnswersCount(DataSnapshot dataSnapshot,String user_id){

        int count=0;


        //myRef = mFirebaseDatabase.getReference("question");


        for(DataSnapshot ds: dataSnapshot.child("questions").getChildren()){
            user_questions user= ds.getValue(user_questions.class);
            Log.d(TAG, "getAnswersCount: "+ user.getUserID());
            Log.d(TAG, "getAnswersCount: "+ user_id);
            if(user.getUserID().equals(user_id)){

                String ptsd= user.getQuestion6().getPost_dramatic_stress_disorder();

            }

        }
        return count;
    }

    public void matchSpecialities(DataSnapshot dataSnapshot,String user_id){


        for(DataSnapshot ds: dataSnapshot.child("questions").getChildren()){
            user_questions user= ds.getValue(user_questions.class);
            Log.d(TAG, "matchSpecialities: "+ user_id);
            Log.d(TAG, "matchSpecialities: "+ user.getUserID());
            //Log.d(TAG, "getAnswersCount: "+ user.getUserID());
            //Log.d(TAG, "getAnswersCount: "+ user_id);
            if(user.getUserID().equals(user_id)){

                Log.d(TAG, "matchSpecialities: user is found");

                for(DataSnapshot ab: dataSnapshot.child("therapists").getChildren()){
                    Therapist therapist=ab.getValue(Therapist.class);
                    Log.d(TAG, "matchSpecialities: "+ therapist.getName());
                    Log.d(TAG, "matchSpecialities: "+ therapist.getSpeciality().getWork_life_issues());
                    Log.d(TAG, "matchSpecialities: "+ user.getQuestion6().getWork_life_issues());

                    if(user.getQuestion6().getAnxiety()!=null && therapist.getSpeciality().getAnxiety()!=null){
                        specialitiesMatched.add("Anxiety");

                    }
                    if(user.getQuestion6().getDepression()!=null && therapist.getSpeciality().getDepression()!=null){
                        specialitiesMatched.add("Depression");

                    }
                    if(user.getQuestion6().getSelf_harm_and_personality_disorders()!=null && therapist.getSpeciality().getSelf_harm_and_personality_disorders()!=null){
                        specialitiesMatched.add("Self harm and Personality Disorders");
                    }
                    if(user.getQuestion6().getPost_dramatic_stress_disorder()!=null && therapist.getSpeciality().getPost_dramatic_stress_disorder()!=null){
                        specialitiesMatched.add("Post Dramatic Stress Disorder");
                    }
                    if(user.getQuestion6().getRelationships_family_or_friends()!=null && therapist.getSpeciality().getRelationships_family_or_friends()!=null){
                        specialitiesMatched.add("Relationships ( family or friends )");
                    }
                    if(user.getQuestion6().getSuicidal_thoughts()!=null && therapist.getSpeciality().getSuicidal_thoughts()!=null){
                        specialitiesMatched.add("Suicidal Thoughts");
                    }
                    if(user.getQuestion6().getDrug_addiction()!=null && therapist.getSpeciality().getDrug_addiction()!=null){
                        specialitiesMatched.add("Drug Addiction");
                    }
                    if(user.getQuestion6().getWork_life_issues()!=null && therapist.getSpeciality().getWork_life_issues()!=null){
                        specialitiesMatched.add("Work-life issues");
                    }
                    if(user.getQuestion6().getMood_disorders()!=null && therapist.getSpeciality().getMood_disorders()!=null){
                        specialitiesMatched.add("Mood disorders");
                    }
                    if(user.getQuestion6().getAnger_management()!=null && therapist.getSpeciality().getAnger_management()!=null){
                        specialitiesMatched.add("Anger Management");
                    }
                    if(user.getQuestion6().getEmotional_regulations()!=null && therapist.getSpeciality().getEmotional_regulations()!=null){
                        specialitiesMatched.add("Emotional Regulations");
                    }
                    if(user.getQuestion6().getPhobia()!=null && therapist.getSpeciality().getPhobia()!=null){
                        specialitiesMatched.add("Phobia");
                    }
                    if(user.getQuestion6().getSelf_esteem_issues()!=null && therapist.getSpeciality().getSelf_esteem_issues()!=null){
                        specialitiesMatched.add("Self-esteem issues");
                    }
                    if(user.getQuestion6().getTrauma_and_abuse()!=null && therapist.getSpeciality().getTrauma_and_abuse()!=null){
                        specialitiesMatched.add("Trauma and abuse");
                    }
                    if(user.getQuestion6().getSchizophrenia()!=null && therapist.getSpeciality().getSchizophrenia()!=null){
                        specialitiesMatched.add("Schizophrenia");
                    }
                    if(user.getQuestion6().getHeadache_management()!=null && therapist.getSpeciality().getHeadache_management()!=null){
                        specialitiesMatched.add("Headache Management");
                    }
                    if(user.getQuestion6().getHealth_counseling()!=null && therapist.getSpeciality().getHealth_counseling()!=null){
                        specialitiesMatched.add("Health counseling");
                    }
                    if(user.getQuestion6().getPersonality_disorder()!=null && therapist.getSpeciality().getPersonality_disorder()!=null){
                        specialitiesMatched.add("Personality Disorder");
                    }

                    Log.d(TAG, "matchSpecialities: "+ user.getUserID());
                    Log.d(TAG, "matchSpecialities: "+ therapist.getPhone_number());
                    Log.d(TAG, "matchSpecialities: "+ specialitiesMatched);

                    if(!specialitiesMatched.isEmpty()){
                        myRef = mFirebaseDatabase.getReference("therapists");
                        myRef.child(therapist.getTherapist_id()).child("assignedTemp").setValue(user.getUserID());
                        letKnowTherapists(user.getUserID(),therapist.getPhone_number());
                        specialitiesMatched.clear();
                    }

                }


            }

        }
    }

    public void letKnowTherapists(String userID,String phoneNumber){
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                Log.e("permission", "Permission already granted.");
            } else {
                requestPermission();
            }
        }

        if(checkPermission()) {

            Log.d(TAG, "letKnowTherapists: Message is being sent");

//Get the default SmsManager//

            SmsManager smsManager = SmsManager.getDefault();
            String message="These specialities ";

            for(int i=0;i<specialitiesMatched.size();i++){
                message= specialitiesMatched.get(i)+",";
            }

            message= message+ " have been matched with the following user id: "+ userID + ". Please respond asap.";

//Send the SMS//

            smsManager.sendTextMessage(phoneNumber, null, message, null, null);

            Log.d(TAG, "letKnowTherapists: Message sent");
        }else {
            Toast.makeText(mContext, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(mContext, Manifest.permission.SEND_SMS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(FirebaseMethods.this, new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(mContext,
                            "Permission accepted", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(mContext,
                            "Permission denied", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    public void addQuestion(String question,String answer, int questionCount){
        myRef = mFirebaseDatabase.getReference();

        Log.d(TAG, "addQuestion: I am inside");


        myRef.child("questions")
                .child("user" + (questionCount+1))
                .child("question"+ 1)
                .child("question")
                .setValue(question);


        myRef.child("questions")
                .child("user" + (questionCount+1))
                .child("question"+ 1)
                .child("answer")
                .setValue(answer);
    }


    public void addAnotherQuestion(String question,String answer, int questionCount, int questionNumber){
        myRef = mFirebaseDatabase.getReference("questions");

        Log.d(TAG, "addQuestion: I am inside");


        myRef.child("user"+ (questionCount))
                .child("question"+ questionNumber)
                .child("question")
                .setValue(question);


        myRef.child("user"+ (questionCount))
                .child("question"+ questionNumber)
                .child("answer")
                .setValue(answer);
    }

    public void addMainQuestion(String question,String answer, int questionCount, int answerCount){
        myRef = mFirebaseDatabase.getReference("questions");

        Log.d(TAG, "addQuestion: I am inside");

        myRef.child("user"+ (questionCount))
                .child("question"+ 6)
                .child("question")
                .setValue(question);


        myRef.child("user"+ (questionCount))
                .child("question"+ 6)
                .child(answer)
                .setValue("checked");
    }

    public void addSpecialities(String answer,String therapist_id){

        myRef = mFirebaseDatabase.getReference("therapists");

        Log.d(TAG, "addQuestion: I am inside");

        myRef.child(therapist_id)
                .child("speciality")
                .child(answer)
                .setValue("checked");

    }

    public void addQuestionsToUser(){}


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
