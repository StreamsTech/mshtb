package com.streamstech.droid.mshtb.fragement;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.adapter.PatientRecyclerViewAdapter;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.Patient;
import com.streamstech.droid.mshtb.data.persistent.PatientDao;

import java.util.List;


public class PatientFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private FASTFormsFragment.OnListFragmentInteractionListener mListener;
    private PatientRecyclerViewAdapter assetRecyclerViewAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PatientFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PatientFragment newInstance(int columnCount) {
        PatientFragment fragment = new PatientFragment();
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

        RecyclerView recyclerView;
        View rootView = inflater.inflate(R.layout.fragment_patient, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        PatientDao patientDao = DatabaseManager.getInstance(getActivity()).getSession().getPatientDao();
        List<Patient> patientList = patientDao.queryBuilder()
                .orderDesc(PatientDao.Properties.Createdtime)
                .list();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        assetRecyclerViewAdapter = new PatientRecyclerViewAdapter(patientList, mListener);
        recyclerView.setAdapter(assetRecyclerViewAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        return rootView;
    }

    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {
        System.out.println("onRefresh");
        mSwipeRefreshLayout.setRefreshing(true);
        // Fetching data from server
//        BackgroundDownloader.getInstance().loadAsset();
        updatePatient();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
//    }

    public void updatePatient(){
        if (!isVisible()){
            return;
        }
        mSwipeRefreshLayout.setRefreshing(false);
        if (assetRecyclerViewAdapter == null){
            return;
        }
        PatientDao patientDao = DatabaseManager.getInstance(getActivity()).getSession().getPatientDao();
        List<Patient> patientList = patientDao.queryBuilder()
                .orderDesc(PatientDao.Properties.Createdtime)
                .list();
        assetRecyclerViewAdapter.swap(patientList);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FASTFormsFragment.OnListFragmentInteractionListener) {
            mListener = (FASTFormsFragment.OnListFragmentInteractionListener) context;
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
}
