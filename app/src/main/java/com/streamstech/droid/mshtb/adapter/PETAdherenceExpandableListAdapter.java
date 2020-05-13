package com.streamstech.droid.mshtb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.data.PETHistoryGroup;
import com.streamstech.droid.mshtb.data.PETPatientGroup;
import com.streamstech.droid.mshtb.data.persistent.PETAdherence;
import com.streamstech.droid.mshtb.data.persistent.PETRegistration;
import com.streamstech.droid.mshtb.fragement.FASTFormsFragment;
import com.streamstech.droid.mshtb.util.Util;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;

import java.util.ArrayList;
import java.util.List;


public class PETAdherenceExpandableListAdapter extends BaseExpandableListAdapter{

    private Context context;
    private List<PETHistoryGroup> historyGroups;

    public PETAdherenceExpandableListAdapter(Context context, List<PETHistoryGroup> historyGroups) {
        this.context = context;
        this.historyGroups = new ArrayList<>(historyGroups);
    }

    public void setData(List<PETHistoryGroup> historyGroups){
        this.historyGroups = new ArrayList<>(historyGroups);
        notifyDataSetChanged();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<Object> groupList = historyGroups.get(groupPosition).getGroupList();
        return groupList.get(childPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View rootView, ViewGroup parent) {

        final PETAdherence data = (PETAdherence) getChild(listPosition, expandedListPosition);
        if (rootView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = layoutInflater.inflate(R.layout.activity_pettreatment_adherence, null);
        }

        MultiLineRadioGroup radioGroup1;
        MultiLineRadioGroup radioGroup2;
        MultiLineRadioGroup radioGroup3;
        MultiLineRadioGroup radioGroup4;
        MultiLineRadioGroup radioGroup5;

        TextView lblTime;
        EditText txtAdverseEffect;
        EditText txtComment;

        Button btnSave;

        lblTime = (TextView) rootView.findViewById(R.id.lblTime) ;
        lblTime.setText(Util.getFormattedDateTime(data.getCreatedtime()));
        txtAdverseEffect = (EditText)rootView.findViewById(R.id.txtAdverseEffect) ;
        txtAdverseEffect.setText(data.getAdverseeffect());
        txtComment = (EditText)rootView.findViewById(R.id.txtComment) ;
        txtComment.setText(data.getComment());
        radioGroup1 = (MultiLineRadioGroup) rootView.findViewById(R.id.radioGroup1);
        radioGroup1.checkAt(data.getMonthoftreatment());
        radioGroup2 = (MultiLineRadioGroup) rootView.findViewById(R.id.radioGroup2);
        radioGroup2.checkAt(data.getWeekoftreatment());
        radioGroup3 = (MultiLineRadioGroup) rootView.findViewById(R.id.radioGroup3);
        radioGroup3.checkAt(data.getAdrerence());
        radioGroup4 = (MultiLineRadioGroup) rootView.findViewById(R.id.radioGroup4);
        radioGroup4.checkAt(data.getSeriousadverse());
        radioGroup5 = (MultiLineRadioGroup) rootView.findViewById(R.id.radioGroup5);
        radioGroup5.checkAt(data.getDoctornotified());
        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        btnSave.setVisibility(View.GONE);
        return rootView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return historyGroups.get(groupPosition).getGroupList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return historyGroups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return historyGroups.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        PETHistoryGroup listTitle = (PETHistoryGroup) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.sticky_list_group_header, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setText(listTitle.getDate());

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