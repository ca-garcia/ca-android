package cu.home.appfirststeps;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.widget.Toast;

public class OverlayMap extends Overlay {

	private Double latitud;
	private Double longitud;
	
	public OverlayMap(Double lat, Double lon) {
		// TODO Auto-generated constructor stub
		
		this.latitud = lat;
		this.longitud = lon;
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow)
	{
		Projection projection = mapView.getProjection();
		GeoPoint geoPoint = new GeoPoint(latitud.intValue(), longitud.intValue());
		
		if (shadow == false)
		{
			Point centro = new Point();
			projection.toPixels(geoPoint, centro);
			
			//Definimos el pincel de dibujo
			Paint p = new Paint();
			p.setColor(Color.BLUE);
			p.setTextSize(24);
			p.setUnderlineText(true);
			
			//Marca Ejemplo 1: Círculo y Texto
			canvas.drawCircle(centro.x, centro.y, 15, p);
			canvas.drawText("Sevilla", centro.x+10+10, centro.y+5, p);
			
			//Marca Ejemplo 2: Bitmap
			Bitmap bm = BitmapFactory.decodeResource(mapView.getResources(), R.drawable.red_pin);
//			canvas.drawBitmap(bm, centro.x - bm.getWidth(), centro.y - bm.getHeight(), p);
			canvas.drawBitmap(bm, centro.x - bm.getWidth()/2, centro.y - bm.getHeight(), p);
		}
	}
	
	@Override
	public boolean onTap(GeoPoint point, MapView mapView)
	{
		Context contexto = mapView.getContext();
		String msg = "Lat: " + point.getLatitudeE6()/1E6 + " - " + "Lon: " + point.getLongitudeE6()/1E6 + "	Zoom: " + mapView.getZoomLevel();
		Toast toast = Toast.makeText(contexto, msg, Toast.LENGTH_SHORT);
		toast.show();
		
		return true;
	}
	

}
