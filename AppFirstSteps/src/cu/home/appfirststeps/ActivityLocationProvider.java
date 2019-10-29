package cu.home.appfirststeps;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityLocationProvider extends Activity {
	
	LocationManager locManager;
	LocationListener locListener;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.location);
	    

	    Button btnactive = (Button) findViewById(R.id.btnActiveLoc);
	    final Button btndisable = (Button) findViewById(R.id.btnDeactiveLoc);
	    final Button btnmap = (Button) findViewById(R.id.btnMap);
	    
	    final EditText lat = (EditText) findViewById(R.id.txtLatitude);
	    lat.setFocusable(false);
	    lat.setClickable(false);
        
		final EditText lon = (EditText) findViewById(R.id.txtLongitude);
		lon.setFocusable(false);
		lon.setClickable(false);
        
	    EditText providers = (EditText) findViewById(R.id.txtProviders);
	    providers.setFocusable(false);
	    providers.setClickable(false);
	    
		final TextView provStatus = (TextView) findViewById(R.id.lblStatus);
		provStatus.setText("Provider Status: OUT_OF_SERVICE");
	    
	    if(locListener == null)
	    	btndisable.setEnabled(false);
	    
//	    Criteria req = new Criteria();
//	    req.setAccuracy(Criteria.ACCURACY_FINE);
//	    req.setAltitudeRequired(true);
//	    
//	    //Mejor proveedor por criterio
//	    String mejorProviderCrit = locManager.getBestProvider(req, false);
//	    
//	    //Lista de proveedores por criterio
//	    List<String> listaProvidersCrit = locManager.getProviders(req, false);
	    
	  //Obtenemos una referencia al LocationManager
		locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//		    locManager = (LocationManager)getSystemService(LOCATION_SERVICE);
//	    List<String> listaProviders = locManager.getAllProviders();
	    List<String> listaProviders = locManager.getProviders(true); //only enable providers
	    
	    for (int i = 0; i < listaProviders.size(); i++) {
	    	
	    	providers.append(Integer.toString(i+1) + ": ");
	    	providers.append(listaProviders.get(i)); 
	    	providers.append(" "); 
		}
	    
	    btnactive.setOnClickListener(new OnClickListener() {
//      	@Override
    	  public void onClick(View arg0) {
    		  
    		
  		  //Si el GPS está habilitado
//		    if (locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//		    	Toast.makeText(ActivityLocationProvider.this, "GPS Enable", Toast.LENGTH_LONG).show();
//		    }
//		    else
//		    	Toast.makeText(ActivityLocationProvider.this, "GPS Disable", Toast.LENGTH_LONG).show();
    		  
    		  //Obtenemos la última posición conocida
    		  Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    		  
    		  //Mostramos la última posición conocida
    		  showPosition(loc);
    		  
    		  //Nos registramos para recibir actualizaciones de la posición
    		  locListener = new LocationListener() {
    			  
    			  public void onLocationChanged(Location location) {
    				  showPosition(location);
				  }
				  public void onProviderDisabled(String provider){
					  Toast.makeText(ActivityLocationProvider.this, "GPS Disable", Toast.LENGTH_SHORT).show();
				  }
				  public void onProviderEnabled(String provider){
					  Toast.makeText(ActivityLocationProvider.this, "GPS Enable", Toast.LENGTH_SHORT).show();
				  }
				  public void onStatusChanged(String provider, int status, Bundle extras){
//					  TextView provStatus = (TextView) findViewById(R.id.lblStatus);
					  
					  switch (status) {
//						  case 0:
//							  provStatus.setText("Provider Status: OUT_OF_SERVICE"); break;
						  case 1:
							  provStatus.setText("Provider Status: TEMPORARILY_UNAVAILABLE"); break;
						  case 2:
								provStatus.setText("Provider Status: AVAILABLE"); break;
	
						  default:
							  provStatus.setText("Provider Status: OUT_OF_SERVICE"); break;
					}
					  
				  }
				  
    		  };
    		  
    		  locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, locListener);
    		  btndisable.setEnabled(true);
//    		  
    	  }
      });
	    
	    btndisable.setOnClickListener(new OnClickListener() {
//	    	@Override
	    	public void onClick(View v) {
	    		if (locListener != null)
	    	    	locManager.removeUpdates(locListener);	    		

	    	TextView prec = (TextView) findViewById(R.id.lblPrecision);
			
			lat.setText("");
			lon.setText("");
			prec.setText("Precisión: ");
			btndisable.setEnabled(false);
	    	}
	    	});
	    
	    btnmap.setOnClickListener(new OnClickListener() {
//	    	@Override
	    	public void onClick(View v) {

	    		Intent map = new Intent(getApplicationContext(), ActivityMap.class);
	    		startActivity(map);
	    	}
	    	});
	    
	    
//	    if (listaProviders.get(0) != null) {
//	    
//	    LocationProvider provider = locManager.getProvider(listaProviders.get(0));
////	    int precision = provider.getAccuracy();
////	    boolean obtieneAltitud = provider.supportsAltitude();
////	    int consumoRecursos = provider.getPowerRequirement();
//	    
//
//
//		    Toast.makeText(this, provider.getName(), Toast.LENGTH_LONG);
//		    
//		    TextView lstProv = (TextView) findViewById(R.id.txtProviders);
//		    lstProv.append(provider.getName());
//		}
//	    else {
//	    	Toast.makeText(this, "No providers found!", Toast.LENGTH_LONG);
//		}
	    
	}//onCreate
	
	private void showPosition(Location loc) {		

		EditText lat = (EditText) findViewById(R.id.txtLatitude);
		EditText lon = (EditText) findViewById(R.id.txtLongitude);
		TextView lblPrecision = (TextView) findViewById(R.id.lblPrecision);
		
		if(loc != null)
		{
		lat.setText("Latitud: " + String.valueOf(loc.getLatitude()));
		lon.setText("Longitud: " + String.valueOf(loc.getLongitude()));
		lblPrecision.setText("Precisión: " + String.valueOf(loc.getAccuracy()));
		}
		else
		{
		lat.setText("Latitud: (sin_datos)");
		lon.setText("Longitud: (sin_datos)");
		lblPrecision.setText("Precisión: (sin_datos)");
		}
		}

}
