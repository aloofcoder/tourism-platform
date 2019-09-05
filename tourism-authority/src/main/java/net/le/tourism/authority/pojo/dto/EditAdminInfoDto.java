package net.le.tourism.authority.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-06-24
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Data
@ToString
public class EditAdminInfoDto {

    private static final long serialVersionUID = 1723937366677226009L;

    @NotNull(message = "管理员编号不能为空！")
    private String adminNum;

    @NotBlank(message = "管理员姓名不能为空！")
    @Length(min = 1, max = 6, message = "管理员姓名长度必须在1~6位之间！")
    @ApiModelProperty(name = "adminName", value = "管理员姓名", dataType = "String", required = true)
    private String adminName;

    @NotBlank(message = "登录名不能为空！")
    @Length(min = 4, max = 10, message = "登录名长度必须在4 ~10位之间！")
    @Pattern(regexp = "^[a-z]{4,10}$", message = "登录名必须为全小写字母！")
    @ApiModelProperty(name = "loginName", value = "登录名", dataType = "String", required = true)
    private String loginName;

    @NotBlank(message = "登录密码不能为空！")
    @Length(min = 8, max = 16, message = "登录密码长度必须在8~16位之间！")
    @ApiModelProperty(name = "adminPwd", value = "登录密码", dataType = "String", required = true)
    private String adminPwd;

    @NotBlank(message = "管理员手机号不能为空！")
    @Pattern(regexp = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$", message = "管理员手机号格式不正确！")
    @ApiModelProperty(name = "adminMobile", value = "手机号", dataType = "String", required = true)
    private String adminMobile;

    @Email(message = "邮箱格式不正确！")
    @NotBlank(message = "管理员邮箱不能为空！")
    @ApiModelProperty(name = "adminEmail", value = "邮箱", dataType = "String", required = true)
    private String adminEmail;

    @Valid
    @NotEmpty(message = "角色不能为空")
    @Size(min = 1, message = "最少选择一个角色")
    @ApiModelProperty(name = "roleIds", value = "管理员角色Id集合", dataType = "List", required = true)
    private List<@NotNull(message = "角色Id不能为空") @Min(value = 1, message = "角色Id必须大于0") Integer> roleIds;

    @NotNull(message = "所在部门不能为空")
    @Min(value = 1, message = "请选择正确的组织")
    @ApiModelProperty(name = "orgId", value = "组织id不能为空", dataType = "Integer", required = true)
    private Integer orgId;
}
