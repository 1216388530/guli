package com.ali.oss.service.impl;

import com.ali.oss.service.OssService;
import com.ali.oss.utils.ConstantPropertiesUtils;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    //具体上传代码
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 上传文件流。
        InputStream inputStream = null;
            try {
                inputStream = file.getInputStream();
                //1.添加文件名随机值
                String uuid = UUID.randomUUID().toString().replaceAll("-","");
                //2.把文件按照日期分类
                //年/月/日/文件名
                String datePath = new DateTime().toString("yyyy/MM/dd");
                //确定path：年/月/日/随机串.格式
                String fileName = datePath+"/"+uuid+"."+(file.getOriginalFilename().split("\\."))[1];

                //第二参数是上传到阿里云中的路径还有文件的名称
                ossClient.putObject(bucketName,fileName, inputStream);
                // 关闭OSSClient。
                //将上传后文件路径返回，手动拼接
                //https://edu-1216388530-0101.oss-cn-beijing.aliyuncs.com/%E4%B8%80%E5%88%87.png
                String url ="https://"+bucketName+"."+endpoint+"/"+fileName;
                ossClient.shutdown();
                return url;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
    }
    //默认为有
    @Override
    public void deleteByUrl(String url) {
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        String objectName = url.substring(url.indexOf("/",10)+1,url.length());
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName, objectName);
        ossClient.shutdown();
    }
}
