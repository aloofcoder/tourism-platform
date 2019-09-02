package net.le.tourism.authority.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.le.tourism.authority.common.exception.AppServiceException;
import net.le.tourism.authority.common.exception.ErrorCode;
import net.le.tourism.authority.pojo.dto.EditAdminInfoDto;
import net.le.tourism.authority.pojo.dto.InsertAdminInfoDto;
import net.le.tourism.authority.pojo.dto.QueryAdminInfoDto;
import net.le.tourism.authority.common.result.CommonResult;
import net.le.tourism.authority.service.IAdminInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 韩乐
 * @since 2019-05-18
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/admin")
public class AdminInfoController {

    @Autowired
    private IAdminInfoService adminInfoService;

    @ApiOperation(value = "分页查询管理员信息")
    @GetMapping
    public CommonResult queryAdminInInfo(@Valid QueryAdminInfoDto queryAdminInfoDto) {
        return new CommonResult(adminInfoService.queryAdminInInfo(queryAdminInfoDto));
    }

    @ApiOperation(value = "添加管理员信息", notes = "需要填写管理员角色，管理员所属组织")
    @PostMapping
    public CommonResult insertAdminInfo(@Valid @RequestBody InsertAdminInfoDto adminInfoDto) {
        if (adminInfoDto.getRoleIds() == null || adminInfoDto.getRoleIds().size() == 0) {
            throw new AppServiceException(ErrorCode.sys_insert_admin_role_error);
        }
        adminInfoService.addAdminInfo(adminInfoDto);
        return new CommonResult();
    }

    @ApiOperation(value = "修改管理员信息", notes = "可以修改角色和组织")
    @PutMapping
    public CommonResult editAdminInfo(@Valid @RequestBody EditAdminInfoDto editAdminInfoDto) {
        if (editAdminInfoDto.getRoleIds() == null || editAdminInfoDto.getRoleIds().size() == 0) {
            throw new AppServiceException(ErrorCode.sys_insert_admin_role_error);
        }
        adminInfoService.editAdminInfo(editAdminInfoDto);
        return new CommonResult();
    }

    @ApiOperation(value = "禁用或启用管理员", notes = "")
    @ApiImplicitParam(name = "adminId", value = "管理员Id", required = true, dataType = "Integer")
    @PutMapping("/status")
    public CommonResult editAdminInfoStatus(@NotNull(message = "管理员ID不能为空") @RequestParam("adminId") Integer adminId) {
        adminInfoService.editAdminInfoStatus(adminId);
        return new CommonResult();
    }

    @ApiOperation(value = "删除管理员信息", notes = "")
    @ApiImplicitParam(name = "adminId", value = "管理员Id", required = true, dataType = "Integer")
    @DeleteMapping
    public CommonResult removeAdminInfo(@NotNull(message = "管理员ID不能为空") @RequestParam("adminId") Integer adminId) {
        adminInfoService.removeAdminInfoById(adminId);
        return new CommonResult();
    }

}

