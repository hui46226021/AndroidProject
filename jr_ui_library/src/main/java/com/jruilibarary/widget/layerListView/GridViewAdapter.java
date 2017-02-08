package com.jruilibarary.widget.layerListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.jruilibarary.widget.layerListView.model.Node;
import com.sh.zsh.jr_ui_library.R;

import java.util.List;


/**
 * gridView适配器
 * Created by zhush on 2016/9/18.
 */
public class GridViewAdapter extends BaseAdapter {

    private Context context;
    List<Node> menuModels;


    public GridViewAdapter(Context context, List<Node> menuModels ) {
        this.context = context;
        this.menuModels = menuModels;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return menuModels.size();
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
        return menuModels.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            holder.yjt = (ImageView) convertView.findViewById(R.id.yjt);
            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();
        }

        if(position==0){
            holder.yjt.setVisibility(View.INVISIBLE);
        }else {
            holder.yjt.setVisibility(View.VISIBLE);
        }
        Node node=  menuModels.get(position);
        holder.text.setText(node.getName());
        return convertView;
    }

    class Holder {
        TextView text;
        ImageView yjt;
    }

    @Override
    public void notifyDataSetChanged() {

        if(menuModels.size()==0||menuModels.get(0).getId()!=999999){
            menuModels.add(0,new Node(999999,999999,"根目录"));
        }
        super.notifyDataSetChanged();
    }
}
