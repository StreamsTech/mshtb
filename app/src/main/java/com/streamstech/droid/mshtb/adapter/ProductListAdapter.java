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
//import com.bumptech.glide.Glide;
//
//import java.util.List;
//import siltrock.com.droid.productqr.R;
//import com.streamstech.droid.mshtb.data.persistent.Product;
//
//public class ProductListAdapter extends BaseAdapter
//{
//    private List<Product> items;
//    private Context context;
//
//    public ProductListAdapter(Context context, List<Product> arrayList)
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
//    public Product getItem(int position)
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
//        private ImageView imgProduct;
//        private TextView lblName;
////        private TextView lblSku;
//        private TextView lblType;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent)
//    {
//        View mvi = convertView;
//        ViewHolder mholder;
//        if(convertView == null)
//        {
//            mvi = LayoutInflater.from(context).inflate(R.layout.product_list_row, null);
//            mholder = new ViewHolder();
//            mholder.imgProduct = (ImageView)mvi.findViewById(R.id.imgProduct);
//            mholder.lblName = (TextView) mvi.findViewById(R.id.lblName);
////            mholder.lblSku = (TextView)mvi.findViewById(R.id.lblSku);
//            mholder.lblType = (TextView)mvi.findViewById(R.id.lblType);
//            mvi.setTag(mholder);
//        }
//        else
//        {
//            mholder = (ViewHolder) mvi.getTag();
//        }
//
//        Product product = items.get(position);
//        mholder.lblName.setText(product.getProductname());
////        mholder.lblSku.setText(product.getSkuid());
//        mholder.lblType.setText(product.getProducttype());
//        //ImageLoader.getInstance().displayImage(product.getImageurl(), mholder.imgProduct);
//        Glide.with(context).load(product.getImageurl()).into(mholder.imgProduct);
//        return mvi;
//    }
//}