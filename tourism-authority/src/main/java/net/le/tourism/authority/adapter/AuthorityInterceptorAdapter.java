package net.le.tourism.authority.adapter;

import lombok.extern.slf4j.Slf4j;
import net.le.tourism.authority.annotation.IgnoreToken;
import net.le.tourism.authority.constant.Constants;
import net.le.tourism.authority.exception.AppServiceException;
import net.le.tourism.authority.exception.ErrorCode;
import net.le.tourism.authority.pojo.dto.TokenDto;
import net.le.tourism.authority.service.ILoginService;
import net.le.tourism.authority.util.BaseContextUtils;
import net.le.tourism.authority.util.TourismUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-06-14
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Slf4j
@Component
public class AuthorityInterceptorAdapter extends HandlerInterceptorAdapter {

    @Autowired
    private ILoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            log.info(request.getRequestURI());
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            IgnoreToken annotation = handlerMethod.getBeanType().getAnnotation(IgnoreToken.class);
            if (annotation == null) {
                annotation = handlerMethod.getMethodAnnotation(IgnoreToken.class);
            }
            if (annotation != null) {
                return super.preHandle(request, response, handler);
            }
            String token = TourismUtils.getRequestToken();
            if (token == null) {
                log.error("当前未登录！");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                throw new AppServiceException(ErrorCode.authority_un_login);
            }
            // 验证是否登录
            TokenDto tokenDto = loginService.validateLogin(token);
            if (tokenDto == null) {
                log.error("登录token无效！");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                throw new AppServiceException(ErrorCode.authority_un_login);
            }
            // 将当前访问用户信息保存到线程本地变量
            BaseContextUtils.set(Constants.LOGIN_ID, tokenDto.getAdminId());
            BaseContextUtils.set(Constants.LOGIN_NUM, tokenDto.getAdminNum());
            BaseContextUtils.set(Constants.LOGIN_NAME, tokenDto.getAdminName());
            BaseContextUtils.set(Constants.LOGIN_TOKEN, tokenDto.getToken());
            BaseContextUtils.set(Constants.LOGIN_ORG, tokenDto.getOrgId());
        }
        return super.preHandle(request, response, handler);
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContextUtils.remove();
        super.afterCompletion(request, response, handler, ex);
    }
}
