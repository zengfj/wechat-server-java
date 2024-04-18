package cn.fanstars.wechat.response;

import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class ApiResponse {

    private boolean success;

    private String message;

    private String data;

    private ApiResponse(boolean success) {
        this.success = success;
    }


    public static ApiResponse success() {
        return new ApiResponse(true);
    }

    public static ApiResponse success(String data) {
        ApiResponse apiResponse = success();
        if (StringUtils.hasLength(data)) {
            apiResponse.setData(data);
        }
        return apiResponse;
    }

    public static ApiResponse error() {
        return new ApiResponse(false);
    }

    public static ApiResponse error(String message) {
        ApiResponse apiResponse = error();
        if (StringUtils.hasLength(message)) {
            apiResponse.setMessage(message);
        }
        return apiResponse;
    }

}
