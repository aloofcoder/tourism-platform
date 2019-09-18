package net.le.tourism.mp.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 保存微信公众号全局access_token 
 * </p>
 *
 * @author 韩乐
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WechatTokenInfo extends Model<WechatTokenInfo> {

private static final long serialVersionUID=1L;

    @TableId(value = "token_id", type = IdType.AUTO)
    private Integer tokenId;

    private String appId;

    private String accessToken;

    private String refreshToken;

    private LocalDateTime createTime;


    @Override
    protected Serializable pkVal() {
        return this.tokenId;
    }

}
