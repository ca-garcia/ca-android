package cu.home.appfirststeps;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class ActivityAbout extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.about);
	    
//		List with subitems
	    
	    final Titular[] elements =
	    		new Titular[]{
	    		new Titular("Acerca de esta aplicaci�n", "Esta aplicaci�n fue desarrollado solo con el �nico prop�sito de entrenar la programaci�n Android. "
	    					+ "En ella se muestran los principales componentes y funcionalidades mas utilizadas implementados como casos de estudio."),
	    		new Titular("Autor", "Carlos Alberto Garc�a Brizuela"),
	    		new Titular("Contactar", "carlosalbertogarcia.b@gmail.com"),
//	    		new Titular("T�tulo 3", "Subt�tulo largo 3"),
//	    		new Titular("T�tulo 4", "Subt�tulo largo 4"),
//	    		new Titular("T�tulo 5", "Subt�tulo largo 5"),
//	    		new Titular("T�tulo 6", "Subt�tulo largo 6"),
//	    		new Titular("T�tulo 7", "Subt�tulo largo 7"),
//	    		new Titular("T�tulo 8", "Subt�tulo largo 8"),
//	    		new Titular("T�tulo 9", "Subt�tulo largo 9"),
//	    		new Titular("T�tulo 10", "Subt�tulo largo 10")
	    		};
	    
		AdapterListAbout adapter = new AdapterListAbout(this, elements);
		ListView lstAbout = (ListView)findViewById(R.id.aboutList);
		lstAbout.setAdapter(adapter);
		
		
	}

}
