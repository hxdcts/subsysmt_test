/*
 * @ClassName Customer
 * @Description 
 * @version 1.0
 * @Date 2016-10-12 11:23:30
 */
package com.pfb.entity;

import com.pfb.entity.base.AbstractBase;

/**
 * 商户实体
 * 
 * @author zhang.mingmeng@pufubao.net
 * @date 2017年07月05日 上午11:24:20
 * @version v1.0
 */
@SuppressWarnings("serial")
public class CityEntity extends AbstractBase {
	
	private Long id;
	private String cityid;		// cityid
	private String city;		// city
	private String provinceid;	// provinceid
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCityid() {
		return cityid;
	}
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvinceid() {
		return provinceid;
	}
	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}
	
}