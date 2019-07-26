package net.le.tourism.authority.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-05-27
 * @modify 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
public class BaseContextUtils {

    public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        return map.get(key);
    }

    public static void remove() {
        threadLocal.remove();
    }
}
