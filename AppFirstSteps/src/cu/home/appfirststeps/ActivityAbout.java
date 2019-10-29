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
	    		new Titular("Acerca de esta aplicación", "Esta aplicación fue desarrollado solo con el único propósito de entrenar la programación Android. "
	    					+ "En ella se muestran los principales componentes y funcionalidades mas utilizadas implementados como casos de estudio."),
	    		new Titular("Autor", "Carlos Alberto García Brizuela"),
	    		new Titular("Contactar", "carlosalbertogarcia.b@gmail.com"),
//	    		new Titular("Título 3", "Subtítulo largo 3"),
//	    		new Titular("Título 4", "Subtítulo largo 4"),
//	    		new Titular("Título 5", "Subtítulo largo 5"),
//	    		new Titular("Título 6", "Subtítulo largo 6"),
//	    		new Titular("Título 7", "Subtítulo largo 7"),
//	    		new Titular("Título 8", "Subtítulo largo 8"),
//	    		new Titular("Título 9", "Subtítulo largo 9"),
//	    		new Titular("Título 10", "Subtítulo largo 10")
	    		};
	    
		AdapterListAbout adapter = new AdapterListAbout(this, elements);
		ListView lstAbout = (ListView)findViewById(R.id.aboutList);
		lstAbout.setAdapter(adapter);
		
		
	}

}
