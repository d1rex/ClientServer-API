package pw.direx.networking.server;

import java.net.Socket;

import pw.direx.networking.packet.Packet;

public interface PacketHandlerServer {
	
	public void handlePacket(Packet packet, Socket socket);

}
