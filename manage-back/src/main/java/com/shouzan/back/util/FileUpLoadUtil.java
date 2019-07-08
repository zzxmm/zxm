package com.shouzan.back.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.region.Region;
import com.shouzan.back.constant.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @ClassName: com.shouzan.back.util.FileUpLoadUtil
 * @Author: man.z
 * @Date: 2019-05-13 11:03
 * @Description: 图片的上传下载
 */
@Slf4j
public class FileUpLoadUtil {

    private static final String SECRETID = "AKIDtb5JI1YjiRvAE3utxnOktoUxKXYNpQef";

    private static final String SECRETKEY = "wgEajgPs88tPlskNkriIcgW8VcU8XgaE";

    private static final String REGION = "ap-beijing";

    private static final String BUCKET = "fontend-static-1253421772";

    /**
     * @Description: 上传图片到服务器

     * @[param] [file, path, fileName]
     * @return boolean
     * @author:  man.z
     * @date:  2019-05-13 11:29
     */
    public static boolean upload(MultipartFile file, String path, String fileName){
        String realPath = path + "/" + fileName;
        File dest = new File(realPath);
        //判断文件父目录是否存在
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        try {
            //保存文件
            file.transferTo(dest);
            return Response.SUCCESS;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * @Description: 获取文件的后缀

     * @[param] [fileName]
     * @return java.lang.String
     * @author:  man.z
     * @date:  2019-05-13 12:10
     */
    public static String getSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * @Description: 生成新的文件名

     * @[param] [fileOriginName] 源文件名
     * @return java.lang.String
     * @author:  man.z
     * @date:  2019-05-13 12:11
     */
    public static String getFileName(String fileOriginName){
        return UUID.randomUUID().toString().replace("-","") + getSuffix(fileOriginName);
    }

    /**
     * @Description: 获取文件全路径

     * @[param] [path, fileName]
     * @return java.lang.String
     * @author:  man.z
     * @date:  2019-05-13 12:14
     */
    public static String getFullPathName(String path , String fileName){
        return path+"/"+fileName;
    }

    /**
     * @Description: 删除临时文件

     * @[param] [filePath, fileName]
     * @return void
     * @author:  man.z
     * @date:  2019-05-13 12:17
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

    /**
     * @Description: POST请求cos上传图片

     * @[param] [path, cosPath, cosName, fileName]
     * @return java.lang.String
     * @author:  man.z
     * @date:  2019-05-13 12:19
     */
    public static boolean HttpPostCOS(String fullPathName, String fileName) {

        // 1 初始化用户身份信息（secretId, secretKey）。
        COSCredentials cred = new BasicCOSCredentials(SECRETID, SECRETKEY);
        // 采用了新的region名字，可用region的列表可以在官网文档中获取，也可以参考下面的XML SDK和JSON SDK的地域对照表
        ClientConfig clientConfig = new ClientConfig(new Region(REGION));
        COSClient cosClient = new COSClient(cred, clientConfig);

        // bucket的名字需要的包含appid
        String bucketName = BUCKET;

        // 以下是向这个存储桶上传一个文件的示例
        String key = "shouzan-static/common_static/images/common" + "/" + fileName;

        File localFile = new File(fullPathName);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        // 设置存储类型, 默认是标准(Standard), 低频(standard_ia)
        putObjectRequest.setStorageClass(StorageClass.Standard_IA);
        String etag = "";
        try {
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            // putobjectResult会返回文件的etag
            etag = putObjectResult.getETag();
        } catch (CosServiceException e) {
            log.error(e.getMessage());
        } catch (CosClientException e) {
            log.error(e.getMessage());
        }
        // 关闭客户端
        cosClient.shutdown();
        return etag != null ? true : false;
    }
    // 获取新名称
    public static String getImgName(String fileName,String lastName) {
        String[] split = fileName.split("\\.");
        String newName = split[0] + lastName + "." + split[1];
        return newName;
    }

}
