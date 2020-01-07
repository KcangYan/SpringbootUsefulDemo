package com.kcangyan.usefulDemo.model;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.gson.Gson;
import com.kcangyan.usefulDemo.config.MyselfConfig;

import java.sql.*;
import java.util.*;

public class JavaMysql {
    private static String JDBC_DRIVER = MyselfConfig.getJdbcDriver();
    private static String DB_URL = MyselfConfig.getDbUrl();
    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    //static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    //static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&serverTimezone=UTC";
    // 数据库的用户名与密码，需要根据自己的设置
    private static String USER = MyselfConfig.getMysqlUser();
    private static String PASS = MyselfConfig.getMysqlPass();
    //druid
    private static DruidDataSource dataSource = null;
    //连接Druid show full processlist;
    private static void SetDruidDataSource(){
        if(dataSource == null){
            //System.out.println("连接Druid");
            try{
                dataSource = new DruidDataSource();
                //设置连接参数
                dataSource.setUrl(DB_URL);
                dataSource.setDriverClassName(JDBC_DRIVER);
                dataSource.setUsername(USER);
                dataSource.setPassword(PASS);
                //配置初始化大小、最小、最大
                dataSource.setInitialSize(MyselfConfig.getDruidInitialSize());
                dataSource.setMinIdle(MyselfConfig.getDruidMinIdle());
                dataSource.setMaxActive(MyselfConfig.getDruidMaxActive());
                //连接泄漏监测
                dataSource.setRemoveAbandoned(MyselfConfig.isDruidRemoveAbandoned());
                dataSource.setRemoveAbandonedTimeout(MyselfConfig.getDruidRemoveAbandonedTimeout());
                //配置获取连接等待超时的时间
                dataSource.setMaxWait(MyselfConfig.getDruidMaxWait());
                //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
                dataSource.setTimeBetweenEvictionRunsMillis(MyselfConfig.getDruidTimeBetweenEvictionRunsMillis());
                //防止过期
                dataSource.setValidationQuery("SELECT 1");
                dataSource.setTestWhileIdle(MyselfConfig.isDruidTestWhileIdle());
                dataSource.setTestOnBorrow(MyselfConfig.isDruidTestOnBorrow());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public static Connection DruidConnect() throws SQLException {
        Connection con = null;
        try {
            SetDruidDataSource();
            //System.out.println(JDBC_DRIVER);
            con = dataSource.getConnection();
        } catch (Exception e) {
            throw e;
        }
        return con;
    }

    public static Connection connect() {
        Connection conn = null;
        //Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            // 打开链接
            //System.out.println("连接数据库...");
            conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
            // 执行查询
            //System.out.println(" 实例化Statement对象...");
            //stmt = (Statement) conn.createStatement();
            return conn;
        }catch(SQLException se){
            // 处理 JDBC 错误
            //se.printStackTrace();
            return null;
        }catch(Exception e){
            // 处理 Class.forName 错误
            //e.printStackTrace();
            return null;
        }
    }
    /*
    Statement类可用于执行SQL语句，不同类型的SQL语句，需要使用不同的方法，
    ResultSet  executeQuery(String sql) //用于执行select语句,返回结果集
    int  executeUpdate(String sql) //用于执行insert、delete和update语句，返回int
    boolean  execute(String sql) //用于执行create和drop等语句,返回boolean
    */
    // 创建表
    public static String CreateTable(Connection conn){
        String sql = "CREATE TABLE IF NOT EXISTS JavaMysqlTableTestOne(" +
                "id INT," +
                "name VARCHAR(20)," +
                "pwd VARCHAR(20)," +
                "news VARCHAR(50)," +
                "PRIMARY KEY (id))" +
                "ENGINE=InnoDB DEFAULT CHARSET=utf8";
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            boolean rs = stmt.execute(sql);
            //return "success";
            stmt.close();
            if(!rs){
                return "success";
            }else {
                return "fail";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }
    public static Map getTableObject(String TableName){
        // Integer,Float,Double,String(Date,Time,Url)
        Map reMap = new HashMap();
        //Table JavaMysqlTableTestOne
        Map m = new HashMap();
        m.put("id","Integer");
        m.put("name","String");
        m.put("pwd","String");
        m.put("news","String");
        reMap.put("JavaMysqlTableTestOne",m);
        return (Map) reMap.get(TableName);
    }
    //插入
    private static String GetValueTypeString(Object m){
        Integer i = new Integer(1);
        if(m.getClass() == " ".getClass()){
            if(m.toString() == "NULL"){
                return m.toString();
            }
            return String.format("'%s'",m);
        }else {
            return m.toString();
        }
    }
    public static String InsertTable(Connection conn, String TableName, Map InsertNews){
        String keySqlString = "(";
        String valueSqlString = "(";
        Set<String> keys = InsertNews.keySet();
        for(String key:keys){
            keySqlString = keySqlString + key.toString() + ",";
            valueSqlString = valueSqlString + GetValueTypeString(InsertNews.get(key)) + ",";
        }
        keySqlString = keySqlString.substring(0,keySqlString.length()-1) + ")";
        valueSqlString = valueSqlString.substring(0,valueSqlString.length()-1) + ")";
        String sql;
        sql = "INSERT INTO" + " " + TableName + keySqlString + "VALUES" + valueSqlString;
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            Integer row = stmt.executeUpdate(sql);
            stmt.close();
            if(row == 1){
                return "success";
            }else {
                return "fail";
            }
            //return "success";
        } catch (SQLException e) {
            //e.printStackTrace();
            return "ERROR";
        }
    }
    //删除
    public static String DeleteTable(Connection conn, String TableName, String condition){
        String sql;
        sql = "DELETE FROM" + " " + TableName + " WHERE " + condition;
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            Integer row = stmt.executeUpdate(sql);
            stmt.close();
            if(row != 0){
                return "success";
            }else {
                return "fail";
            }
            //return "success";
        } catch (SQLException e) {
            //e.printStackTrace();
            return "ERROR";
            //return sql;
        }
    }
    //更新数据
    public static String UpdateTable(Connection conn, String TableName, String SetCondition, String WhereCondition){
        String sql;
        sql = "UPDATE" + " " + TableName + " SET " + SetCondition + " WHERE " + WhereCondition;
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            Integer row = stmt.executeUpdate(sql);
            stmt.close();
            if(row != 0){
                return "success";
            }else {
                return "fail";
            }
            //return "success";
        } catch (SQLException e) {
            //e.printStackTrace();
            return "ERROR";
            //return sql;
        }
    }
    //查询数据
    public static List SelectTable(Connection conn, String TableName, String SelectSql){
        Statement stmt = null;
        ResultSet rs = null;
        Map TableMap = getTableObject(TableName);
        Set<String> keys = TableMap.keySet();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SelectSql);
            List reList = new ArrayList();
            Gson gs = new Gson();
            try{
                while (rs.next()){
                    Map m = new HashMap();
                    for(String key:keys){
                        if(TableMap.get(key).toString().equals("String")){
                            m.put(key,rs.getString(key));
                            continue;
                        }
                        if(TableMap.get(key).toString().equals("Integer")){
                            m.put(key,rs.getInt(key));
                            continue;
                        }
                        if(TableMap.get(key).toString().equals("Float")){
                            m.put(key,rs.getFloat(key));
                            continue;
                        }
                        if(TableMap.get(key).toString().equals("Double")){
                            m.put(key,rs.getDouble(key));
                            continue;
                        }
                    }
                    reList.add(gs.toJson(m));
                }
            }catch (Exception e){
                rs.close();
                stmt.close();
            }
            rs.close();
            stmt.close();
            return reList;
            //return "success";
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    //原生sql操作
    public static String InsertDelUpDataTable(Connection conn, String sql){
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            Integer row = 0;
            try {
                row = stmt.executeUpdate(sql);
            }catch (Exception e){
                stmt.close();
            }
            stmt.close();
            if(row != 0){
                return "success";
            }else {
                return "fail";
            }
            //return "success";
        } catch (SQLException e) {
            //e.printStackTrace();
            return "ERROR";
            //return sql;
        }
    }
}
