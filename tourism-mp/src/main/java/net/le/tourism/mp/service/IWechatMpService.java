package net.le.tourism.mp.service;

import me.chanjar.weixin.mp.bean.result.WxMpUser;
import net.le.tourism.mp.pojo.dto.MPTokenDto;
import net.le.tourism.mp.pojo.entity.UserMpInfo;
import net.le.tourism.mp.pojo.entity.WechatUserInfo;
import net.le.tourism.mp.pojo.vo.TokenVo;

/**
 * @author hanle
 * @version v1.0
 * @date 2019/9/2
 * @modify
 * @copyright zhishoubao
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
public interface IWechatMpService {

    String buildReqUrl(String appId);

    TokenVo login(String code, String appId);

    /**
     * 获取公众号登录状态
     * @param openId
     * @return
     */
    UserMpInfo getLoginStatus(String openId);

    MPTokenDto checkLogin(String token);

    WxMpUser getWechatUserInfo();
}
