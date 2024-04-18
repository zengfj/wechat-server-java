package cn.fanstars.wechat.util;

import cn.fanstars.wechat.config.ApiConfig;
import cn.fanstars.wechat.response.ApiResponse;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

@Component
public class ApiCodeUtil implements ApplicationContextAware {

    private static ApiConfig apiConfig;

    public static ApiResponse verifyCode(String code) {
        if (!verificationMap.containsKey(code)) {
            return ApiResponse.error("验证码验证失败");
        }
        VerificationValue value = verificationMap.get(code);
        Long time;
        if (value == null || (time = value.getTime()) == null) {
            return ApiResponse.error("验证码验证失败");
        }
        if (System.currentTimeMillis() - time > apiConfig.getCodeExpireTime()) {
            verificationMap.remove(code);
            return ApiResponse.error("验证码已过期");
        }
        verificationMap.remove(code);
        return ApiResponse.success(value.getOpenid());
    }

    @Data
    @Builder
    private static class VerificationValue {
        private String openid;
        private Long time;
    }

    private static final Map<String, VerificationValue> verificationMap = new HashMap<>();

    private static final int verificationMapMaxSize = 20;

    public static synchronized String generateCode(String openId) {
        String code = getRandomCode(apiConfig.getCodeLength());
        while (verificationMap.containsKey(code)) {
            code = getRandomCode(apiConfig.getCodeLength());
        }
        verificationMap.put(code, VerificationValue.builder()
                .openid(openId)
                .time(System.currentTimeMillis()).build());
        if (verificationMap.size() > verificationMapMaxSize) {
            removeExpiredPairs();
        }
        return code;
    }

    private static void removeExpiredPairs() {
        long now = System.currentTimeMillis();
        Iterator<Map.Entry<String, VerificationValue>> iterator = verificationMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, VerificationValue> entry = iterator.next();
            VerificationValue value = entry.getValue();
            long time = value.getTime() == null ? 0 : value.getTime();
            if (now - time > apiConfig.getCodeExpireTime()) {
                iterator.remove();
            }
        }
    }

    /**
     * 获取随机数
     */
    public static String getRandomCode(int length) {
        String sources = "0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(sources.charAt(random.nextInt(sources.length() - 1)));
        }
        return sb.toString();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        apiConfig = applicationContext.getBean(ApiConfig.class);
    }
}
