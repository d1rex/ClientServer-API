package pw.direx.networking.threads.server;

import java.io.ObjectInputStream;

import pw.direx.networking.packet.Packet;
import pw.direx.networking.server.PacketHandlerServer;
import pw.direx.networking.server.Server;
import pw.direx.networking.server.User;

public class ServerThreadPacketReader extends Thread {
	
	@Override
	public void run() 
	{
		while(true)
		{
			try
			{
				for(User user : Server.getInstance().getConnectedClients())
				{
					ObjectInputStream objectInputStream = new ObjectInputStream(user.getSocket().getInputStream());
					Object object = objectInputStream.readObject();
					if(object instanceof Packet)
					{
						PacketHandlerServer packetHandlerServer = Server.getInstance().getPacketHandlers().get(((Packet) object).getIdentifier());
						if(packetHandlerServer != null)
						{
							System.out.println("(Networking Client) Packet with identifier '" + ((Packet) object).getIdentifier() + "' parsed on to '" + packetHandlerServer.getClass().getName() + "'");
						}
					}
				}
			}
			catch (Exception e) {}
		}
	}
}
