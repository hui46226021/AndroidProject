package com.jruilibarary.widget.layerListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;


import com.jruilibarary.widget.layerListView.model.Node;

import java.util.List;

/**
 * http://blog.csdn.net/lmj623565791/article/details/40212367
 * @author zhy
 *
 * @param <T>
 */
public abstract class TreeListViewAdapter<T> extends BaseAdapter
{

	protected Context mContext;
	/**
	 * 存储所有可见的Node
	 */
	protected List<Node> mNodes;
	protected LayoutInflater mInflater;
	/**
	 * 存储所有的Node
	 */
	protected List<Node> mAllNodes;

	/**
	 * 点击的回调接口
	 */
	private OnTreeNodeClickListener onTreeNodeClickListener;


	public void closeList(){
		mNodes.clear();
		for(Node node:mAllNodes){
			node.setExpand(false);
			if(node.isRoot()){
				mNodes.add(node);
			}
		}
	}





	public interface OnTreeNodeClickListener
	{
		void onClick(Node node, int position);
	}

	public void setOnTreeNodeClickListener(
			OnTreeNodeClickListener onTreeNodeClickListener)
	{
		this.onTreeNodeClickListener = onTreeNodeClickListener;
	}

	/**
	 * 
	 * @param mTree
	 * @param context
	 * @param datas
	 * @param defaultExpandLevel
	 *            默认展开几级树
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public TreeListViewAdapter(ListView mTree, Context context, List<T> datas,
			int defaultExpandLevel) throws IllegalArgumentException,
			IllegalAccessException
	{
		mContext = context;
		/**
		 * 对所有的Node进行排序
		 */
		mAllNodes = TreeHelper.getSortedNodes(datas, defaultExpandLevel);
		/**
		 * 过滤出可见的Node
		 */
		mNodes = TreeHelper.filterVisibleNode(mAllNodes);
		mInflater = LayoutInflater.from(context);

		/**
		 * 设置节点点击时，可以展开以及关闭；并且将ItemClick事件继续往外公布
		 */
		mTree.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				if (onTreeNodeClickListener != null)
				{
					onTreeNodeClickListener.onClick(mNodes.get(position),
							position);
				}

				Node node=	mNodes.get(position);
				expandOrCollapse(node);
			}

		});

	}



	/**
	 * 相应ListView的点击事件 展开或关闭某节点
	 * 
	 * @param n
	 */
	public void expandOrCollapse(Node n)
	{


		closeBrotherList(n);

		if (n != null)// 排除传入参数错误异常
		{
			if(n.isRoot()){
				closeList();

			}
			if (!n.isLeaf())
			{
				n.setExpand(!n.isExpand());
				mNodes = TreeHelper.filterVisibleNode(mAllNodes);
					notifyDataSetChanged();// 刷新视图
			}

			if(n.isRoot()&&n.isLeaf()){
				notifyDataSetChanged();// 刷新视图
			}


		}
	}

	/**
	 * 关闭同级别列表
	 */
	public void closeBrotherList(Node node){
		if(node.isRoot()){
			closeList();
		}else {
			List<Node>brotherList= node.getParent().getChildren();
			closeChildrenList(brotherList);
		}
	}

	public void closeChildrenList(List<Node> childrenList){
		for(Node node:childrenList){
			if(node.isExpand()==true){
				node.setExpand(false);
				if(!node.isLeaf()){
					closeChildrenList(node.getChildren());
				}

			}
		}

	}

	@Override
	public int getCount()
	{
		return mNodes.size();
	}

	@Override
	public Object getItem(int position)
	{
		return mNodes.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		Node node = mNodes.get(position);
		convertView = getConvertView(node, position, convertView, parent);
		// 设置内边距
		convertView.setPadding(node.getLevel() * 30, 3, 3, 3);
		return convertView;
	}

	public abstract View getConvertView(Node node, int position,
			View convertView, ViewGroup parent);

	/**
	 * 选中那个节点
	 * @param node
     */
	public void setSelected(Node node){
		expandOrCollapse(node);
		if(!node.isLeaf()){
			closeChildrenList(node.getChildren());
		}

	}
}
