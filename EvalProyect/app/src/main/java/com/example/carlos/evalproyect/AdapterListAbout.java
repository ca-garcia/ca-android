package com.example.carlos.evalproyect;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
//import android.widget.ImageView;
import android.widget.TextView;

public class AdapterListAbout extends ArrayAdapter<Titular> {
	
	Activity context;	
//	Titular[] datos;

	//List with subitems
	static Titular[] datos =
			new Titular[]{
					new Titular("Acerca de esta aplicación", "Esta aplicación fue desarrollada " +
							"como proyecto evaluativo en para el Curso de Android Avanzado de Desoft, con el único propósito de entrenar la programación Android. "
							+ "En ella se muestran los principales componentes y funcionalidades utilizadas durante el curso."),
					new Titular("Autor", "Carlos Alberto García Brizuela"),
					new Titular("Contactar", "carlosalbertogarcia.b@gmail.com"),
					new Titular("",""),//para q se vea la linea divisora debajo del elem anterior.
			};
	
	AdapterListAbout(Activity context) {//constructor
		super(context, R.layout.listview_item, datos);
		this.context = context;
//		this.datos = datos;
		}
	
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View item = convertView;
			
			if(item == null)
			{
				LayoutInflater inflater = context.getLayoutInflater();
				item = inflater.inflate(R.layout.listview_item, null);
			}
			
			TextView lblTitulo = (TextView)item.findViewById(R.id.txtTitle);
			lblTitulo.setText(datos[position].getTitulo());
			
			TextView lblSubtitulo = (TextView)item.findViewById(R.id.txtSubtitle);
			lblSubtitulo.setText(datos[position].getSubtitulo());
			
//			switch (position) {
//			
//			case 0:
//				ImageView icon = (ImageView)item.findViewById(R.id.icoAbout);
//				icon.setImageResource(android.R.drawable.ic_menu_info_details);
//				break;
//			case 1:
//				ImageView auhtor = (ImageView)item.findViewById(R.id.icoAbout);
//				auhtor.setImageResource(android.R.drawable.ic_menu_compass);
//				break;
//			case 2:
//				ImageView contact = (ImageView)item.findViewById(R.id.icoAbout);
//				contact.setImageResource(android.R.drawable.ic_menu_send);
//				break;
//
//			default:
//				break;
//			}			
			
						
			return(item);
		}
		
//		public ItemClicked getItem(int position){
//
//			return items.get(position);
//			}

		
}//class

class Titular {

	private String titulo;
	private String subtitulo;

	public Titular(String tit, String sub){
		titulo = tit;
		subtitulo = sub;
	}
	public String getTitulo(){
		return titulo;
	}
	public String getSubtitulo(){
		return subtitulo;
	}

}