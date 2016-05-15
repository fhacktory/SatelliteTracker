package com.example.satellite;

public interface SkyListened {

    public void addListener(SkyListener listener);
    public void deleteListener(SkyListener listener);
    public void updateListener(Sky sky);

}
