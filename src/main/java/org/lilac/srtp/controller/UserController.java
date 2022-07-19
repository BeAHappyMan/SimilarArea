package org.lilac.srtp.controller;

import org.lilac.srtp.common.MessageBox;
import org.lilac.srtp.dao.RedisDao;
import org.lilac.srtp.pojo.LogInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserController {

    @Resource
    RedisDao redisDao;

    @RequestMapping("/getLog")
    public MessageBox<?> searchPOI(HttpServletRequest req){
        String id = req.getSession().getId();
        List<LogInfo> infos  = redisDao.getLog(id);
        return MessageBox.success(infos);
    }

}
