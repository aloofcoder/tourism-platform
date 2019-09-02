package net.le.tourism.authority.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.le.tourism.authority.common.constant.Constants;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 组织机构类
 * </p>
 *
 * @author 韩乐
 * @since 2019-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrgInfo extends Model<OrgInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "org_id", type = IdType.AUTO)
    private Integer orgId;

    @NotNull(message = "上级组织不能为空！")
    @Min(value = 1, message = "上级组织ID必须大于等于1！")
    private Integer parentId;

    @NotEmpty(message = "组织名称不能为空")
    @Length(min = 2, max = 10, message = "组织名称长度必须在2~10位之间！")
    private String orgName;

    @NotEmpty(message = "组织负责人名不能为空！")
    @Length(min = 1, max = 6, message = "组织负责人名长度必须在1~6位之间！")
    private String orgAdmin;

    @NotNull(message = "组织类型不能为空！")
    @Min(value = 1, message = "组织类型值必须大于等于1！")
    private Integer orgClass;

    @NotNull(message = "组织显示时的排序号不能为空！")
    @Min(value = 1, message = "组织显示时的排序号的值必须大于等于1！")
    private Integer orgSort;

    private Integer status;

    private String remark;

    private String createUser;

    private String editUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = Constants.DEFAULT_TIME_ZONE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = Constants.DEFAULT_TIME_ZONE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date editTime;

    @Override
    protected Serializable pkVal() {
        return this.orgId;
    }

}
