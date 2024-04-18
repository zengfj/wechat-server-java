package cn.fanstars.wechat.handler;

import cn.fanstars.wechat.config.ApiConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
public class SubscribeHandler extends AbstractHandler {

    final String SUBSCRIBE_EVENT_KEY_PREFIX_OF_SCAN = "qrscene_";

    private final ApiConfig apiConfig;

    private final ScanHandler scanHandler;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
        log.info("新关注用户 OPENID: " + wxMessage.getFromUser());
        String eventKey = wxMessage.getEventKey();
        if (eventKey.startsWith(SUBSCRIBE_EVENT_KEY_PREFIX_OF_SCAN)) {
            wxMessage.setEventKey(eventKey.replace(SUBSCRIBE_EVENT_KEY_PREFIX_OF_SCAN, ""));
            return scanHandler.handle(wxMessage, context, weixinService, sessionManager);
        }
        return null;
    }

}
