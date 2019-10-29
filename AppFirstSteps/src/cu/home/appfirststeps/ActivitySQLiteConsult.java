package cu.home.appfirststeps;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ActivitySQLiteConsult extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.sqlite_consult);	    

		final ListView lstOpciones = (ListView)findViewById(R.id.sqliteList);
	    
	  //Abrimos la base de datos 'DBUsuarios' en modo lectura
		UserSQLite usdb = new UserSQLite(this);
		SQLiteDatabase db = usdb.getReadableDatabase();
		
		//Si hemos abierto correctamente la base de datos
		if(db != null)
		{    
//		    ArrayList<String> datos = usdb.getAllUsers();
//			ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
			AdapterSQLite adaptador = new AdapterSQLite(this, usdb.getArrayAdapter());
			lstOpciones.setAdapter(adaptador);
		    
		    db.close();
		}
		else
			Toast.makeText(this,"No se pudo conectar a la Base de Datos", 2500).show();
		
	    
    	lstOpciones.setOnItemClickListener(new OnItemClickListener() {
//    		@Override
    		public void onItemClick(AdapterView<?> a, View v, int position, long id) {
    		//Acciones necesarias al hacer click
    			
    			String dbId = ((Titular)lstOpciones.getAdapter().getItem(position)).getSubtitulo();
//    			Toast.makeText(SQLiteConsultActivity.this,"Position:"+ position + " >> ID:"+ dbId, 500).show();
    			
    			Intent intent = new Intent(ActivitySQLiteConsult.this, ActivitySQLiteData.class);
    			Bundle bundle = new Bundle();
    			bundle.putString("userid", dbId);
    			intent.putExtras(bundle);
    			startActivity(intent);
//    			SQLiteConsultActivity.this.finish();//para q no muestre el listado de datos antiguos luego de editar usuario y presionar Back
    			
    		}
    		}); 
	    
	}//onCreate

}
