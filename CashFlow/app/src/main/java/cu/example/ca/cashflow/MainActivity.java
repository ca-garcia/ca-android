package cu.example.ca.cashflow;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.Handler;
import android.provider.CallLog;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

//    private Log LogList;
    AlertDialog dialogCalls;
    AlertDialog dialogAdd;
    ListView listviewCalls;
    static ListView listviewLogs;
    static EditText edtQuickNote;
    static LogSQLite logDB;
    static TextView lblFoot;
    FloatingActionButton fab;
    boolean HIDE_MENU = true;
    boolean EDITED_NOTE = false;
    static View rootView;
    static View footer=null;

    static File ruta_sd_global = Environment.getExternalStorageDirectory();
//    static InputMethodManager imm;

    private static final int MENU_CONFIRM = 1;
    private static final int MENU_UNCONFIRM = 2;
    private static final int MENU_CASH = 3;
    private static final int MENU_UNCASH = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File appdir = new File(ruta_sd_global.getAbsolutePath(),"Cashflow");
        if (!appdir.exists())
            appdir.mkdir();

        logDB = new LogSQLite(this);
//        listviewLogs = (ListView) getLayoutInflater().inflate(R.layout.fragment_main, null).findViewById(R.id.lvLogs);
//        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

//--------------------------------------------------------------------------------------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        /*FloatingActionButton*/ fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
//                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Titular[] callList2 =
				new Titular[]{
				new Titular("+537265416 1", "1450656000000",1),
				new Titular("+537265416 2", "1450656000002",2),
				new Titular("+537265416 3", "1450656000003",3),
				new Titular("+537265416 4", "1450656000004",4),
				new Titular("+537265416 5", "1450656000005",5),
				new Titular("+537265416 6", "1450656000006"),
				new Titular("+537265416 7", "1450656000007"),
				new Titular("+537265416 8", "1450656000008"),
				new Titular("+537265416 9", "1450656000009"),
				new Titular("+537265416 10", "1450656000010")
                };

                Titular[] callList = getCallDetails(MainActivity.this);

                if(callList != null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Llamadas:");
                    builder.setCancelable(true);

                    final View view = getLayoutInflater().inflate(R.layout.listview_dialog, null);
                    listviewCalls = (ListView) view.findViewById(R.id.callsList);
                    AdapterListSubitem adapter= new AdapterListSubitem(MainActivity.this, callList);
                    listviewCalls.setAdapter(adapter);

                    listviewCalls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(final AdapterView<?> a, View v, int position, long id) {
                            // TODO Auto-generated method stub
//                            Toast.makeText(v.getContext(),"Position Clicked:"+ position, Toast.LENGTH_LONG).show();

                            AlertDialog.Builder builder2 = new AlertDialog.Builder(v.getContext());
                            builder2.setTitle("Nuevo Log:");
                            builder2.setIcon(android.R.drawable.ic_input_add);
                            View view2 = getLayoutInflater().inflate(R.layout.dialog_add, null);

                            final String strMilli = ((Titular) listviewCalls.getAdapter().getItem(position)).getSubtitulo();
                            Date date = new Date(Long.valueOf(strMilli));
                            String strDate = new SimpleDateFormat("dd/MM/yyyy - hh:mm a").format(date);

                            final TextView tvNewPhone = (TextView) view2.findViewById(R.id.tvNewPhone);
                            final TextView tvNewDate = (TextView) view2.findViewById(R.id.tvNewDate);
                            tvNewPhone.setText(((Titular) listviewCalls.getAdapter().getItem(position)).getTitulo());
                            tvNewDate.setText(strDate);

                            String[] autoList = logDB.getAutocomplete();
                            final AutoCompleteTextView edtAuto = (AutoCompleteTextView) view2.findViewById(R.id.edtItem);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_1, autoList);
                            edtAuto.setAdapter(adapter);

                            Button btnInsert = (Button) view2.findViewById(R.id.btnInsert);
                            Button btnCancel = (Button) view2.findViewById(R.id.btnCancel);

                            btnInsert.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (edtAuto.getText().length() > 0){
                                        if (logDB.insert(tvNewPhone.getText().toString(), strMilli, edtAuto.getText().toString())) {

//                                            rootView = getLayoutInflater().inflate(R.layout.fragment_main, null);
                                            updateListView();
                                            Snackbar.make((View) mViewPager, "Log insertado correctamente!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                            //Toast.makeText(getApplicationContext(), "Log insertado correctamente.", Toast.LENGTH_SHORT).show();
                                            dialogAdd.dismiss();
                                        } else
                                            Toast.makeText(getApplicationContext(), "No se pudo insertar el Log!", Toast.LENGTH_LONG).show();
                                    }else
                                        Toast.makeText(getApplicationContext(), "No debe dejar elementos en blanco!", Toast.LENGTH_LONG).show();
                                }
                            });

                            btnCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogAdd.cancel();
                                }
                            });

                            builder2.setView(view2);
                            dialogAdd = builder2.create();
                            dialogAdd.show();

                            dialogCalls.dismiss();
                        }
                    });

                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialogCalls.dismiss();
                        }
                    });
                    builder.setNeutralButton("Manual", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AlertDialog.Builder builder3 = new AlertDialog.Builder(v.getContext());
                            builder3.setTitle("Nuevo Log:");
                            builder3.setIcon(android.R.drawable.ic_input_add);
                            View view3 = getLayoutInflater().inflate(R.layout.manual_add, null);
                            final TextView mPhone = (TextView) view3.findViewById(R.id.tvManualPhone);
                            final TextView mDate = (TextView) view3.findViewById(R.id.tvManualDate);
                            final AutoCompleteTextView mAuto = (AutoCompleteTextView) view3.findViewById(R.id.edtManualItem);

                            Date currentDate = new Date();//(Long.valueOf(callDate));
                            mDate.setText(new SimpleDateFormat("dd/MM/yyyy - hh:mm a").format(currentDate));
                            final String milliseconds = String.valueOf(currentDate.getTime());

                            String[] autoList = logDB.getAutocomplete();
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_list_item_1, autoList);
                            mAuto.setAdapter(adapter);
                            Button mInsert = (Button) view3.findViewById(R.id.btnManualInsert);
                            Button mCancel = (Button) view3.findViewById(R.id.btnManualCancel);

                            mInsert.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (mPhone.getText().length() > 0 && mAuto.getText().length() > 0){
                                        if (logDB.insert(mPhone.getText().toString(), milliseconds, mAuto.getText().toString())) {

                                            //AdapterLogsList adapter2 = new AdapterLogsList(v.getContext(), logDB.getArrayAdapter());
                                            //listviewLogs.setAdapter(adapter2);
                                            updateListView();
                                            Snackbar.make((View) listviewLogs.getParent(), "Log insertado correctamente!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                            //Toast.makeText(getApplicationContext(), "Log insertado correctamente.", Toast.LENGTH_SHORT).show();
                                            dialogAdd.dismiss();
                                        } else
                                            Toast.makeText(getApplicationContext(), "No se pudo insertar el Log!", Toast.LENGTH_LONG).show();
                                    }else
                                        Toast.makeText(getApplicationContext(), "No debe dejar elementos en blanco!", Toast.LENGTH_LONG).show();

                                }
                            });
                            mCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogAdd.cancel();
                                }
                            });
                            builder3.setView(view3);
                            dialogAdd = builder3.create();
                            dialogAdd.show();

                            dialogCalls.dismiss();
                        }
                    });
                    builder.setView(view);
                    dialogCalls = builder.create();
                    dialogCalls.show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Registro de llamadas vacío!", Toast.LENGTH_LONG).show();

                 /* New Handler to delay the transition effect and displayed after some seconds.*/
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() { mViewPager.setCurrentItem(0, true); }
                }, 1000);

            }//onClick
        });

    }//onCreate


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

