package com.kcangyan.usefulDemo.model;

import com.kcangyan.usefulDemo.config.MyselfConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JavaRedis {
    private static JedisPool pool = null;
    private static void setRedisConnectionPool() {
        if (pool == null) {
            //创建连接池配置实例
            JedisPoolConfig config = new JedisPoolConfig();
            ;
            //GenericObjectPoolConfig Config = new GenericObjectPoolConfig();
            //最大连接数
            config.setMaxTotal(MyselfConfig.getRedisMaxTotal());
            //最大空闲连接数
            config.setMaxIdle(MyselfConfig.getRedisMaxIdle());
            //最小空闲连接数
            config.setMinIdle(MyselfConfig.getRedisMinIdle());
            //获取连接时的最大等待时间毫秒数
            config.setMaxWaitMillis(MyselfConfig.getRedisMaxWaitMillis());
            //逐出扫描时间间隔
            config.setTimeBetweenEvictionRunsMillis(MyselfConfig.getRedisTimeBetweenEvictionRunsMillis());
            //每timeBetweenEvictionRunsMillis检查一次连接池中空闲的连接,
            //把空闲时间超过minEvictableIdleTimeMillis的连接断开,直到连接池中的连接数到minIdle为止
            config.setMinEvictableIdleTimeMillis(MyselfConfig.getRedisMinEvictableIdleTimeMillis());
            //表示idle object evitor每次扫描的最多的对象数
            config.setNumTestsPerEvictionRun(MyselfConfig.getRedisNumTestsPerEvictionRun());
            //获取连接时检查是否可用
            config.setTestOnBorrow(MyselfConfig.isRedisTestOnBorrow());
            //返回连接时检查是否可用
            config.setTestOnReturn(MyselfConfig.isRedisTestOnReturn());
            pool = new JedisPool(config, MyselfConfig.getRedisHost(), MyselfConfig.getRedisPort());
        }
    }
    public static Jedis getRedisConnection() {
        try {
            setRedisConnectionPool();
            Jedis reJedis = pool.getResource();
            //如果需要密码
            //reJedis.auth(MyselfConfig.getRedisPass());
            return reJedis;
        }catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }
}
