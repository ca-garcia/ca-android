package cu.home.appfirststeps;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.EditText;

public class CharCounterEdit extends EditText {

//	public CharCountEdit() {
//		// TODO Auto-generated constructor stub
//	}
	
	public CharCounterEdit(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs,defStyle);
		}
	public CharCounterEdit(Context context, AttributeSet attrs) {
		super(context, attrs);
		}
	public CharCounterEdit(Context context) {
		super(context);
		}
	
	@Override
	public void onDraw(Canvas canvas)
	{
		Paint p1 = new Paint(Paint.ANTI_ALIAS_FLAG);
		p1.setColor(Color.BLACK);
		p1.setStyle(Style.FILL);
		
		Paint p2 = new Paint(Paint.LINEAR_TEXT_FLAG);
		p2.setColor(Color.WHITE);
		p2.setTextSize(14);
		
		//Llamamos al m�todo de la clase base (EditText)
		super.onDraw(canvas);
		
		//Dibujamos el fondo negro del contador
		canvas.drawRect(this.getWidth()-30, 5, this.getWidth()-5, 20, p1);
		
		//Dibujamos el n�mero de caracteres sobre el contador
		canvas.drawText("" + this.getText().toString().length(), this.getWidth()-30, 17, p2);
		
	}
	

}//class