//        main_Menu = menu;

        if(!HIDE_MENU) {
            menu.findItem(R.id.action_saveNote).setVisible(true);
            menu.findItem(R.id.action_edit).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {}

        switch (id){

            case R.id.action_saveNote:
                saveNote();
                break;

            case R.id.action_edit:
                HIDE_MENU = false;
                EDITED_NOTE = true;
                invalidateOptionsMenu();
                setupToolbar();
                mViewPager.setCurrentItem(1,true);
                edtQuickNote.setFocusableInTouchMode(true);
                edtQuickNote.setClickable(true);
                edtQuickNote.requestFocus();
                break;

            case R.id.action_settings:
//                edtQuickNote.setText("");//clear
//                StringBuilder strb = new StringBuilder();
//                strb.append("Primary: " + System.getenv("EXTERNAL_STORAGE")+"\n");
//                strb.append("Secondary: " + System.getenv("SECONDARY_STORAGE") + "\n");
//                edtQuickNote.setText(strb.toString());
//
////            LogSQLite logdb = new LogSQLite(MainActivity.this);
//                SQLiteDatabase db = logDB.getReadableDatabase();
//
//                ArrayList<Log> temp = logDB.getArrayAdapter();
//                edtQuickNote.append("size:"+Integer.toString(temp.size()));
//                edtQuickNote.append(((Log) temp.get(0)).getPhone());
//                edtQuickNote.append(((Log) temp.get(temp.size()-1)).getPhone());
//                edtQuickNote.append("\n");
//
//                String[] col = new String[]{"id","phone"};
////            Cursor c0 = db.query("Logs",col,null,null,null,null,null);
//                Cursor c =  db.rawQuery("select * from Logs", null);
//                if (c.moveToFirst()){
//                    do {
//                        edtQuickNote.append(c.getString(0) + "- ");
//                        edtQuickNote.append(c.getString(1)+"//");
//                        edtQuickNote.append(c.getString(4)+"$");
//                        edtQuickNote.append(c.getString(5)+"\n");
//
//                    }while (c.moveToNext());
//                }
                break;

            case R.id.action_export:
                try {
                    ArrayList<Log> dbList = logDB.getAllElements();
                    File folder = new File(ruta_sd_global.getAbsolutePath(),"Cashflow");
                    File file = new File(folder,"db_export.txt");
                    OutputStreamWriter fout = new OutputStreamWriter(new FileOutputStream(file));
                    int i = 1;
//                    for (Log elem :dbList) {
                    for (Log elem :dbList) {
                        String x = elem.isConfirmed()?"[X]":"[\t]";
                        Date date = new Date(Long.valueOf(elem.getDate()));
                        String strDate = new SimpleDateFormat("dd/MM/yyyy - hh:mm a").format(date);
                        fout.append(i + " - " + String.format("%s \t%s\t%s\t%s",strDate,elem.getPhone(),x,elem.getItem() ) );
                        fout.append('\n');
                        i++;
                    }
                    fout.close();
                    Toast.makeText(getBaseContext(),"Base de Datos exportada con éxito!", Toast.LENGTH_LONG).show();

                }catch (Exception e){
                    Toast.makeText(getBaseContext(), "No se pudo exportar la Base de Datos! Ha ocurrido un error: "+e.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.action_about:
                startActivity(new Intent(MainActivity.this,AboutActivity.class));
                break;

            case R.id.action_exit:
//                this.finish();
                checkEdited();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        String title = ((Log)listviewLogs.getAdapter().getItem(info.position)).getPhone();
        boolean confirmed = ((Log)listviewLogs.getAdapter().getItem(info.position)).isConfirmed();
        boolean cashed = ((Log)listviewLogs.getAdapter().getItem(info.position)).isCashed();
        menu.setHeaderTitle(title);

//        if(confirmed){
//            menu.add(Menu.NONE, MENU_UNCONFIRM, Menu.NONE, "No Confirmado");
//                if(!cashed)
//                    menu.add(Menu.NONE, MENU_CASH, Menu.NONE, "Cash");
//        }
//        else
//            menu.add(Menu.NONE, MENU_CONFIRM, Menu.NONE, "Confirmado");

        if(!confirmed)
            menu.add(Menu.NONE, MENU_CONFIRM, Menu.NONE, "Confirmado");
        else
            if(confirmed && !cashed){
                menu.add(Menu.NONE, MENU_UNCONFIRM, Menu.NONE, "No Confirmado");
                menu.add(Menu.NONE, MENU_CASH, Menu.NONE, "Cobrado");
            }

//        if(cashed)
//            menu.add(Menu.NONE, MENU_UNCASH, Menu.NONE, "Uncash");
//        else
//            menu.add(Menu.NONE, MENU_CASH, Menu.NONE, "Cash");

        inflater.inflate(R.menu.context_menu, menu);
            if(cashed)//los cobrados son no editables.
                menu.findItem(R.id.ctxMenuEdit).setVisible(false);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        final AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final String id = ((Log)listviewLogs.getAdapter().getItem(info.position)).getId();
        switch (item.getItemId()) {

            case MENU_CONFIRM:
                if(logDB.confirm(id,1)){
                    updateListView();
//                    Toast.makeText(getBaseContext(), "Confirmado!", Toast.LENGTH_LONG).show();
                    Snackbar.make((View) listviewLogs.getParent(), "Confirmado!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
                else
                    Toast.makeText(getBaseContext(), "No se pudo acceder a la DB.", Toast.LENGTH_LONG).show();
                return true;

            case MENU_CASH:
                if(logDB.cash(id, 1)){
                    updateListView();
                    Snackbar.make((View) listviewLogs.getParent(), "Cobrado!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//                    Toast.makeText(getBaseContext(), "Cobrado!", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getBaseContext(), "No se pudo acceder a la DB.", Toast.LENGTH_LONG).show();
                return true;

            case MENU_UNCONFIRM:
                if(logDB.confirm(id, 0)){
                    updateListView();
                    Snackbar.make((View) listviewLogs.getParent(), "Sin Confirmar!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//                    Toast.makeText(getBaseContext(), "Sin Confirmar!", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getBaseContext(), "No se pudo acceder a la DB.", Toast.LENGTH_LONG).show();
                return true;

            case MENU_UNCASH:
                if(logDB.cash(id, 0)){
                    updateListView();
                    Toast.makeText(getBaseContext(), "Sin Cobrar!", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getBaseContext(), "No se pudo acceder a la DB.", Toast.LENGTH_LONG).show();
                return true;

            case R.id.ctxMenuEdit:
                AlertDialog.Builder builderEdit = new AlertDialog.Builder(MainActivity.this);
                builderEdit.setTitle("Editar Log:");
                builderEdit.setIcon(android.R.drawable.ic_menu_edit);
                View view4 = getLayoutInflater().inflate(R.layout.dialog_add, null);

                Log elem = logDB.getElement(id);
                String strMillisec = elem.getDate();
                Date date = new Date(Long.valueOf(strMillisec));
                String strDate = new SimpleDateFormat("dd/MM/yyyy - hh:mm a").format(date);

                final TextView tvPhone = (TextView) view4.findViewById(R.id.tvNewPhone);
                final TextView tvDate = (TextView) view4.findViewById(R.id.tvNewDate);
                tvPhone.setText(elem.getPhone());
                tvDate.setText(strDate);

                String[] autoList = logDB.getAutocomplete();
                final AutoCompleteTextView edtAuto = (AutoCompleteTextView) view4.findViewById(R.id.edtItem);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, autoList);
                edtAuto.setText(elem.getItem());
                edtAuto.setAdapter(adapter);

                Button btnInsert = (Button) view4.findViewById(R.id.btnInsert);
                Button btnCancel = (Button) view4.findViewById(R.id.btnCancel);

                btnInsert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (edtAuto.getText().length() > 0){
                            if (logDB.update(id,edtAuto.getText().toString())) {
                                updateListView();
                                Snackbar.make((View) listviewLogs.getParent(), "Log editado correctamente!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                dialogAdd.dismiss();
                            } else
                                Toast.makeText(getApplicationContext(), "No se pudo editar el Log!", Toast.LENGTH_LONG).show();
                        }else
                            Toast.makeText(getApplicationContext(), "No debe dejar elementos en blanco!", Toast.LENGTH_LONG).show();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogAdd.cancel();
                    }
                });
                builderEdit.setView(view4);
                dialogAdd = builderEdit.create();
                dialogAdd.show();
                return true;

            case R.id.ctxMenuRemove:

                AlertDialog.Builder alert = new AlertDialog.Builder(this);
//                AlertDialog alerta = new AlertDialog.Builder(this);
                alert.setTitle("Eliminar registro:");
                alert.setMessage("Está seguro que desea ELIMINAR el registro?");
//                alert.setIcon(android.R.drawable.ic_dialog_alert);
                alert.setIcon(R.mipmap.ic_warning);

                alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if (logDB.delete(id)) {
                            updateListView();
                            Snackbar.make((View) listviewLogs.getParent(), "Eliminado!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//                            Toast.makeText(getBaseContext(), "Eliminado!", Toast.LENGTH_LONG).show();
                        } else
                            Toast.makeText(getBaseContext(), "No se pudo eliminar el registro.", Toast.LENGTH_LONG).show();

                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Cierra el cuadro de dialogo.
                    }
                });
//                alert.setNeutralButton("Neutral", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        //Cierra el cuadro de dialogo.
//                    }
//                });

                alert.create().show();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                                 Bundle savedInstanceState) {

//            View rootView = null;// = inflater.inflate(R.layout.fragment_main, container, false);
            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    //Abrimos la base de datos 'DBLogs' en modo lectura
//                    LogSQLite logdb = new LogSQLite(container.getContext());
                    SQLiteDatabase db = logDB.getReadableDatabase();

                    //Si hemos abierto correctamente la base de datos
                    if (db != null) {
                        AdapterLogsList adapter2 = new AdapterLogsList(inflater.getContext(), logDB.getArrayAdapter());

                        rootView = inflater.inflate(R.layout.fragment_main, container, false);
                        listviewLogs = (ListView) rootView.findViewById(R.id.lvLogs);

                        if (adapter2.getCount() > 0){
//                            rootView = inflater.inflate(R.layout.fragment_main, container, false);
//                            listviewLogs = (ListView) rootView.findViewById(R.id.lvLogs);
                            //Footer
                            footer = inflater.inflate(R.layout.listview_footer, null);
                            lblFoot = (TextView) footer.findViewById(R.id.lblFooter);
                            String text = adapter2.getCount()-1 == 0?" Elemento":" Elementos";
                            lblFoot.setText(adapter2.getCount() + text);
//                            listviewLogs.addFooterView(footer);
                            listviewLogs.addFooterView(footer,null,false);//clickeable false.
                            listviewLogs.setAdapter(adapter2);
//                            updateListView();//no funciona por ser static la clase

                            listviewLogs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                Toast.makeText(parent.getContext(),"Count "+listviewLogs.getAdapter().getCount()+ " Pos "+position, Toast.LENGTH_SHORT).show();

//                                    if (position < listviewLogs.getAdapter().getCount()-1){//para q al hacer click en el footer no de error.
                                        //Asociamos los menús contextuales a los controles
                                        registerForContextMenu(listviewLogs);
                                        view.showContextMenu();//openContextMenu(v);//no se puede usar x estar en un ambiente static.
                                        //Desasociamos los menús para que no funcione el long click.
                                        unregisterForContextMenu(listviewLogs);
//                                    }

                                }
                            });
                            db.close();
                        }
                        else{
                            listviewLogs.setVisibility(View.GONE);
//                            rootView = inflater.inflate(R.layout.empty_list, container, false);
                        }


                    } else
                        Toast.makeText(container.getContext(), "No se pudo conectar a la Base de Datos", Toast.LENGTH_LONG).show();
                    break;

                case 2:
                    rootView = inflater.inflate(R.layout.fragment_note, container, false);
                    edtQuickNote = (EditText) rootView.findViewById(R.id.edtQuickNote);

//                    final String sd_status = Environment.getExternalStorageState();
//                    final File ruta_sd_global = Environment.getExternalStorageDirectory();
//                    final String sd_primary = System.getenv("EXTERNAL_STORAGE");
//                    final String sd_secondary = System.getenv("SECONDARY_STORAGE");

//                    edtQuickNote.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
//                    edtQuickNote.append(sd_status + "\n");
//                    edtQuickNote.append(sd_primary + "\n");
//                    edtQuickNote.append(sd_secondary + "\n");
//--------------------------------------------------------------------------------------------------
//                    edtQuickNote.setOnTouchListener(new View.OnTouchListener() {
//                        public boolean onTouch(View v, MotionEvent event) {
//                            // TODO Auto-generated method stub
////				            Toast.makeText(getApplicationContext(), "One Tap!", 2500).show();
//                            gestureDetector.onTouchEvent(event);
//                            return true;
//                        }
//
//                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
//                            @Override
//                            public boolean onDoubleTap(MotionEvent e) {
////			                    Log.d("TEST", "onDoubleTap");
//                                Toast.makeText(getContext(), "DoubleTap!", Toast.LENGTH_SHORT).show();
////                                edtQuickNote.setClickable(true);
////                                edtQuickNote.setFocusable(true);
////                                edtQuickNote.setFocusableInTouchMode(true);
////                                edtQuickNote.setText("AAAAAAAA");
//                                edtQuickNote.requestFocus();
////                                saveNote0.setVisibility(View.VISIBLE);
//
////			    	            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                                imm.showSoftInput(edtQuickNote, InputMethodManager.SHOW_IMPLICIT);
//
//                                return super.onDoubleTap(e);
//                            }
//                            // implement here other callback methods like onFling, onScroll as necessary
//                        });
//                    });

                    fillNote(getContext());
                 break;
            }

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Logs";
                case 1:
                    return "Nota Rápida";
//                case 2:
//                    return "SECTION 3";
            }
            return null;
        }
    }

    public void updateListView(){
        AdapterLogsList adapter = new AdapterLogsList(MainActivity.this, logDB.getArrayAdapter());
        //Header
//        View header = getLayoutInflater().inflate(R.layout.listv_header,null);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        Spinner combo = (Spinner) header.findViewById(R.id.spinner);
//        combo.setAdapter(adapter);
//        lstOpt.addHeaderView(header);

        if (adapter.getCount() > 0){
//            if(listviewLogs == null)
//                listviewLogs = (ListView) getLayoutInflater().inflate(R.layout.fragment_main, null).findViewById(R.id.lvLogs);

            //Footer
        if(adapter.getCount() == 1 && listviewLogs.getFooterViewsCount() == 0) //(footer == null)
        {
            /*View*/ footer = getLayoutInflater().inflate(R.layout.listview_footer, null);
            lblFoot = (TextView) footer.findViewById(R.id.lblFooter);
//            listviewLogs.addFooterView(footer);
            listviewLogs.addFooterView(footer,null,false);//clickeable false.
        }
            String text = adapter.getCount()-1 == 0?" Elemento":" Elementos";
            lblFoot.setText(adapter.getCount() + text);
            listviewLogs.setAdapter(adapter);
            listviewLogs.setVisibility(View.VISIBLE);

            listviewLogs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                Toast.makeText(parent.getContext(),"Count "+listviewLogs.getAdapter().getCount()+ " Pos "+position, Toast.LENGTH_SHORT).show();

//                    if (position < listviewLogs.getAdapter().getCount() - 1) {//para q al hacer click en el footer no de error.
                        //Asociamos los menús contextuales a los controles
                        registerForContextMenu(listviewLogs);
                        openContextMenu(view);
                        //Desasociamos los menús para que no funcione el long click.
                        unregisterForContextMenu(listviewLogs);
//                    }
                }
            });

        }else
            listviewLogs.setVisibility(View.GONE);

    }

    private  Titular[] getCallDetails(Context context) {
//        StringBuffer stringBuffer = new StringBuffer();
//        ArrayList<String> callsList = new ArrayList<>();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            Toast.makeText(MainActivity.this, "Problema con los Permisos de la App para acceder al listado de llamadas!", Toast.LENGTH_LONG).show();
            return null;
        }

        String[] strFields = {
                android.provider.CallLog.Calls.NUMBER,
                android.provider.CallLog.Calls.TYPE,
                android.provider.CallLog.Calls.CACHED_NAME,
                android.provider.CallLog.Calls.DATE,
                android.provider.CallLog.Calls.DURATION,

        };

        // Defines a string to contain the selection clause
        String mSelectionClause = android.provider.CallLog.Calls.DATE+ " >= ?";

        // Initializes an array to contain selection arguments
