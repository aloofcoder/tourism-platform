package net.le.tourism.authority.service;

import net.le.tourism.authority.pojo.dto.InsertLoginLogDto;
import net.le.tourism.authority.pojo.entity.LoginLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 韩乐
 * @since 2019-07-16
 */
public interface ILoginLogService extends IService<LoginLog> {

    void addLoginLog(String loginNum, Integer status, String loginResult);
}
