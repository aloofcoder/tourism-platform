package net.le.tourism.authority.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 韩乐
 * @since 2019-06-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SourceInfo extends Model<SourceInfo> {

private static final long serialVersionUID=1L;

    @TableId(value = "source_id", type = IdType.AUTO)
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

    private String createUser;

    private String editUser;

    private Date createTime;

    private Date editTime;


    @Override
    protected Serializable pkVal() {
        return this.sourceId;
    }

}
