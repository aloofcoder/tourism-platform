package net.le.tourism.authority.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.le.tourism.authority.common.constant.Constants;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author 韩乐
 * @since 2019-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AdminInfo extends Model<AdminInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "admin_id", type = IdType.AUTO)
    private Integer adminId;

    private String adminNum;

    @NotBlank(message = "管理员姓名不能为空！")
    @Length(min = 1, max = 6, message = "管理员姓名长度必须在1~6位之间！")
    private String adminName;

    @NotBlank(message = "登录名不能为空！")
    @Length(min = 4, max = 10, message = "登录名长度必须在4 ~10位之间！")
    @Pattern(regexp = "^[a-z]{4,10}$", message = "登录名必须为全小写字母！")
    private String loginName;

    @NotBlank(message = "登录密码不能为空！")
    @Length(min = 8, max = 16, message = "登录密码长度必须在8~16位之间！")
    private String adminPwd;

    /**
     * 密码盐值
     */
    private String salt;

    @NotBlank(message = "管理员手机号不能为空！")
    @Pattern(regexp = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$", message = "管理员手机号格式不正确！")
    private String adminMobile;

    @Email(message = "邮箱格式不正确！")
    @NotBlank(message = "管理员邮箱不能为空！")
    private String adminEmail;

    private Integer status;

    private String remark;

    private String createUser;

    private String editUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = Constants.DEFAULT_TIME_ZONE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = Constants.DEFAULT_TIME_ZONE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date editTime;

    @Override
    protected Serializable pkVal() {
        return this.adminId;
    }

}
