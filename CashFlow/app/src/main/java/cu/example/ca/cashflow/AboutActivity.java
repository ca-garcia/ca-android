package cu.example.ca.cashflow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.about);
//	    setContentView(R.layout.activity_about2);

		//Asignamos el toolbar del MainActivity
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		//Para mostrar el boton de atras(Contando con lo q lleva el AndroidManifest)
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		//List with subitems
		final Titular[] data =
				new Titular[]{
						new Titular("Acerca de esta aplicación:", "Esta aplicación fue desarrollada " +
									"como proyecto personal para el uso exclusivo de nuestro grupo de trabajo, con el único propósito " +
									"de facilitar y hacer más eficiente nuestro trabajo día a día. Esta prohibida su venta y/o distribución " +
									"sin previo acuerdo."),
						new Titular("Autor:", "Carlos Alberto García Brizuela"),
						new Titular("Contactar", "carlosalbertogarcia.b@gmail.com"),
						new Titular("Legal Information", "Copyright 2017 Heyner Group. All Rights Reserved."),
						new Titular("",""),//para q se vea la linea divisora debajo del elem anterior.
				};

		ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_activated_2,android.R.id.text1, data) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent);
				TextView text1 = (TextView) view.findViewById(android.R.id.text1);
				TextView text2 = (TextView) view.findViewById(android.R.id.text2);

				text1.setText(data[position].getTitulo());
				text2.setText(data[position].getSubtitulo());
				return view;
			}
		};
//		ArrayAdapter<Titular> adapter = new ArrayAdapter<Titular>(this,android.R.layout.two_line_list_item,data);
		ListView lstAbout = (ListView)findViewById(R.id.aboutList);
		lstAbout.setAdapter(adapter);
		
	}

}
