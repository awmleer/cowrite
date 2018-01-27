package proto;

import java.io.Serializable;

public class Test implements Serializable {
    public String a;
    public String b;
    public Test(){
        a = "123";
        b = "abc";
    }
}