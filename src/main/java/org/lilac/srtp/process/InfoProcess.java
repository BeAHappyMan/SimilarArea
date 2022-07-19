package org.lilac.srtp.process;

import org.lilac.srtp.pojo.Point;
import org.lilac.srtp.pojo.SearchInfo;
import org.lilac.srtp.utils.JsonUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Component
public class InfoProcess {

    @Resource
    RestTemplate restTemplate;

    //返回由算法找出的区域两点坐标
    public Point[] getPoints(SearchInfo info){

        //要进行访问的url
        String url = "http://localhost:8081/processTest";

        System.out.println(JsonUtil.toJsonString(info));

        //这里传参给python可以传json字符串，用JSON.toJSONString转换info，若返回为json字符串，第三个参数为String.class
        //调用的是SearchController下的processTest接口
        Point[] points = restTemplate.postForObject(url, info, Point[].class);

        return points;
    }

}
