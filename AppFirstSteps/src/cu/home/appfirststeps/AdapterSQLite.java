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
//			new Titular("T�tulo 1", "Subt�tulo largo 1"),
//			new Titular("T�tulo 2", "Subt�tulo largo 2"),
//			new Titular("T�tulo 3", "Subt�tulo largo 3"),
//			new Titular("T�tulo 4", "Subt�tulo largo 4"),
//			new Titular("T�tulo 5", "Subt�tulo largo 5"),
//			new Titular("T�tulo 6", "Subt�tulo largo 6"),
//			new Titular("T�tulo 7", "Subt�tulo largo 7"),
//			new Titular("T�tulo 8", "Subt�tulo largo 8"),
//			new Titular("T�tulo 9", "Subt�tulo largo 9"),
//			new Titular("T�tulo 10", "Subt�tulo largo 10")};
	
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
