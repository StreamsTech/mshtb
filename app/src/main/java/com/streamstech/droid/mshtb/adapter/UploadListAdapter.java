//package com.streamstech.droid.mshtb.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.streamstech.droid.mshtb.R;
//import com.streamstech.droid.mshtb.data.persistent.Screening;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//public class UploadListAdapter extends BaseAdapter
//{
//    private List<Screening> items;
//    private Context context;
//
//    public UploadListAdapter(Context context, List<Screening> arrayList)
//    {
//        this.context = context;
//        this.items = arrayList;
//    }
//
//    public int getCount()
//    {
//        return items.size();
//    }
//
//    public Screening getItem(int position)
//    {
//        return items.get(position);
//    }
//
//    public long getItemId(int position)
//    {
//        return position;
//    }
//
//    public void clearAll()
//    {
//        items.clear();
//        notifyDataSetChanged();
//    }
//
//
//    public void removeLastItem()
//    {
//        items.remove(items.size()-1);
//    }
//
//    private class ViewHolder
//    {
//        private ImageView imgStatus;
//        private TextView lblMessage;
//        private TextView lblTotalImage;
//        private TextView lblTime;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent)
//    {
//        View mvi = convertView;
//        ViewHolder mholder;
//        if(convertView == null)
//        {
//            mvi = LayoutInflater.from(context).inflate(R.layout.uploadlist_row, null);
//            mholder = new ViewHolder();
//            mholder.imgStatus = (ImageView) mvi.findViewById(R.id.imgStatus);
//            mholder.lblMessage = (TextView) mvi.findViewById(R.id.lblMessage);
//            mholder.lblTotalImage = (TextView)mvi.findViewById(R.id.lblTotalImage);
//            mholder.lblTime = (TextView)mvi.findViewById(R.id.lblTime);
//            mvi.setTag(mholder);
//        }
//        else
//        {
//            mholder = (ViewHolder) mvi.getTag();
//        }
//
//        Screening prescription = items.get(position);
//        if (prescription.getDelivered()) {
//            mholder.imgStatus.setBackgroundResource(R.drawable.done);
//        }else{
//            mholder.imgStatus.setBackgroundResource(R.drawable.progress);
//        }
//        mholder.lblMessage.setText(prescription.getMessage());
//        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
//        Date dNow = new Date(prescription.getCreatedtime());
//        mholder.lblTime.setText(format.format(dNow));
//        mholder.lblTotalImage.setText(String.valueOf(prescription.getImageCount()));
//        return mvi;
//    }
//}