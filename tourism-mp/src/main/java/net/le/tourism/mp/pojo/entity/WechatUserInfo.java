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
 * 微信用户信息
 * </p>
 *
 * @author 韩乐
 * @since 2019-09-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WechatUserInfo extends Model<WechatUserInfo> {

private static final long serialVersionUID=1L;

    @TableId(value = "wechat_user_id", type = IdType.AUTO)
    private Integer wechatUserId;

    /**
     * 微信公众号用户的唯一标识
     */
    private String openid;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户的性别 (0男性1女性2未知)
     */
    private Integer sex;

    /**
     * 用户个人资料填写的省份
     */
    private String province;

    /**
     * 普通用户个人资料填写的城市
     */
    private String city;

    /**
     * 国家，如中国为CN
     */
    private String country;

    /**
     * 用户头像
     */
    private String headimgurl;

    /**
     * 用户特权信息
     */
    private String privilege;

    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
     */
    private String unionid;

    private Date createTime;

    private Date editTime;

    private String createUser;

    private String editUser;


    @Override
    protected Serializable pkVal() {
        return this.wechatUserId;
    }

}
