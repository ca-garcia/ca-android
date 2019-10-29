package com.example.carlos.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.carlos.fragmentos.dummy.DummyContent;
import com.example.carlos.fragmentos.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class EmailFragmentList extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;

    private OnListFragmentInteractionListener mListener;

    ListView lstListado;//Added by me

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EmailFragmentList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_email_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new EmailAdapter(DummyContent.ITEMS, mListener));
        }
//        (AdapterView)view.setOn

//        (ListView)view.setOnClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> list, View pview, int pos, long id) {
//                if (mListener!=null) {
//                    mListener.onListFragmentInteraction( (Correo)view.getAdapter().getItem(pos) );
//                }
//            }
//        });

        return view;
    }

    //----------------------------------------------------------------------------------------------
    @Override
    public void onActivityCreated(Bundle state) {//Added by me.
        super.onActivityCreated(state);

        lstListado = (ListView)getView().findViewById(R.id.frgListado);

//        lstListado.setAdapter(new AdaptadorCorreos(this));
//        lstListado.setAdapter((ListAdapter) new EmailAdapter(DummyContent.ITEMS, mListener));

        lstListado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View view, int pos, long id) {
                if (mListener!=null) {
//                    mListener.onCorreoSeleccionado(
                    mListener.onListFragmentInteraction(
                            (DummyItem) lstListado.getAdapter().getItem(pos));
                }
            }
        });
    }
    //----------------------------------------------------------------------------------------------


//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
    public void setListener(OnListFragmentInteractionListener listener){//Added by me.
        this.mListener = listener;
    }
}
