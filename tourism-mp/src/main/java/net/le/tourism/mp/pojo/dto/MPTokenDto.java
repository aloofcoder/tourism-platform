package net.le.tourism.mp.pojo.dto;

import lombok.Data;
import lombok.ToString;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * @author hanle
 * @version v1.0
 * @date 2019/8/24
 * @modify
 * @copyright zhishoubao
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Data
@ToString
public class MPTokenDto {

    private String openId;

    private String appId;

    private String token;
}
