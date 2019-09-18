package net.le.tourism.mp.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import net.le.tourism.authority.common.annotation.IgnoreToken;
import net.le.tourism.authority.common.constant.Constants;
import net.le.tourism.authority.common.exception.AppServiceException;
import net.le.tourism.authority.common.exception.ErrorCode;
import net.le.tourism.authority.common.util.BaseContextUtils;
import net.le.tourism.authority.common.util.CacheUtils;
import net.le.tourism.authority.common.util.CollectionUtils;
import net.le.tourism.authority.common.util.TourismUtils;
import net.le.tourism.mp.pojo.dto.MPTokenDto;
import net.le.tourism.mp.pojo.entity.WechatTokenInfo;
import net.le.tourism.mp.pojo.vo.TokenVo;
import net.le.tourism.mp.service.IWechatMpService;
import net.le.tourism.mp.service.IWechatTokenInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hanle
 * @version v1.0
 * @date 2019/8/1
 * @modify
 * @copyright zhishoubao
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */

@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/mp/{appid}")
public class WechatMPController {

    private final WxMpService wxService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IWechatMpService wechatMpService;

    @Autowired
    private IWechatTokenInfoService wechatTokenInfoService;

    @IgnoreToken
    @ResponseBody
    @RequestMapping("/index")
    public TokenVo login(@PathVariable("appid") String appid, @RequestParam String code) throws Exception {
        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        String token = TourismUtils.getToken();
        String accessTokenCacheKey = TourismUtils.buildMpAccessTokenKey(appid, token);
        // 查看是否有缓存access_token, 如果没有进行获取
        String accessToken = CacheUtils.hGet(redisTemplate, accessTokenCacheKey, "accessToken");
        String openId = CacheUtils.hGet(redisTemplate, accessTokenCacheKey, "openId");
        if (StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(openId)) {
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
            try {
                wxMpOAuth2AccessToken = wxService.oauth2getAccessToken(code);
            } catch (WxErrorException e) {
                e.printStackTrace();
                log.error("获取微信access_token失败");
                throw new AppServiceException(ErrorCode.mp_get_access_token_error);
            }
            openId = wxMpOAuth2AccessToken.getOpenId();
            // 缓存accessToken
            CacheUtils.hMSet(redisTemplate, accessTokenCacheKey, CollectionUtils.objectToMap(wxMpOAuth2AccessToken), wxMpOAuth2AccessToken.getExpiresIn());
            // 将accessToken 保存到数据库
            WechatTokenInfo wechatTokenInfo = new WechatTokenInfo();
            wechatTokenInfo.setAppId(appid);
            wechatTokenInfo.setAccessToken(wxMpOAuth2AccessToken.getAccessToken());
            wechatTokenInfo.setRefreshToken(wxMpOAuth2AccessToken.getRefreshToken());
            wechatTokenInfoService.insertOrUpdateWechatTokenInfo(wechatTokenInfo);
        }
        // 缓存登录token
        String mpTokenKey = TourismUtils.buildMPTokenKey(token);
        MPTokenDto mpTokenDto = new MPTokenDto();
        mpTokenDto.setToken(token);
        mpTokenDto.setAppId(appid);
        mpTokenDto.setOpenId(openId);
        CacheUtils.hMSet(redisTemplate, mpTokenKey, CollectionUtils.objectToMap(mpTokenDto), Constants.TOKEN_EXPIRE_TIME);
        TokenVo tokenVo = new TokenVo();
        tokenVo.setToken(token);
        return tokenVo;
    }

    /*
    WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
        try {
            wxMpOAuth2AccessToken = wxService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        // 登录状态默认未登录
        Integer loginStatus = 0;
        TokenVo tokenVo = new TokenVo();
        // 查看用户是否需要注册
        UserMpInfo userMpInfo = wechatMpService.getLoginStatus(openId);
        if (userMpInfo == null) {
            // 当前微信未登录公众号
            // 是否有注册微信用户信息到系统
            WechatUserInfo wechatUserInfo = wechatMpService.getRegisterStatus(openId);
            if (wechatUserInfo == null) {
                WxMpUser wxMpUser = null;
                try {
                    wxMpUser = wxService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
                } catch (WxErrorException e) {
                    e.printStackTrace();
                }
                wechatUserInfo = new WechatUserInfo();
                wechatUserInfo.setOpenid(openId);
                wechatUserInfo.setNickname(wxMpUser.getNickname());
                wechatUserInfo.setSex(wxMpUser.getSex());
                wechatUserInfo.setProvince(wxMpUser.getProvince());
                wechatUserInfo.setCity(wxMpUser.getCity());
                wechatUserInfo.setCountry(wxMpUser.getCountry());
                wechatUserInfo.setHeadimgurl(wxMpUser.getHeadImgUrl());
                wechatUserInfo.setUnionid(wxMpUser.getUnionId());
                wechatUserInfo.setCreateUser("mp");
                wechatUserInfo.setEditUser("mp");
                wechatMpService.register(wechatUserInfo);
            }
        } else {
            loginStatus = 1;
            // 缓存登录用户的token 和 access_token
            MPTokenDto mpToken = new MPTokenDto();
            mpToken.setToken(userMpInfo.getToken());
            mpToken.setRefreshToken(wxMpOAuth2AccessToken.getAccessToken());
            mpToken.setRefreshToken(wxMpOAuth2AccessToken.getRefreshToken());
            mpToken.setOpenId(openId);
            // TODO - 缓存accessToken信息
            tokenVo.setToken(userMpInfo.getToken());
        }
        tokenVo.setLoginStatus(loginStatus);
     */

    @IgnoreToken
    @ResponseBody
    @RequestMapping("/build_url")
    public String buildReqURL(@PathVariable("appid") String appid) {
        String url = String.format("http://zbuf5n.natappfree.cc/mp/%s/index", appid);
        String redirectURL = wxService.oauth2buildAuthorizationUrl(url, "snsapi_userinfo", "index");
        log.error("RedirectURL >>> " + redirectURL);
        return redirectURL;
    }

    @ResponseBody
    @RequestMapping("/getInfo")
    public WxMpUser getInfo(@PathVariable("appid") String appid) throws WxErrorException {
        String token = BaseContextUtils.get(Constants.MP_TOKEN).toString();
        String openId = BaseContextUtils.get(Constants.OPEN_ID_KEY).toString();
        String accessTokenCacheKey = TourismUtils.buildMpAccessTokenKey(appid, token);
        String accessToken = CacheUtils.hGet(redisTemplate, accessTokenCacheKey, "accessToken");
        WechatTokenInfo wechatTokenInfo = null;
        if (accessToken == null) {
            wechatTokenInfo = wechatTokenInfoService.selectByAppId(appid);
        }
        accessToken = wechatTokenInfo.getAccessToken();
        if (accessToken == null) {
            throw new AppServiceException(ErrorCode.mp_get_access_token_un_authority);
        }
        // 刷新access token
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        wxMpOAuth2AccessToken.setAccessToken(accessToken);
        wxMpOAuth2AccessToken.setOpenId(openId);
        WxMpUser wxMpUser = wxService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
        return wxMpUser;
    }
}
