package com.lra.common.value;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author Anglar
 * @Date 2019/10/21 16:10
 * @Version V1.0
 **/
@Data
@ConfigurationProperties(prefix = "audience")
@Component
public class Audience {

    private String base64Secret;

    private Long expiresSecond;

}
