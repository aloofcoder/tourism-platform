package net.le.tourism.mp.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import net.le.tourism.authority.common.util.CacheUtils;
import net.le.tourism.authority.common.util.CollectionUtils;
import net.le.tourism.authority.common.util.ServletUtils;
import net.le.tourism.mp.pojo.dto.TokenModelDto;
import net.le.tourism.mp.pojo.vo.TokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @ResponseBody
    @RequestMapping("/login")
    public TokenVo login(@PathVariable("appid") String appid, @RequestParam String code) {
        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        TokenVo tokenVo = new TokenVo();
        try {
            WxMpOAuth2AccessToken accessToken = wxService.oauth2getAccessToken(code);
            String token = UUID.randomUUID().toString().replaceAll("-", "");
            TokenModelDto tokenModel = new TokenModelDto();
            tokenModel.setOpenId(accessToken.getOpenId());
            tokenModel.setAccessToken(accessToken.getAccessToken());
            tokenModel.setRefreshToken(accessToken.getRefreshToken());
            Map<String, Object> tokenMap = CollectionUtils.objectToMap(tokenModel);
            CacheUtils.hMSet(redisTemplate, token, tokenMap, accessToken.getExpiresIn());
            tokenVo.setToken(token);
        } catch (WxErrorException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tokenVo;
    }

    @ResponseBody
    @RequestMapping("/build_url")
    public String buildReqURL(@PathVariable("appid") String appid) {
        String url = String.format("http://ef8wj8.natappfree.cc/mp/%s/login", appid);
        String redirectURL = wxService.oauth2buildAuthorizationUrl(url, "snsapi_userinfo", "index");
        log.error("RedirectURL >>> " + redirectURL);
        return redirectURL;
    }

    @ResponseBody
    @RequestMapping("/getInfo")
    public String getInfo() {
        HttpServletRequest request = ServletUtils.getRequest();
        String token = request.getHeader("sid");
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute(token));
        return "";
    }

}
