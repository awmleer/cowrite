package proto;

import java.io.Serializable;

public class SocketData<T> implements Serializable {
    private String meta;
    private T data;

    private SocketData(){}

    public SocketData(String meta, T data){
        this.meta=meta;
        this.data=data;
    }

    public String getMeta(){
        return meta;
    }
    public T getData(){
        return data;
    }
}
