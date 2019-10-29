package com.example.carlos.contentprovideruse;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carlos.contentprovideruse.ClientesProvider.Clientes;

public class MainActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }

    private Button btnInsertar;
    private Button btnConsultar;
    private Button btnEliminar;
    private Button btnLlamadas;
    private TextView txtResultados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Referencias a los controles
        txtResultados = (TextView) findViewById(R.id.TxtResultados);
        btnConsultar = (Button) findViewById(R.id.BtnConsultar);
        btnInsertar = (Button) findViewById(R.id.BtnInsertar);
        btnEliminar = (Button) findViewById(R.id.BtnEliminar);
        btnLlamadas = (Button) findViewById(R.id.BtnLlamadas);

        btnConsultar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //Columnas de la tabla a recuperar
                String[] projection = new String[]{
                        Clientes._ID,
                        Clientes.COL_NOMBRE,
                        Clientes.COL_TELEFONO,
                        Clientes.COL_EMAIL};

                Uri clientesUri = ClientesProvider.CONTENT_URI;

                ContentResolver cr = getContentResolver();

                //Hacemos la consulta
                Cursor cur = cr.query(clientesUri,
                        projection, //Columnas a devolver
                        null,       //Condicion de la query
                        null,       //Argumentos variables de la query
                        null);      //Orden de los resultados

                if (cur.moveToFirst()) {
                    String nombre;
                    String telefono;
                    String email;

                    int colNombre = cur.getColumnIndex(Clientes.COL_NOMBRE);
                    int colTelefono = cur.getColumnIndex(Clientes.COL_TELEFONO);
                    int colEmail = cur.getColumnIndex(Clientes.COL_EMAIL);

                    txtResultados.setText("");

                    do {
                        nombre = cur.getString(colNombre);
                        telefono = cur.getString(colTelefono);
                        email = cur.getString(colEmail);

                        txtResultados.append(nombre + " - " + telefono + " - " + email + "\n");

                    } while (cur.moveToNext());
                }
            }
        });

        btnInsertar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ContentValues values = new ContentValues();

                values.put(Clientes.COL_NOMBRE, "ClienteN");
                values.put(Clientes.COL_TELEFONO, "999111222");
                values.put(Clientes.COL_EMAIL, "nuevo@email.com");

                ContentResolver cr = getContentResolver();

                cr.insert(ClientesProvider.CONTENT_URI, values);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ContentResolver cr = getContentResolver();

                cr.delete(ClientesProvider.CONTENT_URI, Clientes.COL_NOMBRE + " = 'ClienteN'", null);
            }
        });

        btnLlamadas.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String[] projection = new String[]{
                        CallLog.Calls.TYPE,
                        CallLog.Calls.NUMBER};

                Uri llamadasUri = CallLog.Calls.CONTENT_URI;

                ContentResolver cr = getContentResolver();

//                try{
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

//                    Log.e("Log","Problema con los Permisos de la App!");
//                    Toast.makeText(MainActivity.this,"Problema con los Permisos de la App!",Toast.LENGTH_LONG).show();

                    //Toast Personalizado.
//                    Toast toast = Toast.makeText(MainActivity.this, "Problema con los Permisos de la App!", Toast.LENGTH_LONG);
                    Toast toast = new Toast(getApplicationContext());

                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toastLay));

                    TextView text = (TextView) layout.findViewById(R.id.txtToast);
                    text.setText("Problema con los Permisos de la App!");

                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.setGravity(Gravity.CENTER|Gravity.RIGHT,0,0);
                    toast.show();
                    return;
                }

                Cursor cur = cr.query(llamadasUri,
                        projection, //Columnas a devolver
                        null,       //Condicion de la query
                        null,       //Argumentos variables de la query
                        null);      //Orden de los resultados

                    if (cur.moveToFirst())
                    {
                        int tipo;
                        String tipoLlamada = "";
                        String telefono;

                        int colTipo = cur.getColumnIndex(CallLog.Calls.TYPE);
                        int colTelefono = cur.getColumnIndex(CallLog.Calls.NUMBER);

                        txtResultados.setText("");

                        do
                        {

                            tipo = cur.getInt(colTipo);
                            telefono = cur.getString(colTelefono);

                            if(tipo == CallLog.Calls.INCOMING_TYPE)
                                tipoLlamada = "ENTRADA";
                            else if(tipo == CallLog.Calls.OUTGOING_TYPE)
                                tipoLlamada = "SALIDA";
                            else if(tipo == CallLog.Calls.MISSED_TYPE)
                                tipoLlamada = "PERDIDA";

                            txtResultados.append(tipoLlamada + " - " + telefono + "\n");

                        } while (cur.moveToNext());

                    }
//
//                }
// catch (SecurityException sec){
//                    Log.e("Log","Exploto!!");
////                    Toast.makeText("",this,"",Toast.LENGTH_LONG);
//                }


            }
        });
    }


}//class
