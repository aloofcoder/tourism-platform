package net.le.tourism.common.util;

import net.le.tourism.common.exception.AppServiceException;
import net.le.tourism.common.exception.ErrorCode;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-06-14
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
public class FileUtils {

    /**
     * 通过文件流获取文件MD5值
     *
     * @param fis
     * @return
     */
    public static String getMD5(InputStream fis) {
        String mdStr = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            mdStr = toHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new AppServiceException(ErrorCode.sys_file_un_security.getMsg(), e);
        } catch (FileNotFoundException e) {
            throw new AppServiceException(ErrorCode.sys_file_not_null.getMsg(), e);
        } catch (IOException e) {
            throw new AppServiceException(ErrorCode.sys_file_un_read.getMsg(), e);
        }
        return mdStr;
    }

    private static String toHex(byte[] bytes) {
        final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }
}
