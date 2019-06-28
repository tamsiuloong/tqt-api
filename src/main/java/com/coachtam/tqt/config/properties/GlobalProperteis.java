package com.coachtam.tqt.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Copyright (C), 2018-2019
 * @Author: JAVA在召唤
 * @Date: 2019-06-27 15:46
 * @Description:
 */
@Data
@ConfigurationProperties(prefix = "tqt.global")
public class GlobalProperteis {
    private List<String> adminRoles;
}
