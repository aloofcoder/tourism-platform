package net.le.tourism.mp.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import net.le.tourism.authority.common.util.CacheUtils;
import net.le.tourism.authority.common.util.TourismUtils;
import net.le.tourism.mp.pojo.dto.MPTokenDto;
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

    private final WxMpService wxService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public MPTokenDto validateLogin(String token) {
        String tokenKey = TourismUtils.buildMPTokenKey(token);
        String openId = CacheUtils.hGet(redisTemplate, tokenKey, "openId");
        String accessToken = CacheUtils.hGet(redisTemplate, tokenKey, "accessToken");
        String refreshToken = CacheUtils.hGet(redisTemplate, tokenKey, "refreshToken");
        if (openId == null || accessToken == null || refreshToken == null) {
            return null;
        }
        MPTokenDto tokenDto = new MPTokenDto();
        tokenDto.setOpenId(openId);
        tokenDto.setAccessToken(accessToken);
        tokenDto.setRefreshToken(refreshToken);
        tokenDto.setToken(token);
        // 刷新token
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
        try {
            wxMpOAuth2AccessToken = wxService.oauth2refreshAccessToken(refreshToken);
        } catch (WxErrorException e) {
            log.error("刷新access_token 失败！");
            e.printStackTrace();
            return null;
        }
        MPTokenDto tokenModel = new MPTokenDto();
        tokenModel.setOpenId(wxMpOAuth2AccessToken.getOpenId());
        tokenModel.setAccessToken(wxMpOAuth2AccessToken.getAccessToken());
        tokenModel.setRefreshToken(wxMpOAuth2AccessToken.getRefreshToken());
        tokenModel.setToken(token);
        Map<String, Object> tokenMap = BeanMap.create(tokenModel);
        CacheUtils.hMSet(redisTemplate, tokenKey, tokenMap, wxMpOAuth2AccessToken.getExpiresIn());
        return tokenDto;
    }
}
