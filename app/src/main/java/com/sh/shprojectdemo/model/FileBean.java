package com.sh.shprojectdemo.model;


import com.jruilibrary.widget.layerListView.model.TreeNodeId;
import com.jruilibrary.widget.layerListView.model.TreeNodeLabel;
import com.jruilibrary.widget.layerListView.model.TreeNodePid;

public class FileBean
{
	@TreeNodeId
	private int _id;
	@TreeNodePid
	private int parentId;
	@TreeNodeLabel
	private String name;
	private long length;
	private String desc;

	public FileBean(int _id, int parentId, String name)
	{
		super();
		this._id = _id;
		this.parentId = parentId;
		this.name = name;
	}

}
