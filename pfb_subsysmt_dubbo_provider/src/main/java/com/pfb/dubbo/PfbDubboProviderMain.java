package com.pfb.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PfbDubboProviderMain {
	public static void main(String[] args) throws Exception {
		try{
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
	                new String[] {"application-context.xml"});
	        context.start();
//	        System.in.read(); // press any key to exit
		}catch(Exception e){
			e.printStackTrace();
		}
        while(true){}
    }
}
