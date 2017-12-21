package com.pfb.biz;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pfb.biz.common.CommonConstant;
import com.pfb.biz.common.HttpClientUtil;
import com.pfb.biz.common.SHA256Utils;
import com.pfb.biz.service.ICustomerRelationBiz;
import com.pfb.biz.upper.common.UpperCommonConfig;
import com.pfb.biz.upper.vo.customer.entry.UpperCustomerEntryRequest;
import com.pfb.biz.upper.vo.customer.entry.UpperCustomerEntryResponse;
import com.pfb.biz.upper.vo.customer.store.UpperStoreAndCustomerRequest;
import com.pfb.biz.upper.vo.customer.store.UpperStoreRequest;
import com.pfb.biz.upper.vo.customer.store.UpperStoreResponse;
import com.pfb.biz.utils.ConvertUtil;
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
import com.pfb.common.util.JSONUtil;
import com.pfb.common.util.StringUtil;
import com.pfb.common.util.UUIDUtil;
import com.pfb.entity.BankInfoEntity;
import com.pfb.entity.CustomerRelationEntity;
import com.pfb.enums.CommonCodeEnum;
import com.pfb.service.BankInfoServiceImpl;

/**
 * Created by IntelliJ IDEA.
 *
 * @author zhanghui
 * @version 1.0
 * @date: 26/09/2017 17:39
 */
@Service
public class DoCustomerBizImpl implements IDoCustomerBiz {

	private static final Logger logger = LoggerFactory.getLogger(DoCustomerBizImpl.class);
	@Resource
	private ICustomerRelationBiz customerRelationBiz;
	@Resource
	private UpperCommonConfig upperCommonConfig;
	@Resource
	private BankInfoServiceImpl bankInfoServiceImpl;

	@Override
	public OuterCustomerEntryResponse doEntry(OuterCustomerEntryRequest outerCustomerEntryRequest) throws Exception {

		OuterCustomerEntryResponse outerCustomerEntryResponse = new OuterCustomerEntryResponse();
		// 1 add to local database
		CustomerRelationEntity param = new CustomerRelationEntity();
		param.setCustomerNum(outerCustomerEntryRequest.getCustomerNum());
		param.setProductType(outerCustomerEntryRequest.getProductNum());
		List<CustomerRelationEntity> customerRelationList = customerRelationBiz.getRelationsByCustomerRelation(param);
		CustomerRelationEntity customerRelation = null;
		if (customerRelationList == null || customerRelationList.size() == 0) {
			customerRelation = new CustomerRelationEntity();
			customerRelation.setCustomerNum(outerCustomerEntryRequest.getCustomerNum());
			customerRelation.setProductType(outerCustomerEntryRequest.getProductNum());
			customerRelation.setCreateTime(new Date());
			// 映射本地商户信息
			customerRelationBiz.addCustomerRelation(customerRelation);
		} else if(customerRelationList.size() == 1){
			customerRelation = customerRelationList.get(0);
			if (!customerRelation.getProductType().equals(outerCustomerEntryRequest.getProductNum())) {
				customerRelation.setId(null);
				customerRelation.setCreateTime(new Date());
				customerRelation.setProductType(outerCustomerEntryRequest.getProductNum());
				customerRelationBiz.addCustomerRelation(customerRelation);
				return outerCustomerEntryResponse;
			}

		}else{
			customerRelation = customerRelationList.get(0);
		}
		UpperStoreAndCustomerRequest upperStoreAndCustomerRequest = new UpperStoreAndCustomerRequest();
		// 创建虚拟门店
		if (customerRelation != null) {
			logger.info("------创建美团虚拟门店---------");
			upperStoreAndCustomerRequest = ConvertUtil.convertEntity(outerCustomerEntryRequest,
					UpperStoreAndCustomerRequest.class);
			UpperStoreRequest upperStoreRequest = JSONUtil
					.parseObject(JSONUtil.toJSONString(upperStoreAndCustomerRequest), UpperStoreRequest.class);
			String location = outerCustomerEntryRequest.getBusinessProvince() + ","
					+ outerCustomerEntryRequest.getBusinessCity() + ","
					+ outerCustomerEntryRequest.getBusinessDistrict();
			upperStoreRequest.setLocation(location);
			upperStoreRequest.setAppId(upperCommonConfig.getAppId());
			upperStoreRequest.setRandom(UUIDUtil.randomUUID());

			// 经营类目暂时写死
			upperStoreRequest.setCateId("2124");
			Map<String, Object> map = upperStoreRequest.toMap();
			logger.info("Map {}", map);
			String sign = SHA256Utils.getSHA256Str(map, upperCommonConfig.getUpperKey());
			upperStoreRequest.setSign(sign);
			HttpClientUtil httpclient = new HttpClientUtil(upperCommonConfig.getEntryStoreUrl());
			String respStr = httpclient.sendJsonPost(JSONUtil.toJSONString(upperStoreRequest), null);
			UpperStoreResponse upperStoreResponse = JSONUtil.parseObject(respStr, UpperStoreResponse.class);
			if (CommonConstant.SUCCESS.equals(upperStoreResponse.getStatus())
					&& "1".equals(upperStoreResponse.getAuditStatus())) {
				customerRelation.setStoreId(upperStoreResponse.getMerchantId());
			} else {
				outerCustomerEntryResponse.setReturnCode(CommonCodeEnum.UPPER_CUSTOMER_FAIL.val());
				outerCustomerEntryResponse.setReturnMsg(CommonCodeEnum.UPPER_CUSTOMER_FAIL.error() + "：门店报备失败，请重新报备");
				return outerCustomerEntryResponse;
			}
		}
		logger.info("商户进件：{}", JSONUtil.toJSONString(customerRelation));
		UpperCustomerEntryRequest upperCustomerEntryRequest = JSONUtil
				.parseObject(JSONUtil.toJSONString(upperStoreAndCustomerRequest), UpperCustomerEntryRequest.class);
		BankInfoEntity bankInfo = bankInfoServiceImpl.getByBranchCode(outerCustomerEntryRequest.getCardAlliedBankNum());
		if (bankInfo == null) {
			outerCustomerEntryResponse.setReturnCode("FAIL");
			outerCustomerEntryResponse.setReturnMsg("支行信息不存在");
			return outerCustomerEntryResponse;
		}
		upperCustomerEntryRequest.setMerchantId(customerRelation.getStoreId());
		upperCustomerEntryRequest.setAppId(upperCommonConfig.getAppId());
		upperCustomerEntryRequest.setRandom(UUIDUtil.randomUUID());
		upperCustomerEntryRequest.setBankId(bankInfo.getBankId());
		upperCustomerEntryRequest.setBankBranchId(bankInfo.getBranchId());
		upperCustomerEntryRequest.setBankCityId(bankInfo.getCityId());
		if (StringUtil.isBlank(upperCustomerEntryRequest.getContactPhone())) {
			upperCustomerEntryRequest.setContactPhone(outerCustomerEntryRequest.getContactPhone());
		}
		if (StringUtil.isBlank(outerCustomerEntryRequest.getRate())) {
			outerCustomerEntryResponse.setReturnCode(CommonConstant.FAIL);
			outerCustomerEntryResponse.setReturnMsg("费率不能为空");
			return outerCustomerEntryResponse;
		}
		BigDecimal rate = new BigDecimal(outerCustomerEntryRequest.getRate());
		BigDecimal nrate = new BigDecimal(10000);

		int intValue = rate.multiply(nrate).intValue();
		upperCustomerEntryRequest.setWxRate(intValue + "");
		upperCustomerEntryRequest.setAliRate(intValue + "");
		String sign = SHA256Utils.getSHA256Str(upperCustomerEntryRequest.toMap(), upperCommonConfig.getUpperKey());
		upperCustomerEntryRequest.setSign(sign);
		HttpClientUtil httpclient = new HttpClientUtil(upperCommonConfig.getEntryCustomerUrl());
		logger.info("请求美团进件数据 {}", JSONUtil.toJSONString(upperCustomerEntryRequest));
		String respStr = httpclient.sendJsonPost(JSONUtil.toJSONString(upperCustomerEntryRequest), null);
		logger.info("请求美团进件返回结果 {}", JSONUtil.toJSONString(respStr));

		UpperCustomerEntryResponse customerResponse = JSONUtil.parseObject(respStr, UpperCustomerEntryResponse.class);
		if (CommonConstant.SUCCESS.equals(customerResponse.getStatus())
				&& !CommonConstant.ORDER_TRADE_TYPE_REJECT.equals(customerResponse.getMerchantStatus())) {
			customerRelation.setMchId(customerResponse.getMerchantId());
			// outerCustomerEntryResponse.setReturnCode("SUCCESS");
		} else {
			outerCustomerEntryResponse.setReturnCode(CommonConstant.FAIL);
			outerCustomerEntryResponse.setReturnMsg(CommonCodeEnum.UPPER_CUSTOMER_FAIL.error());
		}
		customerRelation.setProductType(null);
		customerRelation.setId(null);
		customerRelationBiz.updateCustomerRelation(customerRelation);
		return outerCustomerEntryResponse;
	}

