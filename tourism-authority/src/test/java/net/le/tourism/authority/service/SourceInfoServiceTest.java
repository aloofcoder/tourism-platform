package net.le.tourism.authority.service;

import com.alibaba.fastjson.JSON;
import net.le.tourism.authority.common.constant.Constants;
import net.le.tourism.authority.common.util.BaseContextUtils;
import net.le.tourism.authority.pojo.dto.QuerySourceInfoDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hanle
 * @version v1.0
 * @date 2019/6/29
 * @modify 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SourceInfoServiceTest {

    @Autowired
    private ISourceInfoService sourceInfoService;

    @Before
    public void before() {
        BaseContextUtils.set(Constants.LOGIN_ID, 24);
        BaseContextUtils.set(Constants.LOGIN_NAME, "admin");
        BaseContextUtils.set(Constants.LOGIN_TOKEN, "0469dff7d77c4d61a4dd579d879720bc");
    }

    @Rollback
    @Test
    public void querySourceInfoTest() {
        QuerySourceInfoDto querySourceInfoDto = new QuerySourceInfoDto();
        System.out.println(JSON.toJSON(sourceInfoService.querySourceInfo(querySourceInfoDto)));
    }
}
