package com.example.btc.baseDao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.util.List;

/**
 * Created by Administrator on 2021-01-19.
 */
@Mapper
public interface UrlPara {
    public List<String> getHbpara();
}
