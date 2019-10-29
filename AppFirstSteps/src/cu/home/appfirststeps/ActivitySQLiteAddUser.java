package cu.home.appfirststeps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivitySQLiteAddUser extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.sqlite_data);
	    
	    Button btnsave = (Button) findViewById(R.id.btnsave);

	    btnsave.setOnClickListener(new OnClickListener() {
//      	@Override
      	public void onClick(View arg0) {
      		
      		EditText name = (EditText)findViewById(R.id.editTextName);
      		EditText phone = (EditText)findViewById(R.id.editTextPhone);
      		
		    UserSQLite usdb = new UserSQLite(ActivitySQLiteAddUser.this);
		    
		    if(usdb.insertUser(name.getText().toString(), phone.getText().toString())){
		    	
		    	Toast.makeText(getApplicationContext(), "Usuario insertado con éxito", Toast.LENGTH_SHORT).show();
		    	Intent intent = new Intent(getApplicationContext(),cu.home.appfirststeps.ActivitySQLiteConsult.class);
	            startActivity(intent);
	            kill_activity();	//para q al oprimir atras no regrese a la actividad de add user con los datos ingresados en los EditText.
		    }
		    else
		    	Toast.makeText(getApplicationContext(), "No se pudo insertar el Usuario", Toast.LENGTH_LONG).show();
             
      	}
      	});
	    
	}//onCreate
	
	void kill_activity()
	{ 
	    this.finish();
	}

}
