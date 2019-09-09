package net.le.tourism.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.le.tourism.authority.common.constant.Constants;
import net.le.tourism.authority.common.exception.AppServiceException;
import net.le.tourism.authority.common.exception.ErrorCode;
import net.le.tourism.authority.common.result.PageResult;
import net.le.tourism.authority.common.util.BaseContextUtils;
import net.le.tourism.authority.common.util.TourismUtils;
import net.le.tourism.authority.mapper.AdminInfoMapper;
import net.le.tourism.authority.pojo.dto.EditAdminInfoDto;
import net.le.tourism.authority.pojo.dto.InsertAdminInfoDto;
import net.le.tourism.authority.pojo.dto.QueryAdminInfoDto;
import net.le.tourism.authority.pojo.entity.AdminInfo;
import net.le.tourism.authority.pojo.entity.OrgAdmin;
import net.le.tourism.authority.pojo.vo.QueryAdminInfoVo;
import net.le.tourism.authority.pojo.vo.QueryLoginAdminInfoVo;
import net.le.tourism.authority.service.IAdminInfoService;
import net.le.tourism.authority.service.IOrgAdminService;
import net.le.tourism.authority.service.IRoleAdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 韩乐
 * @since 2019-05-18
 */
@Service
public class AdminInfoServiceImpl extends ServiceImpl<AdminInfoMapper, AdminInfo> implements IAdminInfoService {

    @Autowired
    private AdminInfoMapper adminInfoMapper;

    @Autowired
    private IOrgAdminService orgAdminService;

    @Autowired
    private IRoleAdminService roleAdminService;

