package wasif.whatevervalue.com.pukaarapp.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import wasif.whatevervalue.com.pukaarapp.Login.IntroActivity;
import wasif.whatevervalue.com.pukaarapp.Login.questionActivity1;
import wasif.whatevervalue.com.pukaarapp.R;

public class splash extends Activity {

    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(splash.this,IntroActivity.class);
                splash.this.startActivity(mainIntent);
                splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);    }

    }