package com.coachtam.tqt.config.properties;

import com.coachtam.tqt.config.utils.RsaUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;

/**
 * @author: 9805
 * @create: 2018-10-27
 **/
@ConfigurationProperties(prefix = "tqt.jwt")
@Configuration
@Slf4j
@Data
public class JwtProperties {


    /**
     * 密钥
     */
    private String secret;
    /**
     * 密钥地址
     */
    private String priKeyPath;
    /**
     * 有效期 分钟
     */
    private Integer expire;

    /**
     * 私钥
     */
    private PrivateKey privateKey;
    /**
     * 公钥地址
     */
    private String pubKeyPath;

    /**
     * 公钥
     */
    private PublicKey publicKey;

    /**
     * 参数名字
     */
    private String paramName;

    private List<String> clients;

    private static final Logger logger = LoggerFactory.getLogger(JwtProperties.class);




    @PostConstruct
    public void init()  {
        //判断是否存在，不存在生成公钥和私钥
        File file = new File(pubKeyPath);
        if(!file.exists())
        {
            try {
                RsaUtils.generateKey(pubKeyPath, priKeyPath, secret);
            } catch (Exception e) {
                log.error("自动生成私钥和公钥出错！");
            }
        }
        //将文件系统中的私钥信息读取到privateKey对象中
        try {
            privateKey = RsaUtils.getPrivateKey(priKeyPath);
            publicKey = RsaUtils.getPublicKey(pubKeyPath);
        } catch (Exception e) {
            log.error("获取私钥出错！");
        }
    }

}
