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
import com.streamstech.droid.mshtb.data.persistent.PETAdverseEventFollowup;
import com.streamstech.droid.mshtb.util.Util;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;
import com.xeoh.android.checkboxgroup.CheckBoxGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class PETAdverseEventExpandableListAdapter extends BaseExpandableListAdapter{

    private Context context;
    private List<PETHistoryGroup> historyGroups;

    public PETAdverseEventExpandableListAdapter(Context context, List<PETHistoryGroup> historyGroups) {
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

        final PETAdverseEventFollowup data = (PETAdverseEventFollowup) getChild(listPosition, expandedListPosition);
        if (rootView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = layoutInflater.inflate(R.layout.activity_petadverse_event, null);
        }

        EditText txtOther;
        EditText txtComment;
        MultiLineRadioGroup radioGroup1;
        MultiLineRadioGroup radioGroup2;
        MultiLineRadioGroup radioGroup3;
        MultiLineRadioGroup radioGroup4;
        MultiLineRadioGroup radioGroup5;
        CheckBoxGroup<String> checkBoxGroup;
        CheckBoxGroup<String> checkBoxGroup2;
        TextView lblTime;

        Button btnSave;

        txtOther = (EditText)rootView.findViewById(R.id.txtOther);
        txtOther.setText(data.getOthers());
        txtComment = (EditText)rootView.findViewById(R.id.txtComment);
        txtComment.setText(data.getComment());
        lblTime = (TextView) rootView.findViewById(R.id.lblTime);
        lblTime.setText(Util.getFormattedDateTime(data.getCreatedtime()));
        radioGroup1 = (MultiLineRadioGroup) rootView.findViewById(R.id.radioGroup1);
        radioGroup1.checkAt(data.getMonthoftreatment());
        radioGroup2 = (MultiLineRadioGroup) rootView.findViewById(R.id.radioGroup2);
        radioGroup2.checkAt(data.getNonseriousevent() ? 0 : 1);
        radioGroup3 = (MultiLineRadioGroup) rootView.findViewById(R.id.radioGroup3);
        radioGroup3.checkAt(data.getSeriousevent() ? 0 : 1);
        radioGroup4 = (MultiLineRadioGroup) rootView.findViewById(R.id.radioGroup4);
        radioGroup4.checkAt(data.getConsistentcomoplains());
        radioGroup5 = (MultiLineRadioGroup) rootView.findViewById(R.id.radioGroup5);
        radioGroup5.checkAt(data.getSymptomaction());

        HashMap<CheckBox, String> checkBoxMap = new HashMap<>();
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk1), "0");
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk2), "1");
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk3), "2");
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk4), "3");
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk5), "4");
        checkBoxMap.put((CheckBox) rootView.findViewById(R.id.chk6), "5");
        checkBoxGroup = new CheckBoxGroup<>(checkBoxMap,
                new CheckBoxGroup.CheckedChangeListener<String>() {
                    @Override
                    public void onCheckedChange(ArrayList<String> options) {
                    }
                });
        checkBoxGroup.setValues(Arrays.asList( data.getNonseriousevents()));

        HashMap<CheckBox, String> checkBoxMap2 = new HashMap<>();
        checkBoxMap2.put((CheckBox) rootView.findViewById(R.id.chk7), "0");
        checkBoxMap2.put((CheckBox) rootView.findViewById(R.id.chk8), "1");
        checkBoxMap2.put((CheckBox) rootView.findViewById(R.id.chk9), "2");
        checkBoxMap2.put((CheckBox) rootView.findViewById(R.id.chk10), "3");
        checkBoxMap2.put((CheckBox) rootView.findViewById(R.id.chk11), "4");
        checkBoxMap2.put((CheckBox) rootView.findViewById(R.id.chk12), "5");
        checkBoxGroup2 = new CheckBoxGroup<>(checkBoxMap2,
                new CheckBoxGroup.CheckedChangeListener<String>() {
                    @Override
                    public void onCheckedChange(ArrayList<String> options) {
                    }
                });
        checkBoxGroup2.setValues(Arrays.asList( data.getSeriousevents()));

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