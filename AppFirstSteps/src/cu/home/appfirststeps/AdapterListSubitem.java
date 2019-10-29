package cu.home.appfirststeps;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterListSubitem extends ArrayAdapter<Titular> {
	
	Activity context;	

		private static Titular[] datos =
				new Titular[]{
				new Titular("Título 1", "Subtítulo largo 1"),
				new Titular("Título 2", "Subtítulo largo 2"),
				new Titular("Título 3", "Subtítulo largo 3"),
				new Titular("Título 4", "Subtítulo largo 4"),
				new Titular("Título 5", "Subtítulo largo 5"),
				new Titular("Título 6", "Subtítulo largo 6"),
				new Titular("Título 7", "Subtítulo largo 7"),
				new Titular("Título 8", "Subtítulo largo 8"),
				new Titular("Título 9", "Subtítulo largo 9"),
				new Titular("Título 10", "Subtítulo largo 10")};
	
	AdapterListSubitem(Activity context) {//constructor
		super(context, R.layout.listitem_subitem, datos);
		this.context = context;
		}
	
		public View getView(int position, View convertView, ViewGroup parent)
		{
//			View item = convertView;
//			
//			if(item == null)
//			{
//				LayoutInflater inflater = context.getLayoutInflater();
//				item = inflater.inflate(R.layout.listitem_subitem, null);
//
//			}
//			
//			TextView lblTitulo = (TextView)item.findViewById(R.id.LblTitulo);
//			lblTitulo.setText(datos[position].getTitulo());
//			
//			TextView lblSubtitulo = (TextView)item.findViewById(R.id.LblSubTitulo);
//			lblSubtitulo.setText(datos[position].getSubtitulo());
			
//------------------------ List Optimization ---------------------------------------
			
			View item = convertView;
			ViewHolder holder;
			
			if(item == null)
			{
			LayoutInflater inflater = context.getLayoutInflater();
			item = inflater.inflate(R.layout.listitem_subitem, null);
			holder = new ViewHolder();
			holder.titulo = (TextView)item.findViewById(R.id.LblTitulo);
			holder.subtitulo = (TextView)item.findViewById(R.id.LblSubTitulo);
			
//			ImageView icon = (ImageView)item.findViewById(R.id.icoList);
//			icon.setImageResource(android.R.drawable.ic_delete);
			
			item.setTag(holder);
			}
			else
			{
			holder = (ViewHolder)item.getTag();
			}
			
			holder.titulo.setText(datos[position].getTitulo());
			holder.subtitulo.setText(datos[position].getSubtitulo());			

			ImageView icon = (ImageView)item.findViewById(R.id.icoList);
			
			switch (position) {//seleccionar el icono del elemento de la lista.
			
			case 0:
				icon.setImageResource(android.R.drawable.ic_menu_info_details);
				break;
			case 1:
				icon.setImageResource(android.R.drawable.ic_menu_compass);
				break;
			case 2:
				icon.setImageResource(android.R.drawable.ic_menu_send);
				break;
			case 3:
				icon.setImageResource(android.R.drawable.ic_menu_mylocation);
				break;
			case 4:
				icon.setImageResource(android.R.drawable.ic_menu_directions);
				break;

			default:
				break;
			}
			
			return(item);
		}
		
//		public ItemClicked getItem(int position){
//
//			return items.get(position);
//			}

}

class ViewHolder {
	TextView titulo;
	TextView subtitulo;
	}