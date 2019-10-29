package com.example.carlos.contentprovideruse;
 
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
 
public class ClientesSqliteHelper extends SQLiteOpenHelper {
 
    //Sentencia SQL para crear la tabla de Clientes
    String sqlCreate = "CREATE TABLE Clientes " + 
                       "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
    		           " nombre TEXT, " + 
    		           " telefono TEXT, " +
    		           " email TEXT )";
 
    public ClientesSqliteHelper(Context contexto, String nombre,CursorFactory factory, int version) {
    	
        super(contexto, nombre, factory, version);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creacion de la tabla
        db.execSQL(sqlCreate);
        
        //Insertamos 5 clientes de ejemplo
        for(int i=1; i<=15; i++)
        {
            //Generamos los datos de muestra
            String nombre = "Cliente" + i;
            String telefono = "900-123-00" + i;
            String email = "email" + i + "@mail.com";

            //Insertamos los datos en la tabla Clientes
            db.execSQL("INSERT INTO Clientes (nombre, telefono, email) " +
                       "VALUES ('" + nombre + "', '" + telefono +"', '" + email + "')");
        }
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //NOTA: Por simplicidad del ejemplo aqui utilizamos directamente la opcion de
        //      eliminar la tabla anterior y crearla de nuevo vacia con el nuevo formato.
        //      Sin embargo lo normal sera que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este metodo deberia ser mas elaborado.
 
        //Se elimina la version anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Clientes");
 
        //Se crea la nueva version de la tabla
        db.execSQL(sqlCreate);
    }
}
