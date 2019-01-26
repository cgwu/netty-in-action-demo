package me.gg.test;

import com.alibaba.fastjson.JSON;
import me.gg.nettyinactiondemo.getstarted.UnixTime;
import org.junit.Test;
import org.msgpack.MessagePack;
import org.msgpack.template.Templates;
import org.msgpack.type.Value;
import org.msgpack.unpacker.Converter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 2019/1/26.
 * ref: https://msgpack.org/
 */
public class TestMsgpack {
    @Test
    public void test() throws Exception {
        // Create serialize objects.
        List<String> src = new ArrayList<String>();
        src.add("中msgpack");
        src.add("国kumofs");
        src.add("人viver");

        MessagePack msgpack = new MessagePack();

        // Serialize
        byte[] raw = msgpack.write(src);
        System.out.println("bytes length: "+raw.length);


        // Deserialize directly using a template
        List<String> dst1 = msgpack.read(raw, Templates.tList(Templates.TString));
        System.out.println(dst1.get(0));
        System.out.println(dst1.get(1));
        System.out.println(dst1.get(2));

        System.out.println("---");

        // Or, Deserialze to Value then convert type.
        Value dynamic = msgpack.read(raw);
        List<String> dst2 = new Converter(dynamic)
                .read(Templates.tList(Templates.TString));
        System.out.println(dst2.get(0));
        System.out.println(dst2.get(1));
        System.out.println(dst2.get(2));
        System.out.println("---");

        String json =  JSON.toJSONString(src);
        System.out.println(json);
        System.out.println(json.getBytes("UTF-8").length);
    }

    @Test
    public void testUnixTime() throws Exception {
        UnixTime t1 = new UnixTime();
        t1.setLabel("ab中");

        MessagePack msgpack = new MessagePack();
        // Serialize
        byte[] raw = msgpack.write(t1);
        System.out.println("bytes length: "+raw.length);
        System.out.println(t1);

        // Deserialize directly using a template

        UnixTime t2 = msgpack.read(raw, UnixTime.class);
        System.out.println(t2);
    }
}
