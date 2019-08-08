package net.le.tourism.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.le.tourism.authority.mapper.OrgAdminMapper;
import net.le.tourism.authority.pojo.entity.OrgAdmin;
import net.le.tourism.authority.service.IOrgAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 韩乐
 * @since 2019-06-24
 */
@Service
public class OrgAdminServiceImpl extends ServiceImpl<OrgAdminMapper, OrgAdmin> implements IOrgAdminService {

    @Autowired
    private OrgAdminMapper orgAdminMapper;

    @Override
    public OrgAdmin queryOrgAdminByAdminId(Integer adminId) {
        QueryWrapper<OrgAdmin> wrapper = new QueryWrapper<>();
        wrapper.eq("admin_id", adminId);
        return orgAdminMapper.selectOne(wrapper);
    }
}
