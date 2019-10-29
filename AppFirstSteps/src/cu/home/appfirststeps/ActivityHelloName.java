package cu.home.appfirststeps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ActivityHelloName extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.hello_name);
	
	    // TODO Auto-generated method stub
	    
	    final EditText txtNombre = (EditText)findViewById(R.id.txtNombre);
	    final Button btnHola = (Button)findViewById(R.id.btnHola);
	    final Button btnHome = (Button)findViewById(R.id.btnHome);
	    
      Bundle bundle = getIntent().getExtras();
      if(bundle != null)
      {
      	String name = bundle.getString("NOMBRE");
      	if(name != "")
      		txtNombre.setText(name);
      }
      
      
      btnHola.setOnClickListener(new OnClickListener() {
//      	@Override
    	  public void onClick(View arg0) {
    		  
    		  Intent intent = new Intent(ActivityHelloName.this, ActivityFrmMensaje.class);    		  
    		  Bundle bundle = new Bundle();
    		  bundle.putString("NOMBRE", txtNombre.getText().toString());
    		  intent.putExtras(bundle);
    		  startActivity(intent);
//    	      	HelloNameActivity.this.finish();
    		  
    	  }
      });
      
      btnHome.setOnClickListener(new OnClickListener() {
//      	@Override
      	public void onClick(View arg0) {
      		
      	Intent intent = new Intent(ActivityHelloName.this, ActivityAppFirstSteps.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //It makes sure that you cannot go back to the previous activity with the BACK button.
      	startActivity(intent);
      	ActivityHelloName.this.finish();
      	}
      	});
      
      
      
	}//onCreate

}
