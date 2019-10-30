package net.le.tourism.mp.controller;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import net.le.tourism.authority.common.annotation.IgnoreToken;
import net.le.tourism.authority.common.exception.AppServiceException;
import net.le.tourism.authority.common.exception.ErrorCode;
import net.le.tourism.mp.pojo.vo.TokenVo;
import net.le.tourism.mp.service.IWechatMpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String buildReqURL(@RequestParam("url") String url) {
        return wechatMpService.buildReqUrl(url);
    }

    @IgnoreToken
    @GetMapping("/auth")
    public TokenVo login(@PathVariable("appid") String appid, String code) {
        if (StringUtils.isEmpty(code)) {
            throw new AppServiceException(ErrorCode.mp_auth_error);
        }
        return wechatMpService.login(code, appid);
    }
}
