package net.le.tourism.authority.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author hanle
 * @version v1.0
 * @date 2019/6/29
 * @modify 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Data
@ToString
public class InsertSourceInfoByRoleDto implements Serializable {

    private static final long serialVersionUID = 5572975045266640992L;

    @NotNull(message = "资源父Id不能为空")
    @Min(value = 1, message = "资源父Id必须大于0")
    private Integer parentId;


    /**
     * 系统/菜单/按钮/数据列 的名称
     */
    @NotBlank(message = "资源名称不能为空")
    private String sourceName;

    /**
     * 请求链接
     */
    @NotBlank(message = "资源链接不能为空")
    private String sourceLink;

    /**
     * 资源类型（1系统2菜单3按钮4数据列）
     */
    @NotNull(message = "资源类型不能为空")
    @Range(min = 1, max = 4, message = "资源类型必须大于1小于4")
    private Integer sourceType;

    /**
     * 排序
     */
    @NotNull(message = "资源列表显示排序不能为空")
    @Min(value = 1, message = "资源列表显示排序不能小于1")
    private Integer sourceSort;

    /**
     * 标记（例：sys:user:add）
     */
    @NotBlank(message = "资源标记不能为空")
    private String sourceMark;

    @Valid
    @NotNull(message = "角色不能为空")
    @ApiModelProperty(name = "roleIds", value = "管理员角色Id集合", dataType = "List", required = true)
    private List<@NotNull(message = "角色Id不能为空") @Min(value = 1, message = "角色Id必须大于0") Integer> roleIds;
}
