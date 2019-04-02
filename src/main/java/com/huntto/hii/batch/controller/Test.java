package com.huntto.hii.batch.controller;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class Test {
	
	private final static String InParamTemplate = "<DDSHII><DSCR><SENDCODE>%s</SENDCODE><SENDTIME>%s</SENDTIME><VERSIONS>0100</VERSIONS></DSCR>"
			+ "<DATA><DDS_NAME>%s</DDS_NAME><DDS_DATE>%s</DDS_DATE><PAGE_NO>%d</PAGE_NO></DATA></DDSHII>";

	public static void main(String[] args) {
		
		
		// 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://localhost:8083/hii/services/DdService?wsdl");

        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
        Object[] objects = new Object[0];
        try {
            // invoke("方法名",参数1,参数2,参数3....);
        	String user_id= "3305012";
        	String pwd = "hzsdx3305012";
        	String xmlData = "";
            objects = client.invoke("dds", user_id,pwd,xmlData);
            System.out.println("返回数据:" + objects[0]);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
	}

}
