package cn.yali.redis.test;

import cn.yali.redis.RedisClient;
import cn.yali.redis.exception.RedisException;
import cn.yali.redis.factory.RedisClientFactory;
import cn.yali.redis.utils.SerializeUtil;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by Sterling on 2019/4/4.
 */
public class Tester {

    public static void main(String[] args) throws RedisException {
        RedisClient redis = RedisClientFactory.getInstance();
        String str = null;
        //log(""+redis.getObject("test", Tttt.class));
        //Tttt tttt = new Tttt();
        //log(""+tttt);
        //log(""+redis.set("test", tttt));
        //Tttt t2 = redis.getObject("test", Tttt.class);
        log(""+redis.getObject("test", Tttt.class));
    }

    public static void log(String str) {
        System.out.println(str);
    }
}

class Tttt implements Serializable{
    private String name = "fdfd";
    private String text = "text";

    public String toString() {
        return "{'name':'fdfd','text':'text'}";
    }
}
