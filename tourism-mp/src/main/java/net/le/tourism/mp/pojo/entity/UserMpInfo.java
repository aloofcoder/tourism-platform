package net.le.tourism.mp.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
 * @since 2019-09-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserMpInfo extends Model<UserMpInfo> {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 人员id(包括导游id,管理员id,司机id,导游id等)
     */
    private String staffId;

    /**
     * 微信open_id
     */
    private String openid;

    /**
     * 0有效1删除
     */
    private Integer status;

    private Date createTime;

    private Date editTime;

    private String createUser;

    private String editUser;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
