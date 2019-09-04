package net.le.tourism.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.le.tourism.authority.common.constant.Constants;
import net.le.tourism.authority.common.exception.AppServiceException;
import net.le.tourism.authority.common.exception.ErrorCode;
import net.le.tourism.authority.pojo.dto.EditSourceInfoByRoleDto;
import net.le.tourism.authority.pojo.dto.InsertSourceInfoByRoleDto;
import net.le.tourism.authority.pojo.dto.QuerySourceInfoDto;
import net.le.tourism.authority.pojo.entity.RoleSource;
import net.le.tourism.authority.pojo.entity.SourceInfo;
import net.le.tourism.authority.pojo.vo.QuerySourceInfoVo;
import net.le.tourism.authority.service.ISourceInfoService;
import net.le.tourism.authority.mapper.RoleAdminMapper;
import net.le.tourism.authority.mapper.RoleSourceMapper;
import net.le.tourism.authority.mapper.SourceInfoMapper;
import net.le.tourism.authority.common.util.BaseContextUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<QuerySourceInfoVo> querySourceInfo(QuerySourceInfoDto querySourceInfoDto) {
        // 查询当前登录人的所有角色ID
        Integer adminNum = Integer.parseInt(BaseContextUtils.get(Constants.ADMIN_NUM).toString());
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
        Integer parentId = -1;
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

    @Override
    public void insertSourceInfo(InsertSourceInfoByRoleDto insertSourceInfoByRoleDto) {
        String loginNum = BaseContextUtils.get(Constants.ADMIN_NUM).toString();
        SourceInfo entity = new SourceInfo();
        BeanUtils.copyProperties(insertSourceInfoByRoleDto, entity);
        entity.setStatus(0);
        entity.setCreateUser(loginNum);
        entity.setEditUser(loginNum);
        sourceInfoMapper.insert(entity);
    }

    @Override
    public void removeSourceInfoById(Integer sourceId) {
        if (sourceId == null || sourceId <= 0) {
            throw new AppServiceException(ErrorCode.sys_source_remove_error);
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
        List<RoleSource> roleSourceList = roleSourceMapper.selectList(queryWrapper);
        if (roleSourceList.size() > 0) {
            throw new AppServiceException(ErrorCode.sys_source_remove_error);
        }
        sourceInfoMapper.deleteById(sourceId);
    }

    @Override
    public void editSourceInfo(EditSourceInfoByRoleDto editSourceInfoByRoleDto) {
        String loginName = BaseContextUtils.get(Constants.ADMIN_NAME).toString();
        SourceInfo entity = new SourceInfo();
        BeanUtils.copyProperties(editSourceInfoByRoleDto, entity);
        entity.setEditUser(loginName);
        entity.setEditTime(new Date());
        sourceInfoMapper.updateById(entity);
    }
}
