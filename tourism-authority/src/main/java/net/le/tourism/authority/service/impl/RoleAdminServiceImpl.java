package net.le.tourism.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.le.tourism.authority.mapper.RoleAdminMapper;
import net.le.tourism.authority.pojo.entity.RoleAdmin;
import net.le.tourism.authority.service.IRoleAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 韩乐
 * @since 2019-06-24
 */
@Service
public class RoleAdminServiceImpl extends ServiceImpl<RoleAdminMapper, RoleAdmin> implements IRoleAdminService {

    @Autowired
    private RoleAdminMapper roleAdminMapper;

    @Override
    public void insertRoleAdminByAdmin(String adminNum, List<Integer> roleIds) {
        roleAdminMapper.insertRoleAdminByAdmin(adminNum, roleIds);
    }

    @Override
    public void removeByAdminNum(String adminNum) {
        QueryWrapper<RoleAdmin> roleWrapper = new QueryWrapper();
        roleWrapper.eq("admin_num", adminNum);
        this.remove(roleWrapper);
    }

    @Override
    public List<Integer> queryByAdminNum(String adminNum) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("admin_num", adminNum);
        List<RoleAdmin> roleAdmins = roleAdminMapper.selectList(wrapper);
        return roleAdmins.stream().map(RoleAdmin::getRoleId).collect(Collectors.toList());
    }
}
