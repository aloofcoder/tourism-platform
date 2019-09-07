package net.le.tourism.mp.controller;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import net.le.tourism.authority.common.annotation.IgnoreToken;
import net.le.tourism.authority.common.constant.Constants;
import net.le.tourism.authority.common.util.BaseContextUtils;
import net.le.tourism.authority.common.util.CacheUtils;
import net.le.tourism.authority.common.util.TourismUtils;
import net.le.tourism.mp.pojo.dto.MPTokenDto;
import net.le.tourism.mp.pojo.vo.TokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.UUID;

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

    @IgnoreToken
    @ResponseBody
    @RequestMapping("/login")
    public TokenVo login(@PathVariable("appid") String appid, @RequestParam String code) {
        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        WxMpOAuth2AccessToken accessToken = null;
        try {
            accessToken = wxService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        MPTokenDto tokenModel = new MPTokenDto();
        tokenModel.setOpenId(accessToken.getOpenId());
        tokenModel.setAccessToken(accessToken.getAccessToken());
        tokenModel.setRefreshToken(accessToken.getRefreshToken());
        tokenModel.setToken(token);
        Map<String, Object> tokenMap = BeanMap.create(tokenModel);
        String tokenKey = TourismUtils.buildMPTokenKey(token);
        CacheUtils.hMSet(redisTemplate, tokenKey, tokenMap, accessToken.getExpiresIn());
        TokenVo tokenVo = new TokenVo();
        tokenVo.setToken(token);
        return tokenVo;
    }

    @IgnoreToken
    @ResponseBody
    @RequestMapping("/build_url")
    public String buildReqURL(@PathVariable("appid") String appid) {
        String url = String.format("http://xkgiji.natappfree.cc/mp/%s/login", appid);
        String redirectURL = wxService.oauth2buildAuthorizationUrl(url, "snsapi_userinfo", "index");
        log.error("RedirectURL >>> " + redirectURL);
        return redirectURL;
    }

    @ResponseBody
    @RequestMapping("/getInfo")
    public WxMpUser getInfo() {
        String token = BaseContextUtils.get(Constants.MP_TOKEN).toString();
        String openId = BaseContextUtils.get(Constants.OPEN_ID_KEY).toString();
        String tokenKey = TourismUtils.buildMPTokenKey(token);
        String accessToken = CacheUtils.hGet(redisTemplate, tokenKey, "accessToken");
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        wxMpOAuth2AccessToken.setAccessToken(accessToken);
        wxMpOAuth2AccessToken.setOpenId(openId);
        WxMpUser wxMpUser = null;
        try {
            wxMpUser = wxService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        log.info(JSON.toJSONString(wxMpUser));
        return wxMpUser;
    }

}
