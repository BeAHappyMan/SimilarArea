package org.lilac.srtp.service;

import org.lilac.srtp.common.MessageBox;
import org.lilac.srtp.dao.RedisDao;
import org.lilac.srtp.pojo.Point;
import org.lilac.srtp.pojo.SearchInfo;
import org.lilac.srtp.process.InfoProcess;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class SearchService {

    @Resource
    InfoProcess infoProcess;
    @Resource
    RedisDao redisDao;

    public MessageBox<?> processInfo(SearchInfo info){
        //如果信息都正确，则传给后台算法，否则返回错误消息
        MessageBox messageBox = null;
        if(false){
            //根据不同情况生成不同的错误类型
            messageBox = MessageBox.error("1","error");
        }
        else {
            Point[] points = infoProcess.getPoints(info);
            messageBox = MessageBox.success(points);
        }
        return messageBox;
    }

    /**
     * 根据SessionId记录信息
     * @param info
     * @param rsl
     * @param req
     */
    public void recordSearch(SearchInfo info, MessageBox rsl, HttpServletRequest req){
        Point[] points = (Point[]) rsl.getData();
        String id = req.getSession().getId();
        redisDao.addLog(info, id, points);
    }

}
