package net.le.tourism.authority.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.le.tourism.authority.pojo.dto.QueryFileInfoDto;
import net.le.tourism.authority.pojo.entity.FileInfo;
import net.le.tourism.authority.pojo.vo.QueryFileInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 韩乐
 * @since 2019-06-14
 */
@Repository
public interface FileInfoMapper extends BaseMapper<FileInfo> {

    List<QueryFileInfoVo> queryFileInfo(Page page, @Param("dto") QueryFileInfoDto queryFileInfoDto);
}
