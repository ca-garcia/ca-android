package com.example.carlos.evalproyect;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
        Product[] arr = new Product[cursor.getCount()];
        int pos=0;

        if (cursor.moveToFirst()) {

//            products = new ArrayList<>(cursor.getCount());
            String name;
            String model;
            String item;

            int colName = cursor.getColumnIndex(ProductsProvider.Products.COL_NAME);
            int colModel = cursor.getColumnIndex(ProductsProvider.Products.COL_MODEL);
            int colItem = cursor.getColumnIndex(ProductsProvider.Products.COL_ITEM);

            do {
                name = cursor.getString(colName);
                model = cursor.getString(colModel);
                item = cursor.getString(colItem);

                Product elem = new Product();
                elem.setName(name);
                elem.setModel(model);
                elem.setItem(item);
                products.add(elem);

                //Array
                arr[pos] = elem;
                pos++;

//				txtResultados.append(nombre + " - " + telefono + " - " + email + "\n");

            } while (cursor.moveToNext());
        }

        //Fill the List with subitems
        AdapterCursorList adapter = new AdapterCursorList(this,cursor,products);
//        AdapterExampleList exampleList = new AdapterExampleList(this,arr);

        ListView lstExamples = (ListView)findViewById(R.id.listProd);
        lstExamples.setAdapter(adapter);
//        lstExamples.setAdapter(exampleList);





        //-----------------------------------------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

                        if (cursor.moveToFirst()) {

                            String name;
                            String model;
                            String itemnumb;

                            int colName = cursor.getColumnIndex(ProductsProvider.Products.COL_NAME);
                            int colModel = cursor.getColumnIndex(ProductsProvider.Products.COL_MODEL);
                            int colItem = cursor.getColumnIndex(ProductsProvider.Products.COL_ITEM);

                            do {
                                name = cursor.getString(colName);
                                model = cursor.getString(colModel);
                                itemnumb = cursor.getString(colItem);

                                fout.write(name + " - " + model + " - " + itemnumb + "\n");
//                                txtResultados.append(name + " - " + model + " - " + itemnumb + "\n");

                            } while (cursor.moveToNext());
                        }

                        fout.close();


                        Toast.makeText(this,"Base de Datos exportada correctamente!",Toast.LENGTH_LONG).show();
                        break;

                    case Environment.MEDIA_MOUNTED_READ_ONLY:

                        Toast.makeText(this,"Targeta SD montada en solo lectura!",Toast.LENGTH_LONG).show();
                        break;
                }

            }catch (Exception e){

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
}
