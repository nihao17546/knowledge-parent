package com.nihaov.knowledge.service.impl;

import com.nihaov.knowledge.service.IRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by nihao on 18/4/27.
 */
@Service
public class RedisServiceImpl implements IRedisService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String redisCode = "utf-8";

    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String get(String key) {
        return redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    byte[] result=connection.get(key.getBytes());
                    if(result==null)
                        return null;
                    return new String(result, redisCode);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    @Override
    public List<String> keys(String pattern) {
        return redisTemplate.execute(new RedisCallback<List<String>>() {
            @Override
            public List<String> doInRedis(RedisConnection redisConnection) throws DataAccessException {
                Set<byte[]> set = redisConnection.keys(pattern.getBytes());
                List<String> se = new ArrayList<String>();
                if(set!=null){
                    for(byte[] by:set){
                        if(by!=null){
                            se.add(new String(by));
                        }
                    }
                }
                return se;
            }
        });
    }

    @Override
    public Map<String, String> mulGet(String pattern) {
        Map<String,String> result = new LinkedHashMap<>();
        Set<byte[]> set = redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.keys(pattern.getBytes());
            }
        });
        if(!set.isEmpty()){
            List<String> keyList = new ArrayList<>();
            final byte[][] key = new byte[set.size()][];
            int i = 0;
            for(byte[] by:set){
                key[i++] = by;
                keyList.add(new String(by));
            }
            List<String> valueList = redisTemplate.execute(new RedisCallback<List<String>>() {
                @Override
                public List<String> doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    List<byte[]> li = redisConnection.mGet(key);
                    List<String> re = new ArrayList<>();
                    for(int s=0;s<li.size();s++){
                        byte[] by = li.get(s);
                        if(by==null){
                            re.add(null);
                        }
                        else{
                            re.add(new String(by));
                        }
                    }
                    return re;
                }
            });
            for(int s=0;s<keyList.size();s++){
                String k = keyList.get(s);
                String v = valueList.get(s);
                result.put(k,v);
            }
        }
        return result;
    }

    @Override
    public Long incr(String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.incr(key.getBytes());
            }
        });
    }
}
