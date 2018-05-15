//package com.streamstech.droid.mshtb.adapter;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.view.Gravity;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.GridView;
//import android.widget.ImageView;
//
//import java.util.ArrayList;
//
///**
// * Created by AKASH-LAPTOP on 8/15/2017.
// */
//
//public class ImageCaptureAdapter extends BaseAdapter {
//
//    private Context mContext;
//    public ArrayList<String> map = new ArrayList<>();
//    public ImageCaptureAdapter(Context c) {
//        mContext = c;
//    }
//
//    public int getCount() {
//        return map.size();
//    }
//
//    public Object getItem(int position) {
//        return null;
//    }
//
//    // create a new ImageView for each item referenced by the Adapter
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ImageView imageView;
//        if (convertView == null) { // if it's not recycled, initialize some
//            // attributes
//            imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new GridView.LayoutParams(300, 300,
//                    Gravity.CENTER));
//            //imageView.setLayoutParams(new GridView.LayoutParams(200, 200,
//            //               Gravity.CENTER));
//            imageView.setScaleType(ImageView.ScaleType.CENTER);
//            imageView.setPadding(1, 1, 1, 1);
//
//        } else {
//            imageView = (ImageView) convertView;
//        }
//
//        Bitmap bm = decodeSampledBitmapFromResource(map.get(position), 300, 300);
//        //Bitmap myBitmap = BitmapFactory.decodeFile(map.get(position));
//        imageView.setImageBitmap(bm);
//
//        //imageView.setImageBitmap(BitmapFactory.decodeFile(map.get(position)));
//        return imageView;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//}