package net.le.tourism.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.le.tourism.authority.common.constant.Constants;
import net.le.tourism.authority.common.exception.AppServiceException;
import net.le.tourism.authority.common.exception.ErrorCode;
import net.le.tourism.authority.pojo.dto.EditRoleInfoDto;
import net.le.tourism.authority.pojo.dto.InsertRoleInfoDto;
import net.le.tourism.authority.pojo.dto.QueryRoleInfoDto;
import net.le.tourism.authority.pojo.entity.RoleAdmin;
import net.le.tourism.authority.pojo.entity.RoleInfo;
import net.le.tourism.authority.pojo.entity.RoleSource;
import net.le.tourism.authority.pojo.vo.QueryRoleInfoVo;
import net.le.tourism.authority.mapper.RoleAdminMapper;
import net.le.tourism.authority.mapper.RoleInfoMapper;
import net.le.tourism.authority.mapper.RoleSourceMapper;
import net.le.tourism.authority.common.result.PageResult;
import net.le.tourism.authority.service.IRoleInfoService;
import net.le.tourism.authority.common.util.BaseContextUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 韩乐
 * @since 2019-05-18
 */
@Service
public class RoleInfoServiceImpl extends ServiceImpl<RoleInfoMapper, RoleInfo> implements IRoleInfoService {

    @Autowired
    private RoleInfoMapper roleInfoMapper;

    @Autowired
    private RoleSourceMapper roleSourceMapper;

    @Autowired
    private RoleAdminMapper roleAdminMapper;

    @Override
    public void insertRoleInfo(InsertRoleInfoDto insertRoleInfoDto) {
        String loginNum = BaseContextUtils.get(Constants.LOGIN_NUM).toString();
        RoleInfo entity = new RoleInfo();
        BeanUtils.copyProperties(insertRoleInfoDto, entity);
        entity.setStatus(0);
        entity.setCreateUser(loginNum);
        entity.setEditUser(loginNum);
        roleInfoMapper.insert(entity);
        Integer roleId = entity.getRoleId();
        roleSourceMapper.insertBatchRoleSource(roleId, insertRoleInfoDto.getSourceIds());
    }

    @Override
    public PageResult<QueryRoleInfoVo> queryRoleInfo(QueryRoleInfoDto queryRoleInfoDto) {
        Page<QueryRoleInfoVo> page = new Page<>(queryRoleInfoDto.getCurrentPage(), queryRoleInfoDto.getPageSize());
        page.setRecords(roleInfoMapper.queryRoleInfo(page, queryRoleInfoDto));
        return new PageResult<>(page);
    }

    @Override
    public void editRoleInfo(EditRoleInfoDto editRoleInfoDto) {
        RoleInfo entity = roleInfoMapper.selectById(editRoleInfoDto.getRoleId());
        if (entity == null) {
            throw new AppServiceException(ErrorCode.sys_role_info_is_null);
        }
        BeanUtils.copyProperties(editRoleInfoDto, entity);
        roleInfoMapper.updateById(entity);
        // 删除原来分配的角色
        QueryWrapper<RoleSource> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", editRoleInfoDto.getRoleId());
        roleSourceMapper.delete(wrapper);
        // 添加新角色
        roleSourceMapper.insertBatchRoleSource(editRoleInfoDto.getRoleId(), editRoleInfoDto.getSourceIds());
    }

    @Override
    public void removeRoleInfo(Integer roleId) {
        // 如果当前角色有分配用户不能删除
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("role_id", roleId);
        List<RoleAdmin> roleAdminList = roleAdminMapper.selectList(wrapper);
        if (roleAdminList.size() > 0) {
            throw new AppServiceException(ErrorCode.sys_remove_role_error);
        }
        // 删除给当前角色分配的资源
        roleSourceMapper.delete(wrapper);
        // 删除角色
        roleInfoMapper.deleteById(roleId);
    }
}
