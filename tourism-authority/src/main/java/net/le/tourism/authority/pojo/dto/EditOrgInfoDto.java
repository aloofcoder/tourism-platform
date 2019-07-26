package net.le.tourism.authority.pojo.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class EditOrgInfoDto extends InsertOrgInfoDto {

    @NotNull(message = "组织Id不能为空")
    @Min(value = 1, message = "组织Id必须大于0")
    private Integer id;
}
