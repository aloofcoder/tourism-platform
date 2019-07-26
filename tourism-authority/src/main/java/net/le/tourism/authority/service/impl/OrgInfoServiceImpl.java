package net.le.tourism.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.le.tourism.authority.constant.Constants;
import net.le.tourism.authority.exception.AppServiceException;
import net.le.tourism.authority.exception.ErrorCode;
import net.le.tourism.authority.mapper.OrgAdminMapper;
import net.le.tourism.authority.mapper.OrgInfoMapper;
import net.le.tourism.authority.pojo.dto.EditOrgInfoDto;
import net.le.tourism.authority.pojo.dto.InsertOrgInfoDto;
import net.le.tourism.authority.pojo.dto.QueryOrgInfoDto;
import net.le.tourism.authority.pojo.entity.OrgAdmin;
import net.le.tourism.authority.pojo.entity.OrgInfo;
import net.le.tourism.authority.pojo.vo.QueryOrgInfoVo;
import net.le.tourism.authority.service.IOrgInfoService;
import net.le.tourism.authority.util.BaseContextUtils;
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
 * @since 2019-05-18
 */
@Service
public class OrgInfoServiceImpl extends ServiceImpl<OrgInfoMapper, OrgInfo> implements IOrgInfoService {

    @Autowired
    private OrgInfoMapper orgInfoMapper;

    @Autowired
    private OrgAdminMapper orgAdminMapper;

    @Override
    public List<QueryOrgInfoVo> queryOrgInfo(QueryOrgInfoDto queryOrgInfoDto) {
        // 获取当前登录人下的所有 parentId
        String adminId = BaseContextUtils.get(Constants.LOGIN_ID).toString();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("admin_id", adminId);
        OrgAdmin entity = orgAdminMapper.selectOne(wrapper);
        if (entity == null) {
            return new ArrayList<>();
        }
        List<QueryOrgInfoVo> list = orgInfoMapper.queryOrgInfo(entity.getOrgId());
        return list;
    }

    @Override
    public void insertOrgInfo(InsertOrgInfoDto insertOrgInfoDto) {
        String loginNum = BaseContextUtils.get(Constants.LOGIN_NUM).toString();
        if (insertOrgInfoDto == null || insertOrgInfoDto.getParentId() == null) {
            throw new AppServiceException(ErrorCode.sys_insert_org_error);
        }
        OrgInfo entity = new OrgInfo();
        BeanUtils.copyProperties(insertOrgInfoDto, entity);
        entity.setStatus(0);
        entity.setCreateUser(loginNum);
        entity.setEditUser(loginNum);
        orgInfoMapper.insert(entity);
    }

    @Override
    public void editOrgInfo(EditOrgInfoDto editOrgInfoDto) {
        if (editOrgInfoDto == null || editOrgInfoDto.getParentId() == null) {
            throw new AppServiceException(ErrorCode.sys_edit_org_error);
        }
        String loginName = BaseContextUtils.get(Constants.LOGIN_NAME).toString();
        OrgInfo entity = new OrgInfo();
        BeanUtils.copyProperties(editOrgInfoDto, entity);
        entity.setEditUser(loginName);
        entity.setEditTime(new Date());
        orgInfoMapper.updateById(entity);
    }

    @Override
    public void removeOrgInfo(Integer orgId) {
        // 要删除的组织下有子组织 不能被删除
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("parent_id", orgId);
        List<OrgInfo> orgInfoList = orgInfoMapper.selectList(wrapper);
        if (orgInfoList.size() > 0) {
            throw new AppServiceException(ErrorCode.sys_remove_org_error);
        }
        // 无子组织可以删除
        orgInfoMapper.deleteById(orgId);
    }
}
