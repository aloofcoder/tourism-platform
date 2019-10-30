package net.le.tourism.mp;

import net.le.tourism.mp.mapper.UserMpInfoMapper;
import net.le.tourism.mp.mapper.WechatTokenInfoMapper;
import net.le.tourism.mp.pojo.entity.UserMpInfo;
import net.le.tourism.mp.service.IWechatMpService;
import net.le.tourism.mp.service.impl.WechatMPServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

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

    public static final String openId = "o3SPy5K-CVa9bUKtNIFnjnmuX2Ro";

    @Mock
    private UserMpInfoMapper userMpInfoMapper;

    @InjectMocks
    private IWechatMpService wechatMpService = new WechatMPServiceImpl();

    @Before
    public void setMockOutPut() {
        UserMpInfo userMpInfo = new UserMpInfo();
        userMpInfo.setOpenid(openId);
        userMpInfo.setStatus(0);
        userMpInfo.setId(1);
        when(userMpInfoMapper.selectByOpenId(openId)).thenReturn(userMpInfo);
    }

    @Test
    public void TestInsertOrUpdate() {
        // 在测试中使用mock对象
        assertEquals(wechatMpService.getLoginStatus(openId).getOpenid(), openId);
    }

}
