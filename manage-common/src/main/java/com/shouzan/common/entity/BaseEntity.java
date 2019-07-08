package com.shouzan.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * @author liyong
 *         需要配置 mapper.ORDER=BEFORE uuid 方起作用
 */
public class BaseEntity {

	@Id
	@Column(name = "fid")
	// mysql
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select replace(uuid(), '-', '')")
	private String id;

	public String getId() {

		return id;
	}

	public void setId(String id) {

		this.id = id;
	}

	// // 创建
	// @Column(name = "fcreator_id")
	//
	// protected String creatorId;// 数据创建者用户id（新增数据的用户id）
	// @Column(name = "fcreate_time")
	// protected Date createTime;// 数据创建时间
	//
	// // 更新
	// @Column(name = "flast_editor_id")
	// protected String lastEditorId;// 数据最后一次修改人id
	// @Column(name = "flast_edit_time")
	// protected Date lastEditTime;// 数据最后一次修改时间
	//
	// // 审核
	// @Column(name = "fchecked", nullable = false)
	// protected Integer checked;// 审核标志，1：已审核，0：未审核
	// @Column(name = "fchecker_id")
	// protected String checkerId;// 审核/反审核人id
	// @Column(name = "fcheck_time")
	// protected Date checkTime;// 审核/反审核时间
	//
	// // 删除
	// @Column(name = "fdeleted", nullable = false)
	// protected Integer deleted;// 逻辑删除标志，1:已删除；0：未删除
	// @Column(name = "fdelete_user_id")
	// protected String deleteUserId;// 逻辑删除时的用户id
	// @Column(name = "fdeleted_time")
	// protected Date deleteTime;// 逻辑删除日期
	//
	// // 开始结束日期
	// @Column(name = "fstart_time")
	// protected Date startDate;
	// @Column(name = "fend_time")
	// protected Date endDate;
	//
	// public String getCreatorId() {
	// StringBuilder sb = new StringBuilder();
//		// @formatter:off
//		sb.append("1222")
//		.append("1222")
//		.append("1222")
//		.append("1222");
//		// @formatter:on
	// return creatorId;
	// }
	//
	// public void setCreatorId(String creatorId) {
	// this.creatorId = creatorId;
	// }
	//
	// public Date getCreateTime() {
	// return createTime;
	// }
	//
	// public void setCreateTime(Date createTime) {
	// this.createTime = createTime;
	// }
	//
	// public String getLastEditorId() {
	// return lastEditorId;
	// }
	//
	// public void setLastEditorId(String lastEditorId) {
	// this.lastEditorId = lastEditorId;
	// }
	//
	// public Date getLastEditTime() {
	// return lastEditTime;
	// }
	//
	// public void setLastEditTime(Date lastEditTime) {
	// this.lastEditTime = lastEditTime;
	// }
	//
	// public Integer getChecked() {
	// return checked;
	// }
	//
	// public void setChecked(Integer checked) {
	// this.checked = checked;
	// }
	//
	// public String getCheckerId() {
	// return checkerId;
	// }
	//
	// public void setCheckerId(String checkerId) {
	// this.checkerId = checkerId;
	// }
	//
	// public Date getCheckTime() {
	// return checkTime;
	// }
	//
	// public void setCheckTime(Date checkTime) {
	// this.checkTime = checkTime;
	// }
	//
	// public Integer getDeleted() {
	// return deleted;
	// }
	//
	// public void setDeleted(Integer deleted) {
	// this.deleted = deleted;
	// }
	//
	// public String getDeleteUserId() {
	// return deleteUserId;
	// }
	//
	// public void setDeleteUserId(String deleteUserId) {
	// this.deleteUserId = deleteUserId;
	// }
	//
	// public Date getDeleteTime() {
	// return deleteTime;
	// }
	//
	// public void setDeleteTime(Date deleteTime) {
	// this.deleteTime = deleteTime;
	// }
	//
	// public Date getStartDate() {
	// return startDate;
	// }
	//
	// public void setStartDate(Date startDate) {
	// this.startDate = startDate;
	// }
	//
	// public Date getEndDate() {
	// return endDate;
	// }
	//
	// public void setEndDate(Date endDate) {
	// this.endDate = endDate;
	// }

	@Transient
	private Integer page = 1;

	@Transient
	private Integer rows = 10;

	public Integer getPage() {

		return page;
	}

	public void setPage(Integer page) {

		this.page = page;
	}

	public Integer getRows() {

		return rows;
	}

	public void setRows(Integer rows) {

		this.rows = rows;
	}

}
