/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba.broadcaster.updconnection;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author Thiago
 */
public class Connection {

    private DatagramSocket socket;   
    private InetAddress group;
    private String ip;
    private int port;

    public Connection(String ip, int port) throws SocketException, UnknownHostException {

        this.ip = ip;
        this.port = port;

        this.socket = new DatagramSocket();
        this.group = InetAddress.getByName(ip);
    }
    
    public DatagramPacket getDatagramPacket( byte[] buffer ) {
        
        return new DatagramPacket(buffer, buffer.length, group, port);
    }
    
     public InetAddress getGroup() {
        return group;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public DatagramSocket getSocket() {
        return socket;
    }
}
