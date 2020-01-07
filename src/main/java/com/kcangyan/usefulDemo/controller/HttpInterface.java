package com.kcangyan.usefulDemo.controller;

import com.kcangyan.usefulDemo.config.MyselfConfig;
import com.kcangyan.usefulDemo.model.JavaMongoDB;
import com.kcangyan.usefulDemo.model.JavaMysql;
import com.kcangyan.usefulDemo.model.JavaRedis;
import com.mongodb.MongoClient;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HttpInterface {
    //实际应用mongo自带线程池，构建全局mongoClient即可，并且不用close(),会自动释放连接对象返回线程池
    //public static MongoClient mongoClient = JavaMongoDB.connect();
    public static void main1(String[] args) throws SQLException {
        Connection conn = (Connection) JavaMysql.connect();
        Statement stmt = (Statement) conn.createStatement();
        String sql;
        sql = "show databases";
        ResultSet rs = stmt.executeQuery(sql);
        // 展开结果集数据库
        while(rs.next()) {
            System.out.print(rs.getString("Database")+"\n");
        }
        // 完成后关闭
        rs.close();
        stmt.close();
        conn.close();
    }
    public static void main2(String[] args){
        MongoClient mongoClient = JavaMongoDB.connect();

        List<Document> list = new ArrayList();
        Document d = new Document();
        d.append("name","主次");
        d.append("news","你好 球机");
        list.add(d);
        //System.out.println(JavaMongoDB.InsertCollection(mongoClient,"test", (ArrayList) list));
        String reList = JavaMongoDB.SelectCollection(mongoClient,"test");
        mongoClient.close();
        System.out.println(reList);
        /*
        Bson filter1 = Filters.in("name","七中","主次");
        Bson filter2 = Filters.in("news","你好 泰山");
        System.out.println(JavaMongoDB.DeleteCollection(mongoClient,"test",Filters.or(filter1,filter2)));
        mongoClient.close();

        Bson filter1 = Filters.in("name","名字");
        Document d = new Document();
        Document e = new Document();
        Document f = new Document();
        f.append("news", "！");
        f.append("oldNews","？");
        e.append("newsO", f);
        d.append("$set",e);
        System.out.println(JavaMongoDB.UpdateCollection(mongoClient,"test",filter1,d));
        mongoClient.close();

         */
    }
    public static void main3(String[] args) throws SQLException {
        Connection conn = (Connection) JavaMysql.DruidConnect();
        Map m = new HashMap();
        m.put("id",6);
        m.put("name","defaultName");
        m.put("pwd","defaultPwd");
        m.put("news","NULL");
        try {
            conn.setAutoCommit(false);//设置不能自动提交
            //System.out.println(JavaMysql.DeleteTable(conn,"JavaMysqlTableTestOne","pwd = '15901590'"));
            System.out.println(JavaMysql.InsertTable(conn,"JavaMysqlTableTestOne",m));
            //System.out.println(JavaMysql.UpdateTable(conn,"JavaMysqlTableTestOne",
            //"pwd='defaultPwdOO'","name ='lastNameOOO'"));
            //System.out.println(JavaMysql.SelectTable(conn,"JavaMysqlTableTestOne",
            //"select * from JavaMysqlTableTestOne"));
            //System.out.println(JavaMysql.CreateTable(conn));
            int a = 10 /0 ;
            conn.commit(); //手动提交
            conn.setAutoCommit(true);//设置可以自动提交
            conn.close();
        }catch (Exception e){
            conn.rollback();//回滚操作
            conn.setAutoCommit(true);//设置可以自动提交
            conn.close();
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/mongo", method = RequestMethod.GET)
    public Object reMongo(){
        MongoClient mongoClient = JavaMongoDB.connect();
        String re = JavaMongoDB.SelectCollection(mongoClient,"test");
        mongoClient.close();
        return re;
    }

    @RequestMapping(value="/mysql", method = RequestMethod.GET)
    public Object reMysql() throws SQLException {
        Jedis jedis = JavaRedis.getRedisConnection();
        String res = jedis.get("JavaMysqlTableTestOne");
        if(null == res){
            synchronized (this){
                res = jedis.get("JavaMysqlTableTestOne");
                if(res == null){
                    Connection conn = (Connection) JavaMysql.DruidConnect();
                    List reList =  JavaMysql.SelectTable(conn,"JavaMysqlTableTestOne",
                            "select * from JavaMysqlTableTestOne");
                    conn.close();
                    res = reList.toString();
                    jedis.set("JavaMysqlTableTestOne",res);
                    jedis.expire("JavaMysqlTableTestOne",100);
                    //System.out.println("mysql");
                }
                //System.out.println("单线程");
            }
        }
        jedis.close();
        //System.out.println("redis");
        return res;
    }

    @RequestMapping(value = "/user1/{id}/{psd}", method = RequestMethod.GET)
    public String reUserInfo(@PathVariable("id") int id, @PathVariable("psd") String name){
        return "id = " + id + "name = " + name;
    }
    @RequestMapping(value = "/user2", method = RequestMethod.GET, params = {"id","name"})
    public String reUserInfo2(@RequestParam Integer id, @RequestParam String name){
        return "id = " + id + "name = " + name;
    }
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Object getUserInfo(HttpServletRequest request,
                              @RequestParam(value = "id", required = false, defaultValue = "1") Integer id,
                              @RequestParam(value = "name", required = false, defaultValue = "asd") String name){
        Map user = new HashMap();
        user.put("id",id);
        user.put("name",name);
        return user;
    }
    @RequestMapping(value = "/users")
    public Object getUser(HttpServletRequest request, @RequestBody Map jsonObject){
        Map user = new HashMap();
        System.out.println(jsonObject.toString());
        user.put("name","asd");
        user.put("id",10);
        user.put("news",request.getMethod()+jsonObject.get("code"));
        return user;
    }

    @GetMapping(value = "/download/{name}")
    public String download(@PathVariable("name") String name, HttpServletRequest request, HttpServletResponse response){
        String fileName = name;
        if(!fileName.equals("") && fileName != null){
            try {
                String filePath = MyselfConfig.getDownloadPath();
                File file = new File(filePath,fileName);
                if(file.exists()){
                    response.setContentType("application/force-download");// 设置强制下载不打开
                    response.addHeader("Content-Disposition", "attachment;fileName=" +
                            URLEncoder.encode(fileName, "UTF-8"));// 设置文件名
                    byte[] buffer = new byte[1024];
                    FileInputStream fileInput = new FileInputStream(file);
                    BufferedInputStream bufferedInput = new BufferedInputStream(fileInput);
                    try {
                        OutputStream outputS = response.getOutputStream();
                        int i = bufferedInput.read(buffer);
                        while (i != -1) {
                            outputS.write(buffer,0,i);
                            i = bufferedInput.read(buffer);
                        }
                        fileInput.close();
                        bufferedInput.close();
                        outputS.close();
                    }catch (Exception e){
                        fileInput.close();
                        bufferedInput.close();
                    }
                }else{
                    return null;
                }
            }catch (Exception e){
                return null;
            }
        }
        return null;
    }
    @PostMapping(value = "/upload")
    public String upload(@RequestParam("File") MultipartFile file){
        try{
            String filename = file.getOriginalFilename();
            String suffixName = filename.substring(filename.lastIndexOf("."));//后缀名
            String filepath = MyselfConfig.getUploadPath() + "\\" + filename;
            File fileIO = new File(filepath);
            if(fileIO.getParentFile().exists()){
                fileIO.getParentFile().mkdirs();//新建文件夹
            }
            long MaxSize = 2*1024*1024;
            //System.out.println(file.getSize());
            if(file.getSize()>=MaxSize){
                return "上传文件不得大于2M!";
            }
            file.transferTo(fileIO);//写入
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }

}
