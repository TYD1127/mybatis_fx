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
