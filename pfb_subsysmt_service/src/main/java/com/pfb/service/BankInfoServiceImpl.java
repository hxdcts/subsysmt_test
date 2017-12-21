package com.pfb.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pfb.common.page.PageQueryInterface;
import com.pfb.common.page.PageQueryParam;
import com.pfb.dao.BankInfoDaoImpl;
import com.pfb.entity.BankInfoEntity;

@Service
public class BankInfoServiceImpl implements PageQueryInterface<BankInfoEntity> {

    @Resource
    BankInfoDaoImpl bankInfoDaoImpl;

	@Override
	public BankInfoEntity add(BankInfoEntity bankInfoEntity) {
		return bankInfoDaoImpl.add(bankInfoEntity);
	}

	@Override
	public BankInfoEntity getById(Long id) {
		return bankInfoDaoImpl.getById(id);
	}

	@Override
	public int delById(Long id) {
		return 0;
	}

	@Override
	public int update(BankInfoEntity  bankInfoEntity) {
		return 0;
	}

	@Override
	public int getRecordCount(PageQueryParam<BankInfoEntity> pageQueryParam) {
		return bankInfoDaoImpl.getRecordCount(pageQueryParam);
	}

	@Override
	public List<BankInfoEntity> queryListForPage(PageQueryParam<BankInfoEntity> pageQueryParam) {
		return bankInfoDaoImpl.queryListForPage(pageQueryParam);
	}

	@Override
	public List<BankInfoEntity> queryListByParam(BankInfoEntity queryParam) {
		return bankInfoDaoImpl.queryListByParam(queryParam);
	}

	/**
	 * 根据联行号查询实体
	 * 
	 * @author:zhang.mingmeng@pufubao.net
	 */
	public BankInfoEntity getByBranchCode(String branchCode) {
		return bankInfoDaoImpl.getByBranchCode(branchCode);
	}

	/**
	 * 根据分行ID查询实体
	 * 
	 * @author:zhang.mingmeng@pufubao.net
	 */
	public BankInfoEntity getByBranchId(String branchId) {
		return bankInfoDaoImpl.getByBranchId(branchId);
	}
}
