package wasif.whatevervalue.com.pukaarapp.Session;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.facebook.FacebookSdk;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import wasif.whatevervalue.com.pukaarapp.More.MoreActivity;
import wasif.whatevervalue.com.pukaarapp.R;
import wasif.whatevervalue.com.pukaarapp.Utils.BottomNavigationViewHelper;
import wasif.whatevervalue.com.pukaarapp.Utils.SectionsPagerAdapter;

public class SessionActivity extends AppCompatActivity  {

    private static final String TAG = "SessionActivity";
    private Context mContext=SessionActivity.this;
    private static final int ACTIVITY_NUM = 0;
    DrawerLayout drawer;

    //widgets
    private ViewPager mViewPager;
    private FrameLayout mFrameLayout;
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        mViewPager = (ViewPager) findViewById(R.id.viewpager_container);
        mFrameLayout = (FrameLayout) findViewById(R.id.container);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relLayoutParent);

        setupViewPager();

        FacebookSdk.sdkInitialize(getApplicationContext());
        Log.d("AppLog", "key:" + FacebookSdk.getApplicationSignature(this));

    }


    /**
     * Responsible for adding the 3 tabs: Settings, Session, Therapist Profile
     */
    private void setupViewPager(){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SettingsFragment()); //index 0
        adapter.addFragment(new wasif.whatevervalue.com.pukaarapp.Session.SessionFragment()); //index 1
        adapter.addFragment(new TherapistFragment()); //index 2
        mViewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_arrow);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_arrow);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_arrow);
    }
}
