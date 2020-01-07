package com.kcangyan.usefulDemo.config;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

@Configuration
public class MyselfConfig {
    private static InputStream in = null;
    private static Properties props = new Properties();
    //其他配置信息
    private static String downloadPath = null;
    private static String uploadPath = null;
    //mysql配置信息
    private static String JDBC_DRIVER = null;
    private static String DB_URL = null;
    private static String MysqlUser = null;
    private static String MysqlPass = null;
    //druid配置信息
    private static int DruidInitialSize = 1;
    private static int DruidMinIdle = 1;
    private static int DruidMaxActive = 20;
    private static boolean DruidRemoveAbandoned = true;
    private static int DruidRemoveAbandonedTimeout = 30;
    private static int DruidMaxWait = 20000;
    private static int DruidTimeBetweenEvictionRunsMillis = 20000;
    private static boolean DruidTestWhileIdle = true;
    private static boolean DruidTestOnBorrow = true;
    //MongoDB配置信息
    private static String MongoDBUser = null;
    private static String MongoDBPass = null;
    private static String MongoDBDataBase = null;
    private static String MongoDBHost = null;
    private static int MongoDBPort = 27017;
    //Redis配置信息
    private static String RedisHost = "127.0.0.1";
    private static int RedisPort = 6379;
    private static String RedisPass = null;
    private static int RedisMaxTotal = 100;
    private static int RedisMaxIdle = 20;
    private static int RedisMinIdle = 5;
    private static int RedisMaxWaitMillis = 10000;
    private static int RedisTimeBetweenEvictionRunsMillis = 1000;
    private static int RedisMinEvictableIdleTimeMillis = 600000;
    private static int RedisNumTestsPerEvictionRun = 10;
    private static boolean RedisTestOnBorrow = true;
    private static boolean RedisTestOnReturn = true;
    private static String DruidConfigIP = "127.0.0.1";

    static {
        try {
            in = MyselfConfig.class.getResourceAsStream("/application.properties");
            props.load(in);
            downloadPath = props.getProperty("MyselfConfig.downloadPath");
            uploadPath = props.getProperty("MyselfConfig.uploadPath");

            JDBC_DRIVER = props.getProperty("Mysql.JDBC_DRIVER");
            DB_URL = props.getProperty("Mysql.DB_URL");
            MysqlUser = props.getProperty("Mysql.userName");
            MysqlPass = props.getProperty("Mysql.PassWord");

            DruidInitialSize = Integer.parseInt(props.getProperty("Druid.dataSource.InitialSize"));
            DruidMinIdle = Integer.parseInt(props.getProperty("Druid.dataSource.MinIdle"));
            DruidMaxActive = Integer.parseInt(props.getProperty("Druid.dataSource.MaxActive"));
            DruidRemoveAbandoned = Boolean.parseBoolean(props.getProperty("Druid.dataSource.RemoveAbandoned"));
            DruidRemoveAbandonedTimeout = Integer.parseInt(props.getProperty("Druid.dataSource.RemoveAbandonedTimeout"));
            DruidMaxWait = Integer.parseInt(props.getProperty("Druid.dataSource.MaxWait"));
            DruidTimeBetweenEvictionRunsMillis = Integer.parseInt(props.getProperty("Druid.dataSource.TimeBetweenEvictionRunsMillis"));
            DruidTestWhileIdle = Boolean.parseBoolean(props.getProperty("Druid.dataSource.TestWhileIdle"));
            DruidTestOnBorrow = Boolean.parseBoolean(props.getProperty("Druid.dataSource.TestOnBorrow"));

            MongoDBUser = props.getProperty("MongoDB.userName");
            MongoDBPass = props.getProperty("MongoDB.passWord");
            MongoDBDataBase = props.getProperty("MongoDB.dataBase");
            MongoDBHost = props.getProperty("MongoDB.host");
            MongoDBPort = Integer.parseInt(props.getProperty("MongoDB.port"));

            RedisHost = props.getProperty("Redis.host");
            RedisPort = Integer.parseInt(props.getProperty("Redis.port"));
            RedisPass = props.getProperty("Redis.passWord");
            RedisMaxTotal = Integer.parseInt(props.getProperty("Redis.MaxTotal"));
            RedisMaxIdle = Integer.parseInt(props.getProperty("Redis.MaxIdle"));
            RedisMinIdle = Integer.parseInt(props.getProperty("Redis.MinIdle"));
            RedisMaxWaitMillis = Integer.parseInt(props.getProperty("Redis.MaxWaitMillis"));
            RedisTimeBetweenEvictionRunsMillis = Integer.parseInt(props.getProperty("Redis.TimeBetweenEvictionRunsMillis"));
            RedisMinEvictableIdleTimeMillis = Integer.parseInt(props.getProperty("Redis.MinEvictableIdleTimeMillis"));
            RedisNumTestsPerEvictionRun = Integer.parseInt(props.getProperty("Redis.NumTestsPerEvictionRun"));
            RedisTestOnBorrow = Boolean.parseBoolean(props.getProperty("Redis.TestOnBorrow"));
            RedisTestOnReturn = Boolean.parseBoolean(props.getProperty("Redis.TestOnReturn"));

            DruidConfigIP = props.getProperty("Druid.dataSource.platformIP");

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            String time = df.format(new Date());
            System.out.println(time + "  INFO ---- --- [   MyselfConfig] com.hsupup.kcangyan.config               :"
                    +" 导入自定义配置成功！");
        } catch (Exception e) {
            e.printStackTrace();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            String time = df.format(new Date());
            System.out.println(time + "  WANG ---- --- [   MyselfConfig] com.hsupup.kcangyan.config               :"
                    +" 导入自定义配置失败，读不到resource下的配置文件！");
        }
    }

