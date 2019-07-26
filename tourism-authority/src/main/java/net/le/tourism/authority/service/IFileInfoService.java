package net.le.tourism.authority.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.le.tourism.authority.pojo.dto.QueryFileInfoDto;
import net.le.tourism.authority.pojo.entity.FileInfo;
import net.le.tourism.authority.pojo.vo.QueryFileInfoVo;
import net.le.tourism.authority.result.PageResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 韩乐
 * @since 2019-06-14
 */
public interface IFileInfoService extends IService<FileInfo> {

    /**
     * 上传图片到服务器
     * @param file
     * @return
     */
    String uploadImg(MultipartFile file) ;

    PageResult<QueryFileInfoVo> queryFileInfo(QueryFileInfoDto queryFileInfoDto);
}
