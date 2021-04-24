package com.example.btc.services.baseDaoService;

import com.example.btc.baseDao.UrlPara;
import com.example.btc.controller.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2021-01-19.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class UrlParaService {
    @Autowired UrlPara urlPara;
    public List<String> getUrlPara()
    {
        return urlPara.getHbpara();
    }
    public User getuser(User user){return urlPara.getuser(user);}
}
