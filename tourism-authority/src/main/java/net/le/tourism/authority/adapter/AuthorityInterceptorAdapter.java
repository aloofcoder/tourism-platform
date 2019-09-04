package net.le.tourism.authority.adapter;

import lombok.extern.slf4j.Slf4j;
import net.le.tourism.authority.common.annotation.IgnoreToken;
import net.le.tourism.authority.common.constant.Constants;
import net.le.tourism.authority.common.exception.AppServiceException;
import net.le.tourism.authority.common.exception.ErrorCode;
import net.le.tourism.authority.common.util.BaseContextUtils;
import net.le.tourism.authority.common.util.TourismUtils;
import net.le.tourism.authority.pojo.dto.AuthTokenDto;
import net.le.tourism.authority.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-06-14
 * @modify 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
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
            AuthTokenDto authTokenDto = loginService.validateLogin(token);
            if (authTokenDto == null) {
                log.error("登录token无效！");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                throw new AppServiceException(ErrorCode.authority_un_login);
            }
            // 将当前访问用户信息保存到线程本地变量
            BaseContextUtils.set(Constants.ADMIN_NUM, authTokenDto.getAdminNum());
            BaseContextUtils.set(Constants.ADMIN_ORG, authTokenDto.getOrgId());
            BaseContextUtils.set(Constants.LOGIN_TOKEN, authTokenDto.getToken());
        }
        return super.preHandle(request, response, handler);
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContextUtils.remove();
        super.afterCompletion(request, response, handler, ex);
    }
}
