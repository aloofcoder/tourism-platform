package net.le.tourism.authority.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.le.tourism.authority.pojo.dto.EditAdminInfoDto;
import net.le.tourism.authority.pojo.dto.InsertAdminInfoDto;
import net.le.tourism.authority.pojo.dto.QueryAdminInfoDto;
import net.le.tourism.authority.pojo.entity.AdminInfo;
import net.le.tourism.authority.pojo.vo.QueryAdminInfoVo;
import net.le.tourism.authority.common.result.PageResult;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 韩乐
 * @since 2019-05-18
 */
public interface IAdminInfoService extends IService<AdminInfo> {

    PageResult<QueryAdminInfoVo> queryAdminInInfo(QueryAdminInfoDto queryAdminInfoDto);

    AdminInfo getAdminInfoByLoginName(String loginName);

    Integer addAdminInfo(InsertAdminInfoDto adminInfoDto);

    void editAdminInfo(EditAdminInfoDto editAdminInfoDto);

    void removeAdminInfoById(Integer adminId);

    void editAdminInfoStatus(Integer adminId);
}
