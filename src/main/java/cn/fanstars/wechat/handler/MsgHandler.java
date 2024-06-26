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
import org.springframework.util.StringUtils;

import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
public class MsgHandler extends AbstractHandler {

    private final ApiConfig apiConfig;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        log.info("接收到文本消息: {}", JSON.toJSONString(wxMessage));
        String content = wxMessage.getContent();
        if (!StringUtils.hasLength(content)) {
            content = "";
        }
        if (content.equals(apiConfig.getSendCodeKeyword())) {
            String codeText = ApiCodeUtil.getCodeText(wxMessage.getFromUser());
            return new TextBuilder().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                    .content(codeText).build();
        }
        // 添加自定义回复
        Map<String, String> customReplyMap = apiConfig.getCustomReplyMap();
        if (customReplyMap != null && customReplyMap.containsKey(content)) {
            return new TextBuilder().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                    .content(customReplyMap.get(content)).build();
        }
        return null;
    }

}
