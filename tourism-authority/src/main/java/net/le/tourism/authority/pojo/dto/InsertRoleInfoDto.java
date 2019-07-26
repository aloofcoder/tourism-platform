package net.le.tourism.authority.pojo.dto;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author hanle
 * @version v1.0
 * @date 2019/6/29
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Data
@ToString
public class InsertRoleInfoDto implements Serializable {

    private static final long serialVersionUID = 8655350489167500205L;

    @NotBlank(message = "角色名称不能为空")
    @Length(min = 2, max = 8, message = "角色名称长度必须在2-8位之间")
    private String roleName;

    @NotBlank(message = "角色描述不能为空")
    @Length(min = 2, message = "角色描述长度不能小于2")
    private String roleDesc;

    @NotNull(message = "角色显示排序不能为空")
    @Min(value = 1, message = "角色显示排序不能小于1")
    private Integer roleSort;

    @Valid
    @NotEmpty(message = "资源集合不能为空")
    private List<@Min(value = 1, message = "资源Id必须大于0") Integer> sourceIds;
}
