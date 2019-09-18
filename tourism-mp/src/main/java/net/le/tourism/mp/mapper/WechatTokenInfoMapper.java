package net.le.tourism.mp.mapper;

import net.le.tourism.mp.pojo.entity.WechatTokenInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 保存微信公众号全局access_token  Mapper 接口
 * </p>
 *
 * @author 韩乐
 * @since 2019-09-18
 */
@Repository
public interface WechatTokenInfoMapper extends BaseMapper<WechatTokenInfo> {

    WechatTokenInfo selectByAppId(String appId);

}
