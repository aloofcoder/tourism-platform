package net.le.tourism.authority.service.impl;

import net.le.tourism.authority.common.constant.Constants;
import net.le.tourism.authority.pojo.dto.InsertLoginLogDto;
import net.le.tourism.authority.pojo.entity.LoginLog;
import net.le.tourism.authority.mapper.LoginLogMapper;
import net.le.tourism.authority.service.ILoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.le.tourism.authority.common.util.BaseContextUtils;
import net.le.tourism.authority.common.util.ServletUtils;
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


    @Override
    public void addLoginLog(InsertLoginLogDto insertLoginLogDto) {
        String loginNum = BaseContextUtils.get(Constants.LOGIN_NUM).toString();
        LoginLog entity = new LoginLog();
        entity.setAdminNum(loginNum);
        entity.setLoginTime(new Date());
        String loginIp = ServletUtils.getIpAddr();
        entity.setLoginIp(loginIp);
    }
}
