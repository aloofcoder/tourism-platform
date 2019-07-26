package net.le.tourism.authority.service;

import net.le.tourism.authority.constant.Constants;
import net.le.tourism.authority.pojo.dto.InsertOrgInfoDto;
import net.le.tourism.authority.pojo.dto.QueryOrgInfoDto;
import net.le.tourism.authority.util.BaseContextUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-06-25
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrgInfoServiceTest {

    @Autowired
    private IOrgInfoService orgInfoService;

    @Before
    public void before() {
        BaseContextUtils.set(Constants.LOGIN_ID, "24");
        BaseContextUtils.set(Constants.LOGIN_NAME, "admin");
        BaseContextUtils.set(Constants.LOGIN_TOKEN, "0469dff7d77c4d61a4dd579d879720bc");
    }

    @Test
    public void searchOrgInfoTest() {
        QueryOrgInfoDto queryOrgInfoDto = new QueryOrgInfoDto();
        orgInfoService.queryOrgInfo(queryOrgInfoDto);
    }


    @Test
    public void insertOrgInfoTest() {
        InsertOrgInfoDto insertOrgInfoDto = new InsertOrgInfoDto();
        insertOrgInfoDto.setOrgAdmin("管理员");
        insertOrgInfoDto.setOrgClass(2);
        insertOrgInfoDto.setOrgSort(1);
        insertOrgInfoDto.setOrgName("贝肯管理平台");
        insertOrgInfoDto.setParentId(-1);
        orgInfoService.insertOrgInfo(insertOrgInfoDto);
    }
}
