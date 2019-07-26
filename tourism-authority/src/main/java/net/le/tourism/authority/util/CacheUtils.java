package net.le.tourism.authority.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-06-15
 * @modify 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
public class CacheUtils {

    /**
     * 设置字符串永久缓存
     *
     * @param redisTemplate
     * @param key
     * @param value
     */
    public static void set(StringRedisTemplate redisTemplate, String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置字符串可过期缓存
     *
     * @param key
     * @param value
     * @param seconds
     */
    public static void set(StringRedisTemplate redisTemplate, String key, String value, int seconds) {
        redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
    }

    /**
     * 获取字符串value
     *
     * @param redisTemplate
     * @param key
     * @return
     */
    public static String get(StringRedisTemplate redisTemplate, String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除字符串
     */
    public static void remove(RedisTemplate redisTemplate, String key) {
        redisTemplate.delete(key);
    }

    /**
     * 设置hash
     *
     * @param redisTemplate
     * @param key
     * @param value
     * @param second
     */
    public static void hMSet(RedisTemplate redisTemplate, String key, Map<Object, Object> value, long second) {
        redisTemplate.opsForHash().putAll(key, value);
        redisTemplate.expire(key, second, TimeUnit.SECONDS);
    }

    /**
     * 获取hash中某一项的值
     *
     * @param redisTemplate
     * @param key
     * @param name
     * @return
     */
    public static String hGet(RedisTemplate redisTemplate, String key, String name) {
        String value = redisTemplate.opsForHash().get(key, name).toString();
        return value;
    }

    public static Set<String> keys(RedisTemplate redisTemplate, String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 模糊匹配一个key
     *
     * @param redisTemplate
     * @param pattern
     * @return
     */
    public static String getOnlyKey(RedisTemplate redisTemplate, String pattern) {
        String result = null;
        Set<String> set = redisTemplate.keys(pattern);
        Iterator<String> iterator = set.iterator();
        if (iterator.hasNext()) {
            result = iterator.next();
        }
        return result;
    }
}
