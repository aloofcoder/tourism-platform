package net.le.tourism.mp.mapper;

import net.le.tourism.mp.pojo.entity.WechatTokenInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 韩乐
 * @since 2019-09-21
 */
@Repository
public interface WechatTokenInfoMapper extends BaseMapper<WechatTokenInfo> {

    void insertOrUpdate(WechatTokenInfo wechatTokenInfo);
}
