package cu.example.ca.cashflow;

import android.app.Activity;
import android.content.Context;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AdapterListSubitem extends ArrayAdapter<Titular> {
	
//	Activity context;
	Titular[] datos;
	
	AdapterListSubitem(Context context, Titular[] data) {//constructor
		super(context, R.layout.listview_dialog_item, data);
//		this.context = context;
		this.datos = data;
		}
	
		public View getView(int position, View convertView, ViewGroup parent)
		{
//			View item = convertView;
//			
//			if(item == null)
//			{
//				LayoutInflater inflater = context.getLayoutInflater();
//				item = inflater.inflate(R.layout.listitem_subitem, null);
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
			Date date = new Date(Long.valueOf(datos[position].getSubtitulo()));
			
			if(item == null)
			{
			LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();			
			item = inflater.inflate(R.layout.listview_dialog_item, null);
			holder = new ViewHolder();
			holder.titulo = (TextView)item.findViewById(R.id.tvphone);
			holder.subtitulo = (TextView)item.findViewById(R.id.tvdate);
			holder.type = (ImageView)item.findViewById(R.id.imgTypeCall);

//			ImageView icon = (ImageView)item.findViewById(R.id.icoList);
//			icon.setImageResource(android.R.drawable.ic_delete);
			
			item.setTag(holder);
			}
			else
			{
			holder = (ViewHolder)item.getTag();
			}
			
			holder.titulo.setText(datos[position].getTitulo());
//			holder.titulo.setText(AdapterLogsList.spaceCountryCode(datos[position].getTitulo()));
//			holder.subtitulo.setText(datos[position].getSubtitulo());
//			holder.subtitulo.setText(new SimpleDateFormat("c dd/MM/yyyy - hh:mm a").format(date));
			holder.subtitulo.setText(AdapterLogsList.printDate(date));//"c d/MM/yy h:mm a"

			int call_type = datos[position].getType();
			switch (call_type) {
				case CallLog.Calls.INCOMING_TYPE://1
					holder.type.setImageResource(android.R.drawable.sym_call_incoming);
					break;
				case CallLog.Calls.OUTGOING_TYPE://2
					holder.type.setImageResource(android.R.drawable.sym_call_outgoing);
					break;
				case CallLog.Calls.MISSED_TYPE://3
					holder.type.setImageResource(android.R.drawable.sym_call_missed);
					break;
				case 5://CANCELED
					holder.type.setImageResource(android.R.drawable.sym_call_incoming);
					break;
				default:
					holder.type.setImageResource(android.R.drawable.sym_action_call);
					break;
			}
			return(item);
		}

}

class ViewHolder {
	TextView titulo;
	TextView subtitulo;
	ImageView type;
	}

class Titular {
	
	private String titulo;
	private String subtitulo;
	private int type = 0;

	public Titular(String tit, String sub){
	titulo = tit;
	subtitulo = sub;
	}
	public Titular(String tit, String sub, int call_type){
	titulo = tit;
	subtitulo = sub;
	type = call_type;
	}
	public String getTitulo(){
	return titulo;
	}
	public String getSubtitulo(){
	return subtitulo;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}