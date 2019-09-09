package net.le.tourism.authority.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.le.tourism.authority.common.result.CommonResult;
import net.le.tourism.authority.pojo.dto.EditSourceInfoByRoleDto;
import net.le.tourism.authority.pojo.dto.InsertSourceInfoByRoleDto;
import net.le.tourism.authority.pojo.dto.QuerySourceInfoDto;
import net.le.tourism.authority.pojo.vo.QuerySourceInfoVo;
import net.le.tourism.authority.service.ISourceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 韩乐
 * @since 2019-06-28
 */
@Api(tags = "菜单管理")
@Validated
@RestController
@RequestMapping("/source")
public class SourceInfoController {

    @Autowired
    private ISourceInfoService sourceInfoService;

    /**
     * 查询所有资源
     *
     * @param querySourceInfoDto
     * @return
     */
    @ApiOperation(value = "查询所有授权资源", notes = "资源信息以树状返回")
    @GetMapping
    public CommonResult querySourceInfo(QuerySourceInfoDto querySourceInfoDto) {
        List<QuerySourceInfoVo> sourceList = sourceInfoService.querySourceInfo(querySourceInfoDto);
        return new CommonResult(sourceList);
    }


    /**
     * 添加资源信息
     *
     * @param insertSourceInfoByRoleDto
     * @return
     */
    @ApiOperation(value = "添加资源信息")
    @PostMapping
    public CommonResult insertSourceInfo(@RequestBody @Valid InsertSourceInfoByRoleDto insertSourceInfoByRoleDto) {
        sourceInfoService.insertSourceInfo(insertSourceInfoByRoleDto);
        return new CommonResult();
    }

    /**
     * 修改资源信息
     *
     * @param editSourceInfoByRoleDto
     * @return
     */
    @ApiOperation(value = "修改资源信息")
    @PutMapping
    public CommonResult editSourceInfoByRole(@RequestBody @Valid EditSourceInfoByRoleDto editSourceInfoByRoleDto) {
        sourceInfoService.editSourceInfo(editSourceInfoByRoleDto);
        return new CommonResult();
    }


    @ApiOperation(value = "删除资源信息")
    @DeleteMapping
    public CommonResult removeSourceInfoBySourceId(@NotNull(message = "资源Id不能为空") @Min(value = 1, message = "资源Id必须大于0") @RequestParam("sourceId") Integer sourceId) {
        sourceInfoService.removeSourceInfoBySourceId(sourceId);
        return new CommonResult();
    }
}

