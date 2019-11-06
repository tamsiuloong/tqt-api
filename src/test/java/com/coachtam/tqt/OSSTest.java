package com.coachtam.tqt;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @Copyright (C), 2018-2019
 * @Author: JAVA在召唤
 * @Date: 2019-11-02 15:08
 * @Description:
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest

public class OSSTest {
    //https://help.aliyun.com/document_detail/32009.html
    @Test
    public void test1() throws FileNotFoundException {
//        // Endpoint以杭州为例，其它Region请按实际情况填写。
//        String endpoint = "http://oss-cn-chengdu.aliyuncs.com";
//        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
//        String accessKeyId = "LTAIqgCjNjkp3LcZ";
//        String accessKeySecret = "H7OigjloeI28Tj87IF9oPnfH1uwCMF";
//
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//
//        // 上传文件流。
//        InputStream inputStream = new FileInputStream("/Users/macbook/downloads/吴奇隆_四川准达信息技术股份有限公司_20190910_100838.m4a");
//        PutObjectResult result = ossClient.putObject("tqt", "吴奇隆_四川准达信息技术股份有限公司_20190910_1008382.m4a", inputStream);
//
//        // 关闭OSSClient。
//        ossClient.shutdown();

        String s1 = "abc";
        String s2 = "a"+new String("bc");
        System.out.println(s1==s2);
    }
}
