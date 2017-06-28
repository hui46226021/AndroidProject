package com.jrfunclibrary.area;

import java.sql.Timestamp;


/**
 * CommArea entity. @author MyEclipse Persistence Tools
 */

public class CommArea implements java.io.Serializable {

	// Fields

	private Long areaId;
	private String areaName;
	private Long parentAreaId;
	private String nodePath;
	private String nodeFullName;
	private Boolean isLeaf;
	private Long levelId;
	private Long orderNum;
	private String remark;
	private Long createUserId;
	private Timestamp createDate;
	private Long lastModifyUserId;
	private Timestamp lastModifyDate;
	private Long versionId;

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Long getParentAreaId() {
		return parentAreaId;
	}

	public void setParentAreaId(Long parentAreaId) {
		this.parentAreaId = parentAreaId;
	}

	public String getNodePath() {
		return nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	public String getNodeFullName() {
		return nodeFullName;
	}

	public void setNodeFullName(String nodeFullName) {
		this.nodeFullName = nodeFullName;
	}

	public Boolean getLeaf() {
		return isLeaf;
	}

	public void setLeaf(Boolean leaf) {
		isLeaf = leaf;
	}

	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Long getLastModifyUserId() {
		return lastModifyUserId;
	}

	public void setLastModifyUserId(Long lastModifyUserId) {
		this.lastModifyUserId = lastModifyUserId;
	}

	public Timestamp getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Timestamp lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public Long getVersionId() {
		return versionId;
	}

	public void setVersionId(Long versionId) {
		this.versionId = versionId;
	}
}