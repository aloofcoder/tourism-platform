package net.le.tourism.authority.service.impl;

import net.le.tourism.authority.common.constant.Constants;
import net.le.tourism.authority.pojo.dto.InsertLoginLogDto;
import net.le.tourism.authority.pojo.entity.LoginLog;
import net.le.tourism.authority.mapper.LoginLogMapper;
import net.le.tourism.authority.service.ILoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
<<<<<<< HEAD
import net.le.tourism.authority.common.util.BaseContextUtils;
import net.le.tourism.authority.common.util.ServletUtils;
=======
import net.le.tourism.authority.util.BaseContextUtils;
import net.le.tourism.authority.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> bc14e8d1a1a1ad54b6f19616f77ad8f596f92f8a
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 韩乐
 * @since 2019-07-16
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public void addLoginLog(String loginNum, Integer status, String loginResult) {
        LoginLog entity = new LoginLog();
        entity.setAdminNum(loginNum);
        entity.setLoginTime(new Date());
        String loginIp = ServletUtils.getIpAddr();
        entity.setLoginIp(loginIp);
        entity.setStatus(status);
        entity.setLoginResult(loginResult);
        loginLogMapper.insert(entity);
    }
}
