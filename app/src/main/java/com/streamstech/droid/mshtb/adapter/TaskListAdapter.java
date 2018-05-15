//package com.streamstech.droid.mshtb.adapter;
//
//import android.content.Context;
//import android.text.Html;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//import java.util.List;
//import siltrock.com.droid.productqr.R;
//import com.streamstech.droid.mshtb.data.persistent.Task;
//
//public class TaskListAdapter extends BaseAdapter
//{
//    private List<Task> items;
//    private Context context;
//
//    public TaskListAdapter(Context context, List<Task> arrayList)
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
//    public Task getItem(int position)
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
//        private TextView lblImage;
//        private TextView lblMessage;
//        private TextView lblDescription;
//        private TextView lblDate;
//        private TextView lblEndDate;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent)
//    {
//        View mvi = convertView;
//        ViewHolder mholder;
//        if(convertView == null)
//        {
//            mvi = LayoutInflater.from(context).inflate(R.layout.task_list_row, null);
//            mholder = new ViewHolder();
//            mholder.lblImage = (TextView) mvi.findViewById(R.id.lblImage);
//            mholder.lblMessage = (TextView) mvi.findViewById(R.id.lblMessage);
//            mholder.lblDescription = (TextView) mvi.findViewById(R.id.lblDescription);
//            mholder.lblDate = (TextView)mvi.findViewById(R.id.lblDate);
//            mholder.lblEndDate = (TextView)mvi.findViewById(R.id.lblEndDate);
//            mvi.setTag(mholder);
//        }
//        else
//            mholder = (ViewHolder) mvi.getTag();
//
//        Task task = items.get(position);
//        if (task.getImagemust())
//            mholder.lblImage.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_box_black_24dp, 0, 0, 0);
//        else
//            mholder.lblImage.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_box_outline_blank_black_24dp, 0, 0, 0);
//
//        if (task.getImagemust())
//            mholder.lblMessage.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_box_black_24dp, 0, 0, 0);
//        else
//            mholder.lblMessage.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_box_outline_blank_black_24dp, 0, 0, 0);
//
//        mholder.lblDescription.setText(task.getDescription());
//        mholder.lblDate.setText(Html.fromHtml("<b>Start Date:</b> " + task.getStartdate()));
//        mholder.lblEndDate.setText(Html.fromHtml("<b>End Date:</b> " + task.getEnddate()));
//        return mvi;
//    }
//}