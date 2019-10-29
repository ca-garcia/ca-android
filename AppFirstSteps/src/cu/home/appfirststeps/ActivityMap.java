package cu.home.appfirststeps;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class ActivityMap extends MapActivity{
	
	private MapView mapa = null;
	private MapController controlMapa = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.map);
	    
	    //Obtenemos una referencia al control MapView
	    mapa = (MapView)findViewById(R.id.mapView);
		
	    //Obtenemos el controlador del mapa
		controlMapa = mapa.getController();
	    
	    //Mostramos los controles de zoom sobre el mapa
	    mapa.setBuiltInZoomControls(true);
	    
		controlMapa.setZoom(5);
	    
	    //Obtenemos el centro del mapa
	    GeoPoint loc = mapa.getMapCenter();
	    
	    //Latitud y Longitud (en microgrados)
	    double lat = loc.getLatitudeE6();
	    double lon = loc.getLongitudeE6();
	    
	    //Nivel de zoom actual
	    int zoom = mapa.getZoomLevel();
	    
	    Toast.makeText(this, "Lat: "+ lat/1E6 + "	Lon: "+ lon/1E6 + "	Zoom: "+ zoom, Toast.LENGTH_LONG).show();

	}//onCreate

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	 @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	//creates a menu inflater
    	MenuInflater inflater = getMenuInflater();
    	//generates a Menu from a menu resource file
    	//R.menu.map_menu represents the ID of the XML resource file
    	inflater.inflate(R.menu.map_menu, menu);
    	return true;
    }
	 
	 @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		
		case R.id.mapSatellite:
			
			if(mapa.isSatellite())
			{				
				item.setChecked(false);
				mapa.setSatellite(false);
			}
			else
			{
				item.setChecked(true);
				mapa.setSatellite(true);
			}
			
			break;
		case R.id.mapCenter:			

			Double latitud = 37.40*1E6;
			Double longitud = -5.99*1E6;
			
//			int latitud2 = (int) (37.40*1E6);
//			int longitud2 = (int) (-5.99*1E6);
			
//			int longi = (int) -122.084095;
//			int lati = (int) 37.422006;

			GeoPoint loc = new GeoPoint(latitud.intValue(), longitud.intValue());
//			GeoPoint loc = new GeoPoint(latitud2, longitud2);
//			GeoPoint loc = new GeoPoint(lati, longi);
			
//			controlMapa.setCenter(loc);
//			controlMapa.setZoom(10);
			
			int zoomActual = mapa.getZoomLevel();			
			controlMapa.animateTo(loc);
			
			if(zoomActual<10)
				for(int i=zoomActual; i<10; i++)
					controlMapa.zoomIn();
			
			
			break;
			
		case R.id.myPosition:
			
			Double lat = 37.40*1E6;
			Double lon = -5.99*1E6;
			
			//Añadimos la capa de marcadores
			List<Overlay> capas = mapa.getOverlays();
			OverlayMap om = new OverlayMap(lat, lon);
			capas.add(om);
			mapa.postInvalidate();
			
			break;

//		default:
//			break;
		}


		return super.onOptionsItemSelected(item);
	}
	 

}
