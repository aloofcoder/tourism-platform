package net.le.tourism.authority.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.HttpMethod;
import net.le.tourism.authority.annotation.IgnoreToken;
import net.le.tourism.authority.pojo.dto.AdminLoginInfo;
import net.le.tourism.authority.pojo.vo.TokenVo;
import net.le.tourism.authority.result.CommonResult;
import net.le.tourism.authority.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-06-15
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Api(tags = "登录管理")
@RestController
@RequestMapping("/authority")
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @IgnoreToken
    @PostMapping
    @ApiOperation(value = "管理员登陆接口")
    public CommonResult login(@Valid @RequestBody AdminLoginInfo adminLoginInfo) {
        TokenVo tokenVo = loginService.login(adminLoginInfo);
        return new CommonResult(tokenVo);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "管理员退出")
    public CommonResult logout() {
        loginService.logout();
        return new CommonResult();
    }
}
