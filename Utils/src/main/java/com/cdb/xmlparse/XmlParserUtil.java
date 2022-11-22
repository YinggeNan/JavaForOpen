package com.cdb.xmlparse;

import com.cdb.xmlparse.xml_to_bean.ProductSeriesModel;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

/**
 * @Author yingge
 * @Date 2022/11/19 1:51
 */
public class XmlParserUtil {
    public static void main(String[] args) {
        testXmlToBean();
    }
    public static void testXmlToBean(){
        try {
            ProductSeriesModel productSeriesModel = deserializeFromXmlToPOJO(ProductSeriesModel.class, "/ProductSeries1.xml");
            System.out.println(productSeriesModel.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static Object xmlStrToOject(Class<?> clazz, String xmlStr) throws Exception {
        Object xmlObject = null;
        Reader reader = null;
        JAXBContext context = JAXBContext.newInstance(clazz);
        // XML 转为对象的接口
        Unmarshaller unmarshaller = context.createUnmarshaller();
        reader = new StringReader(xmlStr);
        //以文件流的方式传入这个string
        xmlObject = unmarshaller.unmarshal(reader);
        if (null != reader) {
            reader.close();
        }
        return xmlObject;
    }

    /**
     * convert to stream from configFilePath to the java bean typed clazz
     * @param clazz
     * @param configFilePath
     * @return
     * @param <T>
     * @throws Exception
     */
    public static <T> T deserializeFromXmlToPOJO(Class<T> clazz, String configFilePath) throws Exception {
        Resource resource = new ClassPathResource(configFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), "utf-8"));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while((line = br.readLine())!=null){
            buffer.append(line);
        }
        br.close();
        // convert xml file to java bean
        return (T)xmlStrToOject(clazz, buffer.toString());
    }
}
