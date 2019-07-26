package net.le.tourism.authority.pojo.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-06-26
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Data
@ToString
public class TokenDto {

    private Integer adminId;

    private String adminNum;

    private String adminName;

    private String token;
}
