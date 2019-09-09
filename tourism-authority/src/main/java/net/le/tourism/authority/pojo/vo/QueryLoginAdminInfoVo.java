package net.le.tourism.authority.pojo.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class QueryLoginAdminInfoVo {

    private String adminName;

    private String loginName;

    private String adminAvatar;
}
