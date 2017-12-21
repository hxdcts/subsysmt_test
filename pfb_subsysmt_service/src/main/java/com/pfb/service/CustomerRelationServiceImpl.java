package com.pfb.service;

import com.pfb.common.page.PageQueryInterface;
import com.pfb.common.page.PageQueryParam;
import com.pfb.dao.CustomerRelationDaoImpl;
import com.pfb.entity.CustomerRelationEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CustomerRelationServiceImpl implements PageQueryInterface<CustomerRelationEntity> {

    @Resource
    private CustomerRelationDaoImpl customerRelationDao;

    @Override
    public CustomerRelationEntity add(CustomerRelationEntity customerRelationEntity) {
        return customerRelationDao.add(customerRelationEntity);
    }

    @Override
    public CustomerRelationEntity getById(Long id) {
        return customerRelationDao.getById(id);
    }

    @Override
    public int delById(Long id) {
        return customerRelationDao.delById(id);
    }

    @Override
    public int update(CustomerRelationEntity customerRelationEntity) {
        return customerRelationDao.update(customerRelationEntity);
    }

    @Override
    public int getRecordCount(PageQueryParam<CustomerRelationEntity> pageQueryParam) {
        return customerRelationDao.getRecordCount(pageQueryParam);
    }

    @Override
    public List<CustomerRelationEntity> queryListForPage(PageQueryParam<CustomerRelationEntity> pageQueryParam) {
        return customerRelationDao.queryListForPage(pageQueryParam);
    }

    @Override
    public List<CustomerRelationEntity> queryListByParam(CustomerRelationEntity customerRelationEntity) {
        return customerRelationDao.queryListByParam(customerRelationEntity);
    }
}
