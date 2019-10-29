package com.example.carlos.evalproyect;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import android.widget.AdapterView;
import android.view.LayoutInflater;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Cursor cursor;
    private ListView lstProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Columnas de la tabla a recuperar
        String[] projection = new String[]{
                ProductsProvider.Products._ID,
                ProductsProvider.Products.COL_NAME,
                ProductsProvider.Products.COL_MODEL,
                ProductsProvider.Products.COL_ITEM
        };

        Uri productsURI = ProductsProvider.CONTENT_URI;

        ContentResolver cr = getContentResolver();

        //Hacemos la consulta
        cursor = cr.query(productsURI,
                projection, //Columnas a devolver
                null,       //Condicion de la query
                null,       //Argumentos variables de la query
                null);      //Orden de los resultados

//        if (cursor.moveToFirst()){
//            Toast.makeText(this,cursor.getString(1),Toast.LENGTH_LONG).show();
//            TextView txtResultados = (TextView) findViewById(R.id.txtResult);
//            txtResultados.setText("");
//
//            do {
//				txtResultados.append(cursor.getString(1) + " - " + cursor.getString(2) + " - " + cursor.getString(3) + "\n");
//
//            } while (cursor.moveToNext());
//        }

//        ArrayList products = new ArrayList<>();
        ArrayList<Product> products = new ArrayList<>(cursor.getCount());
//        Product[] arr = new Product[cursor.getCount()];
//        int pos=0;

        if (cursor.moveToFirst()) {

//            products = new ArrayList<>(cursor.getCount());
            String id;
            String name;
            String model;
            String item;

            int colName = cursor.getColumnIndex(ProductsProvider.Products.COL_NAME);
            int colModel = cursor.getColumnIndex(ProductsProvider.Products.COL_MODEL);
            int colItem = cursor.getColumnIndex(ProductsProvider.Products.COL_ITEM);

            do {
                id = cursor.getString(0);//Id
                name = cursor.getString(colName);
                model = cursor.getString(colModel);
                item = cursor.getString(colItem);

                Product elem = new Product(id,name,model,item);
//                elem.setName(name);
//                elem.setModel(model);
//                elem.setItem(item);
                products.add(elem);

                //Array
//                arr[pos] = elem;
//                pos++;

//				txtResultados.append(nombre + " - " + telefono + " - " + email + "\n");

            } while (cursor.moveToNext());
        }

        //Fill the List with subitems
        AdapterCursorList adapter = new AdapterCursorList(this,cursor,products);
//        AdapterExampleList exampleList = new AdapterExampleList(this,arr);

        lstProducts = (ListView)findViewById(R.id.listProd);
        lstProducts.setAdapter(adapter);
//        lstProducts.setAdapter(exampleList);

        lstProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Product prod = (Product) lstProducts.getAdapter().getItem(position);
                Intent i = new Intent(MainActivity.this, DetailsActivity.class);
                i.putExtra("_id", prod.getId());
                i.putExtra("_name", prod.getName());
                i.putExtra("_model", prod.getModel());
                i.putExtra("_item", prod.getItem());
                MainActivity.this.finish();//para q cuando se inserte un dato nuevo no muestre la lista antigua.
                startActivity(i);

            }
        });


        //-----------------------------------------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,NewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //It makes sure that you cannot go back to the previous activity with the BACK button.
                MainActivity.this.finish();//para q cuando se inserte un dato nuevo no muestre la lista antigua.
                startActivity(intent);

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

    }//onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            startActivity(new Intent(MainActivity.this, PreferencesActivity.class));
            return true;
        }
        if (id == R.id.action_new) {

            startActivity(new Intent(MainActivity.this, NewActivity.class));
            MainActivity.this.finish();//para q cuando se inserte un dato nuevo no muestre la lista antigua.
            return true;
        }
        if (id == R.id.action_export) {

            try {
                String sd_status = Environment.getExternalStorageState();

                switch (sd_status){
                    case Environment.MEDIA_MOUNTED:

                        File ruta_sd_global = Environment.getExternalStorageDirectory();
                        File file = new File(ruta_sd_global.getAbsolutePath(),"db_export.txt");
                        OutputStreamWriter fout = new OutputStreamWriter(new FileOutputStream(file));
                        fout.write(" Listado de Productos - Exportación".concat("\n"));
                        fout.write("----------------------------------------------------------------".concat("\n"));

                        if (cursor.moveToFirst()) {

                            String name;
                            String model;
                            String itemnumb;
                            int count = 1;

                            int colName = cursor.getColumnIndex(ProductsProvider.Products.COL_NAME);
                            int colModel = cursor.getColumnIndex(ProductsProvider.Products.COL_MODEL);
                            int colItem = cursor.getColumnIndex(ProductsProvider.Products.COL_ITEM);

                            do {
                                name = cursor.getString(colName);
                                model = cursor.getString(colModel);
                                itemnumb = cursor.getString(colItem);


                                fout.write(new StringBuilder().append(count+"-  ").append(name).append(" - M:").append(model).append(" - #:").append(itemnumb).append("\n").toString());
                                fout.write("----------------------------------------------------------------".concat("\n"));
                                count++;
//                                txtResultados.append(name + " - " + model + " - " + itemnumb + "\n");

                            } while (cursor.moveToNext());
                        }

                        fout.close();


                        Toast.makeText(this,"Base de Datos exportada correctamente!",Toast.LENGTH_LONG).show();
                        break;

                    case Environment.MEDIA_MOUNTED_READ_ONLY:

                        Toast.makeText(this,"Targeta SD montada en modo SOLO LECTURA!",Toast.LENGTH_LONG).show();
                        break;

                    case Environment.MEDIA_REMOVED:

                        Toast.makeText(this,"No se encuentra ninguna Targeta SD!",Toast.LENGTH_LONG).show();
                        break;
                }

            }catch (Exception e){

//                Toast.makeText(this,"Error: "+ e.getMessage(),Toast.LENGTH_LONG).show();
                Toast.makeText(this,"Error: "+ e.getMessage(),Toast.LENGTH_LONG).show();

                 }
            return true;
        }
        if (id == R.id.action_about) {

            startActivity(new Intent(MainActivity.this, AboutActivity.class));
            return true;
        }
        if (id == R.id.action_exit) {

            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {    				//se presiona la tecla back
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // do something on back.

            AlertDialog alerta = new AlertDialog.Builder(this).create();
            alerta.setTitle("Cerrar aplicación?");
            alerta.setMessage("Está a punto de cerrar la aplicación. Seguro que desea salir?");
//        	alerta.setIcon(android.R.drawable.ic_dialog_alert);
            alerta.setIcon(R.drawable.ic_warning);

            alerta.setButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //Cierra el cuadro de dialogo.
                }
            });

            alerta.setButton2("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //Cierra la aplicacion.
                    MainActivity.this.finish();
                }
            });

            alerta.show();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}//class
