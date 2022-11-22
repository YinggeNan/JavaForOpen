package com.cdb.xmlparse.xml_to_bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @Author yingge
 * @Date 2022/11/15 21:05
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "FieldGroup")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFieldGroupModel {
    @XmlAttribute
    private String name;
    @XmlAttribute
    private String id;
    @XmlAttribute
    private String referenceId;
    @XmlElement(name = "Field")
    private List<ProductFieldModel> fieldModelList;
}
