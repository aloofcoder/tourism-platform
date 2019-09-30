package net.le.tourism.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.le.tourism.mp.mapper.WechatTokenInfoMapper;
import net.le.tourism.mp.pojo.entity.WechatTokenInfo;
import net.le.tourism.mp.service.IWechatTokenInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 韩乐
 * @since 2019-09-21
 */
@Service
public class WechatTokenInfoServiceImpl extends ServiceImpl<WechatTokenInfoMapper, WechatTokenInfo> implements IWechatTokenInfoService {

    @Autowired
    private WechatTokenInfoMapper wechatTokenInfoMapper;

    @Override
    public WechatTokenInfo selectByAppId(String appId) {
        QueryWrapper<WechatTokenInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("app_id", appId);
        return wechatTokenInfoMapper.selectOne(wrapper);
    }

    @Override
    public void insertOrUpdateWechatToken(WechatTokenInfo wechatTokenInfo) {
        wechatTokenInfoMapper.insertOrUpdate(wechatTokenInfo);
    }
}
