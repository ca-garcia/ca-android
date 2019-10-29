package cu.home.appfirststeps;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdapterSQLite extends ArrayAdapter<Titular> {

//	private static Titular[] datos =
//			new Titular[]{
//			new Titular("Título 1", "Subtítulo largo 1"),
//			new Titular("Título 2", "Subtítulo largo 2"),
//			new Titular("Título 3", "Subtítulo largo 3"),
//			new Titular("Título 4", "Subtítulo largo 4"),
//			new Titular("Título 5", "Subtítulo largo 5"),
//			new Titular("Título 6", "Subtítulo largo 6"),
//			new Titular("Título 7", "Subtítulo largo 7"),
//			new Titular("Título 8", "Subtítulo largo 8"),
//			new Titular("Título 9", "Subtítulo largo 9"),
//			new Titular("Título 10", "Subtítulo largo 10")};
	
//	//Abrimos la base de datos 'DBUsuarios' en modo lectura
//		UserSQLite usdb = new UserSQLite(this.getContext());
////		SQLiteDatabase db = usdb.getReadableDatabase();
//	
	Activity context;	
	ArrayList<Titular> datos;
	
	 AdapterSQLite(Activity context, ArrayList<Titular> list) {
		super(context, R.layout.sqlite_item_list, list);
		this.context = context;
		this.datos = list;
	}
	
	 public View getView(int position, View convertView, ViewGroup parent)
		{
			View item = convertView;
			
			if(item == null)
			{
				LayoutInflater inflater = context.getLayoutInflater();
				item = inflater.inflate(R.layout.sqlite_item_list, null);

			}
			
			TextView lblTitulo = (TextView)item.findViewById(R.id.sqliteTitle);
			lblTitulo.setText(datos.get(position).getTitulo());
			
//			TextView lblId = (TextView)item.findViewById(R.id.sqliteId);
//			lblId.setText(datos.get(position).getSubtitulo());
		 
			return(item);
		}

}
