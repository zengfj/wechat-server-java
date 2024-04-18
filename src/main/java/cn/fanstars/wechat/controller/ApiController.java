package cn.fanstars.wechat.controller;

import cn.fanstars.wechat.response.ApiResponse;
import cn.fanstars.wechat.service.ApiService;
import com.google.zxing.WriterException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/wechat")
public class ApiController {

    private final ApiService apiService;

    @GetMapping("/access_token")
    public ApiResponse accessToken(@RequestHeader("authorization") String token,
                                   @RequestHeader("Authorization") String aToken) throws WxErrorException {
        if (token == null) {
            token = aToken;
        }
        return apiService.accessToken(token);
    }

    @GetMapping("/qrcode")
    public ResponseEntity<byte[]> qrcode(HttpServletResponse response) throws IOException, WriterException, WxErrorException {
        return apiService.qrcode(response);
    }

    @GetMapping("/user")
    public ApiResponse user(@RequestHeader("authorization") String token, @RequestHeader("Authorization") String aToken,
                            String code) {
        if (token == null) {
            token = aToken;
        }
        log.info("token: {}, code: {}", token, code);
        return apiService.wechatUser(token, code);
    }

}
