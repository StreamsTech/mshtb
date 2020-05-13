package com.streamstech.droid.mshtb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.data.PETPatientGroup;
import com.streamstech.droid.mshtb.data.PatientGroup;
import com.streamstech.droid.mshtb.data.persistent.PETRegistration;
import com.streamstech.droid.mshtb.data.persistent.Patient;
import com.streamstech.droid.mshtb.fragement.FASTFormsFragment;

import java.util.ArrayList;
import java.util.List;


public class PETPatientExpandableListAdapter extends BaseExpandableListAdapter{

    private Context context;
    private List<PETPatientGroup> patientGroupList;
    private final FASTFormsFragment.OnListFragmentInteractionListener mListener;

    public PETPatientExpandableListAdapter(Context context, List<PETPatientGroup> expandableListDetail, FASTFormsFragment.OnListFragmentInteractionListener listener) {
        this.context = context;
        patientGroupList = new ArrayList<>(expandableListDetail);
        this.mListener = listener;
    }

    public void setData(List<PETPatientGroup> expandableListDetail){
        patientGroupList = new ArrayList<>(expandableListDetail);
        notifyDataSetChanged();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<PETRegistration> taskAdapterServicePointDataList = patientGroupList.get(groupPosition).getPatientList();
        return taskAdapterServicePointDataList.get(childPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final PETRegistration taskAdapterServicePointData = (PETRegistration) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.sticky_list_store_item, null);
        }

        LinearLayout linearLayout = (LinearLayout) convertView
                .findViewById(R.id.layoutItem);

        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expandedListItem);

        TextView expandedListTextView3 = (TextView) convertView
                .findViewById(R.id.expandedListItem2);

//        if (taskAdapterServicePointData.getTb()) {
//            linearLayout.setBackgroundResource(R.color.colorPrimary);
//            expandedListTextView.setTextColor(this.context.getResources().getColor(R.color.colorWhite));
//            expandedListTextView3.setTextColor(this.context.getResources().getColor(R.color.colorWhite));
//        } else {
//            if (taskAdapterServicePointData.getPresumtivetb()) {
//                linearLayout.setBackgroundResource(R.color.colorAmber);
//                expandedListTextView.setTextColor(this.context.getResources().getColor(R.color.colorWhite));
//                expandedListTextView3.setTextColor(this.context.getResources().getColor(R.color.colorWhite));
//            } else {
//                linearLayout.setBackgroundResource(R.color.colorWhite);
//                expandedListTextView.setTextColor(this.context.getResources().getColor(R.color.colorPrimaryDark));
//                expandedListTextView3.setTextColor(this.context.getResources().getColor(R.color.colorPrimaryDark));
//            }
//        }
        linearLayout.setBackgroundResource(R.color.colorWhite);
        expandedListTextView.setTextColor(this.context.getResources().getColor(R.color.colorPrimaryDark));
        expandedListTextView3.setTextColor(this.context.getResources().getColor(R.color.colorPrimaryDark));

        expandedListTextView.setText(taskAdapterServicePointData.getName());
        expandedListTextView3.setText(taskAdapterServicePointData.getQr());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(taskAdapterServicePointData);
                }
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return patientGroupList.get(groupPosition).getPatientList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return patientGroupList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return patientGroupList.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        PETPatientGroup listTitle = (PETPatientGroup) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.sticky_list_store_header, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setText(listTitle.getDate());

        ((TextView) convertView
                .findViewById(R.id.lblMale)).setText(String.valueOf(listTitle.getMale()));

        ((TextView) convertView
                .findViewById(R.id.lblFemale)).setText(String.valueOf(listTitle.getFemale()));

        TextView lblCounter = (TextView) convertView
                .findViewById(R.id.lblCounter);
        lblCounter.setText(String.valueOf(listTitle.getCount()));

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}