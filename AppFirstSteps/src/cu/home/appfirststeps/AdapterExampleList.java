package cu.home.appfirststeps;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdapterExampleList extends ArrayAdapter<Example> {
	
	Activity context;	
	
	private static Example[] datos =
			new Example[]{
			new Example("Hello World Avanzado", "Hello World clásico pero introduciendo el nombre del usuario y pasando datos entre pantallas","Diseño"),
			new Example("Controles personalizados", "Extender el control TextView agregándole un contador de caracteres","Diseño"),
			new Example("Listado con título y subtítulo", "Listado de opciones con subtítulo de caracterización y Menú Contextual","Diseño"),
			new Example("Pestañas de contenido", "Pestañas comunes para agrupar diferentes contenidos en una misma pantalla","Diseño"),
			new Example("Ejemplo SQLite", "Ejemplo utilizando SQLite donde se crean, se insertan, modifican y eliminan elementos en la base de datos","Base de Datos"),
			new Example("Pantalla de Preferencias ", "Preferencias de la aplicación autogeneradas a traves de archivos xml predefinidos", "Preferencias"),
			new Example("Proveedor de Localización", "Subtítulo largo 7","Localización"),
//			new Example("Título 8", "Subtítulo largo 8"),
//			new Example("Título 9", "Subtítulo largo 9"),
//			new Example("Título 10", "Subtítulo largo 10")
			};
	
	AdapterExampleList(Activity context) {//constructor
		super(context, R.layout.example_listitem, datos);
		this.context = context;
		}
	
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View item = convertView;
			
			if(item == null)
			{
				LayoutInflater inflater = context.getLayoutInflater();
				item = inflater.inflate(R.layout.example_listitem, null);
			}
			
			TextView lblTitulo = (TextView)item.findViewById(R.id.ex_title);
			lblTitulo.setText(datos[position].getTitle());
			
			TextView lblSubtitulo = (TextView)item.findViewById(R.id.ex_subtitle);
			lblSubtitulo.setText(datos[position].getSubtitle());
			
			TextView lblGrupo = (TextView)item.findViewById(R.id.ex_group);
			lblGrupo.setText(datos[position].getGroup());
			
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