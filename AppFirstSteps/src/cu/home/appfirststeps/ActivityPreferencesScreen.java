package cu.home.appfirststeps;

import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

public class ActivityPreferencesScreen extends PreferenceActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);	
	   addPreferencesFromResource(R.xml.preferences);
	   
		
//		Preference listPreference = (Preference) findPreference("opcion3");
		ListPreference listTheme = (ListPreference) findPreference("opcion3");//para cambiar el tema visual de la app
		
		listTheme.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {

//				String textValue = newValue.toString();
//				String currValue = ((ListPreference) preference).getValue();//valor q tenia antes de la nueva seleccion .
//
//			    ListPreference listPreference = (ListPreference) preference;
//			    int index = listPreference.findIndexOfValue(textValue);
//			    CharSequence[] entries = listPreference.getEntries();
			    
			 /* index contains the index of the clicked item
			    textValue is the Selected Value [Text view by user]
			    entries[index] is the Selected Text [text entryValue] */
			    
//			    if(index >= 0)
//		        Toast.makeText(preference.getContext(),"Text:" + entries[index] + " / Value:" + textValue + " / Old value:" + currValue , Toast.LENGTH_LONG).show();
		    
			    Intent act = new Intent(ActivityPreferencesScreen.this,ActivityAppFirstSteps.class);
			    act.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //It makes sure that you cannot go back to the previous activity with the BACK button.
			    ActivityPreferencesScreen.this.finish();
    			startActivity(act);
			    
			    return true;
			}
		
		});
//		
		listTheme.setOnPreferenceClickListener(new OnPreferenceClickListener() {
		             public boolean onPreferenceClick(Preference preference) {
		                 //open browser or intent here
		            	 
//		            	 Toast.makeText(preference.getContext(), preference.getTitle(), Toast.LENGTH_SHORT).show();
		            	 
		            	 return true;
		             }
		         });
		
	}//onCreate
	
////	@Override
//	public boolean onPreferenceClick(Preference preference, Object value)
//	{
//	    String textValue = value.toString();
//	    
//	    Toast.makeText(preference.getContext(), preference.getTitle() +" / "+ textValue, Toast.LENGTH_SHORT).show();
//
//	    return true;
//	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();

		Intent home = new Intent(this,ActivityAppFirstSteps.class);
		startActivity(home);
		this.finish();
	}

}//class
