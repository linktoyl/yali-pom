package cn.yali.redis;

import cn.yali.redis.config.RedisConnectConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis 操作接口
 */
public interface RedisClient {

    boolean connect(RedisConnectConfig config);

    boolean del(final String key);

    boolean set(final String key, final String value);
    boolean set(final String key, final Object value);
    boolean set(final String key, final List value);
    boolean set(final String key, final Map value);

    boolean sadd(final String key, final String value);
    boolean sismember(final String key, final String value);
    boolean srem(final String key, final String value);
    Set<String> smembers(final String key);

    Long expire(final String key, final int expires);

    String getString(final String key);
    Map getMap(final String key);
    List getList(final String key);
    <T> T getObject(final String key, Class<T> clazz);

}
