package net.le.tourism.mp.mapper;

import net.le.tourism.mp.pojo.entity.UserMpInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 韩乐
 * @since 2019-09-12
 */
@Repository
public interface UserMpInfoMapper extends BaseMapper<UserMpInfo> {

    /**
     * 根据openId查看公众号登录状态
     * @param openId
     * @return
     */
    UserMpInfo selectByOpenId(String openId);
}