//        String[] mSelectionArgs0 = { createDate(2016,8,1).toString() }; //no funciona cursor vacio.
//        String[] mSelectionArgs = { String.valueOf(createDate(2016, Calendar.AUGUST, 0)) };//OK
        String[] mSelectionArgs = { String.valueOf(lastMonth()) };

        String strOrder = android.provider.CallLog.Calls.DATE + " DESC limit 500";

        Cursor cursor = context.getContentResolver().query(
                android.provider.CallLog.Calls.CONTENT_URI,
                strFields,
                mSelectionClause,
                mSelectionArgs,
                strOrder//null
        );
//        Cursor cursor0 = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,null, null, null, CallLog.Calls.DATE + " DESC");

        if (cursor.moveToFirst())
        {
//            Titular[] arrCalls = new Titular[0];
            Titular[] arrCalls = new Titular[cursor.getCount()];

            int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
            int name = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
            int date = cursor.getColumnIndex(CallLog.Calls.DATE);
            int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);

            do {
                String phNumber = cursor.getString(number);
                if (phNumber.length()<4)//for Private number.
                    phNumber = "Privado";
//                String callType = cursor.getString(type);
                int callType = cursor.getInt(type);
                String callName = cursor.getString(name);
                String callDate = cursor.getString(date);
                Date callDayTime = new Date(Long.valueOf(callDate));
                String callDuration = cursor.getString(duration);
                String dir = null;
                String callerId = callName != null? phNumber+"-"+callName:phNumber;
//                String callerId2 = phNumber+" - "+callType;

//                int dircode = Integer.parseInt(callType);
                switch (callType) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = "OUTGOING";
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        dir = "INCOMING";
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        dir = "MISSED";
                        break;
                }
    //            stringBuffer.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- "
    //                    + dir + " \nCall Date:--- " + callDayTime
    //                    + " \nCall duration in sec :--- " + callDuration);
    //            stringBuffer.append("\n----------------------------------");
