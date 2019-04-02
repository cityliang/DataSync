package com.huntto.hii.batch.dds.gen;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;

@WebServiceClient(name = "DdService", 
                  wsdlLocation = "http://115.29.2.183:8888/hii/services/DdService?wsdl",
                  targetNamespace = "http://dds.webservice.hii.huntto.com/") 
public class DdService extends Service {

    public final static QName SERVICE = new QName("http://dds.webservice.hii.huntto.com/", "DdService");
    public final static QName DdServiceManagerImplPort = new QName("http://dds.webservice.hii.huntto.com/", "DdServiceManagerImplPort");

    public DdService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    @WebEndpoint(name = "DdServiceManagerImplPort")
    public DdServiceManager getDdServiceManagerImplPort() {
        return super.getPort(DdServiceManagerImplPort, DdServiceManager.class);
    }

//    @WebEndpoint(name = "DdServiceManagerImplPort")
//    public DdServiceManager getDdServiceManagerImplPort(WebServiceFeature... features) {
//        return super.getPort(DdServiceManagerImplPort, DdServiceManager.class, features);
//    }

}
