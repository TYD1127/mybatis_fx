# Mybatis复习一

## 什么是Mybatis

```
MyBatis 是一款优秀的持久层框架，它支持自定义 SQL、存储过程以及高级映射。MyBatis 免除了几乎所有的 JDBC 代码以及设置参数和获取结果集的工作。MyBatis 可以通过简单的 XML 或注解来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录
```

### 1.创建Maven

### 2.导入依赖

```xml
<dependencies>
    <!--        junit  测试-->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.13</version>
    </dependency>
    <!--    Mybatis-->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.5.2</version>
    </dependency>
    <!--    Mysql-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.49</version>
    </dependency>
</dependencies>
<build>
    <!--    静态资源过滤-->
    <resources>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>false</filtering>
        </resource>
        <resource>
            <directory>src/main/resources</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>false</filtering>
        </resource>
    </resources>
</build>
```



### 4.创建MybatisConfig.xml配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <!--引入外部properties文件  -->
    <properties resource="db.properties"></properties>

    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${driver}"/>
                <property name="url" value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>
    </environments>
<!--引入映射文件-->
    <mappers>
        <mapper resource="com/alpha/mapper/userMapper.xml"/>
    </mappers>

</configuration>
```



### 5.创建外部db.properties数据库配置文件

```properties
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/ssmbuild?useSSL=true&useUnicode=true&characterEncoding=utf8
username=root
password=root
```



### 6.创建实体类；

```java
package com.alpha.pojo;

/**
 * @Author TangYaD
 * @Date 2020/10/10 15:51
 * 实体类
 * @Version 1.0
 */
public class User {
    private  int uid;
    private  String uname;
    private  String upwd;

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", uname='" + uname + '\'' +
                ", upwd='" + upwd + '\'' +
                '}';
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpwd() {
        return upwd;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }

    public User(int uid, String uname, String upwd) {
        this.uid = uid;
        this.uname = uname;
        this.upwd = upwd;
    }
}
```



### 7.创建工具类（以后搭配spring之后交给spring去做了）

```java
package com.alpha.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author TangYaD
 * @Date 2020/10/10 16:04
 * SqlSessionFactory工具类
 * @Version 1.0
 */
public class mybatis_util {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
//            定义初始配置文件 resourece直接读取resource包下面文件
            String resource = "mybatisConfig.xml";
            //读取配置文件
            InputStream inputStream = Resources.getResourceAsStream(resource);
            //获取sqlSessionFactory对象
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取SqlSession连接
    public static SqlSession getSession() {

        return sqlSessionFactory.openSession();
    }
}
```

### 8.创建mapper接口文件

```java
package com.alpha.mapper;

import com.alpha.pojo.User;

/**
 * @Author TangYaD
 * @Date 2020/10/10 15:49
 * @Version 1.0
 *
 */
public interface userMapper {
//    实现查询方法接口
    User ByName();
}
```

### 9.创建mapper配置文件（用来编写sql语句）

```
##### namespace 必须和接口名一样
##### id就是方法名一致
```



```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.alpha.mapper.userMapper">
    <!--根据name pwd查询User-->
    <select id="ByName" resultType="com.alpha.pojo.User">
      select * from ssmbuild.user
   </select>
    <!--    添加一个用户-->

</mapper>
```

##### 10.创建测试类实现

```java
package com.alpha;

import com.alpha.mapper.userMapper;
import com.alpha.pojo.User;
import com.alpha.util.mybatis_util;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

/**
 * @Author TangYaD
 * @Date 2020/10/10 15:55
 * @Version 1.0
 */
public class myTest {
    @Test
    public void select() {
//获取sqlssion对象
        SqlSession session = mybatis_util.getSession();
//获取getMapper
        userMapper mapper = session.getMapper(userMapper.class);
//调用ByName方法
        User user = mapper.ByName();
//输出结果
        System.out.println(user);
//关闭连接
        session.close();
    }
}
```

