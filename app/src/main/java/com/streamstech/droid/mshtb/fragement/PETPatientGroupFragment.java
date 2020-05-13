package com.streamstech.droid.mshtb.fragement;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.adapter.PETPatientExpandableListAdapter;
import com.streamstech.droid.mshtb.adapter.PatientExpandableListAdapter;
import com.streamstech.droid.mshtb.data.PETPatientGroup;
import com.streamstech.droid.mshtb.data.PatientGroup;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.PETRegistration;
import com.streamstech.droid.mshtb.data.persistent.PETRegistrationDao;
import com.streamstech.droid.mshtb.data.persistent.Patient;
import com.streamstech.droid.mshtb.data.persistent.PatientDao;
import com.streamstech.droid.mshtb.util.Util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class PETPatientGroupFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private FASTFormsFragment.OnListFragmentInteractionListener mListener;
    PETPatientExpandableListAdapter adapter;
    ExpandableListView lstEvent;
    SwipeRefreshLayout mSwipeRefreshLayout;
    List<PETPatientGroup> patientGroups;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PETPatientGroupFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PETPatientGroupFragment newInstance(int columnCount) {
        PETPatientGroupFragment fragment = new PETPatientGroupFragment();
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

        View rootView = inflater.inflate(R.layout.fragment_patient_group_list, container, false);
        lstEvent = (ExpandableListView)rootView.findViewById(R.id.lstPatient);
        readData();
        adapter = new PETPatientExpandableListAdapter(getActivity(), patientGroups, mListener);
        lstEvent.setAdapter(adapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        return rootView;
    }

    private void readData(){

        PETRegistrationDao dao = DatabaseManager.getInstance().getSession().getPETRegistrationDao();
        List<PETRegistration> list = dao.queryBuilder()
                .orderDesc(PETRegistrationDao.Properties.Createdtime)
                .list();

        HashMap<String, List<PETRegistration>> hashMap = new HashMap<>();
        for (PETRegistration patient : list){
            String date = Util.getFormattedDate(patient.getCreatedtime());
            if (hashMap.containsKey(date)) {
                List<PETRegistration> lst = hashMap.get(date);
                lst.add(patient);
                hashMap.put(date, lst);
            } else {
                List<PETRegistration> lst = new ArrayList<>();
                lst.add(patient);
                hashMap.put(date, lst);
            }
        }
        Map<String, List<PETRegistration>> treeMap = new TreeMap<>(
                new Comparator<String>() {
                    @Override
                    public int compare(String str1, String str2) {
                        return str2.compareTo(str1);//sort in descending order
                    }
                });
        treeMap.putAll(hashMap);
        patientGroups = new ArrayList<>();
        for (Map.Entry<String, List<PETRegistration>> entry : treeMap.entrySet()) {
            PETPatientGroup patientGroup = new PETPatientGroup();
            patientGroup.setDate(entry.getKey());
            patientGroup.setCount(entry.getValue().size());
            patientGroup.setPatientList(entry.getValue());
            HashMap<String, Integer> map = getGenderCount(entry.getValue());
            patientGroup.setMale(map.get("M"));
            patientGroup.setFemale(map.get("F"));
            patientGroups.add(patientGroup);
        }
    }

    private HashMap<String, Integer> getGenderCount(List<PETRegistration> patients) {
        int male, female;
        male = female = 0;
        for(PETRegistration patient : patients) {
            if ("m".equalsIgnoreCase(patient.getGender())) {
                male++;
            } else {
                female++;
            }
        }
        HashMap<String, Integer> map = new HashMap<>();
        map.put("M", male);
        map.put("F", female);
        return map;
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

    @Override
    public void onResume() {
        readData();
        adapter.setData(patientGroups);
        adapter.notifyDataSetChanged();
        super.onResume();
//        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    public void updatePatient(){
        if (!isVisible()){
            return;
        }
        mSwipeRefreshLayout.setRefreshing(false);
        readData();
        adapter.setData(patientGroups);
        adapter.notifyDataSetChanged();
//        PatientDao patientDao = DatabaseManager.getInstance(getActivity()).getSession().getPatientDao();
//        List<Patient> patientList = patientDao.queryBuilder()
//                .orderDesc(PatientDao.Properties.Createdtime)
//                .list();
//        assetRecyclerViewAdapter.swap(patientList);
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