//                callsList.add(phNumber);
//                arrCalls[cursor.getPosition()]= phNumber + " Fecha: " + String.format(callDayTime.toString(),"");

//                if(callType == CallLog.Calls.INCOMING_TYPE || callType == CallLog.Calls.MISSED_TYPE){
                    //yyyy-MM-dd HH:mm 1969-12-31 16:00
                    Titular elem = new Titular(callerId,callDate,callType);
                    arrCalls[cursor.getPosition()]= elem;//phNumber + " Fecha: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(callDayTime);
//                }//if
            }while (cursor.moveToNext());

            cursor.close();
            return arrCalls;

        }//if
        else
            return null;
    }
    public static Long createDate(int year, int month, int day)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTimeInMillis();
    }
    public static Long lastMonth()
    {
        Calendar calendar = Calendar.getInstance();
//        Long today = calendar.getTimeInMillis();
//        int year = calendar.get(Calendar.YEAR);
        int year = calendar.get(Calendar.MONTH) == 0 ? calendar.get(Calendar.YEAR) - 1 : calendar.get(Calendar.YEAR);// Si estoy en ENERO para leer DIC año anterior.
        int month = calendar.get(Calendar.MONTH) > 0 ? calendar.get(Calendar.MONTH) - 1 : 11;// 0->JANUARY 11->DECEMBER
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return createDate(year, month, day);
    }

    private static void fillNote(Context context){

        String sd_status = Environment.getExternalStorageState();
        try {
            switch (sd_status){
                case Environment.MEDIA_MOUNTED:

                    File folder = new File(ruta_sd_global.getAbsolutePath(),"Cashflow");
                    if (!folder.exists())
                        folder.mkdir();

                    //Get the text file
                    File file = new File(folder,"qnote.dat");
                    if (!file.exists())
                        file.createNewFile();
                    else{
                        //Read text from file
                        StringBuilder text = new StringBuilder();

//                        BufferedReader fin = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String line;

                        while ((line = br.readLine()) != null) {
                            text.append(line);
                            text.append('\n');
                        }
                        br.close();
                        //fin.close();
                        edtQuickNote.setText(text);
                    }
                    break;

                case Environment.MEDIA_MOUNTED_READ_ONLY:

                    Toast.makeText(context,"Targeta SD montada en modo SOLO LECTURA!",Toast.LENGTH_LONG).show();
                    break;

                case Environment.MEDIA_REMOVED:

                    Toast.makeText(context,"No se encuentra ninguna Targeta SD!",Toast.LENGTH_LONG).show();
                    break;
            }

        }catch (Exception e){
            Toast.makeText(context,"Error: "+ e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    private void setupToolbar() {
        //Esta ya esta hecho en el onCreate().
//        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
        final View toolbar_cancel = findViewById(R.id.toolbar_cancel);
        final TextView tvcancel = (TextView) findViewById(R.id.tvBtn_cancel);
        // Hide the title
        getSupportActionBar().setTitle(null);
        //Show buttons.
        toolbar_cancel.setVisibility(View.VISIBLE);
        // Set onClickListener to customView
        tvcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                toolbar_cancel.setVisibility(View.INVISIBLE);
                getSupportActionBar().setTitle(R.string.app_name);
                HIDE_MENU = true;
                EDITED_NOTE = false;
                invalidateOptionsMenu();
                edtQuickNote.setFocusableInTouchMode(false);
                edtQuickNote.setClickable(false);
                edtQuickNote.clearFocus();
                fillNote(getBaseContext());
//                imm.hideSoftInputFromWindow(edtQuickNote.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });
    }

    private void saveNote(){
        String sd_status = Environment.getExternalStorageState();
        try {
            switch (sd_status){
                case Environment.MEDIA_MOUNTED:
                    File folder = new File(ruta_sd_global.getAbsolutePath(),"Cashflow");
                    if (!folder.exists())
                        folder.mkdir();

                    //Get the text file
                    File file = new File(folder,"qnote.dat");
                    if (!file.exists())
                        file.createNewFile();

                    //Writing SD
                    OutputStreamWriter fout = new OutputStreamWriter(new FileOutputStream(file));
                    fout.write(String.valueOf(edtQuickNote.getText()));
//                    fout.write("----------------------------------------------------------------".concat("\n"));
//                    fout.write("Primary: ".concat(sd_primary).concat("\n"));
//                    fout.write("Secondary: ".concat(sd_secondary).concat("\n"));
                    fout.close();

//                  Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
//                  setSupportActionBar(mToolbar);
                    View toolbar_cancel = findViewById(R.id.toolbar_cancel);
                    toolbar_cancel.setVisibility(View.INVISIBLE);
                    getSupportActionBar().setTitle(R.string.app_name);
                    HIDE_MENU = true;
                    EDITED_NOTE = false;
                    invalidateOptionsMenu();//redraw main menu.
                    edtQuickNote.setFocusableInTouchMode(false);
                    edtQuickNote.setClickable(false);
                    edtQuickNote.clearFocus();

                    Toast.makeText(MainActivity.this,"Guardado!",Toast.LENGTH_SHORT).show();
                    break;

                case Environment.MEDIA_MOUNTED_READ_ONLY:

                    Toast.makeText(MainActivity.this,"Targeta SD montada en modo SOLO LECTURA!",Toast.LENGTH_LONG).show();
                    break;

                case Environment.MEDIA_REMOVED:

                    Toast.makeText(MainActivity.this,"No se encuentra ninguna Targeta SD!",Toast.LENGTH_LONG).show();
                    break;
            }

        }catch (Exception e){
            Toast.makeText(MainActivity.this,"Error: "+ e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void checkEdited(){
        if(EDITED_NOTE){
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setTitle("Salir de la aplicación?");
            alerta.setMessage("Está a punto de cerrar la aplicación y existen cambios sin guardar en la Nota Rápida. Desea guardar los cambios realizados?");
//          alerta.setIcon(android.R.drawable.ic_dialog_alert);
            alerta.setIcon(R.mipmap.ic_warning);

            alerta.setNeutralButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //Cierra la app.
                    MainActivity.this.finish();
                }
            });
            alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //Guarda los cambios y cierra la aplicacion.
                    saveNote();
                    MainActivity.this.finish();
                }
            });
            alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //Cierra el cuadro de dialogo.
                }
            });
            alerta.create().show();
//            return true;
        }//if EDITED_NOTE
        else
            this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  { 					//se presiona la tecla back
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // do something on back.
            checkEdited();
        }
        return super.onKeyDown(keyCode, event);
    }



}//MainActivity
