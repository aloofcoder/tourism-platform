package net.le.tourism.authority.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.le.tourism.authority.pojo.dto.EditSourceInfoByRoleDto;
import net.le.tourism.authority.pojo.dto.InsertSourceInfoByRoleDto;
import net.le.tourism.authority.pojo.dto.QuerySourceInfoDto;
import net.le.tourism.authority.pojo.entity.SourceInfo;
import net.le.tourism.authority.pojo.vo.QuerySourceInfoVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 韩乐
 * @since 2019-06-28
 */
public interface ISourceInfoService extends IService<SourceInfo> {

    List<QuerySourceInfoVo> querySourceInfo(QuerySourceInfoDto querySourceInfoDto);

    void insertSourceInfo(InsertSourceInfoByRoleDto insertSourceInfoByRoleDto);

    void editSourceInfo(EditSourceInfoByRoleDto editSourceInfoByRoleDto);

    void removeSourceInfoBySourceId(Integer sourceId);

}
