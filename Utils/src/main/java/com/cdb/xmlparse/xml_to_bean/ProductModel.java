package com.cdb.xmlparse.xml_to_bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @Author yingge
 * @Date 2022/11/15 21:12
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {
    @XmlAttribute
    private String name;
    @XmlAttribute
    private String category;
    @XmlAttribute
    private String type;
    @XmlElement(name = "FieldGroup")
    private List<ProductFieldGroupModel> productFieldGroupModels;
    private List<ProductFieldModel> productSortedFieldModels;

    /**
     * 将product里引用的 ProductFieldGroup 里的fields和自己定义的fields都塞到sortedMethods,
     * 按照 sequence 定义的顺序排序：首先 ProductFieldGroup 级别的 sequence 排序，然后 ProductFieldGroup 内的
     * sequence排序
     * @param referenceProductFieldGroupModelList
     */
    public void initFieldsWithReferenceFieldGroups(List<ProductFieldGroupModel> referenceProductFieldGroupModelList) throws Exception {
        if(this.productFieldGroupModels == null ||  this.productFieldGroupModels.size()<1){
            this.productSortedFieldModels = null;
            return;
        }
        // 迭代当前Product的每个filedGroup
        for(ProductFieldGroupModel currentFieldGroupModel: this.productFieldGroupModels){
            if(StringUtils.isNotBlank(currentFieldGroupModel.getReferenceId())){
                // fieldGroup不能同时包含 referenceId 和 fields
                if(currentFieldGroupModel.getFieldModelList()!=null && currentFieldGroupModel.getFieldModelList().size()>0){
                    throw new Exception(String.format("referenceId=%s,不能同时包含 referenceId和fields", currentFieldGroupModel.getReferenceId()));
                }
                // 在可引用的所有 FieldGroup中找到和当前 FieldGroup匹配的哪个
                ProductFieldGroupModel foundReferenceGroup = referenceProductFieldGroupModelList.stream().filter(referenceFieldGroup -> StringUtils.equals(currentFieldGroupModel.getReferenceId(), referenceFieldGroup.getId()))
                        .findFirst().orElse(null);
                if(foundReferenceGroup==null){
                    throw new Exception(String.format("找不到referenceId=%s的FieldGroup",currentFieldGroupModel.getReferenceId()));
                }
                // 把 reference FieldGroup中的所有字段放到当前的 fieldGroup中
                currentFieldGroupModel.setFieldModelList(foundReferenceGroup.getFieldModelList());
            }
        }
    }
}
