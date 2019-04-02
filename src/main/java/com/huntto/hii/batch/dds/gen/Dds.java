
package com.huntto.hii.batch.dds.gen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dds", propOrder = {
    "userId",
    "pwd",
    "xmlData"
})
public class Dds {

    @XmlElement(name = "user_id")
    protected String userId;
    protected String pwd;
    @XmlElement(name = "xml_data")
    protected String xmlData;

}
