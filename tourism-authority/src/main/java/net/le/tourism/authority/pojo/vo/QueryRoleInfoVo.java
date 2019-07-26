package net.le.tourism.authority.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.ToString;

/**
 * @author hanle
 * @version v1.0
 * @date 2019/7/4
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Data
@ToString
@ApiModel(value = "QueryRoleInfo", description = "角色列表返回信息")
public class QueryRoleInfoVo {

    @ApiModelProperty(value = "roleId", name = "角色Id", dataType = "Integer")
    private Integer roleId;

    @ApiModelProperty(value = "roleName", name = "角色名称", dataType = "String")
    private String roleName;

    @ApiModelProperty(value = "roleDesc", name = "角色名称", dataType = "String")
    private String roleDesc;

    @ApiModelProperty(value = "roleSort", name = "角色名称", dataType = "String")
    private Integer roleSort;

    @ApiModelProperty(value = "status", name = "角色状态", dataType = "Integer")
    private Integer status;

    @ApiModelProperty(value = "remark", name = "备注", dataType = "String")
    private String remark;
}
