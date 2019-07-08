package com.shouzan.back.util;

import com.alibaba.fastjson.JSONObject;
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
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * @Author: bin.yang
 * @Date: 2019/1/4 10:02
 * @Description:  微信公众号卡卷工具类
 */
@Slf4j
public class WeChatCoupon {

    public final static String WCCARD_UPLOAD = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";

    public final static String WCCARD_CREATE = "https://api.weixin.qq.com/card/create?access_token=ACCESS_TOKEN";

    public final static String WCCARD_UPDATE = "https://api.weixin.qq.com/card/update?access_token=ACCESS_TOKEN";

    public final static String WCCARD_STOCK = "https://api.weixin.qq.com/card/modifystock?access_token=ACCESS_TOKEN";

    public final static String WCCARD_DELETE = "https://api.weixin.qq.com/card/delete?access_token=ACCESS_TOKEN";

    private static final String SECRETID = "AKIDRo1AC4yHcoxxytbonaDfzKOWj2RwpC3r";

    private static final String SECRETKEY = "DVFIPcuOvXC2JsMRa0V7nZPKGGtcPTiA";

    private static final String REGION = "ap-beijing";

    private static final String BUCKET = "fontend-static-1253421772";

    /**
     * @Description: (上传图片到服务器)
     * @param file
     * @param path
     * @param fileName
     * @[param] [file, path, fileName]
     * @return boolean
     * @author:  bin.yang
     * @date:  2019/1/4 10:07 AM
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
     * 获取文件后缀
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成新的文件名
     * @param fileOriginName 源文件名
     * @return
     */
    public static String getFileName(String fileOriginName){
        return UUID.randomUUID().toString().replace("-","") + getSuffix(fileOriginName);
    }

    /**
     * 获取文件全路径
     * @param path
     * @param fileName
     * @return
     */
    public static String getFullPathName(String path , String fileName){
        return path+"/"+fileName;
    }

    /**
     * @Description: (上传图片到微信CDN获取)
     * @param filePath
     * @param token
     * @[param] [filePath, token]
     * @return com.alibaba.fastjson.JSONObject
     * @author:  bin.yang
     * @date:  2019/1/4 5:13 PM
     */
    public static JSONObject sendWeChat( String filePath ,String token) throws Exception {
        String result = null;
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("文件不存在");
        }
        /**
         * 第一部分
         */
        String url = WCCARD_UPLOAD.replace("ACCESS_TOKEN", token);
        URL urlObj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false); // post方式不能使用缓存
        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary="+ BOUNDARY);
        // 请求正文信息
        // 第一部分：
        StringBuilder sb = new StringBuilder();
        sb.append("--"); // 必须多两道线
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\""+ file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
        byte[] head = sb.toString().getBytes("utf-8");
        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);
        // 文件正文部分
        // 把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();
        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
        out.write(foot);
        out.flush();
        out.close();
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if(result==null){
                result = buffer.toString();
            }
        } catch (IOException e) {
            log.error("发送POST请求出现异常！" + e);
            log.error(e.getMessage());
            throw new IOException("数据读取异常");
        } finally {
            if(reader!=null){
                reader.close();
            }
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject;
    }

    /**
     * @Description: (删除临时文件)
     * @param filePath
     * @param fileName
     * @[param] [filePath, fileName]
     * @return void
     * @author:  bin.yang
     * @date:  2018/12/14 2:52 PM
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
     * @Description: (POST请求调用微信API传输JSON数据)
     * @param token
     * @param json
     * @[param] [token, json]
     * @return com.alibaba.fastjson.JSONObject
     * @author:  bin.yang
     * @date:  2019/1/4 5:12 PM
     */
    public static JSONObject HttpPostWeChat(String token,String apiUrl , String json) {
        JSONObject jsonObject = null;
        String url = apiUrl.replace("ACCESS_TOKEN", token);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try{
            httpClient = HttpClients.createDefault();
            //创建httpPost对象
            HttpPost httpPost = new HttpPost(url);
            //给httpPost设置JSON格式的参数
            StringEntity requestEntity = new StringEntity(json,"UTF-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(requestEntity);
            //发送HttpPost请求，获取返回值
            String execute = httpClient.execute(httpPost, responseHandler);//调接口获取返回值时，必须用此方法
            jsonObject = JSONObject.parseObject(execute);
        }
        catch(Exception e)
        {
            log.error(e.getMessage());
            jsonObject.put("errcode",1);
        }
        finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                log.error(e.getMessage());
            }
        }
        return jsonObject;
    }

    /**
     * @Description: (POST请求COS服务器上传图片)
     * @param path
     * @param cosName
     * @param fileName
     * @[param] [path, cosName, fileName]
     * @return java.lang.String
     * @author:  bin.yang
     * @date:  2019/1/11 10:11 AM
     */
    public static String HttpPostCOS(String path, String cosPath ,String cosName, String fileName) {

        // 1 初始化用户身份信息（secretId, secretKey）。
        COSCredentials cred = new BasicCOSCredentials( SECRETID, SECRETKEY);
        // 采用了新的region名字，可用region的列表可以在官网文档中获取，也可以参考下面的XML SDK和JSON SDK的地域对照表
        ClientConfig clientConfig = new ClientConfig(new Region(REGION));
        COSClient cosClient = new COSClient(cred, clientConfig);

        // bucket的名字需要的包含appid
        String bucketName = BUCKET;

        // 以下是向这个存储桶上传一个文件的示例
        String key = cosPath+"/"+cosName;

        File localFile = new File(path+"/"+fileName);
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
        return etag;
    }

    /**
     * @Auther: bin.yang
     * @Date: 2019/1/11 10:11 AM
     *
     * @Description: 获取 文件名加后缀
     */
    public static String getName(String urlName , String fileName){
        int last = urlName.lastIndexOf("/");
        int lastTwo = urlName.lastIndexOf("/", last-1);
        String name = urlName.substring(lastTwo+1, last);
        String Suffix = fileName.substring(fileName.lastIndexOf("."));
        return name+Suffix;
    }
}
