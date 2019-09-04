package net.le.tourism.mp.service;

import net.le.tourism.mp.pojo.dto.MPTokenDto;

/**
 * @author hanle
 * @version v1.0
 * @date 2019/9/2
 * @modify
 * @copyright zhishoubao
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
public interface IWechatMpService {

    MPTokenDto validateLogin(String token);
}
