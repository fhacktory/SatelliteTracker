package com.example.socket;

import java.util.ArrayList;
import java.util.Vector;
import java.net.*;
import java.io.*;

import android.util.Log;

/**
 * @author francois
 * Singleton socket connection to setup a client or server connection.
 */
public class ConnectionSocket implements SocketListened {

    private static ConnectionSocket instance;
    
    private ServerSocket server;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Receive receive;
    private InetAddress adresse;
    
    private String ip;
    private int port;

    private ArrayList<SocketListener> list = new ArrayList<SocketListener>();
    
    private boolean connect = false;
    private String message, str;
    
    
    /**
     * Intance a client connection by server's ip and port
     * @param ip ip to connect
     * @param port port to connect
     */
     private ConnectionSocket(String ip, int port) {
    	 this.ip = ip;
    	 this.port = port;
    }

    /**
     * Intance a server connection on a port
     * @param port port to listen
     */
    private ConnectionSocket(int port) {
        try{
            server = new ServerSocket(port);
            socket = server.accept();
            connect =  true;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            receive = new Receive();
            receive.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        //System.out.println("[ConnectionSocket] Server run");
    }
    
    /**
     * return a Singleton instance of client connection
     * @return singleton instance
     */
    public static ConnectionSocket getInstance(String ip, int port) {
        if (instance == null)
            instance = new ConnectionSocket(ip, port);
        return instance;
    }

    /**
     * return a Singleton instance of server connection
     * @return singleton instance
     */
    public static ConnectionSocket getInstance(int port) {
        if (instance == null)
            instance = new ConnectionSocket(port);
        return instance;
    }
    
    /**
     * return a Singleton instance of server connection
     * @return singleton instance
     */
    public static ConnectionSocket getInstance() {
        if (instance != null)
            return instance;
        else
        	return null;
    }
    
    public boolean connect() {
    	if(ip == null)
    		return false;
    	
    	if(connect)
    		return true;
    	
    	try {
            socket = new Socket(ip, port);
            connect =  true;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            receive = new Receive();
            receive.start();
            Log.v("Rspi", "Client run");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    	
    	return true;
    }

    
    /**
     * Don't use it. please use the SocketListener interface to listen socket receive
     * @return the current message received
     */
    public String receive() {
        return message;
    }
    
    
    /**
     * Send a message to the client/server connection
     * @param str message to send
     */
    public void send(String str) {
        
         out.print(str);
         out.flush();
        
    }
        
    
    /**
     * return the current ip, where server listen
     * @return ip server
     */
    public String getIP() {
        try {
            adresse = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
           ex.printStackTrace();
        }
        String a =  adresse.toString();
        int i = 0;
        while(a.charAt(i) !='/') {
            i++;
        }
        a = a.substring(i+1, a.length());
        
        return a;
    }
    
    /**
     * @return connection's status
     */
    public boolean isConnecting() {
        return connect;
    }
    
    
    
    
    /**
     * Close the connection
     */
    public void close() {
        try {
            connect = false;
            socket.close();
            if (server != null)
            	server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
        	// Wait 1/2s the end o receive thread
        	Thread.currentThread().sleep(500);
        } catch (Exception e) {
        	e.printStackTrace();
        }
   }
    
    /**
     * add listener to call back
     */
    @Override 
    public void addSocketListener(SocketListener listener) {
	    list.add(listener);
    }

    /**
     * delete listener to call back
     */
    @Override
    public void deleteSocketListener(SocketListener listener)  {
	    list.remove(listener);
    }

    
    @Override
    public void updateSocketListener(String str) {
    	for(SocketListener listener : list)
    		listener.updateSocketListener(str);
    }
        
    
    /**
     * Thread wich listen new message endlessly
     */
    class Receive extends Thread {
        
        public Receive() {
            
        }
        
        public void run() {
        
           while(connect) {
            	try {
               	   str = in.readLine();
            	} catch (IOException e) {
                   e.printStackTrace();
                }
                if(str != null)
                	updateSocketListener(str);
                
	        }
            
        }
        
    }

    
}
