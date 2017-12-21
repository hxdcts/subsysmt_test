package com.pfb.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pfb.common.page.PageQueryInterface;
import com.pfb.common.page.PageQueryParam;
import com.pfb.dao.CityDaoImpl;
import com.pfb.entity.CityEntity;

@Service
public class CityServiceImpl implements PageQueryInterface<CityEntity> {
	
	@Resource
	private CityDaoImpl cityDaoImpl;
	
	/**
	 * 根据地区码查询信息
	 * 
	 * @param areaid
	 * @return
	 * @author zhang.mingmeng@pufubao.net
	 * @date 2016年10月13日 下午8:35:06
	 */
	public CityEntity getByCityid(String cityid) {
		return cityDaoImpl.getByCityid(cityid);
	}
	
	@Override
	public CityEntity add(CityEntity t) {
		return null;
	}

	@Override
	public CityEntity getById(Long id) {
		return null;
	}

	@Override
	public int delById(Long id) {
		return 0;
	}

	@Override
	public int update(CityEntity t) {
		return 0;
	}

	@Override
	public int getRecordCount(PageQueryParam<CityEntity> pageQueryParam) {
		return 0;
	}

	@Override
	public List<CityEntity> queryListForPage(PageQueryParam<CityEntity> pageQueryParam) {
		return null;
	}

	@Override
	public List<CityEntity> queryListByParam(CityEntity queryParam) {
		return cityDaoImpl.queryListByParam(queryParam);
	}
	
}
