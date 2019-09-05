package net.le.tourism.authority.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.le.tourism.authority.pojo.entity.OrgAdmin;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 韩乐
 * @since 2019-06-24
 */
public interface IOrgAdminService extends IService<OrgAdmin> {

    OrgAdmin queryByAdminNum(String adminNum);

    void removeByAdminNum(String adminNum);

    void updateByAdminNum(String adminNum, Integer orgId);
}
