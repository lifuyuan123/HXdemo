package azsecuer.zhuoxin.com.easeuidemo.adapter;

/**
 * Created by Administrator on 2017/3/5.
 */

public class Demo {
    String name,msg;

    public Demo(String name, String msg) {
        this.name = name;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Demo{" +
                "name='" + name + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
