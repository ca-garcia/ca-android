package cu.home.appfirststeps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ActivityFrmMensaje extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frmmensaje);
	
	TextView txtMensaje = (TextView)findViewById(R.id.TxtMensaje);
	Bundle bundle = getIntent().getExtras();
	final String name = bundle.getString("NOMBRE");
	//txtMensaje.setText("Hola " + bundle.getString("NOMBRE"));
	//txtMensaje.setText("Hola " + bundle.getString("NOMBRE") + ". Welcome to your first android app!" );
	txtMensaje.setText("Hola " + name + ". Welcome to your first android app!" );
	
	final Button btnBack = (Button)findViewById(R.id.btnBack);
	
	btnBack.setOnClickListener(new OnClickListener() {
	//	@Override
		public void onClick(View arg0) {
		Intent intent = new Intent(ActivityFrmMensaje.this, ActivityHelloName.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //It makes sure that you cannot go back to the previous activity with the BACK button.
		Bundle bundle = new Bundle();
		bundle.putString("NOMBRE", name);
		intent.putExtras(bundle);
		startActivity(intent);		
		ActivityFrmMensaje.this.finish();//close the Activity
		
		}
		});
	
	
	}//onCreate

}
