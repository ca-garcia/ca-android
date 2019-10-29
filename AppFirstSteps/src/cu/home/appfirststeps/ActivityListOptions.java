package cu.home.appfirststeps;

import android.app.Activity;
//import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityListOptions extends Activity {
	
	ListView list;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.listview);
	
////	Simple items list
//	final String[] datos = new String[]{"Elem1","Elem2","Elem3","Elem4","Elem5"};
//	ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
//	ListView lstOpciones = (ListView)findViewById(R.id.listView1);
//	lstOpciones.setAdapter(adaptador); 
//	
	
//	List with subitems
	
	AdapterListSubitem adaptador2 = new AdapterListSubitem(this);
	ListView lstOpciones2 = (ListView)findViewById(R.id.listView1);
	lstOpciones2.setAdapter(adaptador2);
	
	this.list = lstOpciones2;//para utilizarla en el onCreateContextMenu.
	
	//Asociamos los menús contextuales a los controles
	registerForContextMenu(lstOpciones2);

	
	lstOpciones2.setOnItemClickListener(new OnItemClickListener() {
//		@Override
		public void onItemClick(AdapterView<?> a, View v, int position, long id) {
		//Acciones necesarias al hacer click
			
//			ItemClicked item = adapter2.getItem(position);//no funciona.
			
//			Intent intent = new Intent(ListOptionsActivity.this,AppFirstStepsActivity.class);
//			//based on item add info to intent
//			startActivity(intent);
			
			Toast.makeText(ActivityListOptions.this,"Position Clicked:"+ position, 500).show();
		}
		});
			
	
	}//onCreate
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
		
	super.onCreateContextMenu(menu, v, menuInfo);
	MenuInflater inflater = getMenuInflater();
	
	//Para poder insertar el setHeaderTitle
//	ListSubitemAdapter adaptador = new ListSubitemAdapter(this);
//	ListView lstOpciones = (ListView)findViewById(R.id.listView1);
//	lstOpciones.setAdapter(adaptador);
	
	AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
//	menu.setHeaderTitle(this.list.getAdapter().getItem(info.position).toString());//lista de textos simple.
//	menu.setHeaderTitle("Opciones:");
	menu.setHeaderTitle(((Titular) this.list.getAdapter().getItem(info.position)).getTitulo());//adaptador de tipo clase.
	inflater.inflate(R.menu.context_menu, menu);
	
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		
		switch (item.getItemId()) {
//			case R.id.CtxLblOpc1:
//				lblMensaje.setText("Etiqueta: Opcion 1 pulsada!");
//				return true;
//			case R.id.CtxLblOpc2:
//				lblMensaje.setText("Etiqueta: Opcion 2 pulsada!");
//				return true;
			case R.id.CtxLstOpc1:
				Toast.makeText(ActivityListOptions.this,"Lista[" + info.position + "]: Opcion 1 pulsada!", 500).show();
				return true;
			case R.id.CtxLstOpc2:
				Toast.makeText(ActivityListOptions.this,"Lista[" + info.position + "]: Opcion 2 pulsada!", 500).show();
				return true;
				
			default:
			return super.onContextItemSelected(item);
		}
	}
	
	
}//class