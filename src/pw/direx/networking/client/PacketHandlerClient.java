package pw.direx.networking.client;

import pw.direx.networking.packet.Packet;

public interface PacketHandlerClient {

	public void handlePacket(Packet packet);
	
}
