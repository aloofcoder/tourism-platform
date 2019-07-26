package net.le.tourism.authority.pojo.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-06-25
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Data
@ToString
public class QueryOrgInfoVo implements Serializable {

    private static final long serialVersionUID = -6375999551281694982L;

    private Integer parentId;

    private String parentName;

    private Integer orgId;

    private String orgName;

    private String orgAdmin;

    private Integer orgSort;

    private Integer orgClass;

    private Integer status;

    private List<QueryOrgInfoVo> children;
}
