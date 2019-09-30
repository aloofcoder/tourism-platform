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
 * @since 2019-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WechatTokenInfo extends Model<WechatTokenInfo> {

private static final long serialVersionUID=1L;

    @TableId(value = "wechat_token_id", type = IdType.AUTO)
    private Integer wechatTokenId;

    private String appId;

    private String accessToken;

    private String refreshToken;

    private Date createTime;

    private Date editTime;


    @Override
    protected Serializable pkVal() {
        return this.wechatTokenId;
    }

}
