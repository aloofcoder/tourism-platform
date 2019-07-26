package net.le.tourism.authority.service;

import net.le.tourism.authority.pojo.dto.AdminLoginInfo;
import net.le.tourism.authority.pojo.dto.TokenDto;
import net.le.tourism.authority.pojo.vo.TokenVo;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-06-15
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
public interface ILoginService {

    /**
     * 管理员登录
     */
    TokenVo login(AdminLoginInfo adminLoginInfo);

    /**
     * 管理员登出
     */
    void logout();

    /**
     * 登录验证
     *
     * @param token
     * @return
     */
    TokenDto validateLogin(String token);
}
