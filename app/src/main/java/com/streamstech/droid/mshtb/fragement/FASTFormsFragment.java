package com.streamstech.droid.mshtb.fragement;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.adapter.FormsRecyclerViewAdapter;
import com.streamstech.droid.mshtb.data.Forms;
import com.streamstech.droid.mshtb.data.FormsType;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollment;
import com.streamstech.droid.mshtb.data.persistent.PETRegistration;
import com.streamstech.droid.mshtb.data.persistent.Patient;

import java.util.ArrayList;
import java.util.List;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FASTFormsFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FASTFormsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FASTFormsFragment newInstance(int columnCount) {
        FASTFormsFragment fragment = new FASTFormsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forms, container, false);

        List<Forms> list = getForms();

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
//            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            } else {
//                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//            }
            recyclerView.setAdapter(new FormsRecyclerViewAdapter(list, mListener));
        }
        return view;
    }


    public void reload(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private List<Forms> getForms(){
        List<Forms> list = new ArrayList<>();
        list.add(new Forms("Screening", "FAST", FormsType.SCREENING, android.R.drawable.ic_menu_camera));
        list.add(new Forms("Test Indication", "FAST", FormsType.INDICATION, android.R.drawable.ic_menu_add));
        list.add(new Forms("XRay Result", "FAST", FormsType.RESULT_XRAY, android.R.drawable.ic_menu_agenda));
        list.add(new Forms("XPert MTB/RIF Result", "FAST", FormsType.RESULT_XPERT,android.R.drawable.ic_menu_camera));
        list.add(new Forms("AFB Smear Result", "FAST", FormsType.RESULT_SMEAR,android.R.drawable.ic_menu_camera));
        list.add(new Forms("Histopathology Result", "FAST", FormsType.RESULT_HISTOPATHOLOGY, android.R.drawable.ic_menu_camera));
        list.add(new Forms("Treatment Initiation", "FAST", FormsType.TREATMENT_INITIATION, android.R.drawable.ic_menu_camera));
        list.add(new Forms("Treatment Outcome", "FAST", FormsType.TREATMENT_OUTCOME, android.R.drawable.ic_menu_camera));
        return list;
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
        void onListFragmentInteraction(Forms item);
        void onListFragmentInteraction(Patient item);
        void onListFragmentInteraction(PETRegistration item);
        void onListFragmentInteraction(PETEnrollment item);
    }
}
