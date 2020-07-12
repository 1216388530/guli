package com;

import com.ali.oss.utils.ConstantPropertiesUtils;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

public class Test {

    public void test(){
        /**
         * aliyun.oss.file.endpoint=oss-cn-beijing.aliyuncs.com
         *     aliyun.oss.file.keyid=LTAI4GG73NYihRyA9JXgK7H5
         *     aliyun.oss.file.keysecret=YUo2S6Q37LD0patoC24OcZFM6MVrY9
         *     bucket可以在控制台创建，也可以使用java代码创建
         *     aliyun.oss.file.bucketname=edu-1216388530-0101
         *
         */
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAI4GG73NYihRyA9JXgK7H5";
        String accessKeySecret = "YUo2S6Q37LD0patoC24OcZFM6MVrY9";
        String bucketName = "edu-1216388530-0101";
        String objectName = "2020/06/05/3db457cae083428288ceb1a82aa09a9f.jpg";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName, objectName);
        ossClient.shutdown();
    }
    @org.junit.Test
    public void test2(){
        String si ="https://edu-1216388530-0101.oss-cn-beijing.aliyuncs.com/2020/06/05/68a13c71c2f24120ad550aea9775f998.png";
        System.out.println(si.substring(si.indexOf("/",10)+1,si.length()));
    }
}
