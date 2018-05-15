//package com.streamstech.droid.mshtb.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//import com.streamstech.droid.mshtb.R;
//import com.streamstech.droid.mshtb.io.BackgroundUploader;
//
//public class OfflineTaskListAdapter extends BaseAdapter
//{
//    private List<TaskFeedback> items;
//    private Context context;
//
//    public OfflineTaskListAdapter(Context context, List<TaskFeedback> arrayList)
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
//    public TaskFeedback getItem(int position)
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
//        private TextView lblId;
//        private TextView lblDescription;
//        private TextView lblTotalImage;
//        private TextView lblTime;
//        private ImageView imgUpload;
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent)
//    {
//        View mvi = convertView;
//        ViewHolder mholder;
//        if(convertView == null)
//        {
//            mvi = LayoutInflater.from(context).inflate(R.layout.task_feedback_list_row, null);
//            mholder = new ViewHolder();
//            mholder.lblId = (TextView)mvi.findViewById(R.id.lblId);
//            mholder.lblDescription = (TextView) mvi.findViewById(R.id.lblDescription);
//            mholder.lblTotalImage = (TextView)mvi.findViewById(R.id.lblTotalImage);
//            mholder.lblTime = (TextView)mvi.findViewById(R.id.lblTime);
//            mholder.imgUpload = (ImageView) mvi.findViewById(R.id.imgUpload);
//            mholder.imgUpload.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                synchronized public void onClick(View v) {
//                    TaskFeedback taskFeedback = items.get(position);
//                    taskFeedback.setStatus("UPLOADING");
//                    Toast.makeText(context, "Task feedback is uploading now", Toast.LENGTH_LONG).show();
//                    DatabaseManager.getInstance().getSession().getTaskFeedbackDao().update(taskFeedback);
//                    items.remove(position);
//                    BackgroundUploader.getInstance().uploadTask(taskFeedback);
//                    notifyDataSetChanged();
//                }
//            });
//            mvi.setTag(mholder);
//        }
//        else
//        {
//            mholder = (ViewHolder) mvi.getTag();
//        }
//
//        TaskFeedback taskFeedback = items.get(position);
//        mholder.lblId.setText(String.valueOf(taskFeedback.getId()));
//        mholder.lblDescription.setText(taskFeedback.getDescription());
//        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
//        Date dNow = new Date(taskFeedback.getCreatedtime());
//        mholder.lblTime.setText(format.format(dNow));
//        mholder.lblTotalImage.setText(String.valueOf(taskFeedback.getImagecount()));
//        return mvi;
//    }
//}