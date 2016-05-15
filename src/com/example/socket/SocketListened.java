package com.example.socket;

import java.util.Vector;

public interface SocketListened {

    public void addSocketListener(SocketListener listener);
    public void deleteSocketListener(SocketListener listener);
    public void updateSocketListener(String str);

}
