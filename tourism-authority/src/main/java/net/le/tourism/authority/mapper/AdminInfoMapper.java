package net.le.tourism.authority.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.le.tourism.authority.pojo.dto.QueryAdminInfoDto;
import net.le.tourism.authority.pojo.entity.AdminInfo;
import net.le.tourism.authority.pojo.vo.QueryAdminInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 韩乐
 * @since 2019-05-18
 */
@Repository
public interface AdminInfoMapper extends BaseMapper<AdminInfo> {

    List<QueryAdminInfoVo> queryAdminInInfo(Page page, @Param("dto") QueryAdminInfoDto queryAdminInfoDto);
}
