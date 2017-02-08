package com.jruilibarary.widget.layerListView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.jruilibarary.widget.layerListView.model.Node;
import com.sh.zsh.jr_ui_library.R;

import java.util.List;

/**
 * Created by zhush on 2017/2/8
 * E-mail zhush@jerei.com
 * PS 
 */

public class MyTreeAdapter<T> extends TreeListViewAdapter<T>
{
	private Context mContext;
	public MyTreeAdapter(ListView mTree, Context context, List<T> datas,
						 int defaultExpandLevel) throws IllegalArgumentException,
			IllegalAccessException
	{

		super(mTree, context, datas, defaultExpandLevel);
		mContext= context;
	}

	@Override
	public View getConvertView(Node node , int position, View convertView, ViewGroup parent)
	{
		
		ViewHolder viewHolder = null;
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.list_item, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.icon = (ImageView) convertView
					.findViewById(R.id.id_treenode_icon);
			viewHolder.label = (TextView) convertView
					.findViewById(R.id.id_treenode_label);
			viewHolder.openIcon = (ImageView) convertView
					.findViewById(R.id.open);

			convertView.setTag(viewHolder);

		} else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}

//		if (node.getIcon() == -1)
//		{
//			viewHolder.icon.setVisibility(View.INVISIBLE);
//		} else
//		{
//			viewHolder.icon.setVisibility(View.VISIBLE);
//			viewHolder.icon.setImageResource(node.getIcon());
//		}

		if(node.isRoot()&&node.isExpand()){
			viewHolder.icon.setVisibility(View.VISIBLE);
		}else {
			viewHolder.icon.setVisibility(View.INVISIBLE);
		}

		if(node.isExpand()){
			viewHolder.openIcon.setImageResource(R.drawable.xjt);
			viewHolder.label.setTextColor(mContext.getResources().getColorStateList(R.color.ios_blue));
		}else {
			viewHolder.openIcon.setImageResource(R.drawable.yjt2);
			viewHolder.label.setTextColor(mContext.getResources().getColorStateList(R.color.text_gray1));
		}

		if(node.isLeaf()){
			viewHolder.openIcon.setVisibility(View.INVISIBLE);
		}else {
			viewHolder.openIcon.setVisibility(View.VISIBLE);
		}


		viewHolder.label.setText(node.getName());
		
		
		return convertView;
	}

	private final class ViewHolder
	{
		ImageView icon;
		TextView label;
		ImageView openIcon;
	}

}
