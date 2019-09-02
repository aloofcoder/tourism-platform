package net.le.tourism.authority.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 韩乐
 * @since 2019-07-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginLog extends Model<LoginLog> {

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "login_log_id", type = IdType.AUTO)
    private Integer loginLogId;

    /**
     * 管理员id
     */
    private String adminNum;

    private Date loginTime;

    /**
     * 登录ip
     */
    private String loginIp;

    private Integer status;

    private String loginResult;

    @Override
    protected Serializable pkVal() {
        return this.loginLogId;
    }

}
