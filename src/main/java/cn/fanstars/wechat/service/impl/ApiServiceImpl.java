package cn.fanstars.wechat.service.impl;

import cn.fanstars.wechat.config.ApiConfig;
import cn.fanstars.wechat.response.ApiResponse;
import cn.fanstars.wechat.service.ApiService;
import cn.fanstars.wechat.util.ApiCodeUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class ApiServiceImpl implements ApiService {

    private final ApiConfig apiConfig;
    private final WxMpService wxMpService;

    @Override
    public ApiResponse accessToken(String token) throws WxErrorException {
        if (!verifyToken(token)) {
            return ApiResponse.error("令牌验证失败");
        }
        return ApiResponse.success(wxMpService.getAccessToken())
                ;
    }

    @Override
    public ResponseEntity<byte[]> qrcode(HttpServletResponse response) throws IOException, WriterException, WxErrorException {
        WxMpQrcodeService qrcodeService = wxMpService.getQrcodeService();
        WxMpQrCodeTicket wxMpQrCodeTicket = qrcodeService.qrCodeCreateTmpTicket(apiConfig.getQrcodeSceneId(),
                apiConfig.getQrcodeExpireTime());
        String url = wxMpQrCodeTicket.getUrl();
        log.info("qrcode url: {}", url);
        return generateQRCode(wxMpQrCodeTicket.getUrl());
    }

    private ResponseEntity<byte[]> generateQRCode(String url) throws WriterException, IOException {
        // 设置二维码的宽度和高度
        int width = apiConfig.getWidth(), height = apiConfig.getHeight();
        // 设置二维码的图片格式
        String format = "png";

        // 创建一个Map对象，用于存储编码提示信息
        Map<EncodeHintType, Object> hints = new HashMap<>();
        // 设置纠错级别为L（7%）
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        // 边距，指定二维码图像的边距大小
        hints.put(EncodeHintType.MARGIN, apiConfig.getMargin());
        // 设置字符集为UTF-8
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        // 创建一个QRCodeWriter对象，用于生成二维码
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        // 使用QRCodeWriter对象生成二维码矩阵
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height, hints);

        // 创建一个BufferedImage对象，用于存储二维码图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取Graphics2D对象，用于绘制二维码图片
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        // 设置背景颜色为白色
        graphics.setColor(Color.WHITE);
        // 填充整个图片区域
        graphics.fillRect(0, 0, width, height);
        // 设置前景颜色为黑色
        graphics.setColor(Color.BLACK);

        // 遍历二维码矩阵，将黑色模块绘制到图片上
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (bitMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }

        // 创建一个ByteArrayOutputStream对象，用于存储图片数据
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // 将BufferedImage对象写入到ByteArrayOutputStream对象中
        ImageIO.write(image, format, outputStream);
        // 将ByteArrayOutputStream对象转换为字节数组
        byte[] bytes = outputStream.toByteArray();

        // 返回一个ResponseEntity对象，包含图片数据和Content-Type头信息
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("image/" + format)).body(bytes);
    }

    @Override
    public ApiResponse wechatUser(String token, String code) {
        if (!verifyToken(token)) {
            return ApiResponse.error("令牌验证失败");
        }
        return ApiCodeUtil.verifyCode(code);
    }

    @Override
    public boolean verifyToken(String token) {
        String configToken;
        return StringUtils.hasLength(configToken = apiConfig.getToken()) && configToken.equals(token);
    }

}
