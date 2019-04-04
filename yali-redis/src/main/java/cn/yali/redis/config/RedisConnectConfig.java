package cn.yali.redis.config;

import cn.yali.redis.exception.RedisException;
import cn.yali.redis.type.RedisMode;

import java.io.IOException;
import java.util.Properties;

/**
 * Redis连接配置
 */
public class RedisConnectConfig {
    private final static String regex = "(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])){3}(:\\d+){1}(,(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])){3}(:\\d+){1})*";

    private RedisMode mode;
    private String password;
    private Integer timeout = 30;
    private Integer soTimeout = 30;
    private Integer maxAttempts = 8;
    private String address;

    private Integer pool_maxTotal = 500;
    private Integer pool_maxIdle = 50;
    private Integer pool_minIdle = 10;
    private Long pool_maxWait = Long.valueOf(100000);
    private boolean pool_testOnBorrow = true;
    private boolean pool_testOnReturn = true;

    public void init() throws RedisException {
        try {
            Properties prop = new Properties();
            prop.load(RedisConnectConfig.class.getClassLoader().getResourceAsStream("redis.properties"));
            mode = RedisMode.valueOf(prop.getProperty("jedis.mode", "SINGLE"));
            pool_maxTotal = Integer.valueOf(prop.getProperty("jedis.pool.maxTotal", "500"));
            pool_maxIdle = Integer.valueOf(prop.getProperty("jedis.pool.maxIdle", "50"));
            pool_minIdle = Integer.valueOf(prop.getProperty("jedis.pool.minIdle", "10"));
            pool_maxWait = Long.valueOf(prop.getProperty("jedis.pool.maxWait", "100000"));
            pool_testOnBorrow = Boolean.valueOf(prop.getProperty("jedis.pool.testOnBorrow", "true"));
            pool_testOnReturn = Boolean.valueOf(prop.getProperty("jedis.pool.testOnReturn", "true"));

            timeout = Integer.valueOf(prop.getProperty("jedis.timeout", "30"));
            soTimeout = Integer.valueOf(prop.getProperty("jedis.soTimeout", "30"));
            maxAttempts = Integer.valueOf(prop.getProperty("jedis.maxAttempts", "8"));
            address = prop.getProperty("jedis.address", "");
            if (!address.matches(regex)) {
                throw new RedisException("加载redis.properties配置, address错误.");
            }
            password = prop.getProperty("jedis.password", "");

        } catch (IOException e) {
            throw new RedisException("加载redis.properties配置失败.");
        } catch (NumberFormatException e) {
            throw new RedisException("加载redis.properties配置, NumberFormat错误.");
        } catch (IllegalArgumentException e) {
            throw new RedisException("加载redis.properties配置, RedisMode错误.");
        } catch (Exception e) {
            throw new RedisException("加载redis.properties配置, 转换错误.");
        }
    }

    public RedisMode getMode() {
        return mode;
    }

    public String getPassword() {
        return password;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public Integer getSoTimeout() {
        return soTimeout;
    }

    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public String getAddress() {
        return address;
    }

    public Integer getPool_maxTotal() {
        return pool_maxTotal;
    }

    public Integer getPool_maxIdle() {
        return pool_maxIdle;
    }

    public Integer getPool_minIdle() {
        return pool_minIdle;
    }

    public Long getPool_maxWait() {
        return pool_maxWait;
    }

    public boolean isPool_testOnBorrow() {
        return pool_testOnBorrow;
    }

    public boolean isPool_testOnReturn() {
        return pool_testOnReturn;
    }
}
