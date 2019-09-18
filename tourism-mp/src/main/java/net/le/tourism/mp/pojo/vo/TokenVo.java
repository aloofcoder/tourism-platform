package net.le.tourism.mp.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

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
@ApiModel(value = "公众号登录状态")
public class TokenVo {

    @ApiModelProperty(value = "登录token", notes = "用户未登录时token为空", name = "token", dataType = "string")
    private String token;

    @ApiModelProperty(value = "登录状态", notes = "0 未登录 1 已登录", name = "loginStatus", dataType = "string", required = true)
    private Integer loginStatus;
}
