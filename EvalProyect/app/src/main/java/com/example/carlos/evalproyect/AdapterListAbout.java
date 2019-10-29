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
	Titular[] datos;
	
	/*
	 * Comentariado el arreglo 'Titular[] datos' para poder reutilizar la
	 * clase adaptador en cualquier actividad y pasarle la lista creada por 
	 * parametro al constructor. Fue necesario crear el arreglo como atributo 
	 * de la clase para poder utilizarlo en el metodo getView().
	 * 
	 * */
//	final Titular[] datos =
//    		new Titular[]{
//    		new Titular("Acerca de esta aplicación", ""),
//    		new Titular("Autor", "Carlos Alberto Garcia Brizuela"),
//    		new Titular("Contactar", "carlosalbertogarcia.b@gmail.com"),
//    		new Titular("Título 3", "Subtítulo largo 3"),
//    		new Titular("Título 4", "Subtítulo largo 4"),
//    		new Titular("Título 5", "Subtítulo largo 5"),
//    		new Titular("Título 6", "Subtítulo largo 6"),
//    		new Titular("Título 7", "Subtítulo largo 7"),
//    		new Titular("Título 8", "Subtítulo largo 8"),
//    		new Titular("Título 9", "Subtítulo largo 9"),
//    		new Titular("Título 10", "Subtítulo largo 10")
//    		};
	
	AdapterListAbout(Activity context, Titular[] datos) {//constructor
		super(context, R.layout.listview_item, datos);
		this.context = context;
		this.datos = datos;
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