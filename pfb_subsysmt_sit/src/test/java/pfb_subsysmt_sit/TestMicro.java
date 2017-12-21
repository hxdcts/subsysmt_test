package pfb_subsysmt_sit;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pfb.biz.IDoPayBiz;
import com.pfb.biz.utils.ConvertUtil;
import com.pfb.biz.vo.outer.micro.OuterMicroRequest;
import com.pfb.biz.vo.outer.micro.OuterMicroResponse;
import com.pfb.biz.vo.outer.query.OuterOrderQueryRequest;
import com.pfb.biz.vo.outer.refund.OuterRefundRequest;
import com.pfb.biz.vo.outer.refundquery.OuterRefundQueryRequest;
import com.pfb.biz.vo.outer.scan.OuterScanRequest;
import com.pfb.biz.vo.outer.scan.OuterScanResponse;
import com.pfb.biz.vo.outer.webpay.OuterWebpayRequest;
import com.pfb.biz.vo.outer.webpay.OuterWebpayResponse;
import com.pfb.common.util.JSONUtil;
import com.pfb.common.util.UUIDUtil;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})

public class TestMicro {
	    private Logger logger = LoggerFactory.getLogger(this.getClass());

	    @Resource
	    private IDoPayBiz doPayBiz;
	    @Before
	    public void setUp() throws Exception {

	    }

	    @After
	    public void tearDown() throws Exception {

	    }

//	    @Test
	    public void toSettleTest() throws Exception {
	    	OuterMicroRequest micro = new OuterMicroRequest();
	    	micro.setCustomerNum("PC150846720677810709");
	    	micro.setOrderNum("P2017"+System.currentTimeMillis());
	    	micro.setTotalFee("1");
	    	micro.setOrderType("WECHAT");
	    	micro.setSettleMode("");
	    	micro.setDescription("测试");
	    	micro.setSubject("测试");
	    	micro.setNonceStr("asdfa123123qwedf");
	    	micro.setAuthCode("134651196820590885");
	    	OuterMicroResponse outerMicroResponse = doPayBiz.doMicro(micro);
	    	System.out.println(micro.getOrderNum());
	    	
//	    	OuterOrderQueryRequest orderQueryRequest =new OuterOrderQueryRequest();
//	    	orderQueryRequest.setOrderNum("P20171513069148258");
//	    	orderQueryRequest.setCustomerNum("cts");
//	    	orderQueryRequest.setNonceStr(System.currentTimeMillis()+"");
//	    	doPayBiz.doOrderQuery(orderQueryRequest);
	    }
	    @Test
	    public void testWebPay(){
//	    	Map<String, Object> resultMap = new HashMap<String,Object>();
//	    	OuterWebpayRequest webpayRequest = new OuterWebpayRequest();
//	    	webpayRequest.setTotalFee("1");
//	    	webpayRequest.setOrderNum("PP201712131812");
//	    	webpayRequest.setOrderType("WECHAT");
//	    	webpayRequest.setDescription("测试");
//	    	webpayRequest.setSubject("测试");
//	    	webpayRequest.setNonceStr("asdfasfasdfasdfas");
//	    	webpayRequest.setCustomerNum("123123");
//	    	String url = doPayBiz.doWebpayAuth(webpayRequest);
//	    	System.out.println(url);
//	    	
//	    	OuterWebpayResponse outerWebpayResponse = doPayBiz.doWebpay("11", "ovmY7w_7PL4rzfDpXrdoNqdu5h6c");
//	    	resultMap = ConvertUtil.objectToMap(outerWebpayResponse, null);
//	    	System.out.println(resultMap);
	    	
//	    	OuterScanRequest outerScanRequest = new OuterScanRequest();
//	    	outerScanRequest.setCustomerNum("PC150846720677810709");
//	    	outerScanRequest.setOrderNum("asdf");
//	    	outerScanRequest.setTotalFee("11");
//	    	outerScanRequest.setOrderType("WECHAT");
//	    	outerScanRequest.setDescription("测试");
//	    	outerScanRequest.setSubject("测试");
//	    	outerScanRequest.setNonceStr("asdfasfasdfasdfas");
//	    	OuterScanResponse outerScanResponse = doPayBiz.doScan(outerScanRequest);
	    	
	    	
	    	OuterRefundRequest refundRequest= new OuterRefundRequest();
	    	refundRequest.setCustomerNum("PC150846720677810709");
	    	refundRequest.setOrderNum("P20171513592012648");
	    	refundRequest.setRefundNum("R2017"+System.currentTimeMillis());
	    	refundRequest.setNonceStr("asdfadfasdfasf");
	    	refundRequest.setRefundFee("1");
//	    	doPayBiz.doRefund(refundRequest);
	    	
	    	OuterRefundQueryRequest outerRefundQueryRequest = new OuterRefundQueryRequest();
	    	outerRefundQueryRequest.setCustomerNum("PC150846720677810709");
	    	outerRefundQueryRequest.setOrderNum("P20171513592012648");
	    	outerRefundQueryRequest.setRefundNum("R20171513592094444");
	    	outerRefundQueryRequest.setNonceStr("asdfadfasdf");
	    	doPayBiz.doRefundQuery(outerRefundQueryRequest);
	  }
	    
	    
	}