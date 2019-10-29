package com.example.carlos.evalproyect;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class NewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        Button add = (Button) findViewById(R.id.btnInsert);
        Button back = (Button) findViewById(R.id.btnBack);
        final TextView edtname = (TextView) findViewById(R.id.edtName);
        final TextView edtmodel = (TextView) findViewById(R.id.edtModel);
        final TextView edtitem = (TextView) findViewById(R.id.edtItem);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentValues values = new ContentValues();

                String name = edtname.getText().toString();
                String model = edtmodel.getText().toString();
                String itemnum = edtitem.getText().toString();

                values.put(ProductsProvider.Products.COL_NAME, name);
                values.put(ProductsProvider.Products.COL_MODEL, model);
                values.put(ProductsProvider.Products.COL_ITEM, itemnum);

                ContentResolver cr = getContentResolver();
                cr.insert(ProductsProvider.CONTENT_URI, values);

                edtname.setText("");
                edtmodel.setText("");
                edtitem.setText("");

                Snackbar.make(view, "Producto insertado correctamente!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                onBackPressed();

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER | Gravity.TOP, 0, 0);
                toast.makeText(NewActivity.this,"Producto insertado correctamente!",Toast.LENGTH_LONG).show();
//                toast.setDuration(Toast.LENGTH_LONG);
//                toast.show();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewActivity.this,MainActivity.class));
                NewActivity.this.finish();
            }
        });
    }

    @Override
    public void onBackPressed() {//para q consulte la db nuevamente y muestre los datos nuevos insertados correctamente.
        super.onBackPressed();

        Intent home = new Intent(this,MainActivity.class);
        startActivity(home);
        this.finish();
    }

}//clas
