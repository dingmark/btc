package com.example.btc.baseDao;

import com.example.btc.controller.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2021-01-19
 */
@Mapper
@Component(value ="userMapper")
public interface UrlPara {
    public List<String> getHbpara();
    public User getuser(@Param("uname")String uname,@Param("upwd")String  upwd);
}
