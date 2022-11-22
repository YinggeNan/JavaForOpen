package com.cdb.xmlparse.xml_to_bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Author yingge
 * @Date 2022/11/15 21:06
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Field")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFieldModel {
    @XmlAttribute
    private String name;
    @XmlAttribute
    private String dataType;
    @XmlAttribute
    private String format;
    @XmlAttribute
    private Integer sequence;
    @XmlAttribute
    private Integer length;
}
