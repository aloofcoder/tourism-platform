package net.le.tourism.authority.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.le.tourism.authority.pojo.dto.EditRoleInfoDto;
import net.le.tourism.authority.pojo.dto.InsertRoleInfoDto;
import net.le.tourism.authority.pojo.dto.QueryRoleInfoDto;
import net.le.tourism.authority.pojo.entity.RoleInfo;
import net.le.tourism.authority.pojo.vo.QueryCompleteRoleInfoVo;
import net.le.tourism.authority.pojo.vo.QueryRoleInfoVo;
import net.le.tourism.authority.result.PageResult;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 韩乐
 * @since 2019-05-18
 */
public interface IRoleInfoService extends IService<RoleInfo> {

    void insertRoleInfo(InsertRoleInfoDto insertRoleInfoDto);

    PageResult<QueryRoleInfoVo> queryRoleInfo(QueryRoleInfoDto queryRoleInfoDto);

    void editRoleInfo(EditRoleInfoDto editRoleInfoDto);

    void removeRoleInfo(Integer roleId);

    List<QueryCompleteRoleInfoVo> queryCompleteRoleInfo();
}
