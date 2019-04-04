package cn.yali.redis.client;

import cn.yali.redis.RedisClient;
import cn.yali.redis.config.RedisConnectConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 集群redis操作客户端.
 */
public class ClusterRedis implements RedisClient {

    private final JedisCluster jedisCluster;

    public ClusterRedis(final RedisConnectConfig rconfig) {
        JedisPoolConfig poolConfigconfig = new JedisPoolConfig();
        poolConfigconfig.setMaxIdle(rconfig.getPool_maxIdle());
        poolConfigconfig.setMaxTotal(rconfig.getPool_maxTotal());
        poolConfigconfig.setMaxWaitMillis(rconfig.getPool_maxWait());
        poolConfigconfig.setMinIdle(rconfig.getPool_minIdle());
        poolConfigconfig.setTestOnBorrow(rconfig.isPool_testOnBorrow());
        poolConfigconfig.setTestOnReturn(rconfig.isPool_testOnReturn());
        Set<HostAndPort> haps = parseHostAndPort(rconfig.getAddress());

        String password = rconfig.getPassword();

        if(password == null || password.equals("")){
            jedisCluster = new JedisCluster(haps, rconfig.getTimeout(), rconfig.getMaxAttempts(), poolConfigconfig);
        }else{
            jedisCluster = new JedisCluster(haps, rconfig.getTimeout(), rconfig.getSoTimeout(), rconfig.getMaxAttempts(), password,  poolConfigconfig);
        }
    }

    public boolean connect(RedisConnectConfig config) {
        return false;
    }

    @Override
    public boolean del(String key) {
        return false;
    }

    public boolean set(String key, String value) {
        return false;
    }

    public boolean set(String key, Object value) {
        return false;
    }

    public boolean set(String key, List value) {
        return false;
    }

    public boolean set(String key, Map value) {
        return false;
    }

    @Override
    public boolean sadd(String key, String value) {
        return false;
    }

    @Override
    public boolean sismember(String key, String value) {
        return false;
    }

    @Override
    public boolean srem(String key, String value) {
        return false;
    }

    @Override
    public Set<String> smembers(String key) {
        return null;
    }

    public Long expire(String key, int expires) {
        return 0L;
    }

    public String getString(String key) {
        return null;
    }

    public Map getMap(String key) {
        return null;
    }

    public List getList(String key) {
        return null;
    }

    public <T> T getObject(String key, Class<T> clazz) {
        return null;
    }

    /**
     * 读取redis集群地址
     * @return
     */
    private static Set<HostAndPort> parseHostAndPort(String hps) {
        Set<HostAndPort> haps = new HashSet<HostAndPort>();
        String[] hpstrArr = hps.split(",");
        for (String hp : hpstrArr) {
            String[] ipAndPort = hp.split(":");
            HostAndPort hap = new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
            haps.add(hap);
        }
        return haps;
    }
}
