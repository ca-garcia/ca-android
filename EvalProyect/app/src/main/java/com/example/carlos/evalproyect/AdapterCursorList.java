package com.example.carlos.evalproyect;

import android.app.Activity;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterCursorList extends ArrayAdapter<Product> {
	
	Activity context;
//	Product[] products;
//	ArrayList<Product> products;
	static ArrayList<Product> products;

//	private static Example[] datos =
//			new Example[]{
////			new Example("Title", "Subtitle largo"),
////			new Example("Title", "Subtitle largo")
//			};
	
	AdapterCursorList(Activity context, Cursor data,  ArrayList<Product> products2) {//constructor
		super(context, R.layout.listview_item,products2);
		this.context = context;

//		products = new ArrayList<>(data.getCount());
		this.products = products2;

		}//constructor

		public View getView(int position, View convertView, ViewGroup parent)
		{
			View item = convertView;

			if(item == null)
			{
				LayoutInflater inflater = context.getLayoutInflater();
				item = inflater.inflate(R.layout.listview_item, null);
			}

//			LayoutInflater inflater = context.getLayoutInflater();
//			View item = inflater.inflate(R.layout.listview_item, null);
			
			TextView lblTitulo = (TextView)item.findViewById(R.id.txtTitle);
			lblTitulo.setText(products.get(position).getName().toString());
			
			TextView lblSubtitulo = (TextView)item.findViewById(R.id.txtSubtitle);
			lblSubtitulo.setText("Item #: "+products.get(position).getItem());
			
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