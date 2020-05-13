package com.streamstech.droid.mshtb.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollment;
import com.streamstech.droid.mshtb.data.persistent.Patient;
import com.streamstech.droid.mshtb.fragement.FASTFormsFragment;

import java.util.List;


public class PETPatientRecyclerViewAdapter extends RecyclerView.Adapter<PETPatientRecyclerViewAdapter.ViewHolder> {

    private List<PETEnrollment> mValues;
    private final FASTFormsFragment.OnListFragmentInteractionListener mListener;

    public PETPatientRecyclerViewAdapter(List<PETEnrollment> items, FASTFormsFragment.OnListFragmentInteractionListener listener) {
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
        holder.lblID.setText(mValues.get(position).getQr());

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
                    mListener.onListFragmentInteraction(holder.mItem);
                }
                return true;
            }
        });
    }

    public void swap(List<PETEnrollment> list){
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
        public PETEnrollment mItem;
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
