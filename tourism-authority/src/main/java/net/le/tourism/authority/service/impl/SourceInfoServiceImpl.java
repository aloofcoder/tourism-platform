package net.le.tourism.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.le.tourism.authority.common.constant.Constants;
import net.le.tourism.authority.common.exception.AppServiceException;
import net.le.tourism.authority.common.exception.ErrorCode;
import net.le.tourism.authority.common.util.BaseContextUtils;
import net.le.tourism.authority.mapper.RoleAdminMapper;
import net.le.tourism.authority.mapper.RoleSourceMapper;
import net.le.tourism.authority.mapper.SourceInfoMapper;
import net.le.tourism.authority.pojo.dto.EditSourceInfoByRoleDto;
import net.le.tourism.authority.pojo.dto.InsertSourceInfoByRoleDto;
import net.le.tourism.authority.pojo.dto.QuerySourceInfoDto;
import net.le.tourism.authority.pojo.entity.SourceInfo;
import net.le.tourism.authority.pojo.vo.QuerySourceInfoVo;
import net.le.tourism.authority.service.IAdminInfoService;
import net.le.tourism.authority.service.IRoleInfoService;
import net.le.tourism.authority.service.ISourceInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 韩乐
 * @since 2019-06-28
 */
@Service
public class SourceInfoServiceImpl extends ServiceImpl<SourceInfoMapper, SourceInfo> implements ISourceInfoService {

    @Autowired
    private RoleAdminMapper roleAdminMapper;

    @Autowired
    private RoleSourceMapper roleSourceMapper;

    @Autowired
    private SourceInfoMapper sourceInfoMapper;

    @Autowired
    private IAdminInfoService adminInfoService;

    @Autowired
    private IRoleInfoService roleInfoService;

    @Override
    public List<QuerySourceInfoVo> querySourceInfo(QuerySourceInfoDto querySourceInfoDto) {
        // 查询当前登录人的所有角色ID
        String adminNum = BaseContextUtils.get(Constants.ADMIN_NUM).toString();
        if (adminNum == null) {
            throw new AppServiceException(ErrorCode.sys_login_info_error);
        }
        List<Integer> roleIds = roleAdminMapper.selectRoleIdByAdminNum(adminNum);
        if (roleIds.size() == 0) {
            return new ArrayList<>();
        }
        // 获取当前角色的所有资源
        List<Integer> sourceIds = roleSourceMapper.selectSourceIdsBatchRoleId(roleIds);
        if (sourceIds.size() == 0) {
            return new ArrayList<>();
        }
        SourceInfo sourceInfo = sourceInfoMapper.selectByParentId(0);
        Integer parentId = sourceInfo.getSourceId();
        // 通过资源ID获取所有授权的资源信息
        List<QuerySourceInfoVo> sourceInfoList = sourceInfoMapper.querySourceInfoBatchSourceId(sourceIds);

        List<QuerySourceInfoVo> trees = new ArrayList<>();

        for (QuerySourceInfoVo treeNode : sourceInfoList) {

            if (parentId.equals(treeNode.getParentId())) {
                trees.add(treeNode);
            }

            for (QuerySourceInfoVo it : sourceInfoList) {
                if (it.getParentId().equals(treeNode.getSourceId())) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<>());
                    }
                    treeNode.getChildren().add(it);
                }
            }
        }
        return trees;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertSourceInfo(InsertSourceInfoByRoleDto insertSourceInfoByRoleDto) {
        String loginName = BaseContextUtils.get(Constants.ADMIN_NAME).toString();
        SourceInfo entity = new SourceInfo();
        BeanUtils.copyProperties(insertSourceInfoByRoleDto, entity);
        entity.setStatus(0);
        entity.setCreateUser(loginName);
        entity.setEditUser(loginName);
        sourceInfoMapper.insert(entity);
        // 将新增菜单授权给用户
        roleSourceMapper.insertBatchRolesSource(insertSourceInfoByRoleDto.getRoleIds(), entity.getSourceId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeSourceInfoBySourceId(Integer sourceId) {
        if (sourceId == null || sourceId <= 0) {
            throw new AppServiceException(ErrorCode.sys_source_remove_error_id_invalid);
        }
        // 如果当前资源有子资源不能删除
        QueryWrapper<SourceInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", sourceId);
        List<SourceInfo> sourceInfoList = sourceInfoMapper.selectList(wrapper);
        if (sourceInfoList.size() > 0) {
            throw new AppServiceException(ErrorCode.sys_source_remove_error);
        }
        // 如果当前资源已分配给角色 不能删除
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("source_id", sourceId);
        roleSourceMapper.delete(queryWrapper);
        sourceInfoMapper.deleteById(sourceId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void editSourceInfo(EditSourceInfoByRoleDto editSourceInfoByRoleDto) {
        String loginName = BaseContextUtils.get(Constants.ADMIN_NAME).toString();
        SourceInfo entity = new SourceInfo();
        BeanUtils.copyProperties(editSourceInfoByRoleDto, entity);
        entity.setEditUser(loginName);
        entity.setEditTime(new Date());
        sourceInfoMapper.updateById(entity);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("source_id", editSourceInfoByRoleDto.getSourceId());
        // 删除原来的菜单分配
        roleSourceMapper.delete(queryWrapper);
        // 给角色授权资源
        roleSourceMapper.insertBatchRolesSource(editSourceInfoByRoleDto.getRoleIds(), editSourceInfoByRoleDto.getSourceId());
    }
}
