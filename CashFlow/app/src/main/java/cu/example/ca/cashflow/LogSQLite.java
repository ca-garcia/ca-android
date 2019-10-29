package cu.example.ca.cashflow;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class LogSQLite extends SQLiteOpenHelper {
	
	public static final String PATH_DB = Environment.getExternalStorageDirectory().getAbsolutePath().concat("/Cashflow");
	public static final String DATABASE = "DBLogs.db";
	public static final String TABLE_LOGS = "Logs";
	public static final String TABLE_AUTOCOMPLETE = "Autocomplete";
	private static final int DB_VERSION = 1;

	//Sentencia SQL para crear la tabla de Usuarios
//	String sqlCreate = "CREATE TABLE Logs (id INTEGER PRIMARY KEY, name VARCHAR(50), phone TEXT)";

	//Sentencia SQL para crear la tabla de Logs
	String sqlCreate = "CREATE TABLE Logs " +
			"(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			" phone TEXT, " +
			" date TEXT, " +
			" item TEXT, " +
			" confirmed INTEGER, " +
			" cashed INTEGER )";

	//Sentencia SQL para crear la tabla de los elementos de autocompletado.
	String sqlCreate2 = "CREATE TABLE Autocomplete " + "(data TEXT )";
	
//	public UserSQLite(Context contexto, String nombre, CursorFactory factory, int version) {
	public LogSQLite(Context context) {
//		super(context, DATABASE, null, DB_VERSION);//Internal memory
		super(context, PATH_DB + File.separator + DATABASE, null, DB_VERSION);//internal sdcard
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Se ejecuta la sentencia SQL de creacion de las tablas
		db.execSQL(sqlCreate);
		db.execSQL(sqlCreate2);

		//Insertar datos de prueba
//		testData(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
		//NOTA: Por simplicidad del ejemplo aqui utilizamos directamente
		// la opcion de eliminar la tabla anterior y crearla de nuevo
		// vacia con el nuevo formato.
		// Sin embargo lo normal sera que haya que migrar datos de la
		// tabla antigua a la nueva, por lo que este metodo deberia
		// ser mas elaborado.
		
		//Se elimina la version anterior de la tabla con todos sus datos
		db.execSQL("DROP TABLE IF EXISTS Logs");
		
		//Se crea la nueva version de la tabla
		db.execSQL(sqlCreate);
	}
	
	public void updateTable(String updSQL) {
		
		this.sqlCreate = updSQL;		
	}
	
	public Log getElement(String id){
		
	      SQLiteDatabase db = this.getReadableDatabase();
//	      Cursor res =  db.rawQuery("select * from Logs where id=" + id + "", null);

        String[] campos = new String[] {"id,phone,date,item,confirmed,cashed"};
        String[] args = new String[] {id};

        Cursor c = db.query(TABLE_LOGS, campos, "id=?", args, null, null, null);//select a log.
//		    Cursor c = db.query(TABLE_LOGS, campos, null, null, null, null, null); //select all logs.
		if (c.moveToFirst()){
            String cid = c.getString(c.getColumnIndex("id"));
	    	String cphone = c.getString(c.getColumnIndex("phone"));
	    	String cdate = c.getString(c.getColumnIndex("date"));
	    	String citem = c.getString(c.getColumnIndex("item"));
            int value = c.getInt(c.getColumnIndex("confirmed"));
            int value2 = c.getInt(c.getColumnIndex("cashed"));
            boolean confirmed = value > 0;
            boolean cashed = value2 > 0 ? true:false;
            Log element = new Log(cid,cphone,cdate,citem,confirmed,cashed);
            return element;
		}
	      return null;
	}
	
	public int numberOfRows(){
		
	      SQLiteDatabase db = this.getReadableDatabase();
	      int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_LOGS);
	      return numRows;
	}
	
	   public boolean insert(String phone, String date, String item)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      ContentValues contentValues = new ContentValues();
	      contentValues.put("phone", phone);
	      contentValues.put("date", date);
	      contentValues.put("item", item);
	      contentValues.put("confirmed", 0);
	      contentValues.put("cashed", 0);
	      db.insert(TABLE_LOGS, null, contentValues);

		   if (!existData(item))//rellenando lista de autocompletado.
			   db.execSQL("INSERT INTO Autocomplete (data) " + "VALUES ('" + item + "')");

		   db.close();
	      return true;
	   }
	   
	   public boolean delete(String id) //(Integer id)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      /*  Returns the number of rows affected if a whereClause is passed in, 0 otherwise.
	       *  To remove all rows and get a count pass "1" as the whereClause.*/
	      int afected_rows = db.delete(TABLE_LOGS,"id = ? ",new String[] { id });
	      
	      return afected_rows > 0 ? true : false;

