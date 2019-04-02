/*
 * 系统名称：卫生监督管理系統
 * 版权所有：(c)2014 浙江创得信息技术有限公司，所有版权保留
 * 版权声明：本软件所有权归浙江创得信息技术有限公司，
 *          未经浙江创得信息技术有限公司同意，禁止拷贝、修改和发布本系统代码。
 * 开发日期：2014年10月
 */
package com.huntto.hii.batch.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;


/**
 * XML工具
 */
final public class XmlUtil {

	/**
	 * 解析xml字符串
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static Document parse(String xml) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		StringReader reader = new StringReader(xml);
		InputSource is = new InputSource(reader);
		Document doc = db.parse(is);
		return doc;
	}
	
	/**
	 * Java对象序列化为XML文档
	 * @param obj
	 * @return
	 */
	public static String serialize(Object obj, Class<?>... classesToBeBound) throws Exception{
		Marshaller marshaller = createMarshaller(classesToBeBound);  
		StringWriter writer = new StringWriter(); 
		marshaller.marshal(obj, writer);

		return writer.toString();
	}

	/**
	 * 反序列化XML文档到Java对象
	 * @param xml
	 * @return
	 */
	public static Object deserialize(String xml, Class<?>... classesToBeBound) throws Exception{
		Unmarshaller unmarshaller = createUnmarshaller(classesToBeBound);  
		StringReader reader = new StringReader(xml); 
		Object obj = unmarshaller.unmarshal(reader);

		return obj;
	}

	/**
	 * 反序列化XML文档到Java对象
	 * @param xml
	 * @return
	 */
	public static Object deserialize(File xmlFile, Class<?>... classesToBeBound) throws Exception{
		return deserialize(xmlFile, "utf-8", classesToBeBound);
	}

	/**
	 * 反序列化XML文档到Java对象
	 * @param xml
	 * @return
	 */
	public static Object deserialize(File xmlFile, String charsetName, Class<?>... classesToBeBound) throws Exception{
		Unmarshaller unmarshaller = createUnmarshaller(classesToBeBound);  
		InputStreamReader reader = null;
		Object obj = null;
		
		try {
			reader = new InputStreamReader(new FileInputStream(xmlFile), charsetName);
			obj = unmarshaller.unmarshal(reader);
		} finally {
			try {
				if(reader != null) reader.close();
			} catch (Exception e2) {
			}
		}
		
		return obj;
	}

	/**
	 * 反序列化XML文档到Java对象
	 * @param xml
	 * @return
	 */
	public static Object deserialize(Node xmlNode, Class<?>... classesToBeBound) throws Exception{
		Unmarshaller unmarshaller = createUnmarshaller(classesToBeBound);  
		Object obj = unmarshaller.unmarshal(xmlNode);

		return obj;
	}

	//------------------------------------------------------
	private static Marshaller createMarshaller(Class<?>... classesToBeBound) throws JAXBException{
		JAXBContext context = JAXBContext.newInstance(classesToBeBound);
		Marshaller marshaller = context.createMarshaller();  
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		return marshaller;
	}
	private static Unmarshaller createUnmarshaller(Class<?>... classesToBeBound) throws JAXBException{
		JAXBContext context = JAXBContext.newInstance(classesToBeBound);
		Unmarshaller unmarshaller = context.createUnmarshaller();  
		return unmarshaller;
	}
}
