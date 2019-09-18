package net.le.tourism.authority.common.constant;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-05-18
 * @modify 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
public class Constants {

    public static final String ADMIN_LOGIN_NAME = "admin";

    /**
     * 登录cookie key
     */
    public static final String AUTHORITY_KEY = "Authorization";

    /**
     * 管理员登录Token redis前缀
     */
    public static final String ADMIN_TOKEN_SUFFIX = "AUTH_TOKEN";

    /**
     * 本项目redis 前缀
     */
    public static final String COMMON_REDIS_SUFFIX = "tourism";

    /**
     * 逗号
     */
    public static final String COMMA = ",";

    /**
     * 管理员编号
     */
    public static final String ADMIN_NUM = "adminNum";
    /**
     * 管理员姓名
     */
    public static final String ADMIN_NAME = "adminName";

    /**
     * 管理员Token
     */
    public static final String LOGIN_TOKEN = "adminToken";

    /**
     * 管理员所在组织
     */
    public static final String ADMIN_ORG = "orgId";


    public static final String ADMIN_ROLE = "adminRole";

    /**
     * 默认时区
     */
    public static final String DEFAULT_TIME_ZONE = "GMT+8";

    /**
     * 公众号登录Token redis前缀
     */
    public static final String MP_TOKEN_SUFFIX = "MP_TOKEN";

    public static final String MP_ACCESS_TOKEN_SUFFIX = "MP_ACCESS_TOKEN";

    /**
     * openid key
     */
    public static final String OPEN_ID_KEY = "openId";

    public static final String MP_TOKEN = "accessToken";

    /**
     * token 失效时间
     */
    public static final int TOKEN_EXPIRE_TIME = 7200;
}
