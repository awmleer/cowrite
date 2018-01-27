package proto;

import java.io.Serializable;


public class SocketData<T> extends SocketDataBase {
    private T data;

    private SocketData(){}

    public SocketData(String meta, T data){
        this.meta=meta;
        this.data=data;
    }

    public T getData(){
        return data;
    }
}
