package wasif.whatevervalue.com.pukaarapp.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


import wasif.whatevervalue.com.pukaarapp.Goals.GoalsActivity;
import wasif.whatevervalue.com.pukaarapp.Groupinars.GroupinarsActivity;
import wasif.whatevervalue.com.pukaarapp.More.MoreActivity;
import wasif.whatevervalue.com.pukaarapp.R;
import wasif.whatevervalue.com.pukaarapp.Schedule.ScheduleActivity;
import wasif.whatevervalue.com.pukaarapp.Session.SessionActivity;

public class BottomNavigationViewHelper {

    private static final String TAG = "BottomNavigationViewHel";

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        Log.d(TAG, "setupBottomNavigationView: setting up bottomNavigationView");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }

    public static void enableNavigation(final Context context, final Activity callingActivity, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.ic_house:
                        Intent intent1 = new Intent(context, SessionActivity.class);//ACTIVITY_NUM = 0
                        context.startActivity(intent1);
                        //callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;

                    case R.id.ic_search1:
                        Intent intent2  = new Intent(context, ScheduleActivity.class);//ACTIVITY_NUM = 1
                        context.startActivity(intent2);
                        //callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;

                    case R.id.ic_circle:
                        Intent intent3 = new Intent(context, GoalsActivity.class);//ACTIVITY_NUM = 2
                        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent3);
                        //callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;

                    case R.id.ic_alert:
                        Intent intent4 = new Intent(context, GroupinarsActivity.class);//ACTIVITY_NUM = 3
                        context.startActivity(intent4);
                        //callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;

                    case R.id.ic_android:
                        Intent intent5 = new Intent(context, MoreActivity.class);//ACTIVITY_NUM = 4
                        context.startActivity(intent5);
                        //callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;
                }


                return false;
            }
        });
    }
}
