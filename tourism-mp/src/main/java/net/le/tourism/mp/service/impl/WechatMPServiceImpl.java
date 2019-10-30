package net.le.tourism.mp.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import net.le.tourism.authority.common.constant.Constants;
import net.le.tourism.authority.common.exception.AppServiceException;
import net.le.tourism.authority.common.exception.ErrorCode;
import net.le.tourism.authority.common.util.BaseContextUtils;
import net.le.tourism.authority.common.util.CacheUtils;
import net.le.tourism.authority.common.util.CollectionUtils;
import net.le.tourism.authority.common.util.TourismUtils;
import net.le.tourism.mp.mapper.UserMpInfoMapper;
import net.le.tourism.mp.pojo.dto.MPTokenDto;
import net.le.tourism.mp.pojo.dto.WechatAccessTokenDto;
import net.le.tourism.mp.pojo.entity.UserMpInfo;
import net.le.tourism.mp.pojo.entity.WechatTokenInfo;
import net.le.tourism.mp.pojo.vo.TokenVo;
import net.le.tourism.mp.service.IWechatMpService;
import net.le.tourism.mp.service.IWechatTokenInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
public class WechatMPServiceImpl implements IWechatMpService {

    @Autowired
    private WxMpService wxService;

    @Autowired
    private UserMpInfoMapper userMpInfoMapper;

    @Autowired
    private IWechatTokenInfoService wechatTokenInfoService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String buildReqUrl(String url) {
        String redirectURL = wxService.oauth2buildAuthorizationUrl(url, "snsapi_userinfo", "index");
        return redirectURL;
    }

    @Override
    public TokenVo login(String code, String appId) {
        if (!this.wxService.switchover(appId)) {
            throw new AppServiceException(ErrorCode.mp_auth_error.getCode(), String.format("未找到对应appid=[%s]的配置，请核实！", appId));
        }
        // 获取微信access token
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
        try {
            wxMpOAuth2AccessToken = wxService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            e.printStackTrace();
            log.error("获取access token 失败");
            throw new AppServiceException(ErrorCode.mp_auth_error);
        }
        updateOauth2AccessToken(appId, wxMpOAuth2AccessToken.getAccessToken(), wxMpOAuth2AccessToken.getRefreshToken());
        // 缓存用户信息
        String token = TourismUtils.getToken();
        String mpTokenKey = TourismUtils.buildMPTokenKey(token);
        MPTokenDto mpTokenDto = new MPTokenDto();
        mpTokenDto.setToken(token);
        mpTokenDto.setAppId(appId);
        mpTokenDto.setOpenId(wxMpOAuth2AccessToken.getOpenId());
        CacheUtils.hMSet(redisTemplate, mpTokenKey, CollectionUtils.objectToMap(mpTokenDto), Constants.TOKEN_EXPIRE_TIME);
        TokenVo tokenVo = new TokenVo();
        tokenVo.setToken(token);
        return tokenVo;
    }

    @Override
    public UserMpInfo getLoginStatus(String openId) {
        UserMpInfo userMpInfo = userMpInfoMapper.selectByOpenId(openId);
        return userMpInfo;
    }

    @Override
    public MPTokenDto checkLogin(String token) {
        String mpTokenKey = TourismUtils.buildMPTokenKey(token);
        String openId = CacheUtils.hGet(redisTemplate, mpTokenKey, "openId");
        String appId = CacheUtils.hGet(redisTemplate, mpTokenKey, "appId");
        if (openId == null) {
            return null;
        }
        MPTokenDto mpTokenDto = new MPTokenDto();
        mpTokenDto.setToken(token);
        mpTokenDto.setAppId(appId);
        mpTokenDto.setOpenId(openId);
        // 刷新用户token
        CacheUtils.hMSet(redisTemplate, mpTokenKey, CollectionUtils.objectToMap(mpTokenDto), Constants.TOKEN_EXPIRE_TIME);
        return mpTokenDto;
    }

    public WxMpOAuth2AccessToken refreshOauth2AccessToken(String appId) {
        String mpAccessTokenKey = TourismUtils.buildMpAccessTokenKey(appId);
        String refreshToken = CacheUtils.hGet(redisTemplate, mpAccessTokenKey, "refreshToken");
        if (StringUtils.isEmpty(refreshToken)) {
            WechatTokenInfo wechatTokenInfo = wechatTokenInfoService.selectByAppId(appId);
            if (wechatTokenInfo == null) {
                return null;
            }
            refreshToken = wechatTokenInfo.getRefreshToken();
        }
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
        try {
            wxMpOAuth2AccessToken = wxService.oauth2refreshAccessToken(refreshToken);
        } catch (WxErrorException e) {
            e.printStackTrace();
            log.error("刷新access_token失败");
        }
        if (wxMpOAuth2AccessToken == null) {
            return null;
        }
        return wxMpOAuth2AccessToken;
    }

    public void updateOauth2AccessToken(String appId, String accessToken, String refreshToken) {
        WechatTokenInfo entity = new WechatTokenInfo();
        entity.setAppId(appId);
        entity.setAccessToken(accessToken);
        entity.setRefreshToken(refreshToken);
        wechatTokenInfoService.insertOrUpdateWechatToken(entity);
    }

    public String getOauth2AccessToken() {
        String appId = BaseContextUtils.get(Constants.APP_ID_KEY).toString();
        String openId = BaseContextUtils.get(Constants.OPEN_ID_KEY).toString();
        WechatTokenInfo wechatTokenInfo = wechatTokenInfoService.selectByAppId(appId);
        if (wechatTokenInfo == null) {
            return null;
        }
        String accessToken = wechatTokenInfo.getAccessToken();
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        wxMpOAuth2AccessToken.setAccessToken(accessToken);
        wxMpOAuth2AccessToken.setOpenId(openId);
        boolean isValid = wxService.oauth2validateAccessToken(wxMpOAuth2AccessToken);
        if (isValid) {
            return accessToken;
        }
        wxMpOAuth2AccessToken = refreshOauth2AccessToken(appId);
        if (wxMpOAuth2AccessToken == null) {
            return null;
        }
        return wxMpOAuth2AccessToken.getAccessToken();
    }

    @Override
    public WxMpUser getWechatUserInfo() {
        String openId = BaseContextUtils.get(Constants.OPEN_ID_KEY).toString();
        String accessToken = getOauth2AccessToken();
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        wxMpOAuth2AccessToken.setAccessToken(accessToken);
        wxMpOAuth2AccessToken.setOpenId(openId);
        WxMpUser wxMpUser = null;
        try {
            wxMpUser = wxService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return wxMpUser;
    }
}
