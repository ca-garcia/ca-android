package com.example.carlos.evalproyect;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class AboutActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.about);
	    
		AdapterListAbout adapter = new AdapterListAbout(this);
		ListView lstAbout = (ListView)findViewById(R.id.aboutList);
		lstAbout.setAdapter(adapter);
		
	}

}
