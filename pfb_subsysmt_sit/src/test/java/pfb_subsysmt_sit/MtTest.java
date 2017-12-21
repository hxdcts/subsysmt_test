package pfb_subsysmt_sit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pfb.biz.common.HttpClientUtil;
import com.pfb.biz.common.SHA256Utils;
import com.pfb.common.util.DateUtil;
import com.pfb.common.util.JSONUtil;
import com.pfb.common.util.StringUtil;
import com.pfb.customer.util.Base64;

import javassist.compiler.ast.NewExpr;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MtTest {
	private static Logger logger = LoggerFactory.getLogger(MtTest.class);
	//品类
	public static void getCatelist(){
		
    	Map<String, Object> map=new HashMap<>();
    	//品类参数
    	map.put("appId", "31119");
    	map.put("random", "6f9b247cf09948af9636d4da85442991");
    	String sha256Str = SHA256Utils.getSHA256Str(map, "806614f1ce9b41c794304b8391c30107");
    	map.put("sign", sha256Str);
    	logger.info("sign="+sha256Str);
    	String url="http://payfront.zc.st.meituan.com/api/common/catelist";
    	 HttpClientUtil httpclient = new HttpClientUtil(url);
    	 JSONObject object = JSONObject.fromObject(map);
         String respStr = httpclient.sendJsonPost(object.toString(), null);
         logger.info("品类返回值："+respStr);
         
    }
    //创建美团虚拟门店
    public static void createPoi(){
    	Map<String, Object> map=new HashMap<>();
    	String temp=UUID.randomUUID().toString().replaceAll("-", "");
    	map.put("merchantName", "董亚洲");
    	map.put("location", "110000,110100,110101");
    	map.put("cateId", "2053");//从getCatelist获取
    	map.put("address", "北京市市辖区东城区");
    	map.put("merchantPhone", "13488818111");
    	map.put("appId", "31119");
    	map.put("random", "3dff75bcc47b44ca9bd5e89a1cdbb738");
    	String sha256Str = SHA256Utils.getSHA256Str(map, "806614f1ce9b41c794304b8391c30107");
    	map.put("sign", sha256Str);
    	logger.info("sign="+sha256Str);
    	String url="http://payfront.zc.st.meituan.com/api/merchant/poi/create";
    	 HttpClientUtil httpclient = new HttpClientUtil(url);
    	 JSONObject object = JSONObject.fromObject(map);
         String respStr = httpclient.sendJsonPost(object.toString(), null);
         logger.info("创建美团虚拟门店返回值："+respStr);
    }
    //商户入件（录入开户信息）
    public static void merchantCreate(){
    	String temp=UUID.randomUUID().toString().replaceAll("-", "");
    	Map<String, Object> map=new HashMap<>();
    	map.put("merchantName", "威尼斯国际大酒店");
    	map.put("address", "文387号");
    	map.put("bankAccountType", "2");
    	map.put("cardNo", "622909328264847714");
    	map.put("accountName", "曹铁拴");
    	map.put("bankId", "19");
    	map.put("bankBranchId", "82371");
    	map.put("bankProvinceId", "110000");
    	map.put("bankCityId", "1");
    	map.put("accountPersonIdCode", "410323198606071019");
    	map.put("contactPhone", "13811210014");
    	map.put("wxRate", "30");
    	map.put("aliRate", "38");
    	
    	map.put("merchantId", "164723298");
    	map.put("appId", "31119");
    	map.put("random", temp);
    	String sha256Str = SHA256Utils.getSHA256Str(map, "806614f1ce9b41c794304b8391c30107");
    	map.put("sign", sha256Str);
    	logger.info("sign="+sha256Str);
    	String url="http://payfront.zc.st.meituan.com/api/merchant/create";
    	 HttpClientUtil httpclient = new HttpClientUtil(url);
    	 JSONObject object = JSONObject.fromObject(map);
         String respStr = httpclient.sendJsonPost(object.toString(), null);
         logger.info("商户入件返回值："+respStr);
    }
    //商户入件状态查询
    public static void merchantCreateQuery(){
    	String temp=UUID.randomUUID().toString().replaceAll("-", "");
    	Map<String, Object> map=new HashMap<>();
    	
    	map.put("merchantId", "164773588");
    	map.put("appId", "31119");
    	map.put("random", temp);
    	String sha256Str = SHA256Utils.getSHA256Str(map, "806614f1ce9b41c794304b8391c30107");
    	map.put("sign", sha256Str);
    	logger.info("sign="+sha256Str);
    	String url="http://payfront.zc.st.meituan.com/api/merchant/create/query";
    	 HttpClientUtil httpclient = new HttpClientUtil(url);
    	 JSONObject object = JSONObject.fromObject(map);
         String respStr = httpclient.sendJsonPost(object.toString(), null);
         logger.info("商户入件状态查询返回值："+respStr);
    }
    //刷卡支付（商户扫用户）
    public static void microPay(){
    	String temp=UUID.randomUUID().toString().replaceAll("-", "");
    	Map<String, Object> map=new HashMap<>();
    	map.put("channel", "wx_barcode_pay");
    	map.put("outTradeNo", "P2017121214270361610035322");
    	map.put("authCode", "134623540964442368");
    	map.put("totalFee", "1");
    	map.put("subject", "测试");
    	map.put("body", "测试");
    	map.put("expireMinutes", "5");
    	
    	map.put("merchantId", "164773588");
    	map.put("appId", "31119");
    	map.put("random", temp);
    	//{body=test, authCode=asdfasf, totalFee=1, subject=tst, outTradeNo=P20171130142703616100352, channel=wx_barcode_pay, expireMinutes=5}
    	String sha256Str = SHA256Utils.getSHA256Str(map, "806614f1ce9b41c794304b8391c30107");
    	map.put("sign", sha256Str);
    	logger.info("sign="+sha256Str);
    	String url="http://payfront.zc.st.meituan.com/api/pay/micropay";
    	 HttpClientUtil httpclient = new HttpClientUtil(url);
    	 JSONObject object = JSONObject.fromObject(map);
         String respStr = httpclient.sendJsonPost(object.toString(), null);
         logger.info("刷卡支付返回值："+respStr);
    }
    public static void microPayQuery(){
    	String temp=UUID.randomUUID().toString().replaceAll("-", "");
    	Map<String, Object> map=new HashMap<>();
    	map.put("outTradeNo", "P20171219150359228100312");
    	map.put("merchantId", "164773588");
    	map.put("appId", "31119");
    	map.put("random", temp);
    	String sha256Str = SHA256Utils.getSHA256Str(map, "806614f1ce9b41c794304b8391c30107");
    	map.put("sign", sha256Str);
    	logger.info("sign="+sha256Str);
    	String url="http://payfront.zc.st.meituan.com/api/pay/query";
    	 HttpClientUtil httpclient = new HttpClientUtil(url);
    	 JSONObject object = JSONObject.fromObject(map);
         String respStr = httpclient.sendJsonPost(object.toString(), null);
         logger.info("刷卡支付返回值："+respStr);
    }
  //获取行政区ID
    public static void getLocationlist(){
    	String temp=UUID.randomUUID().toString().replaceAll("-", "");
    	Map<String, Object> map=new HashMap<>();
    	map.put("appId", "31119");
    	map.put("random", temp);
    	String sha256Str = SHA256Utils.getSHA256Str(map, "806614f1ce9b41c794304b8391c30107");
    	map.put("sign", sha256Str);
    	logger.info("sign="+sha256Str);
    	String url="http://payfront.zc.st.meituan.com/api/common/location";
    	 HttpClientUtil httpclient = new HttpClientUtil(url);
    	 JSONObject object = JSONObject.fromObject(map);
         String respStr = httpclient.sendJsonPost(object.toString(), null);
         logger.info("获取行政区ID返回值："+respStr);
    }
    //获取城市ID
    public static List<Object> getCitylist(){
    	String temp=UUID.randomUUID().toString().replaceAll("-", "");
    	Map<String, Object> map=new HashMap<>();
    	
    	map.put("appId", "31119");
    	map.put("random", temp);
    	String sha256Str = SHA256Utils.getSHA256Str(map, "806614f1ce9b41c794304b8391c30107");
    	map.put("sign", sha256Str);
    	logger.info("sign="+sha256Str);
    	String url="http://payfront.zc.st.meituan.com/api/common/citylist";
    	 HttpClientUtil httpclient = new HttpClientUtil(url);
    	 JSONObject object = JSONObject.fromObject(map);
    	 
         String respStr = httpclient.sendJsonPost(object.toString(), null);
         object = JSONObject.fromObject(respStr);
      // 获取迭代器
         Iterator ite = object.keys();
         List<Object> list =new ArrayList<>();
        		 
         // 遍历jsonObject对象中的数据,将数据添加到jsonToMap对象
         while (ite.hasNext()) {
             String key = ite.next().toString();
             String value = object.get(key).toString();
             if(key.equals("result")){
            	 JSONObject jObject2 = JSONObject.fromObject(value);
            	 String  str = jObject2.get("data").toString();
            	 JSONObject jObject = JSONObject.fromObject(str);
             	Iterator iterator = jObject.keys();
             	while (iterator.hasNext()) {
             		String key2 = iterator.next().toString();
                     String value2 = jObject.get(key2).toString();
                     JSONObject jObject3 = JSONObject.fromObject(value2);
                     Iterator iterator3 = jObject3.keys();
                     while(iterator3.hasNext()){
                     	String key3 = iterator3.next().toString();
                         String value3 = jObject3.get(key3).toString();
                         if(key3.equals("cities")){
                         	//System.out.println(key3+"==="+value3);
                         	 JSONObject jObject4 = JSONObject.fromObject(value3);
                              Iterator iterator4 = jObject4.keys();
                              while(iterator4.hasNext()){
                              	String key4 = iterator4.next().toString();
                                  String value4 = jObject4.get(key4).toString();
                                  System.out.println(key4+"==="+value4);
                                  list.add(key4);
                              }
                         }
                     }
         		}
            	
             }
             
         }
         
         logger.info("获取城市ID返回值："+list.size());
         return list;
    }
  //查询银行信息
    public static List<Map<String, String>> getBankAll(){
    	Map<String, Object> map=new HashMap<>();
    	List<Map<String, String>> list = new ArrayList<>();
    	map.put("appId", "31119");
    	map.put("random", "eXTqDhYtLMpapcbtdEKSlldGgfRQcGpG");
    	String sha256Str = SHA256Utils.getSHA256Str(map, "806614f1ce9b41c794304b8391c30107");
    	map.put("sign", sha256Str);
    	logger.info("sign="+sha256Str);
    	String url="http://payfront.zc.st.meituan.com/api/common/bank/all";
         String respStr = httpGet(url);
         logger.info("查询银行信息接口返回值："+respStr);
         JSONObject jObject = JSONObject.fromObject(respStr);
         JSONArray array = JSONArray.fromObject(jObject.get("data"));
         for(Object object:array){
        	 JSONObject jsonObject = JSONObject.fromObject(object);
        	 System.out.println(jsonObject.get("name"));
        	 Map<String, String> map2 = new HashMap<>();
        	 map2.put("name", jsonObject.get("name").toString());
        	 map2.put("id", jsonObject.get("id").toString());
        	 list.add(map2);
         }
         return list;
    }
    /**
     * 查询分行ID
    * @author: cts
     * @throws Exception 
    * @createTime: 2017年12月9日 上午10:09:18
    * @history: void
     */
    public static void getBankBranch(String cityId,String branchId,String branchName) throws Exception{
    	String url="http://payfront.zc.st.meituan.com/api/common/bank/branch?cityId="+cityId+"&branchId="+branchId;
         String respStr = httpGet(url);
         
         logger.info("cityId = "+cityId+" 查询分行ID接口返回值："+respStr);
         if(StringUtil.isBlank(respStr)){
        	 return;
         }
         JSONObject jObject = JSONObject.fromObject(respStr);
         if(jObject.containsKey("error")){
        	 writeTxtFile(url,"error");
        	 return;
         }
         writeTxtFile("cityId="+cityId+",branchId="+branchId+",branchName="+branchName,"succ");
         JSONArray array = JSONArray.fromObject(jObject.get("data"));
         Set<Map<String, String>> set = new HashSet<>();
         for(Object object:array){
        	 JSONObject jsonObject = JSONObject.fromObject(object);
        	 String branch_name=jsonObject.get("branch").toString();
        	 String branch_id=jsonObject.get("id").toString();
        	 String branch_code = jsonObject.get("code").toString();
        	 int i = select(branch_id);
        	 
        	 if(i>0){
        		 continue;
        	 }
//        	 Map<String, String> map = new HashMap<String, String>();
//        	 map.put("branch_name", branch_name);
//        	 map.put("branch_id", branch_id);
//        	 map.put("cityId", cityId);
//        	 map.put("branchName", branchName);
//        	 map.put("branch_code", branch_code);
//        	 map.put("branchId", branchId);
//        	 set.add(map);
        	 System.out.println("插入 "+branch_id +" "+branch_name +" "+branch_code);
        	 insert(branch_id, branch_name, branch_code, branchId, branchName,cityId);
         }
    }
    public static Connection conn = null;  
    public static Connection dbConn(){
    	 try {  
             Class.forName("com.mysql.jdbc.Driver"); 
            if(conn==null){
//                conn = DriverManager.getConnection("jdbc:mysql://rm-2ze5j8d581v6qhj1ao.mysql.rds.aliyuncs.com:3306/subsysmt?characterEncoding=utf-8", "root_ceshi", "M6ufN4GaJPEF4h0d");//获取连接
            	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dubbo?characterEncoding=utf-8", "root", "root");//获取连接
            }
    	 } catch (Exception e) {  
             e.printStackTrace();  
         }  
    	 return conn;
    }
    public static void insert(String branch_id,String branch_name,String branch_code,String bank_id,String bank_name,String cityId){
    	Connection connection = dbConn();
    	try {
    		StringBuffer sql = new StringBuffer();
    		sql.append("INSERT INTO `mt_bank_info` (`BRANCH_ID`, `BRANCH_NAME`, `BRANCH_CODE`, `BANK_ID`, `BANK_NAME`,city_id) VALUES (");
    		sql.append("'").append(branch_id).append("',");
    		sql.append("'").append(branch_name).append("',");
    		sql.append("'").append(branch_code).append("',");
    		sql.append("'").append(bank_id).append("',");
    		sql.append("'").append(bank_name).append("',");
    		sql.append("'").append(cityId).append("')");
    		System.out.println(sql.toString());
			PreparedStatement ps = connection.prepareStatement(sql.toString());
			ps.execute();
					
		} catch (SQLException e) {
					e.printStackTrace();
				
		}finally {
		}
    }
    public static int select(String branch_id){
    	String sql = "select count(1) as countId from mt_bank_info where branch_id="+branch_id;
    	Connection connection = dbConn();
    	int  result = 0;
    	try {
    		PreparedStatement psPreparedStatement = connection.prepareStatement(sql);
    		ResultSet rSet =  psPreparedStatement.executeQuery();
    		
    		while(rSet.next()){
    			result = rSet.getInt(1);
    		}
    	}catch (Exception e) {
    		e.printStackTrace();
		}
    	return result;
    }
    public static void writeTxtFile(String content,String status)throws Exception{ 
    	String filePath="/Users/cts/Desktop/";
    	if("error".equals(status)){
    		filePath+="error.log";
    	}else{
    		filePath+="success.log";
    	}
    	String str = new String(); //原有txt内容  
        String s1 = new String();//内容更新  
        try {  
            File f = new File(filePath);  
            if (f.exists()) {  
            } else {  
                System.out.print("文件不存在");  
                f.createNewFile();// 不存在则创建  
            }  
            BufferedReader input = new BufferedReader(new FileReader(f));  
  
            while ((str = input.readLine()) != null) {  
                s1 += str + "\n";  
            }  
            input.close();  
            s1 += content;  
            s1+="    "+System.currentTimeMillis();
            BufferedWriter output = new BufferedWriter(new FileWriter(f));  
            output.write(s1);  
            output.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
  
        }    
    	 }  

    public static String httpGet(String url){
        String result="";
          try {
                // 根据地址获取请求
                HttpGet request = new HttpGet(url);//这里发送get请求
                // 获取当前客户端对象
                HttpClient httpClient = new DefaultHttpClient();
                // 通过请求对象获取响应对象
                HttpResponse response = httpClient.execute(request);
                
                // 判断网络连接状态码是否正常(0--200都数正常)
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    result= EntityUtils.toString(response.getEntity(),"utf-8");
                } 
            } catch (Exception e) {
                e.printStackTrace();
            }
        return result;
    }
    public static void refund(){
    	String temp=UUID.randomUUID().toString().replaceAll("-", "");
    	Map<String, Object> map=new HashMap<>();
    	map.put("outTradeNo", "P20171220110913020108344");
    	map.put("refundFee", "1");
    	map.put("refundNo", "R20171220134722792103047");
    	map.put("refundReason", "测试退款");
    	
    	map.put("merchantId", "164773588");
    	map.put("appId", "31119");
    	map.put("random", temp);
    	//{body=test, authCode=asdfasf, totalFee=1, subject=tst, outTradeNo=P20171130142703616100352, channel=wx_barcode_pay, expireMinutes=5}
    	String sha256Str = SHA256Utils.getSHA256Str(map, "806614f1ce9b41c794304b8391c30107");
    	map.put("sign", sha256Str);
    	logger.info("sign="+sha256Str);
    	String url="http://payfront.zc.st.meituan.com/api/refund";
    	 HttpClientUtil httpclient = new HttpClientUtil(url);
    	 JSONObject object = JSONObject.fromObject(map);
         String respStr = httpclient.sendJsonPost(object.toString(), null);
         logger.info("订单退款返回值："+respStr);
    }
    
    public static void refundQuery(){
    	String temp=UUID.randomUUID().toString().replaceAll("-", "");
    	Map<String, Object> map=new HashMap<>();
    	
    	map.put("outTradeNo", "P20171219164543268104162");
    	map.put("refundNo", "P2017121913");
    	
    	map.put("merchantId", "164773588");
    	map.put("appId", "31119");
    	map.put("random", temp);
    	//{body=test, authCode=asdfasf, totalFee=1, subject=tst, outTradeNo=P20171130142703616100352, channel=wx_barcode_pay, expireMinutes=5}
    	String sha256Str = SHA256Utils.getSHA256Str(map, "806614f1ce9b41c794304b8391c30107");
    	map.put("sign", sha256Str);
    	logger.info("sign="+sha256Str);
    	String url="http://payfront.zc.st.meituan.com/api/refund/query";
    	 HttpClientUtil httpclient = new HttpClientUtil(url);
    	 JSONObject object = JSONObject.fromObject(map);
         String respStr = httpclient.sendJsonPost(object.toString(), null);
         logger.info("订单退款查询返回值："+respStr);
    }
    //扫码支付
    public static void precreate(){
    	String temp=UUID.randomUUID().toString().replaceAll("-", "");
    	Map<String, Object> map=new HashMap<>();
    	map.put("outTradeNo", "asdf");
    	map.put("totalFee", "1");
    	map.put("subject", "测试");
    	map.put("body", "测试");
    	map.put("expireMinutes", "2");
    	map.put("channel", "wx_scan_pay");
    	map.put("tradeType", "NATIVE");
    	map.put("openId", "ovmY7w_7PL4rzfDpXrdoNqdu5h6c");
    	map.put("notifyUrl", "http://test.notify.pay.pufubao.net/notify/mt/pay");
    	
    	map.put("merchantId", "164723298");
    	map.put("appId", "31119");
    	map.put("random", "asdfasfasdfasdfas");
    	String sha256Str = SHA256Utils.getSHA256Str(map, "806614f1ce9b41c794304b8391c30107");
    	map.put("sign", sha256Str);
    	logger.info("sign="+sha256Str);
    	String url="http://payfront.zc.st.meituan.com/api/precreate";
    	 HttpClientUtil httpclient = new HttpClientUtil(url);
    	 JSONObject object = JSONObject.fromObject(map);
         String respStr = httpclient.sendJsonPost(object.toString(), null);
         logger.info("扫码支付（用户扫商户）查询返回值："+respStr);
    }
    public static void paymentQuery(){
    	String temp=UUID.randomUUID().toString().replaceAll("-", "");
    	Map<String, Object> map=new HashMap<>();
    	map.put("beginPaymentDate", "20171201");
    	map.put("endPaymentDate", "20171218");
//    	map.put("status", "4");
    	
    	map.put("merchantId", "164773588");
    	map.put("appId", "31119");
    	map.put("random", temp);
    	String sha256Str = SHA256Utils.getSHA256Str(map, "806614f1ce9b41c794304b8391c30107");
    	map.put("sign", sha256Str);
    	logger.info("sign="+sha256Str);
    	String url="http://payfront.zc.st.meituan.com/api/finance/payment/query";
    	 HttpClientUtil httpclient = new HttpClientUtil(url);
    	 JSONObject object = JSONObject.fromObject(map);
         String respStr = httpclient.sendJsonPost(object.toString(), null);
         logger.info("查询打款列表返回值："+respStr);
    }
    
    public static void settleQuery(){
    	String temp=UUID.randomUUID().toString().replaceAll("-", "");
    	Map<String, Object> map=new HashMap<>();
    	map.put("cutDay", "20171219");
    	
    	map.put("merchantId", "164773588");
    	map.put("appId", "31119");
    	map.put("random", temp);
    	String sha256Str = SHA256Utils.getSHA256Str(map, "806614f1ce9b41c794304b8391c30107");
    	map.put("sign", sha256Str);
    	logger.info("sign="+sha256Str);
    	String url="http://payfront.zc.st.meituan.com/api/finance/settle/query";
    	 HttpClientUtil httpclient = new HttpClientUtil(url);
    	 JSONObject object = JSONObject.fromObject(map);
         String respStr = httpclient.sendJsonPost(object.toString(), null);
         logger.info("查询打款列表返回值："+respStr);
    }
    public static void verifyBank(){
    	String temp=UUID.randomUUID().toString().replaceAll("-", "");
    	Map<String, Object> map=new HashMap<>();
    	map.put("cardno", "622909328264847714");
    	map.put("name", "曹铁拴");
    	map.put("requestNo", "2017"+System.currentTimeMillis());
    	map.put("acctype", "2");
    	map.put("branchId", "41332");
    	
    	map.put("merchantId", "164773588");
    	map.put("appId", "31119");
    	map.put("random", temp);
    	String sha256Str = SHA256Utils.getSHA256Str(map, "806614f1ce9b41c794304b8391c30107");
    	map.put("sign", sha256Str);
    	logger.info("sign="+sha256Str);
    	String url="http://payfront.zc.st.meituan.com/api/card/verify";
    	 HttpClientUtil httpclient = new HttpClientUtil(url);
    	 JSONObject object = JSONObject.fromObject(map);
         String respStr = httpclient.sendJsonPost(object.toString(), null);
         logger.info("查询打款列表返回值："+respStr);
    }
    public static void main(String[] args) throws Exception {
    	verifyBank();
    }
}
	