    public static String getDruidConfigIP() {
        return DruidConfigIP;
    }

    public static void setDruidConfigIP(String druidConfigIP) {
        DruidConfigIP = druidConfigIP;
    }

    public static String getMongoDBUser() {
        return MongoDBUser;
    }

    public static void setMongoDBUser(String mongoDBUser) {
        MongoDBUser = mongoDBUser;
    }

    public static String getMongoDBPass() {
        return MongoDBPass;
    }

    public static void setMongoDBPass(String mongoDBPass) {
        MongoDBPass = mongoDBPass;
    }

    public static String getMongoDBDataBase() {
        return MongoDBDataBase;
    }

    public static void setMongoDBDataBase(String mongoDBDataBase) {
        MongoDBDataBase = mongoDBDataBase;
    }

    public static String getMongoDBHost() {
        return MongoDBHost;
    }

    public static void setMongoDBHost(String mongoDBHost) {
        MongoDBHost = mongoDBHost;
    }

    public static Integer getMongoDBPort() {
        return MongoDBPort;
    }

    public static void setMongoDBPort(Integer mongoDBPort) {
        MongoDBPort = mongoDBPort;
    }

    public static int getDruidInitialSize() {
        return DruidInitialSize;
    }

    public static void setDruidInitialSize(int druidInitialSize) {
        DruidInitialSize = druidInitialSize;
    }

    public static int getDruidMinIdle() {
        return DruidMinIdle;
    }

    public static void setDruidMinIdle(int druidMinIdle) {
        DruidMinIdle = druidMinIdle;
    }

    public static int getDruidMaxActive() {
        return DruidMaxActive;
    }

    public static void setDruidMaxActive(int druidMaxActive) {
        DruidMaxActive = druidMaxActive;
    }

    public static boolean isDruidRemoveAbandoned() {
        return DruidRemoveAbandoned;
    }

    public static void setDruidRemoveAbandoned(boolean druidRemoveAbandoned) {
        DruidRemoveAbandoned = druidRemoveAbandoned;
    }

    public static int getDruidRemoveAbandonedTimeout() {
        return DruidRemoveAbandonedTimeout;
    }

    public static void setDruidRemoveAbandonedTimeout(int druidRemoveAbandonedTimeout) {
        DruidRemoveAbandonedTimeout = druidRemoveAbandonedTimeout;
    }

    public static int getDruidMaxWait() {
        return DruidMaxWait;
    }

    public static void setDruidMaxWait(int druidMaxWait) {
        DruidMaxWait = druidMaxWait;
    }

    public static int getDruidTimeBetweenEvictionRunsMillis() {
        return DruidTimeBetweenEvictionRunsMillis;
    }

    public static void setDruidTimeBetweenEvictionRunsMillis(int druidTimeBetweenEvictionRunsMillis) {
        DruidTimeBetweenEvictionRunsMillis = druidTimeBetweenEvictionRunsMillis;
    }

    public static boolean isDruidTestWhileIdle() {
        return DruidTestWhileIdle;
    }

