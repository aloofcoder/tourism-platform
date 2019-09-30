package net.le.tourism.authority.common.util;

import net.le.tourism.authority.common.constant.Constants;
import org.springframework.data.redis.core.script.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-06-20
 * @modify 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
public class TourismUtils {

    public static String getAdminTokenKey(String adminNum, String token) {
        String tokenKey = String.format("%1$s:%2$s:%3$s",
                Constants.ADMIN_TOKEN_SUFFIX, adminNum, token);
        return tokenKey;
    }

    public static String getAdminNum() {
        return String.format("%1$s%2$s%3$s", "T", ((int) (Math.random() * (99999 - 10000)) + 10000), ((int) (Math.random() * (999999999 - 100000000)) + 100000000));
    }

    public static String getToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String getRequestToken() {
        HttpServletRequest request = ServletUtils.getRequest();
        String token = request.getHeader(Constants.AUTH_KEY);
        return token;
    }


    /**
     * 构建管理员登录密码
     *
     * @param salt
     * @param pwd
     * @return
     */
    public static String buildAdminPwd(String salt, String pwd) {
        return DigestUtils.sha1DigestAsHex(salt + pwd);
    }

    /**
     * 获取管理员密码加密盐值
     *
     * @return
     */
    public static String getSalt() {
        return DigestUtils.sha1DigestAsHex(UUID.randomUUID().toString());
    }


    public static String buildMPTokenKey(String token) {
        return String.format("%1$s:%2$s", Constants.MP_TOKEN_SUFFIX, token);
    }

    public static String buildMpAccessTokenKey(String appId) {
        return String.format("%1$s:%2$s", Constants.MP_ACCESS_TOKEN_SUFFIX, appId);
    }

    /*
    public static void main(String[] args) {
        System.out.println(getAdminNum());
        String salt = TourismUtils.getSalt();
        System.out.println(salt);
        System.out.println(TourismUtils.buildAdminPwd(salt, "admin!@#"));
    }
     */
}
