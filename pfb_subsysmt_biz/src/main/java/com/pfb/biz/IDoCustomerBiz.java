package com.pfb.biz;

import org.slf4j.Logger;

import com.pfb.biz.vo.outer.customer.config.OuterCustomerConfigRequest;
import com.pfb.biz.vo.outer.customer.config.OuterCustomerConfigResponse;
import com.pfb.biz.vo.outer.customer.configquery.OuterCustomerConfigQueryRequest;
import com.pfb.biz.vo.outer.customer.configquery.OuterCustomerConfigQueryResponse;
import com.pfb.biz.vo.outer.customer.edit.OuterCustomerEditRequest;
import com.pfb.biz.vo.outer.customer.edit.OuterCustomerEditResponse;
import com.pfb.biz.vo.outer.customer.entry.OuterCustomerEntryRequest;
import com.pfb.biz.vo.outer.customer.entry.OuterCustomerEntryResponse;
import com.pfb.biz.vo.outer.customer.freeze.OuterCustomerFreezeRequest;
import com.pfb.biz.vo.outer.customer.freeze.OuterCustomerFreezeResponse;
import com.pfb.biz.vo.outer.customer.query.OuterCustomerQueryRequest;
import com.pfb.biz.vo.outer.customer.query.OuterCustomerQueryResponse;
import com.pfb.entity.CustomerRelationEntity;

/**
 * 商户业务入口
 *
 * @author zhanghui
 * @version 1.0
 * @date: 26/09/2017 17:37
 */
public interface IDoCustomerBiz {

    /**
     * 商户进件
     *
     * @param outerCustomerEntryRequest
     * @param logger
     * @return
     */
    OuterCustomerEntryResponse doEntry(OuterCustomerEntryRequest outerCustomerEntryRequest) throws Exception;

    /**
     * 商户查询
     *
     * @param outerCustomerQueryRequest
     * @param logger
     * @return
     */
    OuterCustomerQueryResponse doQuery(OuterCustomerQueryRequest outerCustomerQueryRequest) throws Exception;

    /**
     * 商户修改
     *
     * @param outerCustomerEditRequest
     * @param logger
     * @return
     */
    OuterCustomerEditResponse doEdit(OuterCustomerEditRequest outerCustomerEditRequest) throws Exception;

    /**
     * 商户冻结
     *
     * @param outerCustomerFreezeRequest
     * @param logger
     * @return
     */
    OuterCustomerFreezeResponse doFreeze(OuterCustomerFreezeRequest outerCustomerFreezeRequest) throws Exception;

    /**
     * 商户配置
     *
     * @param outerCustomerConfigRequest
     * @param logger
     * @return
     */
    OuterCustomerConfigResponse doConfig(OuterCustomerConfigRequest outerCustomerConfigRequest) throws Exception;

    /**
     * 商户配置
     *
     * @param outerCustomerConfigQueryRequest
     * @param logger
     * @return
     */
    OuterCustomerConfigQueryResponse doConfigQuery(OuterCustomerConfigQueryRequest outerCustomerConfigQueryRequest) throws Exception;

    /**
     * 商户关系映射查询
     *
     * @param customerRelationEntity
     * @param logger
     * @return
     */
    CustomerRelationEntity doCustomerReflactionQuery(CustomerRelationEntity customerRelationEntity) throws Exception;

}
