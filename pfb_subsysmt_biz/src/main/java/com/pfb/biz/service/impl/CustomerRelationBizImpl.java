package com.pfb.biz.service.impl;

import com.pfb.biz.service.ICustomerRelationBiz;
import com.pfb.common.page.PageQueryParam;
import com.pfb.entity.CustomerRelationEntity;
import com.pfb.service.CustomerRelationServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 商户关系映射
 *
 * @author zhanghui
 * @version 1.0
 * @date: 26/09/2017 17:33
 */
@Service
public class CustomerRelationBizImpl implements ICustomerRelationBiz {

    @Resource
    private CustomerRelationServiceImpl customerRelationService;

    @Override
    public List<CustomerRelationEntity> getRelationsByCustomerNum(String customerNum) {
        CustomerRelationEntity queryParam = new CustomerRelationEntity();
        queryParam.setCustomerNum(customerNum);
        PageQueryParam<CustomerRelationEntity> pageQueryParam = new PageQueryParam<>();
        pageQueryParam.setQueryParam(queryParam);
        pageQueryParam.setPageEnable(false);
        List<CustomerRelationEntity> relationList = customerRelationService.queryListForPage(pageQueryParam);
        if (relationList == null) {
            return Collections.emptyList();
        }
        return relationList;
    }

    @Override
    public List<CustomerRelationEntity> getRelationsByCustomerRelation(CustomerRelationEntity customerRelationEntity) {
        PageQueryParam<CustomerRelationEntity> pageQueryParam = new PageQueryParam<>();
        pageQueryParam.setQueryParam(customerRelationEntity);
        pageQueryParam.setPageEnable(false);
        List<CustomerRelationEntity> relationList = customerRelationService.queryListForPage(pageQueryParam);
        if (relationList == null) {
            return Collections.emptyList();
        }
        return relationList;
    }

    @Override
    public String getCustomerApiKey(String customerNum) {
        CustomerRelationEntity queryParam = new CustomerRelationEntity();
        queryParam.setCustomerNum(customerNum);
        PageQueryParam<CustomerRelationEntity> pageQueryParam = new PageQueryParam<>();
        pageQueryParam.setQueryParam(queryParam);
        pageQueryParam.setPageEnable(false);
        List<CustomerRelationEntity> relationList = customerRelationService.queryListForPage(pageQueryParam);
        if (relationList == null) {
            return null;
        }
        CustomerRelationEntity customerRelationEntity = relationList.get(0);
        String apiKey = customerRelationEntity.getApiKey();
        return apiKey;
    }

    @Override
    public CustomerRelationEntity addCustomerRelation(CustomerRelationEntity customerRelation) {
        CustomerRelationEntity relationEntity = customerRelationService.add(customerRelation);
        return relationEntity;
    }

	@Override
	public int updateCustomerRelation(CustomerRelationEntity customerRelation) {
		return customerRelationService.update(customerRelation);
	}
}
