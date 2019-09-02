package net.le.tourism.authority.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-05-18
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Data
@ToString
@ApiModel(value = "分页查询管理员参数")
public class QueryAdminInfoDto extends PageQueryBaseDto {

    @ApiModelProperty(value = "查询条件", name = "condition", dataType = "String")
    private String condition;
}
