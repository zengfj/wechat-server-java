package cn.fanstars.wechat.config;

import lombok.Data;
import me.chanjar.weixin.mp.config.WxMpHostConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "wx.mp")
public class WxMpProperties {

    /**
     * 设置微信公众号的appid
     */
    private String appId;

    /**
     * 设置微信公众号的app secret
     */
    private String secret;

    /**
     * 设置微信公众号的token
     */
    private String token;

    /**
     * 设置微信公众号的EncodingAESKey
     */
    private String aesKey;

    /**
     * 对应于：https://api.weixin.qq.com
     */
    private String apiHost = WxMpHostConfig.API_DEFAULT_HOST_URL;

    /**
     * 对应于：https://open.weixin.qq.com
     */
    private String openHost = WxMpHostConfig.OPEN_DEFAULT_HOST_URL;

    /**
     * 对应于：https://mp.weixin.qq.com
     */
    private String mpHost = WxMpHostConfig.MP_DEFAULT_HOST_URL;

}