package net.le.tourism.authority.pojo.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 添加组织接收参数类
 * @author hanle
 * @version v1.0
 * @date 2019-06-25
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Data
@ToString
public class InsertOrgInfoDto implements Serializable {

    private static final long serialVersionUID = 5461456289478767028L;

    @NotNull(message = "请选择父组织！")
    @Min(value = 1, message = "请选择正确的父组织！")
    private Integer parentId;

    @NotBlank(message = "组织名称不能为空！")
    private String orgName;

    @NotBlank(message = "组织管理员不能为空")
    private String orgAdmin;

    @NotNull(message = "组织类型不能为空")
    @Min(value = 1, message = "请选择正确的组织类型！")
    private Integer orgClass;

    @NotNull(message = "组织排列顺序不能为空")
    @Min(value = 1, message = "请填写正确的组织排列顺序！")
    private Integer orgSort;
}
