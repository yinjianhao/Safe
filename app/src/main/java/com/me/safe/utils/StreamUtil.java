package com.me.safe.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by yin on 2016/7/6.
 * 流工具类
 */
public class StreamUtil {

    /**
     * 输入流转字符串
     *
     * @param is 输入流
     * @return 转换后的字符串
     * @throws IOException
     */
    public static String stream2String(InputStream is) throws IOException {
        byte[] b = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int len;

        if ((len = is.read()) != -1) {
            byteArrayOutputStream.write(b, 0, len);
        }

        return byteArrayOutputStream.toString();
    }

    /**
     * 输入流转字符串
     *
     * @param is 输入流
     * @return 转换后的字符串
     * @throws IOException
     */
    public static String stream2String2(InputStream is) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        StringBuilder stringBuffer = new StringBuilder();
        String line;
        if ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }

        return stringBuffer.toString();
    }
}
