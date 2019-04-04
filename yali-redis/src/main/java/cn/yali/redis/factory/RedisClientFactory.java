package cn.yali.redis.factory;

import cn.yali.redis.RedisClient;
import cn.yali.redis.client.ClusterRedis;
import cn.yali.redis.client.SingleRedis;
import cn.yali.redis.config.RedisConnectConfig;
import cn.yali.redis.exception.RedisException;
import cn.yali.redis.type.RedisMode;

/**
 * redis客户端创建工厂.
 */
public class RedisClientFactory {
    private static RedisClient redisClient;

    public static RedisClient getInstance() throws RedisException {
        if (redisClient == null) {
            RedisConnectConfig config = new RedisConnectConfig();
            config.init();
            if (RedisMode.SINGLE == config.getMode()) {
                redisClient = new SingleRedis(config);
            }else if (RedisMode.CLUSTER == config.getMode()) {
                redisClient = new ClusterRedis(config);
            }else {
                throw new RedisException("redisMode is not SINGLE and CLUSTER");
            }
        }
        return redisClient;
    }
}
