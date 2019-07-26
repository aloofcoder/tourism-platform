package net.le.tourism.authority.service;

import net.le.tourism.authority.pojo.dto.AdminLoginInfo;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-06-15
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginServiceTest {

    @Autowired
    private ILoginService loginService;

    @Test
    public void login() {
        AdminLoginInfo adminLoginInfo = new AdminLoginInfo();
        adminLoginInfo.setLoginNum("admin");
        adminLoginInfo.setLoginPwd("admin");
        System.out.println("=========================== 登录成功 ===========================");
        loginService.login(adminLoginInfo);
    }

    @Test
    public void validateToken() {
        System.out.println(loginService.validateLogin("0469dff7d77c4d61a4dd579d879720bc"));
    }
}
