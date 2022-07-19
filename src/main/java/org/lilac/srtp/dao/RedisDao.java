package org.lilac.srtp.dao;

import org.lilac.srtp.pojo.LogInfo;
import org.lilac.srtp.pojo.Point;
import org.lilac.srtp.pojo.SearchInfo;
import org.lilac.srtp.utils.JedisPoolUtil;
import org.lilac.srtp.utils.JsonUtil;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RedisDao {

    public void addLog(SearchInfo info, String sessionId, Point[] rslPoints){
        JedisPool jedisPool = JedisPoolUtil.getPool();
        Jedis jedis = jedisPool.getResource();

        String key = "Log:" + sessionId;
        LogInfo logInfo = new LogInfo(info, rslPoints);

        //System.out.println(logInfo);

        jedis.lpush(key, JsonUtil.toJsonString(logInfo));
        jedis.expire(key, 60 * 60 * 1); // 默认过期时间1小时，考虑改为会话失效时就删除
        if(jedis.llen(key) > 10) jedis.rpop(key);

    }

    public List<LogInfo> getLog(String sessionId){
        JedisPool jedisPool = JedisPoolUtil.getPool();
        Jedis jedis = jedisPool.getResource();

        String key = "Log:" + sessionId;
        List<String> temp = jedis.lrange(key,0 , -1);
        List<LogInfo> infos = new ArrayList<>();
        for (String s : temp) {
            infos.add(JsonUtil.parse(s, LogInfo.class));
        }
        return infos;
    }
}
