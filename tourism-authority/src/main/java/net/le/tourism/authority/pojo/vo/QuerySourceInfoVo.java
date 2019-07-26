package net.le.tourism.authority.pojo.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class QuerySourceInfoVo implements Serializable {

    private static final long serialVersionUID = 4637016959735658411L;

    private Integer sourceId;

    private Integer parentId;

    /**
     * 系统/菜单/按钮/数据列 的名称
     */
    private String sourceName;

    /**
     * 请求链接
     */
    private String sourceLink;

    /**
     * 资源类型（1系统2菜单3按钮4数据列）
     */
    private Integer sourceType;

    /**
     * 排序
     */
    private Integer sourceSort;

    /**
     * 标记（例：sys:user:add）
     */
    private String sourceMark;

    /**
     * 0 启动 1 禁用
     */
    private Integer status;

    private List<QuerySourceInfoVo> children;

}
