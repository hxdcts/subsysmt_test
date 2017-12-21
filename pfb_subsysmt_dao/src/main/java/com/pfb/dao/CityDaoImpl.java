package com.pfb.dao;


import org.springframework.stereotype.Repository;

import com.pfb.dao.base.SimpleBaseDao;
import com.pfb.entity.CityEntity;


@Repository
public class CityDaoImpl extends SimpleBaseDao<CityEntity> {

	/**
	 * 根据地区码查询信息
	 * 
	 * @param areaid
	 * @return
	 * @author zhang.mingmeng@pufubao.net
	 * @date 2016年10月13日 下午8:35:06
	 */
	public CityEntity getByCityid(String cityid) {
		return this.getSqlQuerySession().selectOne(CityEntity.class.getName()+".getByCityid", cityid);
	}

}
