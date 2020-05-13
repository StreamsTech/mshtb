package com.streamstech.droid.mshtb.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollment;
import com.streamstech.droid.mshtb.data.persistent.PETRegistration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AKASH-LAPTOP on 5/15/2018.
 */

public class PETContactAutoCompleteListAdapter extends ArrayAdapter {

    private List<PETEnrollment> dataList;
    private List<PETEnrollment> filteredData;
    private Context mContext;
    private int itemLayout;

    private PETContactAutoCompleteListAdapter.ListFilter listFilter = new PETContactAutoCompleteListAdapter.ListFilter();

    public PETContactAutoCompleteListAdapter(Context context, int resource, List<PETEnrollment> storeDataLst) {
        super(context, resource, storeDataLst);
        dataList = storeDataLst;
        mContext = context;
        itemLayout = resource;
    }

    @Override
    public int getCount() {
        return filteredData.size();
    }

    @Override
    public PETEnrollment getItem(int position) {
        return filteredData.get(position);
    }

    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(itemLayout, parent, false);
        }

        TextView description = (TextView) view.findViewById(R.id.lblDescription);
        description.setText(getItem(position).getName() + " (" + getItem(position).getAge() + ")");

        TextView paientid = (TextView) view.findViewById(R.id.lblPatientID);
        paientid.setText(getItem(position).getQr());

        return view;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return listFilter;
    }

    public class ListFilter extends Filter {
        private Object lock = new Object();

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    results.values = new ArrayList<String>();
                    results.count = 0;
                }
            } else {
                final String searchStrLowerCase = prefix.toString().toLowerCase();
                //Call to database to get matching records using room
                List<PETEnrollment> matchValues = new ArrayList<>();
                for (PETEnrollment patient: dataList) {
                    if (patient.getName().toLowerCase().indexOf(searchStrLowerCase) != -1
                            || patient.getQr().indexOf(searchStrLowerCase) != -1 ){
                        matchValues.add(patient);
                    }
                }
                results.values = matchValues;
                results.count = matchValues.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                filteredData = (ArrayList<PETEnrollment>)results.values;
            } else {
                filteredData = null;
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

    }
}