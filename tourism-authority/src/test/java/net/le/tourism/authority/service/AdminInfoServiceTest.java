package net.le.tourism.authority.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.le.tourism.authority.common.constant.Constants;
import net.le.tourism.authority.common.result.PageResult;
import net.le.tourism.authority.common.util.BaseContextUtils;
import net.le.tourism.authority.pojo.dto.InsertAdminInfoDto;
import net.le.tourism.authority.pojo.dto.QueryAdminInfoDto;
import net.le.tourism.authority.pojo.entity.AdminInfo;
import net.le.tourism.authority.pojo.vo.QueryAdminInfoVo;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-05-17
 * @modify 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminInfoServiceTest {

    @Autowired
    private IAdminInfoService adminInfoService;

    @Before
    public void before() {
        BaseContextUtils.set(Constants.ADMIN_NAME, "admin");
        BaseContextUtils.set(Constants.ADMIN_NUM, "T92476484763124");
        BaseContextUtils.set(Constants.LOGIN_TOKEN, "0469dff7d77c4d61a4dd579d879720bc");
    }

    @Test
    public void searchAdminInfoTest() {
        System.out.println(adminInfoService.list());
    }

    //    @Rollback
    @Test
    public void insertAdminInfoTest() {
        InsertAdminInfoDto adminInfo = new InsertAdminInfoDto();
        adminInfo.setAdminName("admin");
        adminInfo.setLoginName("admin");
        adminInfo.setAdminPwd("admin!@#");
        adminInfo.setAdminMobile("18149197030");
        adminInfo.setAdminEmail("hanl1946@163.com");
        List<Integer> roleIds = new ArrayList<>();
        roleIds.add(2);
        adminInfo.setRoleIds(roleIds);
        adminInfo.setOrgId(1);
        adminInfoService.addAdminInfo(adminInfo);
    }

    @Test
    public void searchAdminInfoByAdminNameTest() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("admin_name", "admin");
        AdminInfo adminInfo = adminInfoService.getOne(queryWrapper);
        System.out.println(adminInfo);
    }

    @Test
    public void getAdminInfo() {
        QueryAdminInfoDto queryAdminInfoDto = new QueryAdminInfoDto();
        queryAdminInfoDto.setCurrentPage(1);
        queryAdminInfoDto.setPageSize(10);
        queryAdminInfoDto.setCondition("demo");
        PageResult<QueryAdminInfoVo> page = adminInfoService.queryAdminInInfo(queryAdminInfoDto);
        System.out.println(JSON.toJSON(page));
    }
}
