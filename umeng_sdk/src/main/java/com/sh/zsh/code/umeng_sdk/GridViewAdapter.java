package com.sh.zsh.code.umeng_sdk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * gridView适配器
 * Created by zhush on 2015/9/14.
 */
public class GridViewAdapter extends BaseAdapter {

    private Context context;
    private String[] str_name;
    private Integer[] imgs;

    public GridViewAdapter(Context context, String[] str_name, Integer[] imgs) {
        this.context = context;
        this.str_name = str_name;
        this.imgs = imgs;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return imgs.length;
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return imgs[position];
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_layout, parent, false);
            holder.iv_goods_picture = (ImageView) convertView.findViewById(R.id.iv_goods);

            holder.tv_goods_name = (TextView) convertView.findViewById(R.id.tv_goods);
            convertView.setTag(holder);
//            AutoUtils.autoSize(convertView);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.iv_goods_picture.setImageResource(imgs[position]);
        holder.tv_goods_name.setText(str_name[position]);
        return convertView;
    }

    class Holder {
        TextView tv_goods_name;
        ImageView iv_goods_picture;


    }
}
