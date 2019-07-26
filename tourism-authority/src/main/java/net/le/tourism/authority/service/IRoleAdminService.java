package net.le.tourism.authority.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.le.tourism.authority.pojo.entity.RoleAdmin;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 韩乐
 * @since 2019-06-24
 */
public interface IRoleAdminService extends IService<RoleAdmin> {

    void insertRoleAdminByAdmin(Integer adminId, List<Integer> roleIds);
}
