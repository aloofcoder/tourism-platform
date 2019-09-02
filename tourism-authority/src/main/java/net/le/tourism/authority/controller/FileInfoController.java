package net.le.tourism.authority.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.le.tourism.authority.pojo.dto.QueryFileInfoDto;
import net.le.tourism.authority.pojo.vo.QueryFileInfoVo;
import net.le.tourism.authority.common.result.CommonResult;
import net.le.tourism.authority.common.result.PageResult;
import net.le.tourism.authority.service.IFileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 韩乐
 * @since 2019-06-14
 */
@Api(tags = "文件管理")
@RestController
@RequestMapping("/file")
public class FileInfoController {

    @Autowired
    private IFileInfoService fileInfoService;

    @ApiOperation(value = "图片上传接口", notes = "若上传文件原来已存在，将返回原有图片链接，不会重复上传")
    @PostMapping("/upload/image")
    public CommonResult uploadImg(@NotNull(message = "文件不能为空") MultipartFile file) {
        String filePath = fileInfoService.uploadImg(file);
        return new CommonResult(filePath);
    }

    @GetMapping
    @ApiOperation(value = "分页查询文件列表", notes = "")
    public CommonResult queryFileInfo(@Valid QueryFileInfoDto queryFileInfoDto) {
        PageResult<QueryFileInfoVo> pageResult = fileInfoService.queryFileInfo(queryFileInfoDto);
        return new CommonResult(pageResult);
    }

}

