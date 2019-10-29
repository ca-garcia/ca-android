package com.example.carlos.evalproyect;
 
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class ProductsSQLiteHelper extends SQLiteOpenHelper {

    //Sentencia SQL para crear la tabla de Productos
    String sqlCreate = "CREATE TABLE Products " +
                       "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
    		           " name TEXT, " +
    		           " model TEXT, " +
    		           " item TEXT )";

    public ProductsSQLiteHelper(Context contexto, String dbname, CursorFactory factory, int version) {
    	
        super(contexto, dbname, factory, version);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creacion de la tabla
        db.execSQL(sqlCreate);
        
        //Insertamos 15 productos de ejemplo
        for(int i=1; i<=15; i++)
        {
            //Generamos los datos de muestra
            String name = "Producto " + i;
            String model = "KHDT875-A" + i;
//            String item = new StringBuilder().append("2016K").append(i).append("XH").append(i - 1).append("YAB").toString();
            String item = new StringBuilder()
                    .append( Integer.toHexString(i))
                    .append(Integer.toBinaryString(i))
                    .append( Integer.toOctalString(i))
                    .append("YAB").toString();


            //Insertamos los datos en la tabla Products
            db.execSQL("INSERT INTO Products (name, model, item) " +
                       "VALUES ('" + name + "', '" + model +"', '" + item + "')");
        }
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //NOTA: Por simplicidad del ejemplo aqui utilizamos directamente la opcion de
        //      eliminar la tabla anterior y crearla de nuevo vacia con el nuevo formato.
        //      Sin embargo lo normal sera que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este metodo deberia ser mas elaborado.
 
        //Se elimina la version anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Products");
 
        //Se crea la nueva version de la tabla
        db.execSQL(sqlCreate);
    }
}
