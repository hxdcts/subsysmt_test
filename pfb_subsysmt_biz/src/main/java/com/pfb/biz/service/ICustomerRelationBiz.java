package com.pfb.biz.service;

import com.pfb.entity.CustomerRelationEntity;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author zhanghui
 * @version 1.0
 * @date: 26/09/2017 17:32
 */
public interface ICustomerRelationBiz {

    CustomerRelationEntity addCustomerRelation(CustomerRelationEntity customerRelation);

    int updateCustomerRelation(CustomerRelationEntity customerRelation);

    List<CustomerRelationEntity> getRelationsByCustomerNum(String customerNum);

    List<CustomerRelationEntity> getRelationsByCustomerRelation(CustomerRelationEntity customerRelationEntity);

    String getCustomerApiKey(String customerNum);

}
