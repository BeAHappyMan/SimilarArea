package org.lilac.srtp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.lilac.srtp.common.MessageBox;
import org.lilac.srtp.pojo.Point;
import org.lilac.srtp.pojo.SearchInfo;
import org.lilac.srtp.process.InfoProcess;
import org.lilac.srtp.service.SearchService;
import org.lilac.srtp.utils.JsonUtil;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SrtpApplicationTests {

    @Resource
    SearchService ss;

    @Resource
    InfoProcess ip;

    @Test
    public void test(){
        SearchInfo searchInfo = new SearchInfo();
        searchInfo.setTags(new String[]{"tag"});
        Point[] points = new Point[]{new Point(1.0,2.0), new Point(3.0,4.0)};
        searchInfo.setPoints(points);
        MessageBox messageBox = ss.processInfo(searchInfo);
        Point[] rsl = (Point[]) messageBox.getData();
        for (Point point : rsl) {
            System.out.println(point);
        }
        return;
    }

    @Test
    public void test2(){
        SearchInfo info = new SearchInfo(new String[]{"a"},new Point[]{new Point(1.1,2.1), new Point(2.1,3.2)});
        Point[] points = ip.getPoints(info);
        for (Point point : points) {
            System.out.println(point);
        }
    }

    @Test
    public void jacksontest(){
        //ObjectMapper mapper = new ObjectMapper();
        SearchInfo info = new SearchInfo(new String[]{"a","b"}, new Point[]{new Point(1.1,2.2), new Point(3.3,4.4)});
        String tojson = JsonUtil.toJsonString(info);
        SearchInfo dejson = JsonUtil.parse(tojson, SearchInfo.class);
//        try {
//            tojson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(info);
//            dejson = mapper.readValue(tojson, SearchInfo.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
        System.out.println(tojson);
        System.out.println(dejson);

    }


}
