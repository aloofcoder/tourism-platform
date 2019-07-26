package net.le.tourism.authority.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.le.tourism.authority.pojo.entity.RoleSource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 韩乐
 * @since 2019-06-28
 */
@Repository
public interface RoleSourceMapper extends BaseMapper<RoleSource> {

    List<Integer> selectSourceIdsBatchRoleId(List<Integer> list);

    void insertBatchRoleSource(@Param("roleId") Integer roleId, @Param("sourceIds") List<Integer> sourceIds);
}
