package cn.fanstars.wechat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "api")
public class ApiConfig {

    /**
     * token, one-api/new-api 中要设置一样
     */
    private String token;
    /**
     * 验证码长度
     */
    private Integer codeLength = 6;
    /**
     * 验证码过期时间，以毫秒为单位
     */
    private Long codeExpireTime = 300 * 1000L;
    /**
     * 宽
     */
    private Integer width = 300;
    /**
     * 高
     */
    private Integer height = 300;
    /**
     * 边距
     */
    private Integer margin = 1;
    /**
     * 二维码场景id
     */
    private String qrcodeSceneId = "1008";
    /**
     * 二维码过期时间，以秒为单位
     */
    private Integer qrcodeExpireTime = 300;

}
