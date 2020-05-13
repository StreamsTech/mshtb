package com.streamstech.droid.mshtb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.data.NameValue;

import java.util.List;

/**
 * Created by shafi on 7/1/2017.
 */

public class NameValueAdapter extends ArrayAdapter<NameValue> {

    private Context context;
    private List<NameValue> nameValues;
    private LayoutInflater mInflater;

    public NameValueAdapter(Context context, int textViewResourceId,
                            List<NameValue> nameValues) {
        super(context, textViewResourceId, nameValues);
        this.context = context;
        this.nameValues = nameValues;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount(){
        return nameValues.size();
    }

    public NameValue getItem(int position){
        return nameValues.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.name_value_row, null);
        TextView name= (TextView)convertView.findViewById(R.id.lblName);
        NameValue nameValue = nameValues.get(position);
        name.setText(nameValue.getName());
        return  convertView;
    }

    // And here is when the "chooser" is popped up
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.name_value_row, null);
        TextView name= (TextView)convertView.findViewById(R.id.lblName);
        NameValue nameValue = nameValues.get(position);
        name.setText(nameValue.getName());
        return  convertView;
    }
}
