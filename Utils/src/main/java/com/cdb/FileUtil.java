package com.cdb;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * @Author yingge
 * @Date 2022/11/22 21:37
 */
public class FileUtil {
    public static String readFileToStringFromSystemPath(String filePath) {
        String res = null;
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            res = new String(bytes, "utf-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
    public static String readFileToStringFromClassPath(String filePath) {
        String res = null;
        try {
            File file = new ClassPathResource(filePath).getFile();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            res = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
}
