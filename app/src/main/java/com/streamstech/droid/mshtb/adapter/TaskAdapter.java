//package com.streamstech.droid.mshtb.adapter;
//
//import android.content.Context;
//import android.text.Html;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseExpandableListAdapter;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import net.lynncom.ff.R;
//import net.lynncom.ff.components.CustomFontTextView;
//import net.lynncom.ff.model.AppSettings;
//import net.lynncom.ff.model.RegionStore;
//import net.lynncom.ff.orm.Store;
//import net.lynncom.ff.util.Util;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.streamstech.droid.mshtb.data.TaskAdapterCustomerData;
//import com.streamstech.droid.mshtb.data.TaskAdapterServicePointData;
//import com.streamstech.droid.mshtb.data.persistent.Customer;
//import com.streamstech.droid.mshtb.data.persistent.ServicePoint;
//
//
//public class TaskAdapter extends BaseExpandableListAdapter{
//
//    private Context context;
//    private List<TaskAdapterCustomerData> taskAdapterCustomerDatas;
//
//    public TaskAdapter(Context context, List<TaskAdapterCustomerData> expandableListDetail) {
//        this.context = context;
//        taskAdapterCustomerDatas = new ArrayList<>(expandableListDetail);
//    }
//
//    @Override
//    public Object getChild(int groupPosition, int childPosition) {
//        List<ServicePoint> servicePointList = taskAdapterCustomerDatas.get(groupPosition).getServicePointDataList();
//        return servicePointList.get(childPosition);
//    }
//
//    @Override
//    public long getChildId(int listPosition, int expandedListPosition) {
//        return expandedListPosition;
//    }
//
//    @Override
//    public View getChildView(int listPosition, final int expandedListPosition,
//                             boolean isLastChild, View convertView, ViewGroup parent) {
//
//        final ServicePoint servicePoint = (ServicePoint) getChild(listPosition, expandedListPosition);
//        if (convertView == null) {
//            LayoutInflater layoutInflater = (LayoutInflater) this.context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = layoutInflater.inflate(R.layout.sticky_list_store_item, null);
//        }
//
//        LinearLayout imgLocation = (LinearLayout) convertView
//                .findViewById(R.id.layoutLocation);
//        if (store.getLongitude() == AppSettings.INVALID_DATA || store.getLatitude() == AppSettings.INVALID_DATA)
//            imgLocation.setVisibility(View.INVISIBLE);
//        else
//        {
//            CustomFontTextView lblDistance = (CustomFontTextView) convertView
//                    .findViewById(R.id.lblDistance);
//
//            lblDistance.setText(Html.fromHtml(Util.getRelativeDistance(store.getLongitude(), store.getLatitude())));
//        }
//
//
//        TextView expandedListTextView = (TextView) convertView
//                .findViewById(R.id.expandedListItem);
//        expandedListTextView.setText(store.getName());
//
//        TextView lblAddress = (TextView) convertView
//                .findViewById(R.id.lblAddress);
//        lblAddress.setText(store.getAddress());
//
//        TextView lblPhone = (TextView) convertView
//                .findViewById(R.id.lblPhone);
//        lblPhone.setText(store.getMobile());
//
//        return convertView;
//    }
//
//    @Override
//    public int getChildrenCount(int groupPosition) {
//
//        List<Store> countryList = filteredData.get(groupPosition).getStores();
//        return countryList.size();
//    }
//
//    @Override
//    public Object getGroup(int groupPosition) {
//        //return this.expandableListTitle.get(listPosition);
//        return filteredData.get(groupPosition);
//    }
//
//    @Override
//    public int getGroupCount() {
//
//        //return this.expandableListTitle.size();
//        return filteredData.size();
//    }
//
//    @Override
//    public long getGroupId(int listPosition) {
//        return listPosition;
//    }
//
//    @Override
//    public View getGroupView(int listPosition, boolean isExpanded,
//                             View convertView, ViewGroup parent) {
//
//        RegionStore listTitle = (RegionStore) getGroup(listPosition);
//        if (convertView == null) {
//            LayoutInflater layoutInflater = (LayoutInflater) this.context.
//                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = layoutInflater.inflate(R.layout.sticky_list_store_header, null);
//        }
//        TextView listTitleTextView = (TextView) convertView
//                .findViewById(R.id.listTitle);
//        listTitleTextView.setText(listTitle.getName());
//
//        TextView lblCounter = (TextView) convertView
//                .findViewById(R.id.lblCounter);
//        lblCounter.setText(String.valueOf(listTitle.getStorecount()));
//
//        return convertView;
//    }
//
//    @Override
//    public boolean hasStableIds() {
//        return false;
//    }
//
//    @Override
//    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
//        return true;
//    }
//}