//	      return db.delete("Usuarios","1",null);//pasar "1" para eliminar todas las filas de la db.
//	      return db.delete("Usuarios","id = ? ",new String[] { Integer.toString(id) });
	   }
	   
	   public boolean update(String id, /*String phone, String date,*/ String item)
	   {
		   if(item != null){
               SQLiteDatabase db = this.getWritableDatabase();
               ContentValues contentValues = new ContentValues();
               contentValues.put("item", item);
//		   if(confirmed != null)
//			   contentValues.put("confirmed", confirmed);
               int num = db.update(TABLE_LOGS, contentValues, "id = ? ", new String[] { id } );
//	            db.update("Usuarios", contentValues, "id = ? ", new String[] { Integer.toString(id) } );

               if (!existData(item))//rellenando lista de autocompletado.
                   db.execSQL("INSERT INTO Autocomplete (data) " + "VALUES ('" + item + "')");

               db.close();
               return num > 0;//lo mismo q num > 0? true : false
           }
           return false;
	   }
	   public boolean confirm(String id, int confirmed)
	   {
		   if(confirmed >= 0) {
			   SQLiteDatabase db = this.getWritableDatabase();
			   ContentValues contentValues = new ContentValues();
			   contentValues.put("confirmed", confirmed);
			   if (db.update(TABLE_LOGS, contentValues, "id=? ", new String[]{id}) > 0)
				   return true;
		   }
	      return false;
	   }
	   public boolean cash(String id, int cashed)
	   {
		   if(cashed >= 0) {
			   SQLiteDatabase db = this.getWritableDatabase();
			   ContentValues contentValues = new ContentValues();
			   contentValues.put("cashed", cashed);
			   if (db.update(TABLE_LOGS, contentValues, "id=? ", new String[]{id}) > 0)
				   return true;
		   }
	      return false;
	   }
	
	public ArrayList<Log> getAllElements()
	{
	      ArrayList<Log> array_list = new ArrayList<>();
	      
	      //hp = new HashMap();//no se para q es. Venia comentariado con el metodo
	      SQLiteDatabase db = this.getReadableDatabase();
//	      Cursor res =  db.rawQuery("select * from Logs order by date DESC", null);
		  Cursor res =  db.rawQuery( "SELECT * FROM Logs ORDER BY date DESC", null );

		res.moveToFirst();
	      
	      while(!res.isAfterLast()){
			  String id = res.getString(res.getColumnIndex("id"));
			  String phone = res.getString(res.getColumnIndex("phone"));
			  String date = res.getString(res.getColumnIndex("date"));
			  String item = res.getString(res.getColumnIndex("item"));
			  int value = res.getInt(res.getColumnIndex("confirmed"));
			  int value2 = res.getInt(res.getColumnIndex("cashed"));
			  boolean confirmed = value > 0;
			  boolean cashed = value2 > 0 ? true:false;
//			  long milli = Long.valueOf(date);
			  Log element = new Log(id, phone, date, item, confirmed, cashed);
			  array_list.add(element);

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
//		    //Recorremos el cursor hasta que no haya mas registros
//			    do {
////				    String id = c.getString(0);
////				    String name = c.getString(1);
//			    	datos.add(c.getString(c.getColumnIndex("name")));
//			    } while(c.moveToNext());
//		    }
//	   		return datos;
	}
	
	public ArrayList<Log> getArrayAdapter()
	{
	      ArrayList<Log> array_list = new ArrayList<>(0);
	      
	      //hp = new HashMap();//no se para q es. Venia comentariado con el metodo
	      SQLiteDatabase db = this.getReadableDatabase();
//	      Cursor res0 =  db.rawQuery( "select * from Logs", null );
        String[] col = new String[]{"id","phone","date","item","confirmed","cashed"};

        // Defines a string to contain the selection clause
        String selectionClause = "date >= ? OR confirmed = 0 OR cashed = 0";
        // Initializes an array to contain selection arguments
        String[] selectionArgs = { String.valueOf(MainActivity.lastMonth()) };

		Cursor res = db.query(TABLE_LOGS, col, selectionClause, selectionArgs, null, null,"date DESC");
//		Cursor res = db.query(TABLE_LOGS, col, selectionClause, selectionArgs, null, null,"id DESC");
//		Cursor res = db.query(TABLE_LOGS, col, null, null, null, null,"id DESC");

		res.moveToFirst();
	      while(!res.isAfterLast()){
			  String id = res.getString(res.getColumnIndex("id"));
			  String phone = res.getString(res.getColumnIndex("phone"));
			  String date = res.getString(res.getColumnIndex("date"));
			  String item = res.getString(res.getColumnIndex("item"));
			  int value = res.getInt(res.getColumnIndex("confirmed"));
			  int value2 = res.getInt(res.getColumnIndex("cashed"));
			  boolean confirmed = value > 0;
			  boolean cashed = value2 > 0 ? true:false;
              long milli = Long.valueOf(date);

//              if (milli >= MainActivity.lastMonth() || !confirmed || !cashed)
              {
                  Log element = new Log(id, phone, date, item, confirmed, cashed);
                  array_list.add(element);
              }
	    	  res.moveToNext();
	      }

//		db.close();
		return array_list;
	}

	public String[] getAutocomplete(){

		String[] arr = new String[]{};
		ArrayList<String> array_list = new ArrayList<>(0);
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res =  db.rawQuery( "select * from Autocomplete", null );

		if (res.moveToFirst()){
			do {
	    	  String data = res.getString(res.getColumnIndex("data"));
	    	  array_list.add(data);
			}while (res.moveToNext());
		}

		return array_list.toArray(arr);
	}

	public boolean existData(String data){

		SQLiteDatabase db = this.getReadableDatabase();
//		Cursor res =  db.rawQuery("select * from Logs where id=" + id + "", null);

		String[] campos = new String[] {"data"};
		String[] args = new String[] {data};

		Cursor c = db.query(TABLE_AUTOCOMPLETE, campos, "data=?", args, null, null, null);//select a log.
//		    Cursor c = db.query(TABLE_LOGS, campos, null, null, null, null, null); //select all logs.

		boolean exist = c.getCount()>0 ? true:false;
		return  exist;
	}

	private void testData(SQLiteDatabase db){

		Calendar calendar = Calendar.getInstance();
//		calendar.getTimeInMillis();

		//Datos de prueba//
		//Insertamos 15 logs de ejemplo.
		for(int i=1; i<=15; i++)
		{
			//Generamos los datos de muestra
			String phone = "+537265416" + i;
//            String date = i + "/8/2016";
			calendar.set(2016, 7, i);// 0->JANUARY 11->DECEMBER
			String date = String.valueOf(calendar.getTimeInMillis());
			String item = i % 2==0 ? "Kit h81 + g1840":"Kit h81 + g3240";

			//Insertamos los datos en la tabla Logs
			db.execSQL("INSERT INTO Logs (phone, date, item, confirmed, cashed) " +
					"VALUES ('" + phone + "', '" + date +"', '" + item + "', '0' , '0')");
		}

		String[] autoList = new String[]{"kit","h81","kit h81+18","kit h81+32"};
		//Insertamos los datos en la tabla Autocomplete
		db.execSQL("INSERT INTO Autocomplete (data) " + "VALUES ('" + autoList[0] + "')");
		db.execSQL("INSERT INTO Autocomplete (data) " + "VALUES ('" + autoList[1] + "')");
	}
	
}//class
