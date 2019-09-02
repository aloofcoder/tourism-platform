package net.le.tourism.authority.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
@ApiModel(value = "分页查询公用参数")
public class PageQueryBaseDto implements Serializable {

    private static final long serialVersionUID = 3016776723653876379L;

    @ApiModelProperty(value = "当前页", name = "currentPage",  dataType= "Integer", required = true)
    @NotNull(message = "当前页数不能为空！")
    @Min(value = 1, message = "当前页数必须大于等于1！")
    private Integer currentPage;

    @ApiModelProperty(value = "每页条数", name = "pageSize", dataType = "Integer", required = true)
    @NotNull(message = "每页条数不能为空！")
    @Min(value = 1, message = "每页条数必须大于等于1！")
    private Integer pageSize;

}
