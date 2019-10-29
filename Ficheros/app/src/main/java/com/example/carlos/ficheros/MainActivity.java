package com.example.carlos.ficheros;

import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button readSD = (Button) findViewById(R.id.btnReadSD);
        Button writeSD = (Button) findViewById(R.id.btnWriteSD);
        Button readInt = (Button) findViewById(R.id.btnReadInt);
        Button writeInt = (Button) findViewById(R.id.btnWriteInt);
        Button raw = (Button) findViewById(R.id.btnRaw);

        final TextView status = (TextView) findViewById(R.id.status);
        final TextView result = (TextView) findViewById(R.id.txtResult);

        writeInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    OutputStreamWriter fout = new OutputStreamWriter(openFileOutput("test_int.txt", Context.MODE_PRIVATE));
                    fout.write("Funciona!!! :)");
                    fout.close();

                    status.setText("OK");
                    status.setTextColor(Color.GREEN);

                }catch (Exception e){

                    status.setText("Error");
                    status.setTextColor(Color.RED);
                    result.setText("Error: "+ e.getMessage());
                    result.setTextColor(Color.RED);
                }

            }
        });

        readInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BufferedReader fin = new BufferedReader(new InputStreamReader(openFileInput("test_int.txt")));
                    result.setText(fin.readLine());
                    fin.close();

                    status.setText("OK");
                    status.setTextColor(Color.GREEN);

                }catch (Exception e){

                    status.setText("Error");
                    status.setTextColor(Color.RED);
                    result.setText("Error: "+ e.getMessage());
                    result.setTextColor(Color.RED);
                }

            }
        });

        raw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputStream fraw = getResources().openRawResource(R.raw.raw_test);

                    BufferedReader fin = new BufferedReader(new InputStreamReader(fraw));
                    result.setText(fin.readLine());
                    fin.close();
                    fraw.close();

                    status.setText("OK");
                    status.setTextColor(Color.GREEN);

                }catch (Exception e){

                    status.setText("Error");
                    status.setTextColor(Color.RED);
                    result.setText("Error: "+ e.getMessage());
                    result.setTextColor(Color.RED);
                }

            }
        });

        writeSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String sd_status = Environment.getExternalStorageState();

                    switch (sd_status){
                        case Environment.MEDIA_MOUNTED:

                            File ruta_sd_global = Environment.getExternalStorageDirectory();
                            File file = new File(ruta_sd_global.getAbsolutePath(),"prueba_sd.txt");

                            OutputStreamWriter fout = new OutputStreamWriter(new FileOutputStream(file));
                            fout.write("Texto Prueba!!! :)");
                            fout.close();

                            status.setText("OK");
                            status.setTextColor(Color.GREEN);
                            break;

                        case Environment.MEDIA_MOUNTED_READ_ONLY:

                            status.setText("Error");
                            status.setTextColor(Color.BLUE);
                            result.setText("Targeta SD montada en solo lectura!");
                            result.setTextColor(Color.BLUE);
                            break;
                    }


                }catch (Exception e){

                    status.setText("Error");
                    status.setTextColor(Color.RED);
                    result.setText("Error: "+ e.getMessage());
                    result.setTextColor(Color.RED);
                }

            }
        });

        readSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String sd_status = Environment.getExternalStorageState();

                    switch (sd_status){
                        case Environment.MEDIA_MOUNTED:

                            File ruta_sd_global = Environment.getExternalStorageDirectory();
                            File file = new File(ruta_sd_global.getAbsolutePath(),"prueba_sd.txt");

                            BufferedReader fin = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                            result.setText(fin.readLine());
                            fin.close();

                            status.setText("OK");
                            status.setTextColor(Color.GREEN);
                            break;

                        case Environment.MEDIA_UNMOUNTED:

                            status.setText("Error");
                            status.setTextColor(Color.BLUE);
                            result.setText("No se encuentra ninguna targeta SD!");
                            result.setTextColor(Color.BLUE);
                            break;
                    }


                }catch (Exception e){

                    status.setText("Error");
                    status.setTextColor(Color.RED);
                    result.setText("Error: "+ e.getMessage());
                    result.setTextColor(Color.RED);
                }

            }
        });

    }//oncreate
}
