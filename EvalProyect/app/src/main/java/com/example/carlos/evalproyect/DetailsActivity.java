package com.example.carlos.evalproyect;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.os.RemoteException;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class DetailsActivity extends AppCompatActivity {

    TextView edtNewname;
    TextView edtNewmodel;
    TextView edtNewitem;
    Button btnSave;
    Button btnCancel;
    String strid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle extras = getIntent().getExtras();
        strid = extras.getString("_id");

        edtNewname = (TextView) findViewById(R.id.edtNewName);
        edtNewmodel = (TextView) findViewById(R.id.edtNewModel);
        edtNewitem = (TextView) findViewById(R.id.edtNewItem);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        edtNewname.setText(extras.getString("_name"));
//        edtNewname.setFocusable(false);
//        edtNewname.setClickable(false);
//        edtNewname.setEnabled(false);

        edtNewmodel.setText(extras.getString("_model"));
//        edtNewmodel.setFocusable(false);
        edtNewmodel.setClickable(false);

        edtNewitem.setText(extras.getString("_item"));
//        edtNewitem.setFocusable(false);
        edtNewitem.setClickable(false);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentValues values = new ContentValues();

                String name = edtNewname.getText().toString();
                String model = edtNewmodel.getText().toString();
                String itemnum = edtNewitem.getText().toString();

                values.put(ProductsProvider.Products.COL_NAME, name);
                values.put(ProductsProvider.Products.COL_MODEL, model);
                values.put(ProductsProvider.Products.COL_ITEM, itemnum);

                ContentResolver cr = getContentResolver();
//                cr.insert(ProductsProvider.CONTENT_URI, values);
                cr.update(ProductsProvider.CONTENT_URI, values, "_id = " + strid, null);

                btnSave.setVisibility(View.INVISIBLE);
                btnCancel.setVisibility(View.INVISIBLE);
                Snackbar.make(view, "Producto modificado correctamente!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Toast.makeText(DetailsActivity.this, "Producto modificado correctamente!", Toast.LENGTH_LONG).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSave.setVisibility(View.INVISIBLE);
                btnCancel.setVisibility(View.INVISIBLE);
            }
        });

    }//onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prod, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit_Contact) {

            edtNewname.setFocusable(true);
            edtNewname.setClickable(true);
            edtNewname.setEnabled(true);

            edtNewmodel.setFocusable(true);
            edtNewmodel.setClickable(true);

            edtNewitem.setFocusable(true);
            edtNewitem.setClickable(true);

//            btnSave.setEnabled(true);
            btnSave.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);

            Toast.makeText(DetailsActivity.this, "Editable", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.delete_Contact) {

            AlertDialog alert = new AlertDialog.Builder(this).create();
            alert.setTitle("Eliminar Producto");
            alert.setMessage("Está seguro que desea eliminar el producto?");
//        	alert.setIcon(android.R.drawable.ic_dialog_alert);
            alert.setIcon(R.drawable.ic_warning);

            alert.setButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //Cierra el cuadro de dialogo.
                }
            });
            alert.setButton2("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //Elimina el prod.
                    ContentResolver cr = getContentResolver();
                    int result = cr.delete(ProductsProvider.CONTENT_URI, "_id = " + strid, null);
                    if(result >0)
                        Toast.makeText(DetailsActivity.this, "Producto eliminado correctamente!", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(DetailsActivity.this, "Se ha producido un error. No se elimino el producto.", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(DetailsActivity.this, MainActivity.class));
                    DetailsActivity.this.finish();
                }
            });

            alert.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {//para q consulte la db nuevamente y muestre los datos nuevos insertados correctamente.
        super.onBackPressed();

        Intent home = new Intent(DetailsActivity.this,MainActivity.class);
        startActivity(home);
        this.finish();
    }

}//class
