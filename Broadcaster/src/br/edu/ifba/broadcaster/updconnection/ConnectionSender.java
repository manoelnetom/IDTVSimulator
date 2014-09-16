package br.edu.ifba.broadcaster.updconnection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.UnknownHostException;
import br.edu.ifba.broadcaster.udpprotocol.ProtocolPacket;

/**
 *
 * @author Thiago
 */
public class ConnectionSender extends Connection implements IConnectionSender<ProtocolPacket> {

    public ConnectionSender(String ip, int port) throws SocketException, UnknownHostException {

        super(ip, port);
    }

    @Override
    public void sendPacket(ProtocolPacket packet) throws IOException {

        DatagramPacket datagram = super.getDatagramPacket(packet.toByteArray());
        super.getSocket().send(datagram);
    }
}
