package net.le.tourism.authority.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.le.tourism.authority.pojo.entity.RoleAdmin;
import net.le.tourism.authority.service.IRoleAdminService;
import net.le.tourism.authority.mapper.RoleAdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
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
    public void insertRoleAdminByAdmin(Integer adminId, List<Integer> roleIds) {
        roleAdminMapper.insertRoleAdminByAdmin(adminId, roleIds);
    }
}
