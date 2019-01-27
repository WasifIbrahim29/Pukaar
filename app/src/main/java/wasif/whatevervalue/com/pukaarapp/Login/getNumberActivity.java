package wasif.whatevervalue.com.pukaarapp.Login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import wasif.whatevervalue.com.pukaarapp.R;
import wasif.whatevervalue.com.pukaarapp.Utils.FirebaseMethods;

public class getNumberActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "getNumberActivity";
    private Context mContext;
    String email,password,nickname,phoneNumber;
    TextView phoneNo;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_number);
        mContext = getNumberActivity.this;
        Log.d(TAG, "onCreate: started.");

        email=getIntent().getStringExtra("Email");
        nickname=getIntent().getStringExtra("Nickname");
        password=getIntent().getStringExtra("Password");

        phoneNo=findViewById(R.id.phone_number);

        findViewById(R.id.verify_number).setOnClickListener(this);

        Log.d(TAG, "onCreate: "+ email);
        Log.d(TAG, "onCreate: "+ nickname);
        Log.d(TAG, "onCreate: "+ phoneNumber);

    }

    public void goToVerifyNumberActivity(){
        phoneNumber=phoneNo.getText().toString();
        if(phoneNumber.equals("")){

            Toast.makeText(this,"Please Enter your phone number.",Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent= new Intent(this,verifyNumberActivity.class);
            int questionCount= getIntent().getIntExtra("questionNumber",0);
            intent.putExtra("Email",email);
            intent.putExtra("Nickname",nickname);
            intent.putExtra("Password",password);
            intent.putExtra("phoneNumber",phoneNumber);
            intent.putExtra("questionNumber",questionCount);
            startActivity(intent);

        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.verify_number:
                goToVerifyNumberActivity();
                break;
        }
    }
}
