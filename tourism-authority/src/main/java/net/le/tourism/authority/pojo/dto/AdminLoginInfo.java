package net.le.tourism.authority.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-06-15
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Data
@ToString
@ApiModel(value = "AdminLoginInfo", description = "管理员登录参数")
public class AdminLoginInfo implements Serializable {

    private static final long serialVersionUID = -2312829177237237540L;

    @NotBlank(message = "登录账号不能为空！")
    @ApiModelProperty(value = "登录账号（登录名/手机/邮箱）", name = "loginNum", dataType = "String")
    private String loginNum;

    @NotBlank(message = "登录密码不能为空！")
    @ApiModelProperty(value = "登录密码", name = "loginPwd", dataType = "String")
    private String loginPwd;
}
