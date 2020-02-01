package com.coachtam.tqt.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Copyright (C), 2018-2019
 * @Author: JAVA在召唤
 * @Date: 2019-06-27 15:46
 * @Description:
 */
@Data
@ConfigurationProperties(prefix = "tqt.upload")
public class UploadProperteis {
    //上传路径
    private String uploadPath;

    //访问路径
    private String accessPath;

    //附件前缀
    private String appendixPrefix;
    //录音前缀
    private String soundPrefix;


}
