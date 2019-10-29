package cu.example.ca.cashflow;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AdapterLogsList extends ArrayAdapter<Log> {
	
//	Activity context;
//	LayoutInflater inflater;
	ArrayList<Log> data;

//	private static Log[] datos =
//			new Log[]{
//			new Log("1", "+5372654165","15/8/2016","Kit h81 + g1840"),
//			new Log("2", "+5352727450","15/8/2016","Kit h81 + g3240"),
//			};
	
	AdapterLogsList(Context context,ArrayList<Log> array_list) {//constructor
		super(context, R.layout.listview_item, array_list);
//		this.context = (Activity) context;
//		this.inflater =  inflater;
		this.data = array_list;
		}
	
		public View getView(int position, View convertView, ViewGroup parent)
		{
//			View item = convertView;
//
//			if(item == null)
//			{
////				LayoutInflater inflater = context.getLayoutInflater();
//				LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
//				item = inflater.inflate(R.layout.listview_item, null);
//			}
//
//			if(data.get(position).isCashed())//Opacity
//				item.setAlpha((float) 0.35);
//			else
//				item.setAlpha(1);
//
//			TextView lblTitulo = (TextView)item.findViewById(R.id.tvPhone);
//			lblTitulo.setText(data.get(position).getPhone());
//
//			TextView lblSubtitulo = (TextView)item.findViewById(R.id.tvDate);
//			Date date = new Date(Long.valueOf(data.get(position).getDate()));
////			lblSubtitulo.setText(data.get(position).getDate());
//			lblSubtitulo.setText(new SimpleDateFormat("dd/MM/yyyy - hh:mm a").format(date));
//
//			TextView lblItem = (TextView)item.findViewById(R.id.tvItem);
//			lblItem.setText(data.get(position).getItem());
//
//            ImageView img = (ImageView) item.findViewById(R.id.imgCheck);
//            if(data.get(position).isConfirmed())
//                img.setImageResource(android.R.drawable.checkbox_on_background);
//            else
//                img.setImageResource(android.R.drawable.checkbox_off_background);

			
//------------------------ List Optimization ---------------------------------------
			
			View item = convertView;
			ViewHolderLog holder;
//
			if(item == null)
			{
			LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
			item = inflater.inflate(R.layout.listview_item, null);
			holder = new ViewHolderLog();
			holder.phone = (TextView)item.findViewById(R.id.tvPhone);
			holder.date = (TextView)item.findViewById(R.id.tvDate);
			holder.item = (TextView)item.findViewById(R.id.tvItem);
			holder.check = (ImageView) item.findViewById(R.id.imgCheck);
			item.setTag(holder);
			}
			else
			{
			holder = (ViewHolderLog)item.getTag();
			}

			if(data.get(position).isCashed())//Opacity
				item.setAlpha((float) 0.35);
			else
				item.setAlpha(1);

//			holder.phone.setText(data.get(position).getPhone());
			holder.phone.setText(spaceCountryCode(data.get(position).getPhone()));
//			holder.date.setText(data.get(position).getDate());
			Date longDate = new Date( Long.valueOf(data.get(position).getDate()) );
//			holder.date.setText(new SimpleDateFormat("c d/MM/yy h:mm a").format(longDate));
			holder.date.setText(printDate(longDate));
			holder.item.setText(data.get(position).getItem());

			if(data.get(position).isConfirmed())
				holder.check.setImageResource(android.R.drawable.checkbox_on_background);
			else
				holder.check.setImageResource(android.R.drawable.checkbox_off_background);

//------------------------ List Optimization ---------------------------------------
			return(item);
		}

	public static String spaceCountryCode(String phone){

		String code = phone.substring(0,3).concat(" ");
		String number = phone.substring(3,phone.length());
		return code.concat(number);
	}
	public static String printDate(Date date){
		String result="";
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		calendar.setTime(date);
		int dbyear = calendar.get(Calendar.YEAR);
		int dbmonth = calendar.get(Calendar.MONTH);
		int dbday = calendar.get(Calendar.DAY_OF_MONTH);

		if (year==dbyear && month==dbmonth && day==dbday)
			result = "Hoy" + new SimpleDateFormat(" h:mm a", Locale.US).format(date);
		else {
			if (year == dbyear && month == dbmonth && day == dbday + 1)
				result = "Ayer" + new SimpleDateFormat(" h:mm a", Locale.US).format(date);
			else if ((dbday == 30 || dbday == 31) && (year == dbyear || year == dbyear + 1) && (month == dbmonth + 1 || month == 1) && day == 1)//new month or new year.
				result = "Ayer" + new SimpleDateFormat(" h:mm a", Locale.US).format(date);
			else
				result = new SimpleDateFormat("c d/M/yy  h:mm a", Locale.ENGLISH).format(date);
		}

		return result;
	}
}

class ViewHolderLog {
	TextView phone;
	TextView date;
	TextView item;
	ImageView check;
	}

class Log{

	private String id;
	private String phone;
	private String date;
	private String item;
	private boolean confirmed;
	private boolean cashed;

//	public Log(){}

	public Log(String pId, String pPhone, String pDate, String pItem, boolean pConfirmed, boolean pCashed) {
		id = pId;
		phone = pPhone;
		date = pDate;
		item = pItem;
		confirmed = pConfirmed;
		cashed = pCashed;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public boolean isCashed() {
		return cashed;
	}

	public void setCashed(boolean cashed) {
		this.cashed = cashed;
	}
}