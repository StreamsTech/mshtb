package com.streamstech.droid.mshtb.fragement;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.adapter.PETAdverseEventExpandableListAdapter;
import com.streamstech.droid.mshtb.adapter.PETClinicianFollowupExpandableListAdapter;
import com.streamstech.droid.mshtb.adapter.PETPatientExpandableListAdapter;
import com.streamstech.droid.mshtb.data.PETHistoryGroup;
import com.streamstech.droid.mshtb.data.PETPatientGroup;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.PETAdverseEventFollowup;
import com.streamstech.droid.mshtb.data.persistent.PETAdverseEventFollowupDao;
import com.streamstech.droid.mshtb.data.persistent.PETClinicianFollowup;
import com.streamstech.droid.mshtb.data.persistent.PETClinicianFollowupDao;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollment;
import com.streamstech.droid.mshtb.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PETClinicianFollowupGroupFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    PETClinicianFollowupExpandableListAdapter adapter;
    ExpandableListView lstEvent;
    List<PETHistoryGroup> groups = new ArrayList<>();
    PETEnrollment patient;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PETClinicianFollowupGroupFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PETClinicianFollowupGroupFragment newInstance(int columnCount) {
        PETClinicianFollowupGroupFragment fragment = new PETClinicianFollowupGroupFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    public void setEnrollment (PETEnrollment patient) {
        this.patient = patient;
        readData();
        adapter.setData(groups);
        adapter.notifyDataSetChanged();
    }

    public void onClearClicked () {
        patient = null;
        groups.clear();
        adapter.setData(groups);
        adapter.notifyDataSetChanged();
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

        View rootView = inflater.inflate(R.layout.fragment_histotry_group_list, container, false);
        lstEvent = (ExpandableListView)rootView.findViewById(R.id.lstHistory);
        readData();
        adapter = new PETClinicianFollowupExpandableListAdapter(getActivity(), groups);
        lstEvent.setAdapter(adapter);
        return rootView;
    }

    private void readData(){
        if (patient == null) {
            return;
        }
        PETClinicianFollowupDao dao = DatabaseManager.getInstance().getSession().getPETClinicianFollowupDao();
        List<PETClinicianFollowup> list = dao.queryBuilder()
                .where(PETClinicianFollowupDao.Properties.Contactqr.eq(patient.getQr()))
                .orderDesc(PETClinicianFollowupDao.Properties.Createdtime)
                .list();

        HashMap<String, List<Object>> hashMap = new HashMap<>();
        for (PETClinicianFollowup patient : list){
            String date = Util.getFormattedMonth(patient.getCreatedtime());
            if (hashMap.containsKey(date)) {
                List<Object> lst = hashMap.get(date);
                lst.add(patient);
                hashMap.put(date, lst);
            } else {
                List<Object> lst = new ArrayList<>();
                lst.add(patient);
                hashMap.put(date, lst);
            }
        }
        groups = new ArrayList<>();
        for (Map.Entry<String, List<Object>> entry : hashMap.entrySet()) {
            PETHistoryGroup historyGroup = new PETHistoryGroup();
            historyGroup.setDate(entry.getKey());
            historyGroup.setCount(entry.getValue().size());
            historyGroup.setGroupList(entry.getValue());
            groups.add(historyGroup);
        }
    }

    @Override
    public void onResume() {
        readData();
        adapter.setData(groups);
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

     @Override
    public void onDetach() {
        super.onDetach();
    }
}
