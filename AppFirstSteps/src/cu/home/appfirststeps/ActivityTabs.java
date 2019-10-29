package cu.home.appfirststeps;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;

public class ActivityTabs extends Activity {

//	public TabsActivity() {
//		// TODO Auto-generated constructor stub
//	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.tabs);
	
	    // TODO Auto-generated method stub
	    
	    Resources res = getResources();
	    TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
//	    TabHost tabs=(TabHost)findViewById(R.id.tabHost1);//se usa este en dependencia de como se define el @id.
	    tabs.setup();
//	    
	    TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
	    spec.setContent(R.id.tab1);
	    spec.setIndicator("TAB-1", res.getDrawable(android.R.drawable.ic_dialog_alert));
//	    spec.setIndicator("TAB-1", res.getDrawable(R.drawable.ic_launcher));
	    tabs.addTab(spec);
	    
	    spec=tabs.newTabSpec("mitab2");
	    spec.setContent(R.id.tab2);
	    spec.setIndicator("TAB-2", res.getDrawable(android.R.drawable.ic_dialog_map));
	    tabs.addTab(spec);
	    
	    tabs.setCurrentTab(0);
	    
	    tabs.setOnTabChangedListener(new OnTabChangeListener() {
//	    	@Override
	    	public void onTabChanged(String tabId) {
	    		
//	    	Log.i("AndroidTabsDemo", "Pulsada pestaña: " + tabId);	    		
	    		Toast.makeText(ActivityTabs.this,"Pulsada pestaña: " + tabId, 500).show();
	    	
	    	}
	    	});
	 
	    
	}
}
