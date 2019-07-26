package net.le.tourism.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.le.tourism.authority.constant.Constants;
import net.le.tourism.authority.exception.AppServiceException;
import net.le.tourism.authority.exception.ErrorCode;
import net.le.tourism.authority.mapper.AdminInfoMapper;
import net.le.tourism.authority.pojo.dto.EditAdminInfoDto;
import net.le.tourism.authority.pojo.dto.InsertAdminInfoDto;
import net.le.tourism.authority.pojo.dto.QueryAdminInfoDto;
import net.le.tourism.authority.pojo.entity.AdminInfo;
import net.le.tourism.authority.pojo.entity.OrgAdmin;
import net.le.tourism.authority.pojo.entity.RoleAdmin;
import net.le.tourism.authority.pojo.vo.QueryAdminInfoVo;
import net.le.tourism.authority.result.PageResult;
import net.le.tourism.authority.service.IAdminInfoService;
import net.le.tourism.authority.service.IOrgAdminService;
import net.le.tourism.authority.service.IRoleAdminService;
import net.le.tourism.authority.util.BaseContextUtils;
import net.le.tourism.authority.util.TourismUtils;
import org.apache.commons.lang3.StringUtils;
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
    public Integer addAdminInfo(InsertAdminInfoDto adminInfoDto) {
        String loginNum = BaseContextUtils.get(Constants.LOGIN_NUM).toString();
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
        // 生成adminId
        String adminNum = TourismUtils.getAdminNum();
        entity.setAdminNum(adminNum);
        // 获取加密盐值
        String salt = TourismUtils.getSalt();
        // 生成加密密码
        String pwd = TourismUtils.buildAdminPwd(salt, adminInfoDto.getAdminPwd());
        entity.setAdminPwd(pwd);
        entity.setSalt(salt);
        entity.setCreateUser(loginNum);
        entity.setEditUser(loginNum);
        adminInfoMapper.insert(entity);
        // 设置组织
        Integer id = entity.getAdminId();
        OrgAdmin orgAdmin = new OrgAdmin();
        orgAdmin.setAdminId(id);
        orgAdmin.setOrgId(adminInfoDto.getOrgId());
        orgAdminService.save(orgAdmin);
        // 设置角色
        roleAdminService.insertRoleAdminByAdmin(id, adminInfoDto.getRoleIds());
        return entity.getAdminId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void editAdminInfo(EditAdminInfoDto editAdminInfoDto) {
        if (editAdminInfoDto.getRoleIds() == null || editAdminInfoDto.getRoleIds().size() == 0) {
            throw new AppServiceException(ErrorCode.sys_insert_admin_role_error);
        }
        AdminInfo lastAdminInfo = adminInfoMapper.selectById(editAdminInfoDto.getAdminId());
        AdminInfo adminInfo = getAdminInfoByLoginName(editAdminInfoDto.getLoginName());
        // 验证登录名是否与原来一致 不一致验证是否系统已存在
        if (adminInfo != null && !lastAdminInfo.getLoginName().equals(editAdminInfoDto.getLoginName())) {
            throw new AppServiceException(ErrorCode.sys_login_name_exists);
        }
        AdminInfo entity = adminInfoMapper.selectById(editAdminInfoDto.getAdminId());
        BeanUtils.copyProperties(editAdminInfoDto, entity);
        entity.setEditUser(BaseContextUtils.get(Constants.LOGIN_NAME).toString());
        entity.setEditTime(new Date());
        adminInfoMapper.updateById(entity);
        // 删除已分配的角色
        QueryWrapper<RoleAdmin> roleWrapper = new QueryWrapper();
        roleWrapper.eq("admin_id", editAdminInfoDto.getAdminId());
        roleAdminService.remove(roleWrapper);
        // 重新分配角色
        roleAdminService.insertRoleAdminByAdmin(editAdminInfoDto.getAdminId(), editAdminInfoDto.getRoleIds());
        // 更改所在组织部门
        QueryWrapper<OrgAdmin> orgWrapper = new QueryWrapper<>();
        roleWrapper.eq("admin_id", editAdminInfoDto.getAdminId());
        orgAdminService.remove(orgWrapper);
        OrgAdmin orgAdmin = new OrgAdmin();
        orgAdmin.setOrgId(editAdminInfoDto.getOrgId());
        orgAdmin.setAdminId(editAdminInfoDto.getAdminId());
        orgAdminService.save(orgAdmin);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeAdminInfoById(Integer adminId) {
        if (adminId == null || adminId <= 0) {
            throw new AppServiceException(ErrorCode.sys_admin_id_un_found);
        }
        AdminInfo adminInfo = adminInfoMapper.selectById(adminId);

        if (adminInfo == null) {
            throw new AppServiceException(ErrorCode.sys_admin_info_un_found);
        }
        if (Constants.ADMIN_LOGIN_NAME.equals(adminInfo.getLoginName())) {
            throw new AppServiceException(ErrorCode.sys_remove_admin_error);
        }
        QueryWrapper<RoleAdmin> roleWrapper = new QueryWrapper<>();
        roleWrapper.eq("admin_id", adminId);
        // 删除给用户分配的角色
        roleAdminService.remove(roleWrapper);
        // 删除用户所在部门
        QueryWrapper<OrgAdmin> orgWrapper = new QueryWrapper<>();
        orgWrapper.eq("admin_id", adminId);
        orgAdminService.remove(orgWrapper);
        // 不删除数据 修改用户状态为2
        AdminInfo entity = new AdminInfo();
        entity.setAdminId(adminId);
        entity.setStatus(2);
        adminInfoMapper.updateById(entity);
    }

    @Override
    public void editAdminInfoStatus(Integer adminId) {
        String loginName = BaseContextUtils.get(Constants.LOGIN_NAME).toString();
        if (adminId == null || adminId <= 0) {
            throw new AppServiceException(ErrorCode.sys_admin_id_un_found);
        }
        AdminInfo adminInfo = adminInfoMapper.selectById(adminId);
        if (adminInfo == null) {
            throw new AppServiceException(ErrorCode.sys_admin_info_un_found);
        }
        if (Constants.ADMIN_LOGIN_NAME.equals(adminInfo.getLoginName())) {
            throw new AppServiceException(ErrorCode.sys_update_admin_status_error);
        }
        AdminInfo entity = new AdminInfo();
        Integer status = adminInfo.getStatus() == 0 ? 1 : 0;
        entity.setStatus(status);
        entity.setAdminId(adminId);
        entity.setEditUser(loginName);
        entity.setEditTime(new Date());
        adminInfoMapper.updateById(entity);
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

}
