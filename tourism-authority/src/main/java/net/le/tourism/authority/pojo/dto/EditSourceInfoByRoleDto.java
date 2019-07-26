package net.le.tourism.authority.pojo.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author hanle
 * @version v1.0
 * @date 2019/6/29
 * @modify 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Data
@ToString
public class EditSourceInfoByRoleDto extends InsertSourceInfoByRoleDto {

    @NotNull(message = "资源Id不能为空")
    @Min(value = 1, message = "资源Id必须大于0")
    private Integer id;
}
