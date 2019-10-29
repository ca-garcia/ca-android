package cu.home.appfirststeps;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class UserSQLite extends SQLiteOpenHelper {
	
	public static final String DATABASE_NAME = "DBUsuarios.db";
	public static final String CONTACTS_TABLE_NAME = "Usuarios";

	//Sentencia SQL para crear la tabla de Usuarios
	String sqlCreate = "CREATE TABLE Usuarios (id INTEGER PRIMARY KEY, name VARCHAR(50), phone TEXT)";
	
//	public UserSQLite(Context contexto, String nombre, CursorFactory factory, int version) {
	public UserSQLite(Context contexto) {
		super(contexto, DATABASE_NAME, null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		//Se ejecuta la sentencia SQL de creación de la tabla
		db.execSQL(sqlCreate);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
		//NOTA: Por simplicidad del ejemplo aquí utilizamos directamente
		// la opción de eliminar la tabla anterior y crearla de nuevo
		// vacía con el nuevo formato.
		// Sin embargo lo normal será que haya que migrar datos de la
		// tabla antigua a la nueva, por lo que este método debería
		// ser más elaborado.
		
		//Se elimina la versión anterior de la tabla con todos sus datos
		db.execSQL("DROP TABLE IF EXISTS Usuarios");
		
		//Se crea la nueva versión de la tabla
		db.execSQL(sqlCreate);
	}
	
	public void updateTable(String updSQL) {
		
		this.sqlCreate = updSQL;		
	}
	
	public Cursor getData(String id){
		
	      SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select * from Usuarios where id="+id+"", null );
	      return res;
	}
	
	public int numberOfRows(){
		
	      SQLiteDatabase db = this.getReadableDatabase();
	      int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
	      return numRows;
	}
	
	   public boolean insertUser(String name, String phone/*, String email, String street,String place*/)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      ContentValues contentValues = new ContentValues();
	      contentValues.put("name", name);
	      contentValues.put("phone", phone);
//	      contentValues.put("email", email);	
//	      contentValues.put("street", street);
//	      contentValues.put("place", place);
	      db.insert("Usuarios", null, contentValues);
	      return true;
	   }
	   
	   public boolean deleteContact(String id) //(Integer id)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      /*  Returns the number of rows affected if a whereClause is passed in, 0 otherwise.
	       *  To remove all rows and get a count pass "1" as the whereClause.*/
	      int afected_rows = db.delete("Usuarios","id = ? ",new String[] { id });
	      
	      return afected_rows > 0 ? true : false;

//	      return db.delete("Usuarios","1",null);//pasar "1" para eliminar todas las filas de la db.
//	      return db.delete("Usuarios","id = ? ",new String[] { Integer.toString(id) });
	   }
	   
	   public boolean updateContact (String id, String name, String phone/*, String email, String street,String place*/)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      ContentValues contentValues = new ContentValues();
	      contentValues.put("name", name);
	      contentValues.put("phone", phone);
//	      contentValues.put("email", email);
//	      contentValues.put("street", street);
//	      contentValues.put("place", place);
	      db.update("Usuarios", contentValues, "id = ? ", new String[] { id } );
//	      db.update("Usuarios", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
	      return true;
	   }
	
	public ArrayList<String> getAllUsers()
	{
	      ArrayList<String> array_list = new ArrayList<String>();
	      
	      //hp = new HashMap();//no se para q es. Venia comentariado con el metodo
	      SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select * from Usuarios", null );
	      res.moveToFirst();
	      
	      while(res.isAfterLast() == false){
	         array_list.add(res.getString(res.getColumnIndex("name")));
	         res.moveToNext();
	      }	      

		   return array_list;
	      //--------------------------------------------------------------------------

//			String[] campos = new String[] {"id, name"};
//		    String[] args = new String[] {"Usuario1"};
		    
//		    Cursor c = db.query("Usuarios", campos, "usuario=?", args, null, null, null);//select a user.
//		    Cursor c = db.query("Usuarios", campos, null, null, null, null, null); //select all users.
		    
//		    ArrayList<String> datos = new ArrayList<String>();
//		    
//		    //Nos aseguramos de que existe al menos un registro
//		    if (c.moveToFirst()) {
//		    //Recorremos el cursor hasta que no haya más registros
//			    do {
////				    String id = c.getString(0);
////				    String name = c.getString(1);
//			    	datos.add(c.getString(c.getColumnIndex("name")));
//			    	
//			    } while(c.moveToNext());
//		    }
//		    
//	   		return datos;
	}
	
	public ArrayList<Titular> getArrayAdapter()
	{
	      ArrayList<Titular> array_list = new ArrayList<Titular>();
	      
	      //hp = new HashMap();//no se para q es. Venia comentariado con el metodo
	      SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select * from Usuarios", null );
	      res.moveToFirst();
	      
	      while(res.isAfterLast() == false){
	    	  String subtitle = res.getString(res.getColumnIndex("id"));
	    	  String title = res.getString(res.getColumnIndex("name"));
	    	  Titular element = new Titular(title, subtitle);
	    	  array_list.add(element);
	    	  res.moveToNext();
	      }	      

		   return array_list;
	}
	
}//class
