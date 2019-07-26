package net.le.tourism.authority.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-06-25
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Data
@ToString
@ApiModel(value = "QueryAdminInfo", description = "管理员")
public class QueryAdminInfoVo implements Serializable {

    private static final long serialVersionUID = 8697009806741008598L;

    @ApiModelProperty(value = "管理员Id", name = "adminId", dataType = "Integer")
    private Integer adminId;

    @ApiModelProperty(value = "管理员系统编号", name = "adminNum", dataType = "String")
    private String adminNum;

    @ApiModelProperty(value = "管理员姓名", name = "adminName", dataType = "String")
    private String adminName;

    @ApiModelProperty(value = "登录名", name = "loginName", dataType = "String")
    private String loginName;

    @ApiModelProperty(value = "手机号", name = "adminMobile", dataType = "String")
    private String adminMobile;

    @ApiModelProperty(value = "邮箱", name = "adminEmail", dataType = "String")
    private String adminEmail;

    @ApiModelProperty(value = "status", name = "状态(0正常1禁用)", dataType = "Integer")
    private Integer status;

    @ApiModelProperty(value = "remark", name = "备注", dataType = "String")
    private String remark;

    @ApiModelProperty(value = "adminRoles", name = "管理员所拥有的角色", dataType = "String")
    private String adminRoles;
}
