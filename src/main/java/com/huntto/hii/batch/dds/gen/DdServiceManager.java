package com.huntto.hii.batch.dds.gen;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.4
 * 2015-12-08T01:19:38.542+08:00
 * Generated source version: 3.1.4
 * 
 */
@WebService(targetNamespace = "http://service.dds.webservice.webapp.hii.huntto.com/", name = "DdServiceManager")
@XmlSeeAlso({ObjectFactory.class})
public interface DdServiceManager {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "dds", targetNamespace = "http://service.dds.webservice.webapp.hii.huntto.com/", className = "com.huntto.hii.batch.dds.gen.Dds")
    @WebMethod
    @ResponseWrapper(localName = "ddsResponse", targetNamespace = "http://service.dds.webservice.webapp.hii.huntto.com/", className = "com.huntto.hii.batch.dds.gen.DdsResponse")
    public java.lang.String dds(
        @WebParam(name = "user_id", targetNamespace = "")
        java.lang.String userId,
        @WebParam(name = "pwd", targetNamespace = "")
        java.lang.String pwd,
        @WebParam(name = "xml_data", targetNamespace = "")
        java.lang.String xmlData
    );
}