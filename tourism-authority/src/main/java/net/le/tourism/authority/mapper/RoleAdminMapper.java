package net.le.tourism.authority.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.le.tourism.authority.pojo.entity.RoleAdmin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 韩乐
 * @since 2019-06-24
 */
@Repository
public interface RoleAdminMapper extends BaseMapper<RoleAdmin> {

    void insertRoleAdminByAdmin(@Param("adminNum") String adminNum, @Param("roleIds") List<Integer> roleIds);

    List<Integer> selectRoleIdByAdminNum(Integer adminNum);
}
