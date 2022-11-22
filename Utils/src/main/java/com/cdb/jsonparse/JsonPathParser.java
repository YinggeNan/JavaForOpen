package com.cdb.jsonparse;

import com.cdb.FileUtil;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Predicate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * reference:
 * 1.<a href="https://www.baeldung.com/guide-to-jayway-jsonpath">jsonpath的一个简单的教程</a>
 * 2.<a href="https://www.ietf.org/archive/id/draft-goessner-dispatch-jsonpath-00.html">jsonpath规范</a>
 *
 * @Author yingge
 * @Date 2022/11/22 19:51
 */
public class JsonPathParser {

    /**
     * 泛型方法使用
     *
     * @param systemFilePath
     * @param pattern
     * @param <T>
     * @return
     */
    public static <T> T parseWithSystemPath(String systemFilePath, String pattern, Class<?> clazz, Predicate... filters) {
        String jsonContent = FileUtil.readFileToStringFromSystemPath(systemFilePath);
        return parseWithString(jsonContent, pattern, clazz, filters);
    }
    public static <T> T parseWithSystemPath(String systemFilePath, String pattern, Predicate... filters) {
        String jsonContent = FileUtil.readFileToStringFromSystemPath(systemFilePath);
        return parseWithString(jsonContent, pattern, filters);
    }

    public static <T> T parseWithClassPath(String classFilePath, String pattern, Predicate... filters) {
        Resource resource = new ClassPathResource(classFilePath);
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return parseWithInputStream(inputStream, pattern, filters);
    }

    /**
     *
     * @param classFilePath classPath路径,即 resources目录下的位置
     * @param pattern jsonPath的Pattern
     * @param clazz 要返回的数据类型
     * @param filters 过滤器
     * @return
     * @param <T>
     */
    public static <T> T parseWithClassPath(String classFilePath, String pattern, Class<?> clazz, Predicate... filters) {
        Resource resource = new ClassPathResource(classFilePath);
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return parseWithInputStream(inputStream, pattern, clazz,filters);
    }
    public static <T> T parseWithString(String jsonContent, String pattern, Class<?> clazz, Predicate... filters) {
        DocumentContext jsonContext = JsonPath.parse(jsonContent);
        return (T) jsonContext.read(pattern, clazz, filters);
    }
    public static <T> T parseWithString(String jsonContent, String pattern, Predicate... filters) {
        DocumentContext jsonContext = JsonPath.parse(jsonContent);
        return (T) jsonContext.read(pattern, filters);
    }
    public static <T> T parseWithInputStream(InputStream inputStream, String pattern, Predicate... filters) {
        DocumentContext jsonContext = JsonPath.parse(inputStream);
        return (T) jsonContext.read(pattern, filters);
    }
    public static <T> T parseWithInputStream(InputStream inputStream, String pattern, Class<?> clazz, Predicate... filters) {
        DocumentContext jsonContext = JsonPath.parse(inputStream);
        return (T) jsonContext.read(pattern, clazz, filters);
    }

}
