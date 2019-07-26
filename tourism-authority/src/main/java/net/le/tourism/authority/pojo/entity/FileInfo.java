package net.le.tourism.authority.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.le.tourism.authority.constant.Constants;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author 韩乐
 * @since 2019-06-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FileInfo extends Model<FileInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "file_id", type = IdType.AUTO)
    private Integer fileId;

    /**
     * 文件上传时名称
     */
    private String fileName;

    /**
     * 文件保存路径
     */
    private String filePath;

    /**
     * 将文件流进行md5 验证文件是否已有上传
     */
    private String fileMd5;

    /**
     * 文件状态（0正常1删除）
     */
    private Integer status;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 修改人
     */
    private String editUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = Constants.DEFAULT_TIME_ZONE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = Constants.DEFAULT_TIME_ZONE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date editTime;


    @Override
    protected Serializable pkVal() {
        return this.fileId;
    }

}
