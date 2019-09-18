package net.le.tourism.mp.adapter;

import lombok.extern.slf4j.Slf4j;
import net.le.tourism.authority.common.annotation.IgnoreToken;
import net.le.tourism.authority.common.constant.Constants;
import net.le.tourism.authority.common.exception.AppServiceException;
import net.le.tourism.authority.common.exception.ErrorCode;
import net.le.tourism.authority.common.util.BaseContextUtils;
import net.le.tourism.authority.common.util.TourismUtils;
import net.le.tourism.mp.pojo.dto.MPTokenDto;
import net.le.tourism.mp.service.IWechatMpService;
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
    private IWechatMpService wechatMpService;

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
        }
        return super.preHandle(request, response, handler);
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContextUtils.remove();
        super.afterCompletion(request, response, handler, ex);
    }
}
