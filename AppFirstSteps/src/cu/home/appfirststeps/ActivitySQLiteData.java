package cu.home.appfirststeps;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivitySQLiteData extends Activity {
	
    EditText name;
    EditText phone;
    EditText email;
    EditText street;
    EditText place;

    UserSQLite usdb = new UserSQLite(this);
    String strid = "";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.sqlite_data);
	    
	    name = (EditText) findViewById(R.id.editTextName);
	    phone = (EditText) findViewById(R.id.editTextPhone);
	    email = (EditText) findViewById(R.id.editTextStreet);
	    street = (EditText) findViewById(R.id.editTextEmail);
	    place = (EditText) findViewById(R.id.editTextCity);
	    
	    Button btnEditUser = (Button) findViewById(R.id.btnsave);
	
	    Bundle extras = getIntent().getExtras();
	    
	    if (extras !=null) {
	    	
	    	strid = extras.getString("userid");
//		    int intId = Integer.parseInt(strid);	//String to int
		      
		    Cursor user = usdb.getData(strid);
		    user.moveToFirst();
		    
		    name.setText(user.getString(user.getColumnIndex("name")));
		    name.setFocusable(false);
            name.setClickable(false);
            
		    phone.setText(user.getString(user.getColumnIndex("phone")));
            phone.setFocusable(false); 
            phone.setClickable(false);
            
		    email.setText("**********");
            email.setFocusable(false);
            email.setClickable(false);
            
		    street.setText("**********");
            street.setFocusable(false); 
            street.setClickable(false);
            
		    place.setText("**********");
            place.setFocusable(false);
            place.setClickable(false);
		    
		    if (!user.isClosed()) 
	        {
		    	user.close();
	        }
		    
		    Button b = (Button)findViewById(R.id.btnsave);
            b.setVisibility(View.INVISIBLE);
			
		}
	    
	    btnEditUser.setOnClickListener(new OnClickListener() {
//      	@Override
	      	public void onClick(View arg0) {
	      		
	      		Bundle extras = getIntent().getExtras();
	  	      if(extras !=null)
	  	      {
	  	         String Value = extras.getString("userid");
	  	         if(Integer.parseInt(Value)>0){
	  	        	 
	  	            if(usdb.updateContact(Value,name.getText().toString(), phone.getText().toString() )){
	  	               Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();	
	  	               Intent intent = new Intent(getApplicationContext(),cu.home.appfirststeps.ActivitySQLiteData.class);
	  	               Bundle bundle = new Bundle();
	  	               bundle.putString("userid", Value);
	  	               intent.putExtras(bundle);

//	  	               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//	  	               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
	  	               startActivity(intent);
	  	               ActivitySQLiteData.this.finish();
	  	            }		
	  	            else{
	  	            	Toast.makeText(getApplicationContext(), "Not Updated", Toast.LENGTH_SHORT).show();	
	  	            	}
	  	         }
	//  	         else{	//esto esta en una actividad separada - SQLiteAddUserActivity
	//  	            if(usdb.insertUser(name.getText().toString(), phone.getText().toString() ) ){
	//  	               Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();	
	//  	            }		
	//  	            
	//  	            else{
	//  	               Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();	
	//  	            }
	//  	            Intent intent = new Intent(getApplicationContext(),cu.home.appfirststeps.SQLiteDataActivity.class);
	//  	            startActivity(intent);
	//  	         	}
	  	      }
	             
	      	}
      	});
	    
	}//onCreate
	
	   @Override
	    public boolean onCreateOptionsMenu(Menu menu)
	    {
	    	//creates a menu inflater
	    	MenuInflater inflater = getMenuInflater();
	    	//generates a Menu from a menu resource file
	    	//R.menu.main_menu represents the ID of the XML resource file
	    	inflater.inflate(R.menu.display_contact, menu);
	    	return true;
	    }
	   
	   public boolean onOptionsItemSelected(MenuItem item) 
	   { 	super.onOptionsItemSelected(item); 
	      
	      switch(item.getItemId()) 
	      { 
		      case R.id.Edit_Contact: 
		    	  
		    	  Button b = (Button)findViewById(R.id.btnsave);
			      b.setVisibility(View.VISIBLE);
			      
			      name.setEnabled(true);
			      name.setFocusableInTouchMode(true);
			      name.setClickable(true);
		
			      phone.setEnabled(true);
			      phone.setFocusableInTouchMode(true);
			      phone.setClickable(true);
		
			      email.setEnabled(true);
			      email.setFocusableInTouchMode(true);
			      email.setClickable(true);
		
			      street.setEnabled(true);
			      street.setFocusableInTouchMode(true);
			      street.setClickable(true);
		
			      place.setEnabled(true);
			      place.setFocusableInTouchMode(true);
			      place.setClickable(true);
		
			      return true; 
	      
		      case R.id.Delete_Contact:
	    	  
		    	  AlertDialog.Builder builder = new AlertDialog.Builder(this);
			      builder.setMessage(R.string.deleteContact).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
				         	public void onClick(DialogInterface dialog, int id) {
				         		
								String msg = "Deleted Successfully";
								
				         		if (!usdb.deleteContact(strid)) {
				         			msg = "No se pudo eliminar el Usuario. Ha ocurrido un error.";
								}				        	 
					            
					            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();  
					            Intent intent = new Intent(getApplicationContext(),ActivitySQLiteConsult.class);
					            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//para no repetir una instancia de la misma actividad cuando se oprime el boton Back.
					            ActivitySQLiteData.this.finish();
					            startActivity(intent);
					         }
					      })
					      .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
					         public void onClick(DialogInterface dialog, int id) {
					            // User cancelled the dialog
					         }
					      });
		      
			      AlertDialog d = builder.create();
			      d.setTitle("Are you sure?");
			      d.show();
		
			      return true;
			      
			      default: 
			    	  return super.onOptionsItemSelected(item); 

	      } 
	   }
	   
	   public void run(View view)
	   {	
	      Bundle extras = getIntent().getExtras();
	      if(extras !=null)
	      {
	         String Value = extras.getString("userid");
	         if(Integer.parseInt(Value)>0){
	        	 
	            if(usdb.updateContact(Value,name.getText().toString(), phone.getText().toString() )){
	               Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();	
	               Intent intent = new Intent(getApplicationContext(),cu.home.appfirststeps.ActivitySQLiteData.class);
	               startActivity(intent);
	            }		
	            else{
	               Toast.makeText(getApplicationContext(), "Not Updated", Toast.LENGTH_SHORT).show();	
	            }
	         }
//	         else{	//esto esta en una actividad separada - SQLiteAddUserActivity
//	            if(usdb.insertUser(name.getText().toString(), phone.getText().toString() ) ){
//	               Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();	
//	            }		
//	            
//	            else{
//	               Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();	
//	            }
//	            Intent intent = new Intent(getApplicationContext(),cu.home.appfirststeps.SQLiteDataActivity.class);
//	            startActivity(intent);
//	         	}
	      }
	   }
	   
	   

}//class
