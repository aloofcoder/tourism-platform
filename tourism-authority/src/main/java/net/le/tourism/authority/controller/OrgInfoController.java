package net.le.tourism.authority.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.le.tourism.authority.pojo.dto.EditOrgInfoDto;
import net.le.tourism.authority.pojo.dto.InsertOrgInfoDto;
import net.le.tourism.authority.pojo.dto.QueryOrgInfoDto;
import net.le.tourism.authority.pojo.vo.QueryOrgInfoVo;
import net.le.tourism.authority.result.CommonResult;
import net.le.tourism.authority.service.IOrgInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 韩乐
 * @since 2019-05-18
 */
@Api(tags = "组织管理")
@RestController
@RequestMapping("/organization")
public class OrgInfoController {

    @Autowired
    private IOrgInfoService orgInfoService;

    /**
     * 查询组织结构
     * @return
     */
    @ApiOperation(value = "查询登陆用户所在组织全部信息", notes = "组织信息以树状结构返回")
    @GetMapping
    public CommonResult queryOrgInfo(QueryOrgInfoDto queryOrgInfoDto) {
        List<QueryOrgInfoVo> list = orgInfoService.queryOrgInfo(queryOrgInfoDto);
        return new CommonResult(list);
    }

    @ApiOperation(value = "添加组织信息", notes = "")
    @PostMapping
    public CommonResult insertOrgInfo(@Valid @RequestBody InsertOrgInfoDto insertOrgInfoDto) {
        orgInfoService.insertOrgInfo(insertOrgInfoDto);
        return new CommonResult();
    }

    @ApiOperation(value = "修改组织信息", notes = "")
    @PutMapping
    public CommonResult editOrgInfo(@Valid @RequestBody EditOrgInfoDto editOrgInfoDto) {
        orgInfoService.editOrgInfo(editOrgInfoDto);
        return new CommonResult();
    }

    @ApiOperation(value = "删除组织信息")
    @ApiImplicitParam(name = "orgId", value = "组织Id", required = true, dataType = "Integer")
    @DeleteMapping
    public CommonResult removeOrgInfo(@RequestParam("orgId") @NotNull(message = "组织Id不能为空") @Min(value = 1, message = "组织Id必须大于0") Integer orgId) {
        orgInfoService.removeOrgInfo(orgId);
        return new CommonResult();
    }
}

