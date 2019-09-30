package net.le.tourism.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.le.tourism.mp.pojo.entity.WechatTokenInfo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 韩乐
 * @since 2019-09-21
 */
public interface IWechatTokenInfoService extends IService<WechatTokenInfo> {

    /**
     * 根据appId获取微信access token
     *
     * @param appId
     * @return
     */
    WechatTokenInfo selectByAppId(String appId);

    /**
     * 添加或更新微信access token
     * @param wechatTokenInfo
     */
    void insertOrUpdateWechatToken(WechatTokenInfo wechatTokenInfo);
}
