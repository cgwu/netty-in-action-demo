package me.gg.nettyinactiondemo.ch12websocket;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by danny on 2019/1/28.
 */
@Slf4j
public abstract class WebUtil {
    public static Map<String,String> queryStringToMap(String query) {
        if (query.indexOf('?') > 0) {
            query = query.substring(query.indexOf("?") + 1);
//            log.debug(query);
        }
        Map<String, String> map = new HashMap<>();
        String[] args = query.split("&");
        for (String arg : args) {
            String[] kv = arg.split("=");
            map.put(kv[0], kv[1]);
        }
        return map;
    }

    public static String getSingleParam(String queryString, String key){
        Map<String,String> map = queryStringToMap(queryString);
        return map.getOrDefault(key,"");
    }

}
