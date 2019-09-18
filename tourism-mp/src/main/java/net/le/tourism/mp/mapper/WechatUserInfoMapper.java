package net.le.tourism.mp.mapper;

import net.le.tourism.mp.pojo.entity.WechatUserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 微信用户信息 Mapper 接口
 * </p>
 *
 * @author 韩乐
 * @since 2019-09-12
 */
@Repository
public interface WechatUserInfoMapper extends BaseMapper<WechatUserInfo> {

    /**
     * 根据openId查询微信用户
     * @param openId
     * @return
     */
    WechatUserInfo selectByOpenId(String openId);
}
