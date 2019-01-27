package wasif.whatevervalue.com.pukaarapp.Groupinars;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import wasif.whatevervalue.com.pukaarapp.Goals.GoalsActivity;
import wasif.whatevervalue.com.pukaarapp.R;
import wasif.whatevervalue.com.pukaarapp.Utils.BottomNavigationViewHelper;

public class GroupinarsActivity extends AppCompatActivity {

    private static final String TAG = "GroupinarsActivity";
    private Context mContext=GroupinarsActivity.this;
    private static final int ACTIVITY_NUM = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        setupBottomNavigationView();
    }

    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        //BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, this,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
