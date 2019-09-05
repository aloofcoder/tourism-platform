package net.le.tourism.authority.service;

import net.le.tourism.authority.common.constant.Constants;
import net.le.tourism.authority.common.util.BaseContextUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-06-24
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RoleAdminServiceTest {

    @Autowired
    private IRoleAdminService roleAdminService;

    @Before
    public void before() {
        BaseContextUtils.set(Constants.ADMIN_NUM, 24);
        BaseContextUtils.set(Constants.ADMIN_NAME, "admin");
        BaseContextUtils.set(Constants.LOGIN_TOKEN, "0469dff7d77c4d61a4dd579d879720bc");
    }

    @Rollback
    @Test
    public void batchInsertRoleAdminTest() {
        List<Integer> roleIds = new ArrayList<>();
        roleIds.add(1);
        roleIds.add(2);
        roleIds.add(3);
        roleAdminService.insertRoleAdminByAdmin("1", roleIds);
    }
}
