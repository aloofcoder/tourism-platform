package net.le.tourism.mp.service.impl;

import net.le.tourism.mp.mapper.WechatTokenInfoMapper;
import net.le.tourism.mp.pojo.entity.WechatTokenInfo;
import net.le.tourism.mp.service.IWechatTokenInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hanle
 * @version v1.0
 * @date 2019/9/18
 * @modify
 * @copyright zhishoubao
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Service
public class WechatTokenInfoServiceImpl implements IWechatTokenInfoService {

    @Autowired
    private WechatTokenInfoMapper wechatTokenInfoMapper;

    @Override
    public void insertOrUpdateWechatTokenInfo(WechatTokenInfo wechatTokenInfo) {
        String appId = wechatTokenInfo.getAppId();
        WechatTokenInfo entity = selectByAppId(appId);
        if (wechatTokenInfo != null) {
            wechatTokenInfo.setAccessToken(wechatTokenInfo.getAccessToken());
            wechatTokenInfo.setRefreshToken(wechatTokenInfo.getRefreshToken());
            // 更新access token
            wechatTokenInfoMapper.updateById(entity);
        } else {
            wechatTokenInfo = new WechatTokenInfo();
            wechatTokenInfo.setAccessToken(wechatTokenInfo.getAccessToken());
            wechatTokenInfo.setRefreshToken(wechatTokenInfo.getRefreshToken());
            wechatTokenInfo.setAppId(appId);
            // 添加access token
            wechatTokenInfoMapper.insert(entity);
        }
    }

    @Override
    public WechatTokenInfo selectByAppId(String appId) {
        return wechatTokenInfoMapper.selectByAppId(appId);
    }
}
