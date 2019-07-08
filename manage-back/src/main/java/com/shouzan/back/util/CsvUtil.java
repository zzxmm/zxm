package com.shouzan.back.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @ClassName: com.shouzan.back.util.CsvUtil
 * @Author: bin.yang
 * @Date: 2019/5/31 15:42
 * @Description: TODO
 */
@Slf4j
public class CsvUtil {



    /**
     * @Description: 下载到客户端
     * @param response
     * @param csvFilePath
     * @param fileName
     * @[param] [response, csvFilePath, fileName]
     * @return void
     * @author:  bin.yang
     * @date:  2019/5/31 3:44 PM
     */
    public static void exportFile(HttpServletResponse response, String csvFilePath, String fileName)
            throws IOException {
        response.setContentType("application/csv;text/html=UTF-8");
        response.setHeader("Content-Disposition",
                "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

        InputStream in = null;

        try {
            in = new FileInputStream(csvFilePath);
            int len = 0;
            byte[] buffer = new byte[1024];
            response.setCharacterEncoding("UTF-8");
            OutputStream out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                //utf-8的文件在头部有三个字段是用来标识该文件的格式的
//                out.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF });//输出那三个字节的标示信息
                out.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            log.error("异常信息 : [{}]" , e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    log.error("异常信息 : [{}]" , e);
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * @Description: 删除临时文件
     * @param filePath
     * @param fileName
     * @[param] [filePath, fileName]
     * @return void
     * @author:  bin.yang
     * @date:  2019/5/31 3:43 PM
     */
    public static void deleteFile(String filePath, String fileName) {
        File file = new File(filePath);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    if (files[i].getName().equals(fileName)) {
                        files[i].delete();
                        return;
                    }
                }
            }
        }
    }

}
