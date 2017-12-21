package com.pfb.entity.base;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.pfb.common.util.DateUtil;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public abstract class AbstractEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3620040874194489887L;
	private Long id;
	private Integer version;
	private Integer deleted = 0;
	private Date deletedTime;
	private Date createTime;
	private String createTimeText;
	private Date modifyTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Date getDeletedTime() {
		return deletedTime;
	}

	public void setDeletedTime(Date deletedTime) {
		this.deletedTime = deletedTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
		if (createTime != null) {
			this.createTimeText = DateUtil.msToTime(createTime,
					"yyyy-MM-dd HH:mm:ss");
		}
	}

	public String getCreateTimeText() {
		return createTimeText;
	}

	public void setCreateTimeText(String createTimeText) {
		this.createTimeText = createTimeText;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}


}
