package cn.yali.redis.client;

import cn.yali.redis.RedisClient;
import cn.yali.redis.config.RedisConnectConfig;
import cn.yali.redis.type.RedisReply;
import cn.yali.redis.utils.SerializeUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * 单点Redis操作客户端.
 */
public class SingleRedis implements RedisClient {

    private final JedisPool jedisPool;

    public SingleRedis(final RedisConnectConfig config) {
        JedisPoolConfig poolConfigconfig = new JedisPoolConfig();
        poolConfigconfig.setMaxIdle(config.getPool_maxIdle());
        poolConfigconfig.setMaxTotal(config.getPool_maxTotal());
        poolConfigconfig.setMaxWaitMillis(config.getPool_maxWait());
        poolConfigconfig.setMinIdle(config.getPool_minIdle());
        poolConfigconfig.setTestOnBorrow(config.isPool_testOnBorrow());
        poolConfigconfig.setTestOnReturn(config.isPool_testOnReturn());
        String[] address = config.getAddress().split(":");
        String host = address[0];
        int port = Integer.valueOf(address[1]);
        jedisPool = new JedisPool(poolConfigconfig, host, port, config.getTimeout(), config.getPassword());
    }

    public boolean connect(RedisConnectConfig config) {
        return false;
    }

    @Override
    public boolean del(String key) {
        Jedis jedis = jedisPool.getResource();
        return jedis.del(key) > 0;
    }

    public boolean set(final String key, final String value) {
        Jedis jedis = jedisPool.getResource();
        String reply = jedis.set(key, value);
        return RedisReply.OK.equals(reply);
    }

    public boolean set(String key, Object value) {
        Jedis jedis = jedisPool.getResource();
        try {
            String reply = jedis.set(key.getBytes("utf-8"), SerializeUtil.serialize(value));
            return RedisReply.OK.equals(reply);
        } catch (UnsupportedEncodingException e) {
            return false;
        }
    }

    public boolean set(final String key, final List value) {
        Jedis jedis = jedisPool.getResource();
        Long aLong = -1L;
        for (Object obj:value) {
            aLong = jedis.lpush(key, obj.toString());
        }
        return aLong>-1;
    }

    public boolean set(String key, Map value) {
        Jedis jedis = jedisPool.getResource();
        String reply = jedis.hmset(key, value);
        return RedisReply.OK.equals(reply);
    }

    @Override
    public boolean sadd(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        return jedis.sadd(key, value)>0;
    }

    @Override
    public boolean sismember(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        return jedis.sismember(key, value);
    }

    @Override
    public boolean srem(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        return jedis.srem(key, value)>0;
    }

    public Long expire(String key, int expires) {
        Jedis jedis = jedisPool.getResource();
        return jedis.expire(key, expires);
    }

    public String getString(String key) {
        Jedis jedis = jedisPool.getResource();
        return jedis.get(key);
    }

    public Map getMap(String key) {
        Jedis jedis = jedisPool.getResource();
        return jedis.hgetAll(key);
    }

    public List getList(String key) {
        Jedis jedis = jedisPool.getResource();
        return jedis.lrange(key, 0, -1);
    }

    public <T> T getObject(String key, Class<T> clazz) {
        Jedis jedis = jedisPool.getResource();
        try {
            byte[] reply = jedis.get(key.getBytes("utf-8"));
            Object obj = SerializeUtil.unserialize(reply);
            return clazz.cast(obj);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
