package com.pfb.dao;

import org.springframework.stereotype.Repository;

import com.pfb.dao.base.SimpleBaseDao;
import com.pfb.entity.BankInfoEntity;


@Repository
public class BankInfoDaoImpl extends SimpleBaseDao<BankInfoEntity> {
	/**
	 * 根据分行ID查询实体
	 * 
	 * @author:zhang.mingmeng@pufubao.net
	 */
	public BankInfoEntity getByBranchId(String branchId) {
		return this.getSqlQuerySession().selectOne(BankInfoEntity.class.getName()+".getByBranchId", branchId);
	}
	/**
	 * 根据联行号查询实体
	 * 
	 * @author:zhang.mingmeng@pufubao.net
	 */
	public BankInfoEntity getByBranchCode(String branchCode) {
		return this.getSqlQuerySession().selectOne(BankInfoEntity.class.getName()+".getByBranchCode", branchCode);
	}

}
