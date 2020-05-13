package com.streamstech.droid.mshtb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.data.PETHistoryGroup;
import com.streamstech.droid.mshtb.data.persistent.PETAdherence;
import com.streamstech.droid.mshtb.data.persistent.PETFollowup;
import com.streamstech.droid.mshtb.util.Util;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;
import com.xeoh.android.checkboxgroup.CheckBoxGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class PETFollowupExpandableListAdapter extends BaseExpandableListAdapter{

    private Context context;
    private List<PETHistoryGroup> historyGroups;

    public PETFollowupExpandableListAdapter(Context context, List<PETHistoryGroup> historyGroups) {
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

        final PETFollowup data = (PETFollowup) getChild(listPosition, expandedListPosition);
        if (rootView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = layoutInflater.inflate(R.layout.activity_petfollowup, null);
        }

        MultiLineRadioGroup radioGroup1;
        MultiLineRadioGroup radioGroup2;
        MultiLineRadioGroup radioGroup3;
        CheckBoxGroup<String> checkBoxGroup;
        CheckBoxGroup<String> checkBoxGroup2;
        EditText txtSymptom;
        EditText txtOther;
        EditText txtAdherence;
        TextView lblTime;

        lblTime = (TextView) rootView.findViewById(R.id.lblTime) ;
        lblTime.setText(Util.getFormattedDateTime(data.getCreatedtime()));
        txtAdherence = (EditText)rootView.findViewById(R.id.txtAdherence);
        txtAdherence.setText(data.getMisseddoses());
        txtOther = (EditText)rootView.findViewById(R.id.txtOther);
        txtOther.setText(data.getOthersideeffects());
        txtSymptom = (EditText)rootView.findViewById(R.id.txtSymptom);
        txtSymptom.setText(data.getTbsymptomdescription());
        radioGroup1 = (MultiLineRadioGroup) rootView.findViewById(R.id.radioGroup1);
        radioGroup1.checkAt(data.getMonthoftreatment());
        radioGroup2 = (MultiLineRadioGroup) rootView.findViewById(R.id.radioGroup2);
        radioGroup2.checkAt(data.getAnysymptom() ? 0 : 1);
        radioGroup3 = (MultiLineRadioGroup) rootView.findViewById(R.id.radioGroup3);
        radioGroup3.checkAt(data.getConsistentcomoplains());

        HashMap<CheckBox, String> checkBoxMap = new HashMap<>();
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk1), "0");
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk2), "1");
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk3), "2");
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk4), "3");
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk5), "4");

        checkBoxGroup = new CheckBoxGroup<>(checkBoxMap,
                new CheckBoxGroup.CheckedChangeListener<String>() {
                    @Override
                    public void onCheckedChange(ArrayList<String> options) {
                    }
                });
        checkBoxGroup.setValues(Arrays.asList( data.getTbsymptoms()));

        HashMap<CheckBox, String> checkBoxMap2 = new HashMap<>();
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk6), "0");
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk7), "1");
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk8), "2");
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk9), "3");
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk10), "4");
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk11), "5");
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk12), "6");
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk13), "7");
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk14), "8");
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk15), "9");

        checkBoxGroup2 = new CheckBoxGroup<>(checkBoxMap2,
                new CheckBoxGroup.CheckedChangeListener<String>() {
                    @Override
                    public void onCheckedChange(ArrayList<String> options) {
                    }
                });
        checkBoxGroup2.setValues(Arrays.asList( data.getSideeffects()));
        rootView.findViewById(R.id.btnSave).setVisibility(View.GONE);
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