package com.cdb.xml_to_bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @Author yingge
 * @Date 2022/11/15 21:10
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ProductSeries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSeriesModel {
    @XmlElement(name = "FieldGroup")
    private List<ProductFieldGroupModel> productFieldGroupModels;
    @XmlElement(name="Product")
    private List<ProductModel> productModels;

    public void Init() throws Exception {
        if(productModels!=null && productModels.size()<=0){
            return;
        }
        for(ProductModel productModel: productModels){
            productModel.initFieldsWithReferenceFieldGroups(productFieldGroupModels);
        }
    }
}

