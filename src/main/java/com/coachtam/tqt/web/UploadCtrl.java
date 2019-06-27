package com.coachtam.tqt.web;

import com.coachtam.tqt.config.properties.UploadProperteis;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Copyright (C), 2018-2019
 * @Author: JAVA在召唤
 * @Date: 2019-06-27 15:42
 * @Description:
 */
@RestController
@EnableConfigurationProperties(UploadProperteis.class)
public class UploadCtrl {
    @Autowired
    private UploadProperteis uploadProperteis;

    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    //type:1 上传图片 , 2 上传音频
    @PostMapping("/api/uploadFiles/{type}")
    public ResponseEntity<String> uploadFiles(@RequestParam("file")MultipartFile[] files,@PathVariable("type") Integer type){

        if(files==null || files.length==0)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String baseDir = uploadProperteis.getUploadPath()+"/"+(type.equals(1)?"image":"sound")+"/";

        String dir = this.format.format(new Date());

        File fullDir = new File(baseDir+dir);

        //检查目录是否存在
        if (!fullDir.exists())
        {
            fullDir.mkdirs();
        }

        List<String> result = new ArrayList<>();

        for(MultipartFile file :files)
        {
            //相对路径
            String relativePath = dir+"/"+UUID.randomUUID().toString()+ "." +StringUtils.substringAfterLast(file.getOriginalFilename(),".");
            //完整路径
            String fullpath = baseDir + relativePath;

            File filePath = new File(fullpath);
            try {
                file.transferTo(filePath);
                //返回相对路径
                result.add(relativePath);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        String resultStr = result.stream().reduce((a, b) -> a + "," + b).get();
        return ResponseEntity.ok(resultStr);
    }
}
