package pfb_subsysmt_sit;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pfb.biz.IDoCustomerBiz;
import com.pfb.biz.IDoPayBiz;
import com.pfb.biz.vo.outer.customer.entry.OuterCustomerEntryRequest;
import com.pfb.biz.vo.outer.customer.entry.OuterCustomerEntryResponse;
import com.pfb.biz.vo.outer.micro.OuterMicroRequest;
import com.pfb.biz.vo.outer.micro.OuterMicroResponse;
import com.pfb.biz.vo.outer.query.OuterOrderQueryRequest;
import com.pfb.common.util.JSONUtil;
import com.pfb.common.util.UUIDUtil;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})

public class TestEntry {
	    private Logger logger = LoggerFactory.getLogger(this.getClass());

	    @Resource
	    private IDoPayBiz doPayBiz;
	    
	    @Resource
	    private IDoCustomerBiz doCustomerBiz;
	    @Before
	    public void setUp() throws Exception {

	    }

	    @After
	    public void tearDown() throws Exception {

	    }

	    @Test
	    public void toSettleTest() throws Exception {
	    	OuterCustomerEntryRequest outerCustomerEntryRequest = new OuterCustomerEntryRequest();
	    	outerCustomerEntryRequest.setCustomerName("新时代烧烤");
	    	outerCustomerEntryRequest.setCustomerNum("C48494895u34958324546");
	    	outerCustomerEntryRequest.setBusinessProvince("610000");
	    	outerCustomerEntryRequest.setBusinessCity("610500");
	    	outerCustomerEntryRequest.setBusinessDistrict("610581");
	    	outerCustomerEntryRequest.setBusinessType("2053");
	    	outerCustomerEntryRequest.setBusinessAddress("北京市朝阳区建国门外大街5号");
	    	outerCustomerEntryRequest.setContactPhone("15720308087");
	    	outerCustomerEntryRequest.setCardAccountType("PERSONAL");
	    	outerCustomerEntryRequest.setBankCard("622908321066573415");
	    	outerCustomerEntryRequest.setCardAccountName("张明盟");
	    	outerCustomerEntryRequest.setCardAlliedBankNum("309100003181");
	    	outerCustomerEntryRequest.setLegalIdCard("130826199106117918");
	    	outerCustomerEntryRequest.setCardPhone("15720308087");
	    	outerCustomerEntryRequest.setRate("0.003");
	    	outerCustomerEntryRequest.setCardProvince("110000");
	    	OuterCustomerEntryResponse entry = doCustomerBiz.doEntry(outerCustomerEntryRequest);
	    	System.out.println(JSONUtil.toJSONString(entry));
	    	
	    }

	}