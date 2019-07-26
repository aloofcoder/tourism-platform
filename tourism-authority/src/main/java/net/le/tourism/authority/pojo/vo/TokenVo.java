package net.le.tourism.authority.pojo.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-06-15
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Data
@ToString
public class TokenVo implements Serializable {

    private static final long serialVersionUID = 4756329125300877577L;

    private String adminName;

    private String token;
}