	@Override
	public OuterCustomerQueryResponse doQuery(OuterCustomerQueryRequest outerCustomerQueryRequest) throws Exception {
		OuterCustomerQueryResponse outerCustomerQueryResponse = new OuterCustomerQueryResponse();
		return outerCustomerQueryResponse;
	}

	@Override
	public OuterCustomerEditResponse doEdit(OuterCustomerEditRequest outerCustomerEditRequest) throws Exception {
		OuterCustomerEditResponse outerCustomerEditResponse = new OuterCustomerEditResponse();
		return outerCustomerEditResponse;
	}

	@Override
	public OuterCustomerFreezeResponse doFreeze(OuterCustomerFreezeRequest outerCustomerFreezeRequest)
			throws Exception {
		OuterCustomerFreezeResponse outerCustomerFreezeResponse = new OuterCustomerFreezeResponse();
		return outerCustomerFreezeResponse;
	}

	@Override
	public OuterCustomerConfigResponse doConfig(OuterCustomerConfigRequest outerCustomerConfigRequest)
			throws Exception {
		OuterCustomerConfigResponse outerCustomerConfigResponse = new OuterCustomerConfigResponse();
		return outerCustomerConfigResponse;
	}

	@Override
	public OuterCustomerConfigQueryResponse doConfigQuery(
			OuterCustomerConfigQueryRequest outerCustomerConfigQueryRequest) throws Exception {
		OuterCustomerConfigQueryResponse outerCustomerConfigQueryResponse = new OuterCustomerConfigQueryResponse();
		return outerCustomerConfigQueryResponse;
	}

	@Override
	public CustomerRelationEntity doCustomerReflactionQuery(CustomerRelationEntity customerRelationEntity)
			throws Exception {
		List<CustomerRelationEntity> relationList = customerRelationBiz
				.getRelationsByCustomerRelation(customerRelationEntity);
		if (relationList.size() == 0) {
			return null;
		}
		CustomerRelationEntity relation = relationList.get(0);
		return relation;
	}

}
