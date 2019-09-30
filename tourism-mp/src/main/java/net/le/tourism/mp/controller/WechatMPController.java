package net.le.tourism.mp.controller;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import net.le.tourism.authority.common.annotation.IgnoreToken;
import net.le.tourism.mp.pojo.vo.TokenVo;
import net.le.tourism.mp.service.IWechatMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@Controller
@RequestMapping("/mp/{appid}")
public class WechatMPController {

    @Autowired
    private IWechatMpService wechatMpService;

    @IgnoreToken
    @ResponseBody
    @RequestMapping("/build_url")
    public String buildReqURL(@PathVariable("appid") String appid) {
        return wechatMpService.buildReqUrl(appid);
    }

    @IgnoreToken
    @RequestMapping("/index")
    public String login(@PathVariable("appid") String appid, @RequestParam String code, Model model) {
        TokenVo tokenVo = wechatMpService.login(code, appid);
        model.addAttribute("token", tokenVo.getToken());
        return "index";
    }

    @ResponseBody
    @RequestMapping("/getInfo")
    public WxMpUser getInfo() {
        return wechatMpService.getWechatUserInfo();
    }
}
