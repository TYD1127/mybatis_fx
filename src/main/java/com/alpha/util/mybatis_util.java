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
