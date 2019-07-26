package net.le.tourism.authority.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.le.tourism.authority.pojo.dto.EditRoleInfoDto;
import net.le.tourism.authority.pojo.dto.InsertRoleInfoDto;
import net.le.tourism.authority.pojo.dto.QueryRoleInfoDto;
import net.le.tourism.authority.pojo.vo.QueryRoleInfoVo;
import net.le.tourism.authority.result.CommonResult;
import net.le.tourism.authority.result.PageResult;
import net.le.tourism.authority.service.IRoleInfoService;
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
@Api(tags = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleInfoController {

    @Autowired
    private IRoleInfoService roleInfoService;

    @ApiOperation(value = "查询角色信息", notes = "分页查询所有角色信息")
    @GetMapping
    public CommonResult queryRoleInfo(@Valid QueryRoleInfoDto queryRoleInfoDto) {
        PageResult<QueryRoleInfoVo> pageResult = roleInfoService.queryRoleInfo(queryRoleInfoDto);
        return new CommonResult(pageResult);
    }

    @ApiOperation(value = "添加角色", notes = "添加角色同时并分配资源")
    @PostMapping
    public CommonResult insertRoleInfo(@RequestBody @Valid InsertRoleInfoDto insertRoleInfoDto) {
        roleInfoService.insertRoleInfo(insertRoleInfoDto);
        return new CommonResult();
    }


    @ApiOperation(value = "修改角色", notes = "修改角色信息及资源")
    @PutMapping
    public CommonResult editRoleInfo(@RequestBody @Valid EditRoleInfoDto editRoleInfoDto) {
        roleInfoService.editRoleInfo(editRoleInfoDto);
        return new CommonResult();
    }

    @ApiOperation(value = "删除角色信息", notes = "根据角色Id删除角色信息")
    @ApiImplicitParam(name = "roleId", value = "角色Id", required = true, dataType = "Integer")
    @DeleteMapping
    public CommonResult removeRoleInfo(@NotNull(message = "角色ID不能为空") @RequestParam("roleId") Integer roleId) {
        roleInfoService.removeRoleInfo(roleId);
        return new CommonResult();
    }

}

