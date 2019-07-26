package net.le.tourism.authority.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.le.tourism.authority.pojo.entity.OrgInfo;
import net.le.tourism.authority.pojo.vo.QueryOrgInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 韩乐
 * @since 2019-05-18
 */
@Repository
public interface OrgInfoMapper extends BaseMapper<OrgInfo> {

    List<QueryOrgInfoVo> queryOrgInfo(@Param("orgId") Integer orgId);

    List<QueryOrgInfoVo> querySubOrgInfo(@Param("parentId") Integer parentId);
}
