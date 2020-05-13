package com.streamstech.droid.mshtb.fragement;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.adapter.PETAdherenceExpandableListAdapter;
import com.streamstech.droid.mshtb.adapter.PETAdverseEventExpandableListAdapter;
import com.streamstech.droid.mshtb.adapter.PETPatientExpandableListAdapter;
import com.streamstech.droid.mshtb.data.PETHistoryGroup;
import com.streamstech.droid.mshtb.data.PETPatientGroup;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.PETAdherence;
import com.streamstech.droid.mshtb.data.persistent.PETAdherenceDao;
import com.streamstech.droid.mshtb.data.persistent.PETAdverseEventFollowup;
import com.streamstech.droid.mshtb.data.persistent.PETAdverseEventFollowupDao;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollment;
import com.streamstech.droid.mshtb.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PETAdverseEventGroupFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    PETAdverseEventExpandableListAdapter adapter;
    ExpandableListView lstEvent;
    List<PETHistoryGroup> groups = new ArrayList<>();
    PETEnrollment patient;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PETAdverseEventGroupFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PETAdverseEventGroupFragment newInstance(int columnCount) {
        PETAdverseEventGroupFragment fragment = new PETAdverseEventGroupFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_histotry_group_list, container, false);
        lstEvent = (ExpandableListView)rootView.findViewById(R.id.lstHistory);
        readData();
        adapter = new PETAdverseEventExpandableListAdapter(getActivity(), groups);
        lstEvent.setAdapter(adapter);
        return rootView;
    }

    private void readData(){
        if (patient == null) {
            return;
        }
        PETAdverseEventFollowupDao dao = DatabaseManager.getInstance().getSession().getPETAdverseEventFollowupDao();
        List<PETAdverseEventFollowup> list = dao.queryBuilder()
                .where(PETAdverseEventFollowupDao.Properties.Contactqr.eq(patient.getQr()))
                .orderDesc(PETAdverseEventFollowupDao.Properties.Createdtime)
                .list();

        HashMap<String, List<Object>> hashMap = new HashMap<>();
        for (PETAdverseEventFollowup patient : list){
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
