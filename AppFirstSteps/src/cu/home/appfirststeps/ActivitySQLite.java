package cu.home.appfirststeps;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ActivitySQLite extends Activity {
	
	UserSQLite usdb; 
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
	    this.getApplication().setTheme(android.R.style.Theme_DeviceDefault_Light);
//	    ActivitySQLite.this.getApplicationContext().setTheme(android.R.style.Theme_DeviceDefault_Light);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);	
	    setContentView(R.layout.sqlite);
	    
//	    this.getApplication().setTheme(android.R.style.Theme_DeviceDefault_Light);
//	    ActivitySQLite.this.getApplicationContext().setTheme(android.R.style.Theme_DeviceDefault_Light);
	    
	    Button btnconnect = (Button) findViewById(R.id.btnConnect);
	    Button btnpopulate = (Button) findViewById(R.id.btnPopulate);
	    Button btnconsult = (Button) findViewById(R.id.btnExamine);
	    Button btninsert = (Button) findViewById(R.id.btnInsert);
	    
		usdb = new UserSQLite(this);
	    
	    btnconnect.setOnClickListener(new OnClickListener() {
//        	@Override
	    	public void onClick(View arg0) {
	    		
	    	    final TextView status = (TextView) findViewById(R.id.connectionStatus);
	    		
	    		//Abrimos la base de datos 'DBUsuarios' en modo escritura
	    		SQLiteDatabase db = usdb.getWritableDatabase();
	    		
	    		//Si hemos abierto correctamente la base de datos
	    		if(db != null)
	    		{
	    			
	    			status.setVisibility(0); //0 visible //1 invisible //2 gone
	    			Toast.makeText(ActivitySQLite.this,"Conexión Exitosa!", 1500).show();
	    			
	    			new CountDownTimer(10000, 1000) //10 second Timer
	    			        {
	    			            public void onTick(long l) 
	    			            {}

	    			            @Override
	    			            public void onFinish() 
	    			            {
	    			            	status.setVisibility(View.INVISIBLE);
	    			            };
	    			        }.start();
	    			
	    			//Cerramos la base de datos
	    			db.close();
	    			
	    		}//if
	    		else
	    		{
	    			status.setText("No se pudo conectar a la Base de Datos");
	    			status.setTextColor(R.color.red_text);
	    		}
	    		
	    		
	    	}
	    });
	    
	    btnpopulate.setOnClickListener(new OnClickListener() {
//        	@Override
	    	public void onClick(View arg0) {
	    		
	    		TextView popolate = (TextView) findViewById(R.id.txtPopulate);
	    		
	    		//Abrimos la base de datos 'DBUsuarios' en modo escritura
	    		SQLiteDatabase db = usdb.getWritableDatabase();
	    		
	    		//Si hemos abierto correctamente la base de datos
	    		if(db != null)
	    		{      			  
	    			
	    			//Insertamos 5 usuarios de ejemplo
	    			for(int i=1; i<=5; i++)
	    			{
	    				//Generamos los datos
//	    				int codigo = i;
	    				String nombre = "Usuario" + i + " Apellido1 Apellido2";
	    				
	    				//Insertamos los datos en la tabla Usuarios
//	    				db.execSQL("INSERT INTO Usuarios (id, name, phone) " + "VALUES (" + codigo + ", '" + nombre +"')");
//	    				db.execSQL("INSERT INTO Usuarios (id, name, phone) VALUES (" + codigo + ", '" + nombre +"','" + "1-800-1234"+ i + "')");
	    				db.execSQL("INSERT INTO Usuarios (name, phone) VALUES ('" + nombre +"','" + "1-800-1234"+ i + "')");
	    			}
	    			
	    			//Cerramos la base de datos
	    			db.close();    			  
	    			
//	    			popolate.setVisibility(0); //0 visible //1 invisible //2 gone
        			  Toast.makeText(ActivitySQLite.this,"Datos insertados satisfactoriamente!", 2500).show();
	    			
	    		}//if
	    		else
	    		{
	    			popolate.setText("No se pudo insertar los datos");
	    			popolate.setTextColor(R.color.red_text);
	    		}
	    		
	    		
	    	}
	    });
	    
	    btnconsult.setOnClickListener(new OnClickListener() {
//        	@Override
	    	public void onClick(View arg0) {
	    		
	    		Intent sqlConsult = new Intent(ActivitySQLite.this,ActivitySQLiteConsult.class);
	    		startActivity(sqlConsult);
	    		
	    	}
	    });
	    
	    btninsert.setOnClickListener(new OnClickListener() {
//        	@Override
        	public void onClick(View arg0) {
        		          		  
//        		  //Abrimos la base de datos 'DBUsuarios' en modo escritura
//        		  SQLiteDatabase db = usdb.getWritableDatabase();      		  
//        		  
//        		  //Si hemos abierto correctamente la base de datos
//        		  if(db != null)
//        			  {    
//
//            		  String sqlUpd = "CREATE TABLE Usuarios (id INTEGER PRIMARY KEY, name TEXT, phone TEXT)";
//            		  usdb.updateTable(sqlUpd);
////            		  usdb.onUpgrade(db, 1, 2);
//        			  
//        			  //Cerramos la base de datos
//        			  db.close();    			  
//
////        			  popolate.setVisibility(0); //0 visible //1 invisible //2 gone
//        			  Toast.makeText(SQLiteActivity.this,"Update Successful!", 1500).show();
//        			  
//        			  }//if
//        		  else
//        		  	{
//        			  Toast.makeText(SQLiteActivity.this,"Update Failed!", 1500).show();
//        		  	}
        		
        		Intent adduser = new Intent(ActivitySQLite.this,ActivitySQLiteAddUser.class);
	    		startActivity(adduser);
        			  

        	}
        	});
	    
	 
	  
	}//onCreate

}
