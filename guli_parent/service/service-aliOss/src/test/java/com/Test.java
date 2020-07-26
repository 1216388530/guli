package com;

import com.ali.oss.utils.ConstantPropertiesUtils;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

public class Test {

    public void test(){
        
        String endpoint = "自己建";
        String accessKeyId = "自己建";
        String accessKeySecret = "自己建";
        String bucketName = "自己建";
        String objectName = "自己建";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName, objectName);
        ossClient.shutdown();
    }
    @org.junit.Test
    public void test2(){
        String si ="自己建";
        System.out.println(si.substring(si.indexOf("/",10)+1,si.length()));
    }
}
