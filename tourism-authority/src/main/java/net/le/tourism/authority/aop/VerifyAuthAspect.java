package net.le.tourism.authority.aop;

import lombok.extern.slf4j.Slf4j;
import net.le.tourism.authority.common.annotation.IgnoreToken;
import net.le.tourism.authority.common.constant.Constants;
import net.le.tourism.authority.common.exception.AppServiceException;
import net.le.tourism.authority.common.exception.ErrorCode;
import net.le.tourism.authority.common.util.BaseContextUtils;
import net.le.tourism.authority.common.util.ServletUtils;
import net.le.tourism.authority.common.util.TourismUtils;
import net.le.tourism.authority.pojo.dto.AuthTokenDto;
import net.le.tourism.authority.service.ILoginService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author hanle
 * @version v1.0
 * @date 2019/10/30
 * @modify
 * @copyright zhishoubao
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Slf4j
@Aspect
@Order(1)
@Component
public class VerifyAuthAspect {

    @Autowired
    private ILoginService loginService;

    @Pointcut("execution(* net.le.tourism.authority.controller.*.*(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void verify(JoinPoint joinPoint) {
        MethodSignature wrapMethod = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = wrapMethod.getMethod();
        // 判断是否需要进行参数验签
        if (!targetMethod.isAnnotationPresent(IgnoreToken.class)) {
            String token = TourismUtils.getRequestToken();
            HttpServletResponse response = ServletUtils.getResponse();
            if (token == null) {
                log.error("当前未登录！");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                throw new AppServiceException(ErrorCode.authority_un_login);
            }
            // 验证是否登录
            AuthTokenDto authTokenDto = loginService.validateLogin(token);
            if (authTokenDto == null) {
                log.error("登录token无效！");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                throw new AppServiceException(ErrorCode.authority_un_login);
            }
            // 将当前访问用户信息保存到线程本地变量
            BaseContextUtils.set(Constants.ADMIN_NUM, authTokenDto.getAdminNum());
            BaseContextUtils.set(Constants.ADMIN_NAME, authTokenDto.getAdminName());
            BaseContextUtils.set(Constants.LOGIN_TOKEN, authTokenDto.getToken());
        }
    }

    @After("pointcut()")
    public void after() {
        BaseContextUtils.remove();
    }
}
