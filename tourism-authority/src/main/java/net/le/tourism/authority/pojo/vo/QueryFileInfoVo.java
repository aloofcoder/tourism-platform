package net.le.tourism.authority.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

/**
 * @author hanle
 * @version v1.0
 * @date 2019/7/5
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Data
@ToString
public class QueryFileInfoVo {

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
}
