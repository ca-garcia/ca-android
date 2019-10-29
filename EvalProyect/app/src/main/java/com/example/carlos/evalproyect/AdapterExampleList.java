package com.example.carlos.evalproyect;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdapterExampleList extends ArrayAdapter<Product> {
	
	Activity context;	
	
	private static Product[] datos =
			new Product[]{
			new Product("1","Titulo 8", "Subtitulo largo 8",""),
			new Product("2","Titulo 9", "Subtitulo largo 9",""),
			new Product("3","Titulo 10", "Subtitulo largo 10","")
			};
	
	AdapterExampleList(Activity context,Product[] arr) {//constructor
		super(context, R.layout.listview_item, datos);
		this.context = context;
		datos = arr;
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
			lblTitulo.setText(datos[position].getName());
			
			TextView lblSubtitulo = (TextView)item.findViewById(R.id.txtSubtitle);
			lblSubtitulo.setText(datos[position].getModel());
			
//			TextView lblGrupo = (TextView)item.findViewById(R.id.ex_group);
//			lblGrupo.setText(datos[position].getGroup());
			
//------------------------ List Optimization ---------------------------------------
			
//			View item = convertView;
//			ViewHolder holder;
//			
//			if(item == null)
//			{
//			LayoutInflater inflater = context.getLayoutInflater();
//			item = inflater.inflate(R.layout.listitem_subitem, null);
//			holder = new ViewHolder();
//			holder.titulo = (TextView)item.findViewById(R.id.LblTitulo);
//			holder.subtitulo = (TextView)item.findViewById(R.id.LblSubTitulo);
//			item.setTag(holder);
//			}
//			else
//			{
//			holder = (ViewHolder)item.getTag();
//			}
//			
//			holder.titulo.setText(datos[position].getTitulo());
//			holder.subtitulo.setText(datos[position].getSubtitulo());
//			
			
			return(item);
		}
		


}

//class ViewHolder {
//	TextView titulo;
//	TextView subtitulo;
//	}