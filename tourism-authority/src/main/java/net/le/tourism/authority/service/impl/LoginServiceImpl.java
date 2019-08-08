package net.le.tourism.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.le.tourism.authority.constant.Constants;
import net.le.tourism.authority.exception.AppServiceException;
import net.le.tourism.authority.exception.ErrorCode;
import net.le.tourism.authority.mapper.AdminInfoMapper;
import net.le.tourism.authority.mapper.OrgAdminMapper;
import net.le.tourism.authority.pojo.dto.AdminLoginInfo;
import net.le.tourism.authority.pojo.dto.TokenDto;
import net.le.tourism.authority.pojo.entity.AdminInfo;
import net.le.tourism.authority.pojo.entity.OrgAdmin;
import net.le.tourism.authority.pojo.vo.TokenVo;
import net.le.tourism.authority.service.ILoginLogService;
import net.le.tourism.authority.service.ILoginService;
import net.le.tourism.authority.service.IOrgAdminService;
import net.le.tourism.authority.service.IRoleAdminService;
import net.le.tourism.authority.util.BaseContextUtils;
import net.le.tourism.authority.util.CacheUtils;
import net.le.tourism.authority.util.ServletUtils;
import net.le.tourism.authority.util.TourismUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-06-15
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Slf4j
@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private AdminInfoMapper adminInfoMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IOrgAdminService orgAdminService;

    @Autowired
    private ILoginLogService loginLogService;

    @Override
    public TokenVo login(AdminLoginInfo adminLoginInfo) {
        QueryWrapper<AdminInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 0)
                .and(wrapper -> wrapper
                        .like("login_name", adminLoginInfo.getLoginNum())
                        .or().like("admin_mobile", adminLoginInfo.getLoginNum())
                        .or().like("admin_email", adminLoginInfo.getLoginNum()));
        AdminInfo adminInfo = adminInfoMapper.selectOne(queryWrapper);
        if (adminInfo == null) {
            loginLogService.addLoginLog(adminLoginInfo.getLoginNum(), 1, "登录账号不存在");
            throw new AppServiceException(ErrorCode.authority_login_user_un_register);
        }
        String pwd = TourismUtils.buildAdminPwd(adminInfo.getSalt(), adminLoginInfo.getLoginPwd());
        if (!adminInfo.getAdminPwd().equals(pwd)) {
            loginLogService.addLoginLog(adminLoginInfo.getLoginNum(), 1, "登录密码错误");
            throw new AppServiceException(ErrorCode.authority_login_info_error);
        }
        // 获取当前登录人所在组织及所拥有角色
        OrgAdmin orgAdmin = orgAdminService.queryOrgAdminByAdminId(adminInfo.getAdminId());
        if (orgAdmin == null) {
            loginLogService.addLoginLog(adminLoginInfo.getLoginNum(), 1, "账号信息异常");
            throw new AppServiceException(ErrorCode.authority_login_Info_Invalid);
        }
        // 登录成功添加登录日志
        loginLogService.addLoginLog(adminLoginInfo.getLoginNum(), 0, "登录成功");
        String token = TourismUtils.getToken();
        // 生成用户登录token缓存
        String tokenKey = TourismUtils.getAdminTokenKey(adminInfo.getAdminNum(), token);
        TokenDto tokenDto = new TokenDto();
        tokenDto.setAdminId(adminInfo.getAdminId());
        tokenDto.setAdminNum(adminInfo.getAdminNum());
        tokenDto.setAdminName(adminInfo.getAdminName());
        tokenDto.setToken(token);
        tokenDto.setOrgId(orgAdmin.getOrgId());
        // 缓存登录token
        CacheUtils.hMSet(redisTemplate, tokenKey, BeanMap.create(tokenDto), 7200);
        HttpServletResponse response = ServletUtils.getResponse();
        response.addHeader(Constants.AUTHORITY_KEY, token);
        TokenVo tokenVo = new TokenVo();
        tokenVo.setAdminName(adminInfo.getAdminName());
        tokenVo.setToken(token);
        return tokenVo;
    }

    @Override
    public void logout() {
        String token = TourismUtils.getRequestToken();
        String adminId = BaseContextUtils.get(Constants.LOGIN_ID).toString();
        AdminInfo adminInfo = adminInfoMapper.selectById(adminId);
        String tokenKey = TourismUtils.getAdminTokenKey(adminInfo.getAdminNum(), token);
        CacheUtils.remove(redisTemplate, tokenKey);
    }

    @Override
    public TokenDto validateLogin(String token) {
        String pattern = TourismUtils.getAdminTokenKey("*", token);
        String tokenKey = CacheUtils.getOnlyKey(redisTemplate, pattern);
        if (StringUtils.isEmpty(tokenKey)) {
            return null;
        }
        String adminId = CacheUtils.hGet(redisTemplate, tokenKey, Constants.LOGIN_ID);
        String adminNum = CacheUtils.hGet(redisTemplate, tokenKey, Constants.LOGIN_NUM);
        String adminName = CacheUtils.hGet(redisTemplate, tokenKey, Constants.LOGIN_NAME);
        Integer orgId = Integer.parseInt(CacheUtils.hGet(redisTemplate, tokenKey, Constants.LOGIN_ORG));
        if (StringUtils.isEmpty(adminId) || StringUtils.isEmpty(adminName) || StringUtils.isEmpty(adminNum)) {
            return null;
        }
        TokenDto tokenDto = new TokenDto();
        tokenDto.setAdminId(Integer.parseInt(adminId));
        tokenDto.setAdminNum(adminNum);
        tokenDto.setAdminName(adminName);
        tokenDto.setToken(token);
        tokenDto.setOrgId(orgId);
        return tokenDto;
    }
}
