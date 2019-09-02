package net.le.tourism.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.le.tourism.authority.common.constant.Constants;
import net.le.tourism.authority.common.constant.properties.SysFileProperties;
import net.le.tourism.authority.common.exception.AppServiceException;
import net.le.tourism.authority.common.exception.ErrorCode;
import net.le.tourism.authority.pojo.dto.QueryFileInfoDto;
import net.le.tourism.authority.pojo.entity.FileInfo;
import net.le.tourism.authority.pojo.vo.QueryFileInfoVo;
import net.le.tourism.authority.mapper.FileInfoMapper;
import net.le.tourism.authority.common.result.PageResult;
import net.le.tourism.authority.service.IFileInfoService;
import net.le.tourism.authority.common.util.BaseContextUtils;
import net.le.tourism.authority.common.util.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 韩乐
 * @since 2019-06-14
 */
@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements IFileInfoService {

    @Autowired
    private SysFileProperties sysFileProperties;

    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String uploadImg(MultipartFile file) {
        String loginNum = BaseContextUtils.get(Constants.LOGIN_NUM).toString();
        if (file == null) {
            throw new AppServiceException(ErrorCode.sys_file_not_null);
        }
        String filePathSuffix = null;
        FileOutputStream fos = null;
        try {
            // 验证文件是否存在
            String fileMd5 = FileUtils.getMD5(file.getInputStream());
            QueryWrapper<FileInfo> wrapper = new QueryWrapper<>();
            wrapper.eq("file_md5", fileMd5);
            // 如果文件存在直接返回路径
            FileInfo fileInfo = fileInfoMapper.selectOne(wrapper);
            if (fileInfo != null) {
                return String.format("%1$s/%2$s", sysFileProperties.getDomain(), fileInfo.getFilePath());
            }
            Calendar cal = Calendar.getInstance();
            String year = DateFormatUtils.format(cal, "yyyy");
            String month = DateFormatUtils.format(cal, "yyyy-MM");
            String date = DateFormatUtils.format(cal, "yyyy-MM-dd");
            String fileSubPath = String.format("%1$s/%2$s/%3$s", year, month, date);
            // 获取文件后缀
            String fileName = file.getOriginalFilename();
            String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
            // 创建不存在的文件路径
            File fullPath = new File(String.format("%1$s/%2$s/%3$s%4$s", sysFileProperties.getUploadPath(), fileSubPath, fileMd5, fileSuffix));
            if (!fullPath.getParentFile().exists()) {
                fullPath.getParentFile().mkdirs();
            }
            filePathSuffix = String.format("%1$s/%2$s%3$s", fileSubPath, fileMd5, fileSuffix);
            // 保存文件
            fos = new FileOutputStream(fullPath);
            fos.write(file.getBytes());
            FileInfo entity = new FileInfo();
            entity.setFileName(fileName);
            entity.setFilePath(filePathSuffix);
            entity.setFileMd5(fileMd5);
            entity.setStatus(0);
            entity.setCreateUser(loginNum);
            entity.setEditUser(loginNum);
            int count = fileInfoMapper.insert(entity);
            if (count == -1) {
                throw new AppServiceException(ErrorCode.sys_file_upload_insert_error);
            }
        } catch (FileNotFoundException e) {
            System.out.println("创建上传文件失败");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return String.format("%1$s/%2$s", sysFileProperties.getDomain(), filePathSuffix);
    }

    @Override
    public PageResult<QueryFileInfoVo> queryFileInfo(QueryFileInfoDto queryFileInfoDto) {
        Page<QueryFileInfoVo> page = new Page<>(queryFileInfoDto.getCurrentPage(), queryFileInfoDto.getPageSize());
        page.setRecords(fileInfoMapper.queryFileInfo(page, queryFileInfoDto));
        return new PageResult<>(page);
    }
}
