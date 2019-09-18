package net.le.tourism.mp.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import net.le.tourism.authority.common.util.CacheUtils;
import net.le.tourism.authority.common.util.TourismUtils;
import net.le.tourism.mp.mapper.UserMpInfoMapper;
import net.le.tourism.mp.mapper.WechatUserInfoMapper;
import net.le.tourism.mp.pojo.dto.MPTokenDto;
import net.le.tourism.mp.pojo.entity.UserMpInfo;
import net.le.tourism.mp.pojo.entity.WechatUserInfo;
import net.le.tourism.mp.service.IWechatMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author hanle
 * @version v1.0
 * @date 2019/9/2
 * @modify
 * @copyright zhishoubao
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Slf4j
@Service
@AllArgsConstructor
public class WechatMPServiceImpl implements IWechatMpService {

    @Autowired
    private UserMpInfoMapper userMpInfoMapper;

    @Autowired
    private WechatUserInfoMapper wechatUserInfoMapper;

    @Override
    public UserMpInfo getLoginStatus(String openId) {
        UserMpInfo userMpInfo = userMpInfoMapper.selectByOpenId(openId);
        return userMpInfo;
    }

    @Override
    public WechatUserInfo getRegisterStatus(String openId) {
        WechatUserInfo wechatUserInfo = wechatUserInfoMapper.selectByOpenId(openId);
        return wechatUserInfo;
    }

    @Override
    public void register(WechatUserInfo wechatUserInfo) {
        wechatUserInfoMapper.insert(wechatUserInfo);
    }
}
