package com.huntto.hii.batch.dds.gen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
@XmlRegistry
public class ObjectFactory {

    private final static QName _Dds_QNAME = new QName("http://service.dds.webservice.webapp.hii.huntto.com/", "dds");
    private final static QName _DdsResponse_QNAME = new QName("http://service.dds.webservice.webapp.hii.huntto.com/", "ddsResponse");

    public ObjectFactory() {
    }

    public Dds createDds() {
        return new Dds();
    }

    public DdsResponse createDdsResponse() {
        return new DdsResponse();
    }

    @XmlElementDecl(namespace = "http://service.dds.webservice.webapp.hii.huntto.com/", name = "dds")
    public JAXBElement<Dds> createDds(Dds value) {
        return new JAXBElement<Dds>(_Dds_QNAME, Dds.class, null, value);
    }

    @XmlElementDecl(namespace = "http://service.dds.webservice.webapp.hii.huntto.com/", name = "ddsResponse")
    public JAXBElement<DdsResponse> createDdsResponse(DdsResponse value) {
        return new JAXBElement<DdsResponse>(_DdsResponse_QNAME, DdsResponse.class, null, value);
    }

}
