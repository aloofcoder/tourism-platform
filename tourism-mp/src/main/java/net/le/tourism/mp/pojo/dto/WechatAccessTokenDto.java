package net.le.tourism.mp.pojo.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author hanle
 * @version v1.0
 * @date 2019/9/21
 * @modify
 * @copyright zhishoubao
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Data
@ToString
public class WechatAccessTokenDto {

    private String accessToken;

    private String refreshToken;

    private String appId;

    private String expiresIn;
}
