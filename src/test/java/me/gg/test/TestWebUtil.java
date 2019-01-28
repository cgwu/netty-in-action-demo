package me.gg.test;

import me.gg.nettyinactiondemo.ch12websocket.WebUtil;
import org.junit.Test;

import java.util.Map;

/**
 * Created by danny on 2019/1/28.
 */
public class TestWebUtil {
    @Test
    public void testqueryStringToMap(){
        Map<String,String> map = WebUtil.queryStringToMap("/ws?id=1&pwd=2");
        System.out.println(map.get("id"));
        System.out.println(map.get("pwd"));
    }
}
