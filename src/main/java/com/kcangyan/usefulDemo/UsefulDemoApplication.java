package com.kcangyan.usefulDemo;

//import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.mongodb.MongoConfigurationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication
//取消自动配置
//@EnableAutoConfiguration(exclude={DruidDataSourceAutoConfigure.class, MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
//@EnableTransactionManagement 开启事务 @Transactional
public class UsefulDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsefulDemoApplication.class, args);
	}

}
