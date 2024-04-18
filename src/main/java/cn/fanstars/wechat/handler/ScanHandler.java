package cn.fanstars.wechat.handler;

import cn.fanstars.wechat.config.ApiConfig;
import cn.fanstars.wechat.util.ApiCodeUtil;
import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.builder.outxml.TextBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
public class ScanHandler extends AbstractHandler {

    private final ApiConfig apiConfig;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> map,
                                    WxMpService wxMpService, WxSessionManager wxSessionManager) {
        // 扫码事件处理
        log.info("接收到请求消息，内容：{}", JSON.toJSONString(wxMessage));
        if (wxMessage.getEventKey().equals(apiConfig.getQrcodeSceneId())) {
            String code = ApiCodeUtil.generateCode(wxMessage.getFromUser());
            return new TextBuilder().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                    .content("您的验证码是: " + code).build();
        }
        return null;
    }

}
