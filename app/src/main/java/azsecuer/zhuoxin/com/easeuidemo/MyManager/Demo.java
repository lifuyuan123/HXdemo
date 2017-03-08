package azsecuer.zhuoxin.com.easeuidemo.MyManager;

/**
 * Created by Administrator on 2017/3/6.
 */

public class Demo {
    String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Demo(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Demo{" +
                "name='" + name + '\'' +
                '}';
    }
}
