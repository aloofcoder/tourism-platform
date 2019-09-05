package net.le.tourism.authority.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.le.tourism.authority.pojo.entity.SourceInfo;
import net.le.tourism.authority.pojo.vo.QuerySourceInfoVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 韩乐
 * @since 2019-06-28
 */
@Repository
public interface SourceInfoMapper extends BaseMapper<SourceInfo> {

    List<QuerySourceInfoVo> querySourceInfoBatchSourceId(List<Integer> list);

    SourceInfo selectByParentId(Integer parentId);
}