    @Override
    public PageResult<QueryAdminInfoVo> queryAdminInInfo(QueryAdminInfoDto queryAdminInfoDto) {
        Page<QueryAdminInfoVo> page = new Page<>(queryAdminInfoDto.getCurrentPage(), queryAdminInfoDto.getPageSize());
        page.setRecords(adminInfoMapper.queryAdminInInfo(page, queryAdminInfoDto));
        return new PageResult(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String addAdminInfo(InsertAdminInfoDto adminInfoDto) {
        String loginName = BaseContextUtils.get(Constants.ADMIN_NAME).toString();
        if (adminInfoDto.getRoleIds() == null || adminInfoDto.getRoleIds().size() == 0) {
            throw new AppServiceException(ErrorCode.sys_insert_admin_role_error);
        }
        if (adminInfoDto.getOrgId() == null || adminInfoDto.getOrgId() < 1) {
            throw new AppServiceException(ErrorCode.sys_insert_admin_org_error);
        }
        AdminInfo adminInfo = getAdminInfoByLoginName(adminInfoDto.getLoginName());
        if (adminInfo != null) {
            throw new AppServiceException(ErrorCode.sys_login_name_exists);
        }
        // 保存管理员信息
        AdminInfo entity = new AdminInfo();
        BeanUtils.copyProperties(adminInfoDto, entity);
        // 生成adminNum
        String adminNum = TourismUtils.getAdminNum();
        entity.setAdminNum(adminNum);
        // 获取加密盐值
        String salt = TourismUtils.getSalt();
        // 生成加密密码
        String pwd = TourismUtils.buildAdminPwd(salt, adminInfoDto.getAdminPwd());
        entity.setAdminPwd(pwd);
        entity.setSalt(salt);
        entity.setCreateUser(loginName);
        entity.setEditUser(loginName);
        adminInfoMapper.insert(entity);
        // 设置组织
        OrgAdmin orgAdmin = new OrgAdmin();
        orgAdmin.setAdminNum(adminNum);
        orgAdmin.setOrgId(adminInfoDto.getOrgId());
        orgAdminService.save(orgAdmin);
        // 设置角色
        roleAdminService.insertRoleAdminByAdmin(adminNum, adminInfoDto.getRoleIds());
        return adminNum;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void editAdminInfo(EditAdminInfoDto editAdminInfoDto) {
        if (editAdminInfoDto.getRoleIds() == null || editAdminInfoDto.getRoleIds().size() == 0) {
            throw new AppServiceException(ErrorCode.sys_insert_admin_role_error);
        }
        AdminInfo lastAdminInfo = getAdminInfoByAdminNum(editAdminInfoDto.getAdminNum());
        AdminInfo adminInfo = getAdminInfoByLoginName(editAdminInfoDto.getLoginName());
        // 验证登录名是否与原来一致 不一致验证是否系统已存在
        if (adminInfo != null && !lastAdminInfo.getLoginName().equals(editAdminInfoDto.getLoginName())) {
            throw new AppServiceException(ErrorCode.sys_login_name_exists);
        }
        BeanUtils.copyProperties(editAdminInfoDto, lastAdminInfo);
        lastAdminInfo.setEditUser(BaseContextUtils.get(Constants.ADMIN_NAME).toString());
        lastAdminInfo.setEditTime(new Date());
        adminInfoMapper.updateById(lastAdminInfo);
        // 删除已分配的角色
        roleAdminService.removeByAdminNum(editAdminInfoDto.getAdminNum());
        // 重新分配角色
        roleAdminService.insertRoleAdminByAdmin(editAdminInfoDto.getAdminNum(), editAdminInfoDto.getRoleIds());
        // 更改所在组织部门
        orgAdminService.updateByAdminNum(editAdminInfoDto.getAdminNum(), editAdminInfoDto.getOrgId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeAdminInfoById(String adminNum) {
        if (adminNum == null) {
            throw new AppServiceException(ErrorCode.sys_admin_num_not_null);
        }
        AdminInfo adminInfo = getAdminInfoByAdminNum(adminNum);
        if (adminInfo == null) {
            throw new AppServiceException(ErrorCode.sys_admin_info_un_found);
        }
        if (Constants.ADMIN_LOGIN_NAME.equals(adminInfo.getLoginName())) {
            throw new AppServiceException(ErrorCode.sys_remove_admin_error);
        }
        // 删除给用户分配的角色
        roleAdminService.removeByAdminNum(adminNum);
        // 删除用户所在部门
        orgAdminService.removeByAdminNum(adminNum);
        // 不删除数据 修改用户状态为2
        AdminInfo entity = new AdminInfo();
        entity.setAdminNum(adminInfo.getAdminNum());
        entity.setStatus(2);
        updateByAdminNum(entity);
    }

    @Override
    public void editAdminInfoStatus(String adminNum) {
        if (adminNum == null) {
            throw new AppServiceException(ErrorCode.sys_admin_num_not_null);
        }
        AdminInfo adminInfo = getAdminInfoByAdminNum(adminNum);
        if (adminInfo == null) {
            throw new AppServiceException(ErrorCode.sys_admin_info_un_found);
        }
        if (Constants.ADMIN_LOGIN_NAME.equals(adminInfo.getLoginName())) {
            throw new AppServiceException(ErrorCode.sys_update_admin_status_error);
        }
        AdminInfo entity = new AdminInfo();
        Integer status = adminInfo.getStatus() == 0 ? 1 : 0;
        entity.setStatus(status);
        entity.setAdminNum(adminNum);
        entity.setEditUser(BaseContextUtils.get(Constants.ADMIN_NAME).toString());
        entity.setEditTime(new Date());
        updateByAdminNum(entity);
    }

    @Override
    public AdminInfo getAdminInfoByLoginName(String loginName) {
        // 验证登录名是否存在
        QueryWrapper<AdminInfo> wrapper = new QueryWrapper();
        wrapper.eq("login_name", loginName);
        wrapper.and(w -> w.ne("status", 2));
        AdminInfo adminInfo = adminInfoMapper.selectOne(wrapper);
        return adminInfo;
    }

    @Override
    public AdminInfo getAdminInfoByAdminNum(String adminNum) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("admin_num", adminNum);
        AdminInfo lastAdminInfo = adminInfoMapper.selectOne(wrapper);
        return lastAdminInfo;
    }

    @Override
    public QueryLoginAdminInfoVo queryLoginAdminInfo() {
        String adminNum = BaseContextUtils.get(Constants.ADMIN_NUM).toString();
        AdminInfo adminInfo = getAdminInfoByAdminNum(adminNum);
        if (adminInfo == null) {
            throw new AppServiceException(ErrorCode.authority_login_Info_Invalid);
        }
        QueryLoginAdminInfoVo queryLoginAdminInfoVo = new QueryLoginAdminInfoVo();
        BeanUtils.copyProperties(adminInfo, queryLoginAdminInfoVo);
        return queryLoginAdminInfoVo;
    }

    void updateByAdminNum(AdminInfo entity) {
        UpdateWrapper wrapper = new UpdateWrapper();
        wrapper.eq("admin_num", entity.getAdminNum());
        adminInfoMapper.update(entity, wrapper);
    }

}
