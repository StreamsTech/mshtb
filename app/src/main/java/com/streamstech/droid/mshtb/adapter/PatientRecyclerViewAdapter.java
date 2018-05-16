package com.streamstech.droid.mshtb.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.data.persistent.Patient;
import com.streamstech.droid.mshtb.fragement.FormsFragment;
import java.util.List;


public class PatientRecyclerViewAdapter extends RecyclerView.Adapter<PatientRecyclerViewAdapter.ViewHolder> {

    private List<Patient> mValues;
    private final FormsFragment.OnListFragmentInteractionListener mListener;

    public PatientRecyclerViewAdapter(List<Patient> items, FormsFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.lblName.setText(mValues.get(position).getName());
        holder.lblID.setText(mValues.get(position).getPatientid());
        if (mValues.get(position).getTb()){
            holder.vIndivator.setBackgroundResource(R.color.colorPrimary);
//            holder.vIndivator.setVisibility(View.VISIBLE);
        }else if (mValues.get(position).getPresumtivetb()){
            holder.vIndivator.setBackgroundResource(R.color.colorAmber);
//            holder.vIndivator.setVisibility(View.VISIBLE);
        }else{
//            holder.vIndivator.setVisibility(View.GONE);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
                return true;
            }
        });
    }

    public void swap(List<Patient> list){
        if (mValues != null) {
            mValues.clear();
            mValues.addAll(list);
        }
        else {
            mValues = list;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public Patient mItem;
        public final TextView lblName;
        public final TextView lblID;
        public final View vIndivator;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            lblName = (TextView) view.findViewById(R.id.lblName);
            lblID = (TextView) view.findViewById(R.id.lblID);
            vIndivator = (View)view.findViewById(R.id.vIndivator);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + lblName.getText() + "'";
        }
    }
}
