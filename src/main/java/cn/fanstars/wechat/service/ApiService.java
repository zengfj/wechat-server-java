package cn.fanstars.wechat.service;

import cn.fanstars.wechat.response.ApiResponse;
import com.google.zxing.WriterException;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ApiService {

    ApiResponse accessToken(String token) throws WxErrorException;

    ResponseEntity<byte[]> qrcode(HttpServletResponse response) throws IOException, WriterException, WxErrorException;

    ApiResponse wechatUser(String token, String code);

    boolean verifyToken(String token);

}
