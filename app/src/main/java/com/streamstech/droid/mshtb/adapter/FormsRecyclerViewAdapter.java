package com.streamstech.droid.mshtb.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.data.Forms;
import com.streamstech.droid.mshtb.fragement.FASTFormsFragment;

import java.util.List;

public class FormsRecyclerViewAdapter extends RecyclerView.Adapter<FormsRecyclerViewAdapter.ViewHolder> {

    private final List<Forms> mValues;
    private final FASTFormsFragment.OnListFragmentInteractionListener mListener;

    public FormsRecyclerViewAdapter(List<Forms> items, FASTFormsFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.form_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.lblName.setText(mValues.get(position).getName());

//        holder.lblEventTypeImage.setText(Html.fromHtml(Util.getDeviceTypeIcon(mValues.get(position).getAssettype())));
//        if (mValues.get(position).getEventtype().equalsIgnoreCase("AVOID")){
//            holder.imgEventTypeImage.setImageDrawable(ContextCompat.getDrawable((Activity)mListener, R.drawable.warning));
//        }else if (mValues.get(position).getEventtype().equalsIgnoreCase("IN_PROHIBITED")){
//            holder.imgEventTypeImage.setImageDrawable(ContextCompat.getDrawable((Activity)mListener, R.drawable.fence_in));
//        }else if (mValues.get(position).getEventtype().equalsIgnoreCase("OUT_PROHIBITED")){
//            holder.imgEventTypeImage.setImageDrawable(ContextCompat.getDrawable((Activity)mListener, R.drawable.fence_out));
//        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView imgFormTypeImage;
        public final TextView lblName;

        public Forms mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imgFormTypeImage = (ImageView) view.findViewById(R.id.imgFormTypeImage);
            lblName = (TextView) view.findViewById(R.id.lblName);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + lblName.getText() + "'";
        }
    }
}
