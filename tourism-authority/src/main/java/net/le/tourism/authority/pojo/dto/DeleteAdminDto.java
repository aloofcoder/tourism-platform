package net.le.tourism.authority.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ToString
@ApiModel(value = "删除管理员参数类")
public class DeleteAdminDto {

    @NotNull(message = "管理员编号不能为空！")
    @ApiModelProperty(value = "管理员编号", name = "adminNum", dataType = "Integer", required = true)
    private Integer adminId;
}
