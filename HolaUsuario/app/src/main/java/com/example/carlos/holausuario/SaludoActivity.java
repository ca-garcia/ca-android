package com.example.carlos.holausuario;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SaludoActivity extends AppCompatActivity {

    TextView txtSaludo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saludo);

        txtSaludo = (TextView) findViewById(R.id.txtSaludo);
        Bundle b = this.getIntent().getExtras();

        txtSaludo.setText("Hello "+ b.getString("Name"));
    }
}
