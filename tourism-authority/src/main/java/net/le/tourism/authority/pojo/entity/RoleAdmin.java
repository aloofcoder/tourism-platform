package net.le.tourism.authority.pojo.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 韩乐
 * @since 2019-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RoleAdmin extends Model<RoleAdmin> {

private static final long serialVersionUID=1L;

    private Integer id;

    private Integer roleId;

    private Integer adminId;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
