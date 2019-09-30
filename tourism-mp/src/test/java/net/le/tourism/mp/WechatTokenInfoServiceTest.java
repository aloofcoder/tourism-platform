package net.le.tourism.mp;

import net.le.tourism.mp.pojo.entity.WechatTokenInfo;
import net.le.tourism.mp.service.IWechatTokenInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author hanle
 * @version v1.0
 * @date 2019/9/21
 * @modify
 * @copyright zhishoubao
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatTokenInfoServiceTest {

    @Autowired
    private IWechatTokenInfoService wechatTokenInfoService;

    @Test
    public void TestInsertOrUpdate() {
        WechatTokenInfo wechatTokenInfo = new WechatTokenInfo();
        wechatTokenInfo.setAppId("1");
        wechatTokenInfo.setAccessToken("000000");
        wechatTokenInfo.setRefreshToken("11111");
        wechatTokenInfoService.insertOrUpdateWechatToken(wechatTokenInfo);
    }
}