    public static void setDruidTestWhileIdle(boolean druidTestWhileIdle) {
        DruidTestWhileIdle = druidTestWhileIdle;
    }

    public static boolean isDruidTestOnBorrow() {
        return DruidTestOnBorrow;
    }

    public static void setDruidTestOnBorrow(boolean druidTestOnBorrow) {
        DruidTestOnBorrow = druidTestOnBorrow;
    }

    public static String getDownloadPath() {
        return downloadPath;
    }

    public static void setDownloadPath(String downloadPath) {
        MyselfConfig.downloadPath = downloadPath;
    }

    public static String getUploadPath() {
        return uploadPath;
    }

    public static void setUploadPath(String uploadPath) {
        MyselfConfig.uploadPath = uploadPath;
    }

    public static String getJdbcDriver() {
        return JDBC_DRIVER;
    }

    public static void setJdbcDriver(String jdbcDriver) {
        JDBC_DRIVER = jdbcDriver;
    }

    public static String getDbUrl() {
        return DB_URL;
    }

    public static void setDbUrl(String dbUrl) {
        DB_URL = dbUrl;
    }

    public static String getMysqlUser() {
        return MysqlUser;
    }

    public static void setMysqlUser(String USER) {
        MyselfConfig.MysqlUser = USER;
    }

    public static String getMysqlPass() {
        return MysqlPass;
    }

    public static void setMysqlPass(String PASS) {
        MyselfConfig.MysqlPass = PASS;
    }

    public static String getRedisHost() {
        return RedisHost;
    }

    public static void setRedisHost(String redisHost) {
        RedisHost = redisHost;
    }

    public static Integer getRedisPort() {
        return RedisPort;
    }

    public static void setRedisPort(Integer redisPort) {
        RedisPort = redisPort;
    }

    public static String getRedisPass() {
        return RedisPass;
    }

    public static void setRedisPass(String redisPass) {
        RedisPass = redisPass;
    }

    public static Integer getRedisMaxTotal() {
        return RedisMaxTotal;
    }

    public static void setRedisMaxTotal(Integer redisMaxTotal) {
        RedisMaxTotal = redisMaxTotal;
    }

    public static Integer getRedisMaxIdle() {
        return RedisMaxIdle;
    }

    public static void setRedisMaxIdle(Integer redisMaxIdle) {
        RedisMaxIdle = redisMaxIdle;
    }

    public static Integer getRedisMinIdle() {
        return RedisMinIdle;
    }

    public static void setRedisMinIdle(Integer redisMinIdle) {
        RedisMinIdle = redisMinIdle;
    }

    public static Integer getRedisMaxWaitMillis() {
        return RedisMaxWaitMillis;
    }

    public static void setRedisMaxWaitMillis(Integer redisMaxWaitMillis) {
        RedisMaxWaitMillis = redisMaxWaitMillis;
    }

    public static Integer getRedisTimeBetweenEvictionRunsMillis() {
        return RedisTimeBetweenEvictionRunsMillis;
    }

    public static void setRedisTimeBetweenEvictionRunsMillis(Integer redisTimeBetweenEvictionRunsMillis) {
        RedisTimeBetweenEvictionRunsMillis = redisTimeBetweenEvictionRunsMillis;
    }

    public static Integer getRedisMinEvictableIdleTimeMillis() {
        return RedisMinEvictableIdleTimeMillis;
    }

    public static void setRedisMinEvictableIdleTimeMillis(Integer redisMinEvictableIdleTimeMillis) {
        RedisMinEvictableIdleTimeMillis = redisMinEvictableIdleTimeMillis;
    }

    public static Integer getRedisNumTestsPerEvictionRun() {
        return RedisNumTestsPerEvictionRun;
    }

    public static void setRedisNumTestsPerEvictionRun(Integer redisNumTestsPerEvictionRun) {
        RedisNumTestsPerEvictionRun = redisNumTestsPerEvictionRun;
    }

    public static Boolean isRedisTestOnBorrow() {
        return RedisTestOnBorrow;
    }

    public static void setRedisTestOnBorrow(Boolean redisTestOnBorrow) {
        RedisTestOnBorrow = redisTestOnBorrow;
    }

    public static Boolean isRedisTestOnReturn() {
        return RedisTestOnReturn;
    }

    public static void setRedisTestOnReturn(Boolean redisTestOnReturn) {
        RedisTestOnReturn = redisTestOnReturn;
    }
//System.out.println(props.getProperty("MySelfConfig.downloadPath"));
}
