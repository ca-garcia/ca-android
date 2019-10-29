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
		products = products2;

//		this.data = datos;
//		products = new ArrayList<>();
//		products.add(0,new Product("Name1","Model1","ItemNum1"));
//		products.add(1,new Product("Name12","Model12","ItemNum12"));


//		if (data.moveToFirst()) {
//			String name;
//			String model;
//			String item;
//
//			int colName = data.getColumnIndex(ProductsProvider.Products.COL_NAME);
//			int colModel = data.getColumnIndex(ProductsProvider.Products.COL_MODEL);
//			int colItem = data.getColumnIndex(ProductsProvider.Products.COL_ITEM);
//
//			do {
//				name = data.getString(colName);
//				model = data.getString(colModel);
//				item = data.getString(colItem);
//
//				Product elem = new Product();
//				elem.setName(name);
//				elem.setModel(model);
//				elem.setItem(item);
//				products.add(elem);
////				txtResultados.append(nombre + " - " + telefono + " - " + email + "\n");
//
//			} while (data.moveToNext());
//		}

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