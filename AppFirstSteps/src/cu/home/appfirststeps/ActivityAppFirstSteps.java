package cu.home.appfirststeps;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ActivityAppFirstSteps extends Activity {
    /** Called when the activity is first created. 
     * @return */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//--------------------------------------------------------------------------

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = "Preferences not exist!";
        
        if(pref != null)
        	theme = pref.getString("opcion3","light_dark_bar"); //default value -> light_dark_bar

    	if (theme.matches("dark"))
    	{
    		setTheme(android.R.style.Theme_Holo); // (for Android Built In Theme)			
		}
    	else
    		if(theme.matches("mytheme"))
    		{
    			setTheme(R.style.MyTheme); // (for Custom theme)
    		}
    	   	else
    	   		if(theme.matches("light_dark_bar")) //si no existen las preferencias carga este por defecto.
    	   		{
    	   			setTheme(android.R.style.Theme_Holo_Light_DarkActionBar);
    	   		}
    	
        setContentView(R.layout.main_screen);

//		Toast.makeText(this, theme, Toast.LENGTH_LONG).show();
        
//--------------------------------------------------------------------------
        
//    	Fill the List with subitems
    	AdapterExampleList ex_adapter = new AdapterExampleList(this);
    	ListView lstExamples = (ListView)findViewById(R.id.examplesListView);
    	lstExamples.setAdapter(ex_adapter); 
    	
//        TextView group = (TextView) findViewById(R.id.ex_group);
//        TextView title = (TextView) findViewById(R.id.examplesTextView);
//        TextView subt = (TextView) findViewById(R.id.ex_subtitle);
        
//        group.setTextColor(R.color.gray_text);
//        title.setTextColor(Color.BLACK);
//        subt.setTextColor(R.color.gray_text);
    	
//--------------------------------------------------------------------------
    	
    	lstExamples.setOnItemClickListener(new OnItemClickListener() {
//    		@Override
    		public void onItemClick(AdapterView<?> a, View v, int position, long id) {
    		//Acciones necesarias al hacer click
    			
    			switch (position) {
    			case 0:
    				Intent hello = new Intent(ActivityAppFirstSteps.this,ActivityHelloName.class);
        			startActivity(hello);
        			return;
    			case 1:
    				Intent charc = new Intent(ActivityAppFirstSteps.this,ActivityCharCounter.class);
        			startActivity(charc);
        			return;
    			case 2:
    				Intent lista = new Intent(ActivityAppFirstSteps.this,ActivityListOptions.class);
        			startActivity(lista);
        			return;
    			case 3:
    				Intent tabs = new Intent(ActivityAppFirstSteps.this,ActivityTabs.class);
    				startActivity(tabs);
    				return;
    			case 4:
    				Intent sqlite = new Intent(ActivityAppFirstSteps.this,ActivitySQLite.class);
    				startActivity(sqlite);
    				return;
    			case 5:
    				Intent pref = new Intent(ActivityAppFirstSteps.this,ActivityPreferencesScreen.class);
    				startActivity(pref);
    				ActivityAppFirstSteps.this.finish();
    				return;
    			case 6:
    				Intent loc = new Intent(ActivityAppFirstSteps.this,ActivityLocationProvider.class);
        			startActivity(loc);
        			return;
    				
//    			default:
//    				Toast.makeText(AppFirstStepsActivity.this,"Position Clicked:"+ position, 500).show();
    			}
    			
    		}
    		});     
      

        
    }//onCreate
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	//creates a menu inflater
    	MenuInflater inflater = getMenuInflater();
    	//generates a Menu from a menu resource file
    	//R.menu.main_menu represents the ID of the XML resource file
    	inflater.inflate(R.menu.main_menu, menu);
    	return true;
    }
    
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    { 	
//    	//check selected menu item
//    	// R.id.exit is @+id/exit
//    	if(item.getItemId() == R.id.exit){
//    		//close the Activity
//    		this.finish();
//    		return true;
//    	}
//    	return false;
//    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	AlertDialog alerta = new AlertDialog.Builder(this).create();
    	alerta.setTitle("Dialogo de Alerta");
//    	alerta.setIcon(android.R.drawable.ic_dialog_alert);
    	alerta.setIcon(R.drawable.info);
    	
    	alerta.setButton("Cancel", new DialogInterface.OnClickListener() {
    		
    		public void onClick(DialogInterface dialog, int which) {
    			// TODO Auto-generated method stub
    			//Poner aqui lo que se quiere que se haga cuando se oprima el boton.
    			
    		}
    	});
    	
    	alerta.setButton2("Ok", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//Poner aqui lo que se quiere que se haga cuando se oprima el boton.
				
			}
		});
        
    	switch (item.getItemId()) {
    	
		    case R.id.MnuOpc1:
		    	SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
//		    	String arr = "Opcion 1 " + pref.getBoolean("opcion1", false);
		    	String arr = pref.getAll().entrySet().toString();
		    	alerta.setTitle("Preferencias");
//		    	alerta.setIcon(android.R.drawable.ic_dialog_info);
		    	alerta.setIcon(R.drawable.circled_checkmark);
		    	alerta.setMessage(arr);
		    	alerta.show();
		    	
//		    	Toast.makeText(ActivityAppFirstSteps.this,"Preferencias: " + arr, 2500).show();
//		    	Toast.makeText(ActivityAppFirstSteps.this,"Opción 1 Seleccionada", 500).show();
		    	break;
		    	
		    case R.id.MnuOpc2:
//		    	Toast.makeText(AppFirstStepsActivity.this,"Opción 2 Seleccionada", 500).show();break;
		    	alerta.setMessage("Opción 2 Seleccionada");
		    	alerta.show();
		    	break;
		    	
		    case R.id.MnuOpc3://Open submenu.
//		    	chcedit.setText("Opcion 3 pulsada!");
		    	
		    case R.id.SubMnuOpc1:
		    	Toast.makeText(ActivityAppFirstSteps.this,"Submenu Opción 1 Clicked:", 500).show();
		    	break;
		    	
		    case R.id.SubMnuOpc2:
		    	Toast.makeText(ActivityAppFirstSteps.this,"Submenu Opción 2 Clicked:", 500).show();
		    	break;
		    	
		    case R.id.aboutOpc:
		    	Intent about = new Intent(ActivityAppFirstSteps.this,ActivityAbout.class);
    			startActivity(about);
		    	break;
		    	
		    case R.id.exitOpc:
	    		this.finish();//close the Activity
	    		break;
	    		
		    default:
		    	return super.onOptionsItemSelected(item);
    	}
    	
//    	alerta.show();
    	return true;
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  { 					//se presiona la tecla back
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // do something on back.
        	
        	AlertDialog alerta = new AlertDialog.Builder(this).create();
        	alerta.setTitle("Desea salir de la aplicación?");
        	alerta.setMessage("Está a punto de cerrar la aplicación. Está seguro que desea salir?");
//        	alerta.setIcon(android.R.drawable.ic_dialog_alert);
        	alerta.setIcon(R.drawable.warning);
        	
        	alerta.setButton("No", new DialogInterface.OnClickListener() {
        		
        		public void onClick(DialogInterface dialog, int which) {
        			//Cierra el cuadro de dialogo.
        			
        		}
        	});
        	
        	alerta.setButton2("Si", new DialogInterface.OnClickListener() {
    			
    			public void onClick(DialogInterface dialog, int which) {
    				//Cierra la aplicacion.
    				ActivityAppFirstSteps.this.finish();
    				
    			}
    		});
        	
        	alerta.show();
        	
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    
    
    
}//class