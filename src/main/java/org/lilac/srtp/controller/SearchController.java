package org.lilac.srtp.controller;

import org.lilac.srtp.common.MessageBox;
import org.lilac.srtp.pojo.Point;
import org.lilac.srtp.pojo.SearchInfo;
import org.lilac.srtp.service.SearchService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
public class SearchController {

    @Resource
    SearchService searchService;

    @Resource
    RestTemplate restTemplate;

    /**
     * 点击搜索按钮的控制器动作，传入关键字和矩形对角坐标，返回搜索到矩形区域的对焦坐标
     * @param tags
     * @param points
     * @return
     */
    @GetMapping("/search")
    public MessageBox<?> searchPOI(@RequestParam("tags") String[] tags,
                                   @RequestParam("points") Double[] points,
                                   HttpServletRequest req){
        SearchInfo info = new SearchInfo();

        info.setTags(tags);

        Point left_up = new Point(points[0] , points[1]);
        Point right_down = new Point(points[2], points[3]);
        info.setPoints(new Point[]{left_up, right_down});

        MessageBox messageBox = searchService.processInfo(info);
        searchService.recordSearch(info, messageBox, req);
        return messageBox;
    }

    /**
     * 测试restTemplate post方法传参
     * @param info
     * @return
     */
    @PostMapping("/processTest")
    public Point[] processTest(@RequestBody SearchInfo info){
        Double p1lat = info.getPoints()[0].getLat();
        Double p1lng = info.getPoints()[0].getLng();
        Double p2lat = info.getPoints()[1].getLat();
        Double p2lng = info.getPoints()[1].getLng();
        Point[] returnPoints = new Point[2];
        returnPoints[0] = new Point();
        returnPoints[1] = new Point();
        System.out.println(returnPoints[0]==null);
        try {
            String[] args1= new String[]{"python","D:\\srtp\\ExactSFRS\\demo\\test.py",
                    String.valueOf(p1lng),String.valueOf(p2lng),String.valueOf(p1lat),String.valueOf(p2lat)};
            Process proc = Runtime.getRuntime().exec(args1);

            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            String res ="";
            while ((line = in.readLine()) != null){
                res+=line;
//                System.out.println(line);
            }
//            String res[] = line.split(" ");
//            System.out.println(res);
            in.close();
            int state = proc.waitFor();
            System.out.println(state);
            System.out.println(res);
            String p[] = res.split(" ");
//            for (String s : p) {
//                System.out.println(s);
//            }
            returnPoints[0].setLng(Double.valueOf(p[0]));
            returnPoints[0].setLat(Double.valueOf(p[2]));
            returnPoints[1].setLng(Double.valueOf(p[1]));
            returnPoints[1].setLat(Double.valueOf(p[3]));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return returnPoints;
    }

    /**
     * 测试前端与后端的通信
     * @param str
     * @return
     */
//    @GetMapping("/test")
//    public Map<String, String> test(String str){
//        System.out.println(str);
//        Map<String, String> map = new HashMap<>();
//        map.put("str", str);
//        return map;
//    }
//    @PostMapping("/postTest")
//    public MultiValueMap<String, String> postTest(@RequestBody MultiValueMap<String, String> map){
//
//        map.forEach((k,v)->{
//            System.out.println(k + " : " + v);
//        });
//
//        return map;
//    }
//    @GetMapping("/testRest")
//    public Map getForObject(){
//        String url = "http://localhost:8081/postTest";
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("abc","efg");
//        map.add("abc","efg2");
//        Map<String, String> rsl = restTemplate.postForObject(url, map, MultiValueMap.class);
//        return rsl;
//    }
}
