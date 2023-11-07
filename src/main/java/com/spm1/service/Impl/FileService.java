package com.spm1.service.Impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.spm1.configuration.AliyunOssConfig;
import com.spm1.enumaration.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import java.io.ByteArrayInputStream;

import java.io.*;

@Service
public class FileService {
    private static final String[] FILE_TYPE = new String[]{".bmp", ".jpg",
            ".jpeg", ".gif", ".png"};
    private final String endPoint = "oss-cn-beijing.aliyuncs.com";// 地域节点
    private final String accessKeyId = "LTAI5t9gV2wVUH6dgXeMkM7X";
    private final String accessKeySecret = "fGuvAQzcJM5olM5Y5ioLemVeiTWYKV";
    private final String bucketName = "spm-pancake";// OSS的Bucket名称
    private final String urlPrefix = "http://spm-pancake.oss-cn-beijing.aliyuncs.com";// Bucket 域名
    private final String fileHost = "http://spm-pancake.oss-cn-beijing.aliyuncs.com/";// 目标文件夹

    private final OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);// 注入阿里云oss文件服务器客户端

    public String imageUpload(MultipartFile uploadFile, String newFileName,  String filePath) {
        String returnImageUrl;
        // 校验图片格式
        boolean isLegal = false;
        for (String type : FILE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), type)) {
                isLegal = true;
                break;
            }
        }
        if (!isLegal) {// 如果图片格式不合法
            return StatusCode.ERROR.getMsg();
        }
        // 获取文件原名称
        String originalFilename = uploadFile.getOriginalFilename();
        // 获取文件类型
        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 文件上传的路径地址
        String uploadImageUrl =  "/" + filePath + "/" + newFileName;
        // 获取文件输入流
        InputStream inputStream = null;
        try {
            inputStream = uploadFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * 下面两行代码是重点坑：
         * 现在阿里云OSS 默认图片上传ContentType是image/jpeg
         * 也就是说，获取图片链接后，图片是下载链接，而并非在线浏览链接，
         * 因此，这里在上传的时候要解决ContentType的问题，将其改为image/jpg
         */
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType("image/jpg");

        //文件上传至阿里云OSS
        ossClient.putObject(bucketName, uploadImageUrl, inputStream, meta);
        /**
         * 注意：在实际项目中，文件上传成功后，数据库中存储文件地址
         */
        // 获取文件上传后的图片返回地址
        returnImageUrl = "http://" + bucketName + "." + endPoint + "/" + uploadImageUrl;

        return returnImageUrl;
    }

    public String txtUpload(String content, String newFileName,  String filePath) {
        String returnTxtUrl;
        // 文件上传的路径地址
        String uploadTxtUrl =  "/" + filePath + "/" + newFileName;
        try {
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uploadTxtUrl, new ByteArrayInputStream(content.getBytes()));
            // 上传字符串。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            returnTxtUrl = "http://" + bucketName + "." + endPoint + "/" + uploadTxtUrl;
            return returnTxtUrl;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return "fail";
    }

    public String txtRead(String filePath){
        StringBuilder readTxt = new StringBuilder();
        try {
            // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
            OSSObject ossObject = ossClient.getObject(bucketName, filePath);

            // 读取文件内容。
            BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                readTxt.append(line);
            }
            // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
            reader.close();
            // ossObject对象使用完毕后必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
            ossObject.close();
            return readTxt.toString();
        }
        catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        }
        catch (Throwable ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        }
        finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return "fail";
    }
}
