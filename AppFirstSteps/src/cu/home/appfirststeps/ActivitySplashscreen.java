package cu.home.appfirststeps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class ActivitySplashscreen extends Activity {
	
	 /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);	
	    setContentView(R.layout.splashscreen);

        /* New Handler to start the Menu-Activity 
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
//            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(ActivitySplashscreen.this,ActivityAppFirstSteps.class);
                ActivitySplashscreen.this.startActivity(mainIntent);
                ActivitySplashscreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
        
	}//onCreate

}
