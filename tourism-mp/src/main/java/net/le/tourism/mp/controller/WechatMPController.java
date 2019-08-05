package net.le.tourism.mp.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
@RequestMapping("/mp/redirect/{appid}")
public class WechatMPController {

    private final WxMpService wxService;

    @RequestMapping("/login")
    public String login(@PathVariable("appid") String appid, @RequestParam String code) {
        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }

        try {
            WxMpOAuth2AccessToken accessToken = wxService.oauth2getAccessToken(code);
            WxMpUser user = wxService.oauth2getUserInfo(accessToken, null);
            System.out.println(user);
            log.error("loginUser >>> " + user);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return "redirect:index";
    }

    @RequestMapping("/build_url")
    public String buildReqURL() {
        String url = "http://localhost:8002/mp/redirect/wx7bc5e4de130fc588/login";
        String redirectURL = wxService.oauth2buildAuthorizationUrl(url, "snsapi_userinfo", "index");
        log.error("RedirectURL >>> " + redirectURL);
        return redirectURL;
    }
}
