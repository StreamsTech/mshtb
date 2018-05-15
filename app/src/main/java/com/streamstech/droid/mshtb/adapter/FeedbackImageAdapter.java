//package com.streamstech.droid.mshtb.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//import java.util.ArrayList;
//import java.util.List;
//
//import siltrock.com.droid.productqr.R;
//
//public class FeedbackImageAdapter extends BaseAdapter {
//    private LayoutInflater inflater;
//    private List<Item> items;
//
//    private class Item {
//        final int drawableId;
//        final String name;
//
//        Item(String name, int drawableId) {
//            this.name = name;
//            this.drawableId = drawableId;
//        }
//    }
//
//    public FeedbackImageAdapter(Context context) {
//        this.items = new ArrayList();
//        this.inflater = LayoutInflater.from(context);
//    }
//
//    public int getCount() {
//        return this.items.size();
//    }
//
//    public Object getItem(int i) {
//        return this.items.get(i);
//    }
//
//    public long getItemId(int i) {
//        return (long) ((Item) this.items.get(i)).drawableId;
//    }
//
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        View v = view;
//        if (v == null) {
//            v = this.inflater.inflate(R.layout.gridview_item, viewGroup, false);
//            v.setTag(R.id.picture, v.findViewById(R.id.picture));
//            v.setTag(R.id.text, v.findViewById(R.id.text));
//        }
//        TextView name = (TextView) v.getTag(R.id.text);
//        Item item = (Item) getItem(i);
//        ((ImageView) v.getTag(R.id.picture)).setImageResource(item.drawableId);
//        name.setText(item.name);
//        return v;
//    }
//}
