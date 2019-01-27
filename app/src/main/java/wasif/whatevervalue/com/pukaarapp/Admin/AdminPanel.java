package wasif.whatevervalue.com.pukaarapp.Admin;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import wasif.whatevervalue.com.pukaarapp.R;

public class AdminPanel extends AppCompatActivity {

    private static final String TAG = "AdminPanel";

    Button therapistList;
    final int PERMISSION_REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        therapistList=findViewById(R.id.therapistList);

        therapistList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAdminActivity();
            }
        });

    }

    public void goToAdminActivity(){
        Intent intent= new Intent(this,AdminActivity.class);
        startActivity(intent);
    }

}
